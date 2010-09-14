package edificio;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;

public class EdificioDAO {
	
	@SessionTarget
	Session session;
	
	@TransactionTarget
	Transaction transaction;

	@SuppressWarnings("unchecked")
	public List<EdificioDTO> listar() {
		return session.createQuery("from EdificioDTO").list();
	}

}
