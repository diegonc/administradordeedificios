package planes;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import utilidades.AbstractAppl;
import utilidades.HibernateUtil;

public class PlanesAppl extends AbstractAppl {
	
	@SuppressWarnings("unchecked")
	public List<PlanDTO> getPlanByDNI(int dni) {
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("select p from PlanDTO p where p.responsable.dni=:dni");	
			q.setInteger("dni", dni);
			List<PlanDTO> results = (List<PlanDTO>) q.list();
			return results;
		} finally {
			//session.close();
		}
	}
	
	public PlanDTO getPlanById(int id) {
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("select p from PlanDTO p where p.id=:id");	
			q.setInteger("id", id);
			PlanDTO results = (PlanDTO) q.uniqueResult();
			return results;
		}  finally {
			//session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PlanDTO> listar() {
		return (List<PlanDTO>)session.createCriteria(PlanDTO.class).list();
	}
}
