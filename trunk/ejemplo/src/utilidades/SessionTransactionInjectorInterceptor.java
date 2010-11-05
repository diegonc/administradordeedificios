package utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.exception.JDBCConnectionException;

import com.googlecode.s2hibernate.lang.SessionInjectionException;
import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;
import com.googlecode.s2hibernate.struts2.plugin.interceptors.GenericInterceptor;
import com.googlecode.s2hibernate.struts2.plugin.util.Constants;
import com.googlecode.s2hibernate.struts2.plugin.util.HibernatePluginUtils;
import com.googlecode.s2hibernate.struts2.plugin.util.HibernateSessionFactory;
import com.googlecode.s2hibernate.struts2.plugin.util.SessionInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.inject.Inject;


/**
 * Interceptor for Hibernate Session and Transaction Injection
 *
 * <br/><br/>
 * 
 * Interceptor para inje��o da Sess�o Hibernate e da Transa��o
 *
 * @author Jose Yoshiriro - jyoshiriro@gmail.com
 *
 */
@SuppressWarnings(value={"rawtypes","unchecked"})
public class SessionTransactionInjectorInterceptor extends GenericInterceptor implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2222692750520221708L;

	private String sessionTarget;
			
	private String transactionTarget;
	
	private String customSessionFactoryClass;
	
	private String getSessionMethod;
	
	private String staticGetSessionMethod = "true";
	
	private String closeSessionAfterInvoke = "true";

	private String configurationFiles = HibernateSessionFactory.DEFAULT_HIBERATE_CONFIGFILE;

	private String closeSessionMethod;

	private boolean useSessionObjectInCloseMethod = true;

	private String sourceSessionFactory = null;

	private static Set<String> excludedPackages;
	
	private boolean searchInsideJars = false;

	private HttpServletRequest request; 
	
	private static Set<Class> actionsWithoutInjection = new HashSet<Class>();
	
	static {
		excludedPackages = new LinkedHashSet<String>();
		excludedPackages.add("org.apache.");
		excludedPackages.add("org.hibernate.");
		excludedPackages.add("com.opensymphony.");
		excludedPackages.add("org.springframework.");
		excludedPackages.add("org.jboss.");
		excludedPackages.add("java.");
		excludedPackages.add("javax.");
		excludedPackages.add("sun.");
		excludedPackages.add("com.sun.");
		excludedPackages.add("javassist.");
	}
	
	private static void markMappgingNoInjection(Class actionClass) {
		actionsWithoutInjection.add(actionClass);
		log.debug("Action "+actionClass.getName()+" makerd as permanent for no Session/Transaction Injection into Injection Cache");
	}
	
	private static boolean isNotMappedAction(Class actionClass) {
		boolean notmapped = actionsWithoutInjection.contains(actionClass);
		if (notmapped) {
			log.debug("Action "+actionClass.getName()+" has no candidate object for Session/Transaction Injection according the Injection Cache");
		}
		return notmapped;
	}
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		readedObjectsByRequest.put(ServletActionContext.getRequest(),new HashSet());
		
		HibernateSessionFactory.finishUnitOfWork();
		
		Object action = invocation.getAction();

		String namespaceName = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String methodName = invocation.getProxy().getMethod();
		StringBuilder sbMapping = new StringBuilder(namespaceName);
		if (!namespaceName.equals("/"))
			sbMapping.append("/");
		sbMapping.append(actionName);
		sbMapping.append(" - Method: ");
		sbMapping.append(action.getClass().getName());
		sbMapping.append(".");
		sbMapping.append(methodName);
		sbMapping.append("()");
		
		if (action.getClass().equals(ActionSupport.class)) {
			log.warn("Action class \""+action.getClass().getName()+
					"\" from mapping \""+namespaceName+actionName+"\" does not extends \""+
					ActionSupport.class.getName()+"\". " +
					"Hibernate Session and Transaction will not be injected!");
			return invocation.invoke();
		}
		
		Set<Session> injectedSessions = new LinkedHashSet<Session>();
		
		StringBuilder sbErrorMessage = new StringBuilder("Error! Please, check your JDBC/JDNI Configurations and Database Server avaliability. \n ");
		sbErrorMessage.append("at "+sbMapping.toString()+"\n ");
		
		if (!isNotMappedAction(action.getClass())) {
		
			log.debug("Preparing Injection Hibernate Session and Transaction process: "+sbMapping.toString());
			
			List<Field> fields = getFieldsFromAction(invocation.getAction());
			
			
			boolean sessionNotInjected = false;
			
			try {
				if (sessionTarget!=null && (!sessionTarget.equals(""))) {
					injectedSessions = injectHibernateSessionByConfiguration(fields, invocation);
				}
				
				int countOfInjectedSessionsByConfiguration = injectedSessions.size();
				
				injectHibernateCoreSessionByAnnotation(action, injectedSessions);
				int countOfInjectedSessionsByAnnotation = injectedSessions.size();
				
				sessionNotInjected = (countOfInjectedSessionsByAnnotation==countOfInjectedSessionsByConfiguration);
				
				if (sessionNotInjected) {
					log.debug("No target setted for Hibernate Session object at "+sbMapping.toString()+". Use the *"+Constants.HIBERNATEPLUGIN_SESSIONTARGET+"* property or the SessionTarget Annotation. " +
							"If any annotated Session object be found, will be closed after the request.");
					markMappgingNoInjection(action.getClass());
				}
				
			} catch (Exception e) {
				sbErrorMessage.append("Could not open or inject a Hibernate Session in ValueStack: ");
				sbErrorMessage.append(e.getMessage());
				String message = sbErrorMessage.toString();
				System.err.println(message);
				e.printStackTrace();
				throw new SessionInjectionException(message);
			}
			
			if (!sessionNotInjected) {
				try {
					if (transactionTarget!=null && (!transactionTarget.equals(""))) {
						injectHibernateTransactionByConfiguration(fields, injectedSessions, invocation);
					}
					injectHibernateTransactionByAnnotation(action, injectedSessions.iterator().next(), false);
				} catch (Exception e) {
					sbErrorMessage.append("Could not open or put a Hibernate Transaction in ValueStack: ");
					sbErrorMessage.append(e.getMessage());
					String message = sbErrorMessage.toString();
					System.err.println(message);
					e.printStackTrace();
					throw new TransactionException(message);
				}
			}
		
		}
		
		String returnName = "";
		try {
//			ServletActionContext.getRequest().setAttribute("ss", injectedSessions.iterator().next().hashCode());
			returnName = invocation.invoke();
			
			for (Session hibernateSession:injectedSessions) {
				if (!hibernateSession.isOpen())
					continue;
				
				Transaction hibernateTransation = hibernateSession.getTransaction();
				
				try {
					commitHibernateTransaction(hibernateTransation);
					closeHibernateSession(hibernateSession);
					log.debug("Hibernate Transaction Committed");
				} 
				catch (Exception e) {
					hibernateSession.clear();
					sbErrorMessage.append("Could not commit the Hibernate Transaction: ");
					sbErrorMessage.append(e.getMessage());
					String message = sbErrorMessage.toString();
					System.err.println(message);
					closeHibernateSession(hibernateSession);
					detectAndCloseHibernateCoreSessionCreatedLater(action);
					e.printStackTrace();
					throw new SessionInjectionException(message);
				}
		
			}

			detectAndCommitHibernateSessionCreatedLater(action);
			detectAndCloseHibernateCoreSessionCreatedLater(action);
			
			log.debug("Injection Hibernate Session and Transaction process for "+sbMapping.toString()+" finished");
			
			
		} catch (Exception e) {
			if ((e instanceof JDBCConnectionException) || ( (e.getCause()!=null) && (e.getCause() instanceof JDBCConnectionException) ) ) {
				if ( (customSessionFactoryClass==null) || (customSessionFactoryClass.equals("plugin")) ) {
					if (HibernateSessionFactory.isAutoRebuidOnJdbcConnectionError()) {
						log.debug("Full Hibernate Plugin's Session Factory marked for auto rebuild on JDBCConnectionException");
						HibernateSessionFactory.rebuildSessionFactory();
					}
				}
			}
			detectAndRollbackHibernateTransactionCreatedLater(action);
			detectAndCloseHibernateCoreSessionCreatedLater(action);
			throw e;
		}
		return returnName ;
		
	}

	private void commitHibernateTransaction(Transaction hibernateTransation) {
		if (hibernateTransation==null)
			return;
		
		if ( (hibernateTransation.isActive()) && (!hibernateTransation.wasCommitted()) && (!hibernateTransation.wasRolledBack())) {
			hibernateTransation.commit();
			log.debug("Hibernate Transation  "+hibernateTransation+" committed by Full Hibernate Plugin");
		}
	}

	private void closeHibernateSession(Session hibernateSession) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if ( (hibernateSession==null) || (!hibernateSession.isOpen()) )
			return;
		if (isCloseSessionAfterInvoke()) {
			// If using the Plugin's Session Factory
			if ( (customSessionFactoryClass==null) || (customSessionFactoryClass.equalsIgnoreCase("plugin")) ) {
				HibernateSessionFactory.closeSession(hibernateSession);
				log.debug("Hibernate Session closed by Full Hibernate Plugin's Hibernate Session Factory");
			}
			else {
				// Using a custom Session Factory Class
				Object sessionFactory = null;
				sessionFactory = Class.forName(customSessionFactoryClass, false, this.getClass().getClassLoader());
				if (useSessionObjectInCloseMethod) {
					Method method = Class.forName(customSessionFactoryClass).getDeclaredMethod(closeSessionMethod, Session.class);
					method.invoke(sessionFactory, hibernateSession);
				}
				else {
					Method method = Class.forName(customSessionFactoryClass).getDeclaredMethod(closeSessionMethod, null);
					method.invoke(sessionFactory, null);
				}
				log.debug("Hibernate Session closed by custom Hibernate Session Factory ("+sessionFactory.getClass().getName()+")");
			}
		} else {
			//TODO: verificar se o Hibernate Manager est� Habilitado antes de por as sess�es no contexto de aplica��o para economizar mem�ria!
			Set<SessionInfo> hibernateSessions = (Set<SessionInfo>) ActionContext.getContext().getApplication().get("struts2HibernatePlugin_Sessions");
			if (hibernateSessions==null)
				hibernateSessions = new LinkedHashSet<SessionInfo>();
			SessionInfo info = new SessionInfo(hibernateSession,new Date(),request.getSession());
			hibernateSessions.add(info);
			ActionContext.getContext().getApplication().put("struts2HibernatePlugin_Sessions", hibernateSessions);
		}		
	}

	private Set<Session> injectHibernateSessionByConfiguration(List<Field> fields, ActionInvocation invocation) throws Exception {
		Set<Session> sessions = new LinkedHashSet<Session>();
		for (Field field:fields) {
			String[] sessionTargets = sessionTarget.split(",");
			String[] sessionFactoryTargets = sourceSessionFactory.split(",");
			for (int i=0;i<sessionTargets.length;i++) {
				String sessionTarget = sessionTargets[i];
				String trueTarget = "";
				Session hibernateSession = null;
				String[] targetAsArray = sessionTarget.replace(".", ",").split(",");
				
				if (!Pattern.matches(HibernatePluginUtils.wildcardToRegex(targetAsArray[0]), field.getName())) 
					continue;
				
				targetAsArray[0] = field.getName();
				trueTarget = Arrays.toString(targetAsArray).replace("[", "").replace("]", "").replace(", ",".");
				
				if (hibernateSession==null) {
					String sourceSessionFactory = sessionFactoryTargets[i];
					hibernateSession = getHibernateSessionFromFactory(sourceSessionFactory);
				}
				invocation.getStack().setValue(trueTarget, hibernateSession, true);
				sessions.add(hibernateSession);
				log.debug("Hibernate Session injected (by configuration) into Action. Field \""+trueTarget+"\"");				
			}
		}
		return sessions;
	}

	/**
	 * Transactions injected using the same order of the Session objects
	 * @param fields
	 * @param sessions
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	private Set<HibernateSessionTransactionInfo> injectHibernateTransactionByConfiguration(List<Field> fields,
			Set<Session> sessions, ActionInvocation invocation) throws Exception {
		
		Set<HibernateSessionTransactionInfo> infos = new LinkedHashSet<HibernateSessionTransactionInfo>();
		for (Field field:fields) {			
			String trueTarget = "";
			Transaction transaction = null;
			String[] transactionTargets = transactionTarget.split(",");
			for (String transactionTarget : transactionTargets) {
			String[] targetAsArray = transactionTarget.replace(".", ",").split(",");
			
			if (!Pattern.matches(HibernatePluginUtils.wildcardToRegex(targetAsArray[0]), field.getName())) 
				continue;
			
			targetAsArray[0] = field.getName();
			trueTarget = Arrays.toString(targetAsArray).replace("[", "").replace("]", "").replace(", ",".");
			
			for (Session session:sessions) {
				transaction = session.beginTransaction();
			}
			
			invocation.getStack().setValue(trueTarget, transaction);
			HibernateSessionTransactionInfo transactionInfo = new HibernateSessionTransactionInfo();
			transactionInfo.setTransactionObject(transaction);
			transactionInfo.setTransactionTarget(trueTarget);
			infos.add(transactionInfo);
			log.debug("Hibernate Transaction injected (by configuration) into Action. Field \""+trueTarget+"\"");
			}
		}
		
		return infos;
	}
	
	private Session getHibernateSessionFromFactory(String sessionFactoryName) throws Exception {
		Session hibernateSession = null;
		// Using the PLUGIN Session Factory Class
		if ( (customSessionFactoryClass==null) || (customSessionFactoryClass.equalsIgnoreCase("plugin")) ) {
			if (!HibernateSessionFactory.hasSessionFactories()) {
				HibernateSessionFactory.setConfigFiles(configurationFiles);
			}
			hibernateSession  = HibernateSessionFactory.getSession(sessionFactoryName);
			log.debug("Hibernate Session from Full Hibernate Plugin's Hibernate Session Factory");
		} 
		else {
		// Using a custom Session Factory Class
			Object sessionFactory = null;
			if (isStaticGetSessionMethod())
				sessionFactory = Class.forName(customSessionFactoryClass, false, this.getClass().getClassLoader());
			else
				sessionFactory = Class.forName(customSessionFactoryClass).newInstance();				
			Method method = Class.forName(customSessionFactoryClass).getDeclaredMethod(getSessionMethod, null);
			hibernateSession = (Session) method.invoke(sessionFactory, null);
			log.debug("Hibernate Session from custom Hibernate Session Factory ("+sessionFactory.getClass().getName()+")");
		}
		return hibernateSession;
	}
	
	
	/**
	 * Tests if the class is not a class from JDK or commons Libraries like Hibernate, XWork, Apache Commons, JBoss and Spring<br/>
	 * Enums, annotations never are "candidate classes"<br/>
	 * if <b>hibernatePlugin.searchMappingsInsideJars</b> property is "true", class inside *jar* files will be searched.  
	 * @param testClass
	 * @return
	 */
	private boolean isCandidadeClass(Class testClass) {
		if (!searchInsideJars) {
			if ( (testClass.getResource("")==null) || (testClass.getResource("").getProtocol().equals("jar")) ) {
				return false;
			}
		}
		if (testClass.isEnum())
			return false;
		if (testClass.isAnnotation())
			return false;
		if ( (testClass==null) || (testClass.getPackage()==null) || (testClass.equals(Object.class)) )
			return false;

		String testPackage = testClass.getPackage().getName();
		for (String excludedPackage : excludedPackages) {
			if (testPackage.startsWith(excludedPackage))
				return false;
		}
		return true;
	}

	/**
	 * Inject the current Hibernate Session object into annotated fields.
	 * @param targetObject the target object where the {@link @SessionTarget} will be searched
	 * @param sessions Set of Hibernate Sessions to be used
	 * @throws Exception 
	 */
	private void injectHibernateCoreSessionByAnnotation(Object targetObject,
			Set<Session> sessions) throws Exception {
		Class testClass = targetObject.getClass();
		Class actionClass = ActionContext.getContext().getActionInvocation().getAction().getClass();
		while (isCandidadeClass(testClass)) {
			Field[] campos = testClass.getDeclaredFields();
			
			for (Field campo : campos) {
				if (campo.isEnumConstant())
					continue;
				// test for "singletons"
				campo.setAccessible(true);
				Object fieldValue = campo.get(targetObject);
				
				if ( (fieldValue!=null) && (campo.get(targetObject).equals(targetObject)) )
					continue;

				isCandidateToInjectionCache(actionClass,campo.getName());
				
				if (campo.getType().getName().equals(Session.class.getName())) {
					
					if (campo.isAnnotationPresent(SessionTarget.class)) {
						String sessionFactoryName = campo.getAnnotation(SessionTarget.class).value();
						Session hibernateSession = getHibernateSessionFromFactory(sessionFactoryName);
						String setterName = getSetterName(campo.getName());
						try {
							Method setterMethod = targetObject.getClass().getMethod(setterName, Session.class);
							setterMethod.invoke(targetObject, hibernateSession);
						} catch (Exception e) {
							campo.set(targetObject, hibernateSession);
						}
						debugInfoSessionInjectedByAnnotation(campo,testClass);
//						System.out.println(campo.getName()+" "+campo.getDeclaringClass());
						sessions.add(hibernateSession);
					}
				} else {
					campo.setAccessible(true);
					Object subField = campo.get(targetObject);
					if ( (subField!=null) && (isCandidadeClass(campo.getType()) ))
						injectHibernateCoreSessionByAnnotation(subField, sessions);
				}
			}
			
			testClass = testClass.getSuperclass();
		}

	}
	
	/**
	 * For future cache implementation
	 */
	@SuppressWarnings({ "unused" })
	private static HashMap<Class, Set<String>> cacheInjectionMap = new HashMap<Class, Set<String>>();

	/**
	 * Not implemented 
	 * @param actionClass
	 * @param name
	 */
	private void isCandidateToInjectionCache(Class actionClass, String name) {
		/*ValueStack valueStack = ActionContext.getContext().getValueStack();
		
		// tenta obter o Set de valores de valuestack da action
		Set<String> candidates = cacheInjectionMap.get(actionClass);
		if (candidates==null)
			candidates = new HashSet<String>();

		// procura pela chave de valuestack possivel 
		if (valueStack.findValue(name)!=null)
			candidates.add(name);
		for (String candidate : candidates) {
			if (valueStack.findValue(candidate+"."+name)!=null)
				candidates.add(name);
		}
		cacheInjectionMap.put(actionClass, candidates);*/
	}

	private void debugInfoSessionInjectedByAnnotation(Field campo, Class testClass) {
		log.debug("Hibernate Session injected (by annotation) into Action. Field \""+campo.getName()+"\". Class \""+testClass.getName()+"\"");
	}

		/**
	 * Returns <b>true</b> if was found at least 1 annotated target for the Transaction in the ValueStack. Returns <b>false</b> instead.
	 * @param targetObject
	 * @param defaultSession - Default Hibernate Session Object for the annotated transaction object (if no value used in the @TransactionTarget) 
		 * @param hibernateTransaction
	 * @param foundTarget 
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException
		 * @throws NoSuchFieldException 
	 */
	private boolean injectHibernateTransactionByAnnotation(Object targetObject,
			Session defaultSession, boolean foundTarget) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchFieldException {
		Class testClass = targetObject.getClass();
		while (isCandidadeClass(testClass)) {
			Field[] campos = testClass.getDeclaredFields();
			
			for (Field campo : campos) {
				if (campo.isEnumConstant())
					continue;
				// test for "singletons"
				campo.setAccessible(true);
				Object fieldValue = campo.get(targetObject);
				
				if ( (fieldValue!=null) && (campo.get(targetObject).equals(targetObject)) )
					continue;
				
				if (campo.getType().getName().equals(Transaction.class.getName())) {
					if (campo.isAnnotationPresent(TransactionTarget.class)) {
						
						String sessionName = campo.getAnnotation(TransactionTarget.class).value();
						Transaction hibernateTransaction = null;
						if ((sessionName==null) || (sessionName.equals("")) ) {
							hibernateTransaction = defaultSession.getTransaction();
						} else {
							Session sessionObject = null;
							try {
								String sessionGetterName = getGetterName(sessionName);
								Method sessionGetterMethod = targetObject.getClass().getDeclaredMethod(sessionGetterName, Session.class);
								sessionObject =  (Session) sessionGetterMethod.invoke(targetObject);
							} catch (NoSuchMethodException e) {
								Field sessionField = targetObject.getClass().getDeclaredField(sessionName);
								sessionField.setAccessible(true);
								sessionObject = (Session) sessionField.get(targetObject);
							}
							hibernateTransaction = sessionObject.getTransaction();

						}
						
						String setterName = getSetterName(campo.getName());
						try {
							Method setterMethod = targetObject.getClass().getDeclaredMethod(setterName, Transaction.class);
							setterMethod.invoke(targetObject, hibernateTransaction);
							foundTarget = true;
							debugInfoTransactionInjectedByAnnotation(campo, testClass);
						} catch (NoSuchMethodException e) {
							campo.setAccessible(true);
							campo.set(targetObject, hibernateTransaction);
							foundTarget = true;
							debugInfoTransactionInjectedByAnnotation(campo, testClass);
						}
						if (!hibernateTransaction.isActive())
							hibernateTransaction.begin();
					}
				} else {
					campo.setAccessible(true);
					Object subField = campo.get(targetObject);
					if (subField!=null) {
						if (isCandidadeClass(campo.getType())) {
							if (injectHibernateTransactionByAnnotation(subField, defaultSession, foundTarget))
								foundTarget = true;
						}	
					}
				}
			}
			
			testClass = testClass.getSuperclass();
		}

		return foundTarget;
	}
	

	private void debugInfoTransactionInjectedByAnnotation(Field campo, Class testClass) {
		log.debug("Hibernate Transaction injected (by annotation) into Action. Field \""+campo.getName()+"\". Class \""+testClass.getName()+"\"");
	}
	

	private synchronized void detectAndCloseHibernateCoreSessionCreatedLater(Object targetObject) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		
		if (readedObjectsByRequest.get(ServletActionContext.getRequest()).contains(targetObject))
			return;
		readedObjectsByRequest.get(ServletActionContext.getRequest()).add(targetObject);
		
		Class testClass = targetObject.getClass();
		while (isCandidadeClass(testClass)) {
			Field[] campos = testClass.getDeclaredFields();
			
			for (Field campo : campos) {
				
				if (campo.isEnumConstant())
					continue;
				
				try {
					campo.setAccessible(true);
					if ((campo.get(targetObject).equals(targetObject)) )
						continue;
				} catch (LazyInitializationException e) {
					e.printStackTrace();
					continue;
				} 
				
				if (campo.getType().getName().equals(Session.class.getName())) {
					if (campo.isAnnotationPresent(SessionTarget.class)) {
						campo.setAccessible(true);
						Session hibernateSession = (Session) campo.get(targetObject);
						closeHibernateSession(hibernateSession);
					}
				} else {
					campo.setAccessible(true);
					Object subField = campo.get(targetObject);
					if (subField!=null) {
						detectAndCloseHibernateCoreSessionCreatedLater(subField);
					}
				}
			}
			
			testClass = testClass.getSuperclass();
		}
	}
	
	private synchronized void detectAndCommitHibernateSessionCreatedLater(Object targetObject) throws Exception {
		try {
			if (readedObjectsByRequest.get(ServletActionContext.getRequest()).contains(targetObject))
				return;
			readedObjectsByRequest.get(ServletActionContext.getRequest()).add(targetObject);
			Class testClass = targetObject.getClass();
			while (isCandidadeClass(testClass)) {
				Field[] campos = testClass.getDeclaredFields();
				
				for (Field campo : campos) {
					
					if (campo.isEnumConstant())
						continue;
					
					try {
						campo.setAccessible(true);
						Object fieldValue = campo.get(targetObject);
						if ( fieldValue == null || targetObject.equals(fieldValue) )
							continue;
					} catch (LazyInitializationException e) {
						e.printStackTrace();
						continue;
					} 
					
					if (campo.getType().getName().equals(Session.class.getName())) {
						if (campo.isAnnotationPresent(SessionTarget.class)) {
							campo.setAccessible(true);
							Session hibernateSession = (Session) campo.get(targetObject);
							commitHibernateTransaction(hibernateSession.getTransaction());
							closeHibernateSession(hibernateSession);
						}
					} else {
						campo.setAccessible(true);
						Object subField = campo.get(targetObject);
						if (subField!=null) {
							detectAndCommitHibernateSessionCreatedLater(subField);
						}
					}
				}
				
				testClass = testClass.getSuperclass();
			}
		} catch (LazyInitializationException e) {
			e.printStackTrace();
			return;
		}
			catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private static Map<HttpServletRequest, Set> readedObjectsByRequest = new HashMap<HttpServletRequest, Set>();
	
	private synchronized void detectAndRollbackHibernateTransactionCreatedLater(Object targetObject) throws Exception {
		try {
			if (readedObjectsByRequest.get(ServletActionContext.getRequest()).contains(targetObject))
				return;
			readedObjectsByRequest.get(ServletActionContext.getRequest()).add(targetObject);
			
			Class testClass = targetObject.getClass();
			while (isCandidadeClass(testClass)) {
				Field[] campos = testClass.getDeclaredFields();
				
				for (Field campo : campos) {
					
					if (campo.isEnumConstant())
						continue;
					
					try {
						campo.setAccessible(true);
						Object fieldValue = campo.get(targetObject);
						if ( (fieldValue==null) || (fieldValue.equals(targetObject)) )
							continue;
					} catch (LazyInitializationException e) {
						e.printStackTrace();
						continue;
					} 
					
					if (campo.getType().getName().equals(Session.class.getName())) {
						if (campo.isAnnotationPresent(SessionTarget.class)) {
							campo.setAccessible(true);
							Session hibernateSession = (Session) campo.get(targetObject);
							rollbackHibernateTransaction(hibernateSession.getTransaction());
							closeHibernateSession(hibernateSession);
						}
					} else {
						campo.setAccessible(true);
						Object subField = campo.get(targetObject);
						if (subField!=null) {
							detectAndRollbackHibernateTransactionCreatedLater(subField);
						}
					}
				}
				testClass = testClass.getSuperclass();
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void rollbackHibernateTransaction(Transaction hibernateTransaction) {
		if (hibernateTransaction==null) 
			return;

		if ((hibernateTransaction.isActive()) && (!hibernateTransaction.wasRolledBack()) && (!hibernateTransaction.wasCommitted())) {
			hibernateTransaction.rollback();
			log.debug("Hibernate Transaction "+hibernateTransaction+" rolledback");
		}
	}

	private String getSetterName(String name) {
		String firstUpper = name.substring(0,1).toUpperCase();
		String secondOn = name.substring(1,name.length());
		String setterName = "set"+firstUpper+secondOn;
		return setterName;
	}
	
	private String getGetterName(String name) {
		String firstUpper = name.substring(0,1).toUpperCase();
		String secondOn = name.substring(1,name.length());
		String setterName = "get"+firstUpper+secondOn;
		return setterName;
	}

	public static List<Field> getFieldsFromAction(Object action) {
		List<Field> fields = new ArrayList<Field>();
		Class clazz = action.getClass();
		do {
			Field[] fieldsArray = clazz.getDeclaredFields();
			CollectionUtils.addAll(fields, fieldsArray);
			clazz=clazz.getSuperclass();
		} while (!clazz.equals(Object.class));
		return fields;
	}

	public String getSessionTarget() {
		return sessionTarget;
	}

	@Inject(value=Constants.HIBERNATEPLUGIN_SESSIONTARGET, required=false)
	public void setSessionTarget(String sessionTarget) {
		this.sessionTarget = sessionTarget;
	}

	public String getCustomSessionFactoryClass() {
		return customSessionFactoryClass;
	}
	
	@Inject(value=Constants.HIBERNATEPLUGIN_CUSTOMSESSIONFACTORYCLASS, required=false)
	public void setCustomSessionFactoryClass(String customSessionFactoryClass) {
		this.customSessionFactoryClass = customSessionFactoryClass;
	}

	public String getGetSessionMethod() {
		return getSessionMethod;
	}

	@Inject(value=Constants.HIBERNATEPLUGIN_GETSESSIONMETHOD, required=false)
	public void setGetSessionMethod(String getSessionMethod) {
		this.getSessionMethod = getSessionMethod;
	}

	public String getCloseSessionAfterInvoke() {
		return closeSessionAfterInvoke;
	}
	
	public Boolean isCloseSessionAfterInvoke() {
		Boolean resultado = new Boolean(closeSessionAfterInvoke); 
		return resultado;
	}

	@Inject(value=Constants.HIBERNATEPLUGIN_CLOSESESSIONAFTERINVOKE,required=false)
	public void setCloseSessionAfterInvoke(String closeSessionAfterInvoke) {
		this.closeSessionAfterInvoke = closeSessionAfterInvoke;
	}

	public String getStaticGetSessionMethod() {
		return staticGetSessionMethod;
	}
	
	public Boolean isStaticGetSessionMethod() {
		Boolean resultado = new Boolean(staticGetSessionMethod); 
		return resultado;
	}

	@Inject(value=Constants.HIBERNATEPLUGIN_STATICGETSESSIONMETHOD,required=false)
	public void setStaticGetSessionMethod(String staticGetSessionMethod) {
		this.staticGetSessionMethod = staticGetSessionMethod;
	}
	
	
	public String[] getConfigurationFiles() {
		if (configurationFiles==null)
			configurationFiles="";
		return configurationFiles.split(",");
	}
	
	@Inject(value=Constants.HIBERNATEPLUGIN_CONFIGURATIONFILES, required=false)
	public void setConfigurationFiles(String configurationFiles) {
		this.configurationFiles = configurationFiles;
	}

	@Inject(value=Constants.HIBERNATEPLUGIN_TRANSACTIONTARGET, required=false)
	public void setTransactionTarget(String transactionTarget) {
		this.transactionTarget = transactionTarget;
	}
	
	@Inject(value=Constants.HIBERNATEPLUGIN_CLOSESESSIONMETHOD, required=false)
	public void setCloseSessionMethod(String closeSessionMethod) {
		this.closeSessionMethod = closeSessionMethod;
	}

	@Inject(value=Constants.HIBERNATEPLUGIN_USESESSIONOBJECTINCLOSESESSIONMETHOD, required=false)
	public void setUseSessionObjectInCloseMethod(
			boolean useSessionObjectInCloseMethod) {
		this.useSessionObjectInCloseMethod = useSessionObjectInCloseMethod;
	}
	

	@Inject(value=Constants.HIBERNATEPLUGIN_SOURCESESSIONFACTORY, required=false)
	public void setSourceSessionFactory(String sourceSessionFactory) {
		this.sourceSessionFactory = sourceSessionFactory;
	}
	
	@Inject(value=Constants.HIBERNATEPLUGIN_AUTOREBUILD, required=false)
	public void setAutoRebuidOnJdbcConnectionError(
			String autoRebuidOnJdbcConnectionError) {
		HibernateSessionFactory.setAutoRebuidOnJdbcConnectionError(new Boolean(autoRebuidOnJdbcConnectionError));
	}

	public boolean isSearchInsideJars() {
		return searchInsideJars;
	}

	@Inject(value=Constants.HIBERNATEPLUGIN_SEARCHMAPPINGINSIDEJARS, required=false)
	public void setSearchInsideJars(boolean searchInsideJars) {
		this.searchInsideJars = searchInsideJars;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

}



class HibernateSessionTransactionInfo {
	Session sessionObject;
	String sessionTarget;

	Transaction transactionObject;
	String transactionTarget;
	
	String sessionFactoryName;

	public Session getSessionObject() {
		return sessionObject;
	}
	public void setSessionObject(Session sessionObject) {
		this.sessionObject = sessionObject;
	}
	public String getSessionTarget() {
		return sessionTarget;
	}
	public void setSessionTarget(String sessionTarget) {
		this.sessionTarget = sessionTarget;
	}
	public Transaction getTransactionObject() {
		return transactionObject;
	}
	public void setTransactionObject(Transaction transactionObject) {
		this.transactionObject = transactionObject;
	}
	public String getTransactionTarget() {
		return transactionTarget;
	}
	public void setTransactionTarget(String transactionTarget) {
		this.transactionTarget = transactionTarget;
	}
	public String getSessionFactoryName() {
		return sessionFactoryName;
	}
	public void setSessionFactoryName(String sessionFactoryName) {
		this.sessionFactoryName = sessionFactoryName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof HibernateSessionTransactionInfo))
			return false;
		HibernateSessionTransactionInfo info = (HibernateSessionTransactionInfo) obj;
		boolean isEqual = ( 
				((sessionObject!=null) && (sessionObject.equals(info.getSessionObject())) ) ||
				((transactionObject!=null) && ((transactionObject.equals(info.getTransactionObject()))) ) 
				);
		return isEqual;
	}
	
	@Override
	public int hashCode() {
		int hc = 0;
		if (sessionObject!=null)
			hc+=sessionObject.hashCode();
		if (transactionObject!=null)
			hc+=transactionObject.hashCode();
		return hc;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("");
		if (sessionObject!=null) {
			sb.append("Session: ");
			sb.append(sessionObject.hashCode());
			sb.append(" - ");
			sb.append(sessionObject);
		}
		if (transactionObject!=null) {
			sb.append("\n\nTransaction: ");
			sb.append(transactionObject.hashCode());
			sb.append(" - ");
			sb.append(transactionObject);
		}
		return sb.toString();
	}
}
