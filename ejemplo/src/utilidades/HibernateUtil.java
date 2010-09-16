package utilidades;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
	
	  private static final SessionFactory sessionFactory = buildSessionFactory();

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

}
