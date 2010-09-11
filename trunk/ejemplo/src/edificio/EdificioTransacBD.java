package edificio;
import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Collection;

import org.apache.log4j.Logger;

import edificio.EdificioDTO;

import java.util.List;

/**
 * Manipulación de una tabla simple con llave sencilla.
 */
public final class EdificioTransacBD {
	private static Logger _logger = Logger.getLogger(EdificioTransacBD.class);

	/**
	 * Obtener un SessionFactory de un archivo de configuración local.
	 */
	public SessionFactory createSessionFactory() {
		AnnotationConfiguration configuration = new AnnotationConfiguration();
		configuration.configure("/hibernate.cfg.xml");
		return configuration.buildSessionFactory();
	}

	public int insertObject(Session session, EdificioDTO edificio) {
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
		return edificio.getId();
	}

	/**
	 * Perform SELECT statements using Hibernate's load() method
	 */
	public EdificioDTO getObject(SessionFactory factory, int id) {
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
	public EdificioDTO queryObject(SessionFactory factory, int id) {
		Session session = factory.openSession();
		try {
			Criteria criteria = session.createCriteria(EdificioDTO.class);
			criteria.add(Expression.eq("id", id));
			List<?> results = criteria.list();
			return (EdificioDTO) results.get(0);
		} finally {
			session.close();
		}
	}

	/**
	 * Perform SELECT statements using Hibernate's Query API
	 */
	public int findMaxKey(SessionFactory factory) {
		Session session = factory.openSession();
		try {
			Query query = session
					.createQuery("SELECT MAX( edificio.id ) FROM EdificioDTO edificio");			
			List<?> results = query.list();
			return (Integer) results.get(0);
		} finally {
			session.close();
		}
	}
	
	
	/**
	 * Perform UPDATE statements using Hibernate's update() method
	 */
	public void updateObject(SessionFactory factory, EdificioDTO edificio) {
		Session session = factory.openSession();
		try {
			updateObject(session, edificio);
		} finally {
			session.close();
		}
	}

	public void updateObject(Session session, EdificioDTO edificio) {
		boolean commit = false;
		Transaction transaction = session.beginTransaction();
		try {
			session.update(edificio);
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
	public void deleteObject(SessionFactory factory, int id) {
		Session session = factory.openSession();
		try {
			deleteObject(session, id);
		} finally {
			session.close();
		}
	}

	public void deleteObject(Session session, Integer pk) {
		boolean commit = false;
		Transaction transaction = session.beginTransaction();

		try {
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
