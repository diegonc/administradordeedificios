package utilidades;

import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.opensymphony.xwork2.ActionSupport;

import org.hibernate.Session;
import org.hibernate.Transaction;

@SuppressWarnings("serial")
public abstract class SessionAwareAction extends ActionSupport {

	@SessionTarget
	protected Session session;

	public final Session getSession() {
		return session;
	}

	public final Transaction getTransaction() {
		return session.getTransaction();
	}

	public final void setSession(Session session) {
		this.session = session;
		onSetSession();
	}

	protected void onSetSession() { }
}
