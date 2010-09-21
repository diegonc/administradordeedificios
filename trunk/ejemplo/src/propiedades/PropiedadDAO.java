package propiedades;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;

public class PropiedadDAO {
	@SessionTarget
	Session session;

	@TransactionTarget
	Transaction transaction;

	public void grabar(PropiedadDTO propiedad) {
		try {
			session.saveOrUpdate(propiedad);
		} catch (HibernateException e) {
			transaction.rollback();
			throw e;
		}
	}

	public void eliminar(PropiedadDTO propiedad) {
		try {
			session.delete(propiedad);
		} catch (HibernateException e) {
			transaction.rollback();
			throw e;
		}
	}
}
