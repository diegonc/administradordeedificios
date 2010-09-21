package utilidades;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
	
	  private static SessionFactory sessionFactory = buildSessionFactory();

	    private static SessionFactory buildSessionFactory() {
	        try{
	        	 AnnotationConfiguration configuration =  new AnnotationConfiguration();
	        	 configuration.configure("/hibernate.cfg.xml" );
	        	 return configuration.buildSessionFactory();
	        }
	        catch (Throwable ex) {
	            
	            System.err.println("Fallo en la creacion de la SessionFactory." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }

	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }
	    
	    public static synchronized void rebuildSessionFactory() {
	    	sessionFactory.close();
	    	sessionFactory = buildSessionFactory();
	    }
	    
	    public static synchronized Session getSession() {
	    	return getSessionFactory().openSession();	
	    }
	    
	    public static synchronized void closeSession(Session s) {
	    	s.close();
	    }

}
