import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Expression;

import org.apache.log4j.Logger;

import edificio.dto.EdificioDTO;

import java.util.List;

/**
 * Manipulación de una tabla simple con llave sencilla.
 */
public final class TestEdificio {
	private static Logger _logger = Logger.getLogger(TestEdificio.class);

	public static void main(String[] args) {
		try {
			new TestEdificio().execute();
		} catch (Exception e) {
			_logger.error("Error:", e);
		}
	}

	/**
	 * Método de ejecución de altas, bajas, cambios y consultas.
	 */
	private void execute() throws Exception {
		SessionFactory factory = createSessionFactory();
		int id = insertObject(factory);
		EdificioDTO edificio = getObject(factory, id);
		_logger.info("Nombre: " + edificio.getNombre());
		_logger.info("Calle Primera: " + edificio.getCalle());
		edificio.setCalle("Corrientes");
		updateObject(factory, edificio);
		edificio = queryObject(factory, id);
		_logger.info("Nombre: " + edificio.getNombre());
		_logger.info("Calle Final: " + edificio.getCalle());
	    int max = findMaxKey( factory );
		_logger.info( "max key: " + max );
		deleteObject(factory, id);
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private methods

	/**
	 * Obtener un SessionFactory de un archivo de configuración local.
	 */
	private SessionFactory createSessionFactory() {
		AnnotationConfiguration configuration = new AnnotationConfiguration();
		configuration.configure("/hibernate.cfg.xml");
		return configuration.buildSessionFactory();
	}

	/**
	 * Perform INSERT statements using Hibernate's save() method
	 */
	private int insertObject(SessionFactory factory) {
		EdificioDTO edificio = new EdificioDTO();
		edificio.setNombre("Edificio del Sol");
		edificio.setFondo_ordinario(0);
		edificio.setFondo_extraordinario(222);
		edificio.setForma_liq_exp("Como se pueda");
		edificio.setApto_profesional(true);
		edificio.setTasa_anual(43);
		edificio.setAmortizacion(100);
		edificio.setCalle("Medrano");
		edificio.setNumero(222);
		edificio.setLocalidad("CABA");
		edificio.setEncargado_nombre("Jose");
		edificio.setEncargado_telefono("434343");
		edificio.setEncargado_depto("primero");
		edificio.setEncargado_piso("A");
		edificio.setDia_primer_vto(1);

		Session session = factory.openSession();
		try {
			insertObject(session, edificio);
		} finally {
			session.close();
		}
		return edificio.getId();
	}

	private void insertObject(Session session, EdificioDTO edificio) {
		boolean commit = false;
		Transaction transaction = session.beginTransaction();
		try {
			session.save(edificio);
			commit = true;
		} finally {
			if (commit) {
				transaction.commit();
			} else {
				transaction.rollback();
			}
		}
	}

	/**
	 * Perform SELECT statements using Hibernate's load() method
	 */
	private EdificioDTO getObject(SessionFactory factory, int id) {
		Session session = factory.openSession();
		try {
			return (EdificioDTO) session.load(EdificioDTO.class, id);
		} finally {
			session.close();
		}
	}

	/**
	 * Perform SELECT statements using Hibernate's Criteria API
	 */
	private EdificioDTO queryObject(SessionFactory factory, int id) {
		Session session = factory.openSession();
		try {
			Criteria criteria = session.createCriteria(EdificioDTO.class);
			criteria.add(Expression.eq("id", id));
			// return ( FacturaDTO )criteria.uniqueResult();
			List<?> results = criteria.list();
			return (EdificioDTO) results.get(0);
		} finally {
			session.close();
		}
	}

	/**
	 * Perform SELECT statements using Hibernate's Query API
	 */
	private int findMaxKey(SessionFactory factory) {
		Session session = factory.openSession();
		try {
			Query query = session
					.createQuery("SELECT MAX( edificio.id ) FROM EdificioDTO edificio");
			// return ( Integer )query.uniqueResult();
			List<?> results = query.list();
			return (Integer) results.get(0);
		} finally {
			session.close();
		}
	}

	/**
	 * Perform UPDATE statements using Hibernate's update() method
	 */
	private void updateObject(SessionFactory factory, EdificioDTO factura) {
		Session session = factory.openSession();
		try {
			updateObject(session, factura);
		} finally {
			session.close();
		}
	}

	private void updateObject(Session session, EdificioDTO factura) {
		boolean commit = false;
		Transaction transaction = session.beginTransaction();
		try {
			session.update(factura);
			commit = true;
		} finally {
			if (commit) {
				transaction.commit();
			} else {
				transaction.rollback();
			}
		}
	}

	/**
	 * Perform DELETE statements using Hibernate's Query API
	 */
	private void deleteObject(SessionFactory factory, int id) {
		Session session = factory.openSession();
		try {
			deleteObject(session, id);
		} finally {
			session.close();
		}
	}

	private void deleteObject(Session session, Integer pk) {
		boolean commit = false;
		Transaction transaction = session.beginTransaction();

		try {
			// FacturaDTO factura =
			// ( FacturaDTO )session.load( FacturaDTO.class, pk );
			// session.delete( factura );
			Query query = session
					.createQuery("DELETE FROM EdificioDTO factura WHERE factura.id = :id");
			query.setInteger("id", pk);
			query.executeUpdate();
			commit = true;
		} finally {
			if (commit) {
				transaction.commit();
			}
			else {
				transaction.rollback();
			}
		}
	}

}
