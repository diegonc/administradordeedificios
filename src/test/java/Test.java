
import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Expression;

import org.apache.log4j.Logger;

import java.util.List;


/**
 * Manipulación de una tabla simple con llave sencilla.
 */
public final class Test
{
  private static Logger _logger = Logger.getLogger( Test.class );

  public static void main( String[] args )
  {
    try
    {
      new Test().execute();
    }
    catch ( Exception e )
    {
      _logger.error( "Error:", e );
    }
  }

  /**
   * Método de ejecución de altas, bajas, cambios y consultas.
   */
  private void execute() throws Exception
  {
	SessionFactory factory = createSessionFactory();

    int id = insertObject( factory );

    FacturaDTO factura = getObject( factory, id );

    _logger.info( "Nombre inicial: " + factura.getNombre() );

    updateObject( factory, factura );

    factura = queryObject( factory, id );

    _logger.info( "Nombre final: " + factura.getNombre() );

    int max = findMaxKey( factory );

    _logger.info( "max key: " + max );

    deleteObject( factory, id );
  }

  // //////////////////////////////////////////////////////////////////////////
  // Private methods

  /**
   * Obtener un SessionFactory de un archivo de configuración local.
   */
  private SessionFactory createSessionFactory()
  {
    AnnotationConfiguration configuration = new AnnotationConfiguration();

    configuration.configure( "/hibernate.cfg.xml" );

    return configuration.buildSessionFactory();
  }

  /**
   * Perform INSERT statements using Hibernate's save() method
   */
  private int insertObject( SessionFactory factory )
  {
    FacturaDTO factura = new FacturaDTO();

    factura.setNombre( "factura1" );

    Session session = factory.openSession();

    try
    {
      insertObject( session, factura );
    }
    finally
    {
      session.close();
    }

    return factura.getId();
  }

  private void insertObject( Session session, FacturaDTO factura )
  {
    boolean commit = false;
    Transaction transaction = session.beginTransaction();

    try
    {
      session.save( factura );

      commit = true;
    }
    finally
    {
      if ( commit )
        transaction.commit();
      else
        transaction.rollback();
    }
  }

  /**
   * Perform SELECT statements using Hibernate's load() method
   */
  private FacturaDTO getObject( SessionFactory factory, int id )
  {
    Session session = factory.openSession();

    try
    {
      return ( FacturaDTO )session.load( FacturaDTO.class, id );
    }
    finally
    {
      session.close();
    }
  }

  /**
   * Perform SELECT statements using Hibernate's Criteria API
   */
  private FacturaDTO queryObject( SessionFactory factory, int id )
  {
    Session session = factory.openSession();

    try
    {
      Criteria criteria = session.createCriteria( FacturaDTO.class );

      criteria.add( Expression.eq( "id", id ) );
      
      //return ( FacturaDTO )criteria.uniqueResult();

      List<?> results = criteria.list();

      return ( FacturaDTO )results.get( 0 );
    }
    finally
    {
      session.close();
    }
  }

  /**
   * Perform SELECT statements using Hibernate's Query API
   */
  private int findMaxKey( SessionFactory factory )
  {
    Session session = factory.openSession();

    try
    {
      Query query = session
          .createQuery( "SELECT MAX( factura.id ) FROM FacturaDTO factura" );
      
      //return ( Integer )query.uniqueResult();

      List<?> results = query.list();

      return ( Integer )results.get( 0 );
    }
    finally
    {
      session.close();
    }
  }

  /**
   * Perform UPDATE statements using Hibernate's update() method
   */
  private void updateObject( SessionFactory factory, FacturaDTO factura )
  {
    factura.setNombre( "factura2" );

    Session session = factory.openSession();

    try
    {
      updateObject( session, factura );
    }
    finally
    {
      session.close();
    }
  }

  private void updateObject( Session session, FacturaDTO factura )
  {
    boolean commit = false;
    Transaction transaction = session.beginTransaction();

    try
    {
      session.update( factura );

      commit = true;
    }
    finally
    {
      if ( commit )
        transaction.commit();
      else
        transaction.rollback();
    }
  }

  /**
   * Perform DELETE statements using Hibernate's Query API
   */
  private void deleteObject( SessionFactory factory, int id )
  {
    Session session = factory.openSession();

    try
    {
      deleteObject( session, id );
    }
    finally
    {
      session.close();
    }
  }

  private void deleteObject( Session session, Integer pk )
  {
    boolean commit = false;
    Transaction transaction = session.beginTransaction();

    try
    {
      // FacturaDTO factura =
      // ( FacturaDTO )session.load( FacturaDTO.class, pk );

      // session.delete( factura );

      Query query = session
          .createQuery( "DELETE FROM FacturaDTO factura WHERE factura.id = :id" );

      query.setInteger( "id", pk );

      query.executeUpdate();

      commit = true;
    }
    finally
    {
      if ( commit )
        transaction.commit();
      else
        transaction.rollback();
    }
  }

}
