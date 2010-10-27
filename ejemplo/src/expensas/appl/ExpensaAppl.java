package expensas.appl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import expensas.dto.ExpensaDTO;

public class ExpensaAppl {
		
	public SessionFactory createSessionFactory() {
		AnnotationConfiguration configuration = new AnnotationConfiguration();
		configuration.configure("/hibernate.cfg.xml");
		return configuration.buildSessionFactory();
	}
	
	@SuppressWarnings("unchecked")
	public List<ExpensaDTO> getExpensaActivaPorIdProp(SessionFactory factory, int propId) {
		Session session = factory.openSession();
		try {
			Query query = session
			.createQuery("from ExpensaDTO u where u.propiedad.id=:idprop order by u.id");
			query.setInteger("idprop", propId);
			List<ExpensaDTO> results = query.list();
			return results; 
		} finally {
			session.close();
		}
	}
	
	
	
}
