package utilidades;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class UtilidadesConexion {
	
	  /**
	   * Obtener un SessionFactory de un archivo de configuración local.
	   */
	  public static  SessionFactory createSessionFactory()
	  {
		  AnnotationConfiguration configuration = new AnnotationConfiguration();

		  configuration.configure( "/hibernate.cfg.xml" );

		  return configuration.buildSessionFactory();
	  }


}
