package planes;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import utilidades.HibernateUtil;

public class CuotaAppl {
	public CuotaDTO getCuotaById(int idCuota) {
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("select c from CuotaDTO c where c.id=:id");	
			q.setInteger("id", idCuota);
			CuotaDTO results = (CuotaDTO) q.uniqueResult();
			return results;
		} catch (Exception e) {
			return null;
		}
	}
}
