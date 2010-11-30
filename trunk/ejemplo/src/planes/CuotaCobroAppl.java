package planes;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import edificio.EdificioDTO;
import expensas.dto.ExpensaCobroDTO;


import utilidades.HibernateUtil;

public class CuotaCobroAppl {
	
	public boolean existeCobro(int cuotaID) {
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("select c from CuotaCobroDTO c where c.cuota.id=:id");	
			q.setInteger("id", cuotaID);
			List<CuotaCobroDTO> results = (List<CuotaCobroDTO>) q.list();
			if (results.isEmpty()) {
				return false;
			} 
			return true;
		} catch (Exception e) {
			return true;
		}
	}
	
	public int insertCobro(CuotaCobroDTO cobro) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try{
        	session.save(cobro);
        }catch(Exception e){
        	throw e;
        }
        session.getTransaction().commit();
       return cobro.getId();
	}

	public void consolidarCobroCuota(int cuotaId) throws Exception {
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("select c from CuotaCobroDTO c where c.cuota.id=:id");	
			q.setInteger("id", cuotaId);
			CuotaCobroDTO result = (CuotaCobroDTO) q.uniqueResult();
			Set<ExpensaCobroDTO> expensasCobradas = result.getCuota().getPlan().getCobrosCancelados();
			String tipo = expensasCobradas.iterator().next().getLiquidacion().getTipo();
			EdificioDTO edificio =  expensasCobradas.iterator().next().getLiquidacion().getPropiedad().getTipoPropiedad().getEdificio();
			result.setConsolidado(true);
			if (tipo.equals("O")) {
				edificio.setFondo_ordinario(edificio.getFondo_ordinario() + result.getCuota().getMonto() 
						+ result.getCuota().getIntereses() + result.getCuota().getInteresMora());
			} else {
				edificio.setFondo_extraordinario(edificio.getFondo_extraordinario() + result.getCuota().getMonto() 
						+ result.getCuota().getIntereses() + result.getCuota().getInteresMora());
			}
			session.beginTransaction();
	        try{
	        	session.update(result);
	        	session.update(edificio);
	        }catch(Exception e){
	        	throw e;
	        }
	        session.getTransaction().commit();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public boolean paraConsolidar(int cuota_id) {
		Session session = HibernateUtil.getSession();
		try {
			Query q = session.createQuery("select c from CuotaCobroDTO c where c.cuota.id=:id");	
			q.setInteger("id", cuota_id);
			CuotaCobroDTO result = (CuotaCobroDTO) q.uniqueResult();
			if (result!=null) {
				if (result.getConsolidado() == true) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
