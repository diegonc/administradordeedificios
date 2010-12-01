package actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import permisos.AdministradorDePermisos;
import planes.CuotaDTO;
import planes.PlanBuilder;
import planes.PlanDTO;
import propiedades.ResponsableAppl;
import utilidades.SessionAwareAction;
import expensas.dto.ExpensaCobroDTO;
import expensas.dto.ExpensaDTO;

@SuppressWarnings("serial")
public class CalculoCuotasAction extends SessionAwareAction {

	private Date fecha;
	private int responsableDNI;
	private List<Integer> expElegidas;
	private int cantCuotas;
	private int descuento;

	private PlanDTO plan;

	public String execute() {
		if (expElegidas == null) {
			addActionError("No ha seleccionado ninguna expensa con deuda");
			return "error";
		}
		plan = crearPlan();
		return "confirmacion";
	}

	public String confirmar() {
		try {
			session.beginTransaction();
			
			plan = crearPlan();
			session.save(plan);
			// XXX: hibernate no hace cascada para las cuotas :/
			for (CuotaDTO cuota : plan.getCuotas()) {
				session.save(cuota);
			}
			// XXX: hibernate no hace cascada hasta las propiedades :/
			for (ExpensaCobroDTO e : plan.getCobrosCancelados())
				session.saveOrUpdate(e.getLiquidacion().getPropiedad());
			
			getTransaction().commit();
		} catch (HibernateException e) {
			getTransaction().rollback();
			LOG.error("No se pudo guardar el plan.", e);
			addActionError("No se pudo guardar el plan.");
		}
		return SUCCESS;
	}

	public String cancelar() {
		return "cancelar";
	}

	private PlanDTO crearPlan() {
		ResponsableAppl respAppl = new ResponsableAppl(session);
		List<ExpensaCobroDTO> expensas = crearExpensaCobros();

		PlanBuilder pb = new PlanBuilder();
		pb.setFecha(fecha);
		pb.setCantidadCuotas(cantCuotas);
		pb.setResponsable(respAppl.buscar(responsableDNI));

		for (ExpensaCobroDTO cobro : expensas) {
			pb.addExpensaCobro(cobro);
		}

		return pb.calcularPlan();
	}

	private List<ExpensaCobroDTO> crearExpensaCobros() {
		List<ExpensaCobroDTO> cobros = new ArrayList<ExpensaCobroDTO>();

		for (int expId : expElegidas) {
			ExpensaDTO exp = (ExpensaDTO)session.get(ExpensaDTO.class, expId); /* TODO: appl. */
			ExpensaCobroDTO cobro = new ExpensaCobroDTO();
			cobro.setLiquidacion(exp);
			cobro.setComprobante("plan");
			cobro.setConsolidado(false);
			cobro.setFecha(fecha);
			cobro.setMontoPago(exp.getMonto() + exp.getIntereses());
			cobro.setResponsableCobro(AdministradorDePermisos.getInstancia()
					.getUsuario());
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

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	public int getDescuento() {
		return descuento;
	}

}
