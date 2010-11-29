package planes;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import edificio.EdificioDTO;
import gastos.exception.GastoExistenteException;

import utilidades.HibernateUtil;

public class CuotaCobroAppl {
	
	public boolean existeCobro(int cuotaID) {
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("select c from CuotaCobroDTO c where c.cuota.id=:id");	
			q.setInteger("id", cuotaID);
			List<PlanDTO> results = (List<PlanDTO>) q.list();
			if (results.isEmpty()) {
				return false;
			} 
			return true;
		} catch (Exception e) {
			return true;
		}
	}
	
	public int insertCobro(CuotaCobroDTO cobro) throws GastoExistenteException {
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try{
        	session.save(cobro);
        }catch(ConstraintViolationException e){
        	throw new GastoExistenteException("Error al cargar el cobro.");
        }
        session.getTransaction().commit();
       return cobro.getId();
	}
}
