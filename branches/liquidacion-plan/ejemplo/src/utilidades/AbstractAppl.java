package utilidades;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class AbstractAppl {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractAppl.class);

	protected Session session;
	protected Transaction transaction;

	public void grabar(Object o) {
		try {
			session.saveOrUpdate(o);
		} catch (HibernateException e) {
			transaction.rollback();
			session.close();
			LOG.debug("Error al grabar objeto.", e);
			throw e;
		}
	}

	public void eliminar(Object o) {
		try {
			session.delete(o);
		} catch (HibernateException e) {
			transaction.rollback();
			session.close();
			LOG.debug("Error al eliminar objeto.", e);
			throw e;
		}
	}

	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}

	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
}
