package actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.HibernateException;

import permisos.AdministradorDePermisos;
import planes.CuotaDTO;
import planes.PlanBuilder;
import planes.PlanDTO;
import propiedades.ResponsableAppl;
import utilidades.HibernateUtil;

import com.opensymphony.xwork2.ActionSupport;

import expensas.appl.ExpensaAppl;
import expensas.dto.ExpensaCobroDTO;
import expensas.dto.ExpensaDTO;

@SuppressWarnings("serial")
public class CalculoCuotasAction  extends ActionSupport {
	
	private Date fecha;
	private int responsableDNI;
	private List<Integer> expElegidas;
	private int cantCuotas;

	private PlanDTO plan;
	
	public String execute() {
		if (expElegidas == null) {
			return "error";
		}
		plan = crearPlan();
		return "confirmacion";
	}
	
	public String confirmar() {
		//TODO inyectar la sesion con el interceptor.
		Session hSession = HibernateUtil.getSession();
		try {
			hSession.beginTransaction();
			plan = crearPlan();
			hSession.save(plan);
			// XXX: hibernate no hace cascada para las cuotas :/
			for (CuotaDTO cuota : plan.getCuotas()) {
				hSession.save(cuota);
			}
			hSession.getTransaction().commit();
		} catch (HibernateException e) {
			hSession.getTransaction().rollback();
			LOG.error("No se pudo guardar el plan.", e);
		} finally {
			hSession.close();
		}
		return SUCCESS;
	}

	public String cancelar() {
		return ERROR;
	}

	private PlanDTO crearPlan() {
		//TODO inyectar la sesion con el interceptor.
		Session hSession = HibernateUtil.getSession();
		try {
			ResponsableAppl respAppl = new ResponsableAppl(hSession);
			List<ExpensaCobroDTO> expensas = crearExpensaCobros();
			
			PlanBuilder pb = new PlanBuilder();
			pb.setFecha(fecha);
			pb.setCantidadCuotas(cantCuotas);
			pb.setResponsable(respAppl.buscar(responsableDNI));
			
			for (ExpensaCobroDTO cobro : expensas) {
				pb.addExpensaCobro(cobro);
			}
			
			return pb.calcularPlan();
		} finally {
			hSession.close();
		}
	}

	private List<ExpensaCobroDTO> crearExpensaCobros() {
		List<ExpensaCobroDTO> cobros = new ArrayList<ExpensaCobroDTO>();
		ExpensaAppl expAppl = new ExpensaAppl();
				
		for (int expId : expElegidas) {
			// TODO: cada instancia obtenida viene de una sesión de Hibernate distinta.
			ExpensaDTO exp = expAppl.getExpensasById(expId);
			ExpensaCobroDTO cobro = new ExpensaCobroDTO();
			cobro.setLiquidacion(exp);
			cobro.setComprobante("plan");
			cobro.setConsolidado(false);
			cobro.setFecha(fecha);
			cobro.setMontoPago(exp.getMonto());
			cobro.setResponsableCobro(AdministradorDePermisos.getInstancia().getUsuario());
			cobro.setConsolidado(true);
			cobros.add(cobro);
		}
		return cobros;
	}

	public void setExpElegidas(List<Integer> expElegidas) {
		this.expElegidas = expElegidas;
	}


	public List<Integer> getExpElegidas() {
		return expElegidas;
	}


	public void setCantCuotas(int cantCuotas) {
		this.cantCuotas = cantCuotas;
	}


	public int getCantCuotas() {
		return cantCuotas;
	}
	
	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public int getResponsableDNI() {
		return responsableDNI;
	}


	public void setResponsableDNI(int responsableDNI) {
		this.responsableDNI = responsableDNI;
	}

	public PlanDTO getPlan() {
		return plan;
	}

	public void setPlan(PlanDTO plan) {
		this.plan = plan;
	}

}
