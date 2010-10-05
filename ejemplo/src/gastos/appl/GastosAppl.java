package gastos.appl;

import gastos.dto.GastoDTO;
import gastos.dto.GastoPrevisionDTO;
import gastos.dto.GastoRealDTO;
import gastos.exception.GastoExistenteException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import utilidades.HibernateUtil;

public class GastosAppl {
	
	public void addGasto(GastoDTO gasto) throws GastoExistenteException 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try{
        	session.save(gasto);
        }catch(ConstraintViolationException e){
        	throw new GastoExistenteException("Ya existe un Gasto con ese folio asignado al edificio.");
        }
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
	}
	
	
	private List<GastoRealDTO> prepararResultado(List<GastoRealDTO> results, int id) {
		ArrayList<GastoRealDTO> listaLimpia = new ArrayList<GastoRealDTO>();
		java.util.Iterator<GastoRealDTO> iter = results.iterator();
		while (iter.hasNext()) {
			GastoRealDTO gasto = iter.next();
			if (gasto.getEdificio().getId() == id){ 
				if (gasto.getEstado().equals("P")) {
					 listaLimpia.add(gasto);
				} 
			}
		}
		return listaLimpia;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<GastoRealDTO> getGastosPendientesPorEdificio(SessionFactory factory, int id) {
		Session session = factory.openSession();
		try {
			Query q = session.createQuery("select u from GastoRealDTO u");	
			List<GastoRealDTO> results = q.list();
			return (ArrayList<GastoRealDTO>) prepararResultado(results,id);
		} finally {
			session.close();
		}
	}
	
	public GastoRealDTO getGastosRealesPendientesPorid(SessionFactory factory, int id) {
		Session session = factory.openSession();
		try {
			Query q = session.createQuery("select u from GastoRealDTO u where u.id=:id");	
			q.setInteger("id", id);
			GastoRealDTO results = (GastoRealDTO) q.uniqueResult();
			return (GastoRealDTO) results;
		} finally {
			session.close();
		}
	}
	
	public GastoPrevisionDTO getGastosPrevisionPorid(SessionFactory factory, int id) {
		Session session = factory.openSession();
		try {
			Query q = session.createQuery("select u from GastoPrevisionDTO u where u.id=:id");	
			q.setInteger("id", id);
			GastoPrevisionDTO results = (GastoPrevisionDTO) q.uniqueResult();
			return (GastoPrevisionDTO) results;
		} finally {
			session.close();
		}
	}
	

	private ArrayList<GastoPrevisionDTO> prepararResultadoPrevisto(
			List<GastoPrevisionDTO> results, int id) {
		ArrayList<GastoPrevisionDTO> listaLimpia = new ArrayList<GastoPrevisionDTO>();
		java.util.Iterator<GastoPrevisionDTO> iter = results.iterator();
		Date fecha = new Date();
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha); // fecha es el Date de antes.
		int anio = calendario.get(Calendar.YEAR);
		int mes = calendario.get(Calendar.MONTH);
		
		while (iter.hasNext()) {
			GastoPrevisionDTO gasto = iter.next();
			if (gasto.getEdificio().getId() == id && gasto.getAnio() >= anio && (gasto.getAnio() == anio &&  gasto.getMes() > mes)) {
			   listaLimpia.add(gasto);
			}
		}
		return listaLimpia;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<GastoPrevisionDTO> getGastosPrevistoFuturosPorEdificio(SessionFactory factory, int id) {
		Session session = factory.openSession();
		try {
			Query q = session.createQuery("select u from GastoPrevisionDTO u");	
			List<GastoPrevisionDTO> results = q.list();
			return (ArrayList<GastoPrevisionDTO>) prepararResultadoPrevisto(results,id);
		} finally {
			session.close();
		}
	}

	public void deleteGastoReal(SessionFactory factory, int id) {
		boolean commit = false;
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query query = session
			.createQuery("DELETE FROM GastoRealDTO g WHERE g.id = :id");
			query.setInteger("id", id);
			query.executeUpdate();
			commit = true;
		} finally {
			if (commit) {
				transaction.commit();
			}
			else {
				transaction.rollback();
			}
		}
	}
	
	public void deleteGastoPrevision(SessionFactory factory, int id) {
		boolean commit = false;
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query query = session
			.createQuery("DELETE FROM GastoPrevisionDTO g WHERE g.id = :id");
			query.setInteger("id", id);
			query.executeUpdate();
			commit = true;
		} finally {
			if (commit) {
				transaction.commit();
			}
			else {
				transaction.rollback();
			}
		}
	}
	
	public void updateGastoReal(SessionFactory factory, GastoRealDTO gastoReal) {
		Session session = factory.openSession();
		try {
			updateGastoReal(session, gastoReal);
		} finally {
			session.close();
		}
	}

	public void updateGastoReal(Session session,  GastoRealDTO gastoReal) {
		boolean commit = false;
		Transaction transaction = session.beginTransaction();
		try {
			session.update(gastoReal);
			commit = true;
		} finally {
			if (commit) {
				transaction.commit();
			} else {
				transaction.rollback();
			}
		}
	}

	public void updateGastoPrevision(SessionFactory factory,
			GastoPrevisionDTO gastoProvi) {
		Session session = factory.openSession();
		try {
			updateGastoReal(session, gastoProvi);
		} finally {
			session.close();
		}
		
	}

	public void updateGastoReal(Session session, GastoPrevisionDTO gastoProvi) {
		boolean commit = false;
		Transaction transaction = session.beginTransaction();
		try {
			session.update(gastoProvi);
			commit = true;
		} finally {
			if (commit) {
				transaction.commit();
			} else {
				transaction.rollback();
			}
		}
		
	}

}

