package propiedades;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;

public class TipoPropiedadTipoGastoDAO {
	
	@SessionTarget
	Session session;
	
	@TransactionTarget
	Transaction transaction;

	public void grabar(TipoPropiedadTipoGastoDTO tipo) {
		try {
			session.saveOrUpdate(tipo);
		} catch (HibernateException e) {
			transaction.rollback();
			throw e;
		}
	}

	public void eliminar(TipoPropiedadTipoGastoDTO tipo) {
		try {
			session.delete(tipo);
		} catch (HibernateException e) {
			transaction.rollback();
			throw e;
		}
	}

}
