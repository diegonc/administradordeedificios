package expensas;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class ExpensaAppl {
	private static Logger _logger = Logger.getLogger(ExpensaAppl.class);
	
	public SessionFactory createSessionFactory() {
		AnnotationConfiguration configuration = new AnnotationConfiguration();
		configuration.configure("/hibernate.cfg.xml");
		return configuration.buildSessionFactory();
	}
	
	public List<ExpensaDTO> getExpensaActivaPorIdProp(SessionFactory factory, int propId) {
		Session session = factory.openSession();
		try {
			Query query = session
			.createQuery("select u from ExpensaDTO u where u.id=:idprop order by u.id");
			query.setInteger("idprop", propId);
			List<ExpensaDTO> results = query.list();
			return results; 
		} finally {
			session.close();
		}
	}
	
}
