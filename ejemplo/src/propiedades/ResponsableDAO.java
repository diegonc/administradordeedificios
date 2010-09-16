package propiedades;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;

public class ResponsableDAO {
	
	@SessionTarget
	Session session;
	
	@TransactionTarget
	Transaction transaction;

	@SuppressWarnings("unchecked")
	public List<Responsable> listar() {
		return session.createQuery("from Responsable").list();
	}

	public Responsable buscar(Integer dni) {
		return (Responsable) session.get(Responsable.class, dni);
	}

	public void eliminar(Responsable responsable) {
		try {
			session.delete(responsable);
		} catch (HibernateException e) {
			transaction.rollback();
			throw e;
		}
	}

	public void grabar(Responsable responsable) {
		try {
			session.saveOrUpdate(responsable);
		} catch (HibernateException e) {
			transaction.rollback();
			throw e;
		}
	}

}
