package expensas.appl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import utilidades.HibernateUtil;
import utilidades.Periodo;
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
			.createQuery("from ExpensaDTO u where u.propiedad.id=:idprop and u.tipo='O' order by u.id DESC");
			query.setInteger("idprop", propId);
			List<ExpensaDTO> results = query.list();
			return results; 
		} finally {
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ExpensaDTO> getExpensaExtActivaPorIdProp(SessionFactory factory, int propId) {
		Session session = factory.openSession();
		try {
			Query query = session
			.createQuery("from ExpensaDTO u where u.propiedad.id=:idprop and u.tipo='E' order by u.id DESC");
			query.setInteger("idprop", propId);
			List<ExpensaDTO> results = query.list();
			return results; 
		} finally {
			session.close();
		}
	}
	
	public int obtenerNumeroDeOperacion(int idPropiedad, String tipoExpensa){
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("select max(ex.numeroOperacion) from ExpensaDTO ex where" +
				" ex.propiedad.id =:idPropiedad and ex.tipo =:tipoExpensa");
		
		query.setInteger("idPropiedad", idPropiedad);
		query.setString("tipoExpensa", tipoExpensa);
		
		Integer nroOperacion = (Integer) query.uniqueResult();
		if(nroOperacion==null) return 1;
		return nroOperacion.intValue()+1;
	}
	
	@SuppressWarnings("unchecked")
	public ExpensaDTO obtenerExpensaUltimaLiquidacion(int idPropiedad, String tipoExpensa){
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("select ex from ExpensaDTO ex where" +
				" ex.propiedad.id =:idPropiedad and ex.tipo =:tipoExpensa " +
				"order by ex.numeroOperacion desc");
		
		query.setInteger("idPropiedad", idPropiedad);
		query.setString("tipoExpensa", tipoExpensa);
		
		List<ExpensaDTO> expensas = query.list();
		if(expensas.isEmpty()) return null;
		else
			return (ExpensaDTO)query.list().get(0);		
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean existeExpensaUltimaLiquidacion(int idEdificio, int mes, int anio){
		Session session = HibernateUtil.getSession();
		Periodo periodo = new Periodo(mes, anio);
		String fechaInicio = periodo.obtenerFechaInicio();
		String fechaFin = periodo.obtenerFechaFin();
					
		Query query = session.createQuery("select ex from TipoPropiedadDTO tp,EdificioDTO ed,PropiedadDTO p, ExpensaDTO ex" +
				" where tp.edificio.id=ed.id and p.tipoPropiedad.id = tp.id and ex.propiedad.id=p.id " +
				"and ed.id =:idEdificio and ex.fecha between :fechaInicio and :fechaFin");
		
		query.setInteger("idEdificio", idEdificio);
		query.setString("fechaInicio", fechaInicio);
		query.setString("fechaFin", fechaFin);
		
		List<ExpensaDTO> expensas = query.list();
		if(expensas.isEmpty()) return false;
		return true;		
	}
	
	@SuppressWarnings("unchecked")
	public ExpensaDTO getExpensasById(int idExp) {
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery("from ExpensaDTO u where u.id=:id");
		query.setInteger("id", idExp);
		List<ExpensaDTO> expensas = query.list();
		if(expensas.isEmpty()) return null;
		else
			return (ExpensaDTO)query.list().get(0);		
	}
	
	
}
