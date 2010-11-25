package actions;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import permisos.AdministradorDePermisos;
import planes.CuotaDTO;
import planes.PlanBuilder;
import planes.PlanDTO;
import propiedades.ResponsableAppl;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import expensas.appl.ExpensaAppl;
import expensas.dto.ExpensaCobroDTO;
import expensas.dto.ExpensaDTO;

import org.hibernate.Session;

import utilidades.HibernateUtil;

@SuppressWarnings("serial")
public class CalculoCuotasAction  extends ActionSupport {
	
	private Date fecha;
	private int responsableDNI;
	private List<Integer> expElegidas;
	private int cantCuotas;
	
	private Double monto = 0.0;
	private Double tasaMensual = 0.0;
	private String tipoAmort;
	private String tipoExp;
	private ArrayList<CuotaDTO> ListaCuotas = new ArrayList<CuotaDTO>();
	private Map<String,Object> session;
	
	public String execute() {
		Session hSession = HibernateUtil.getSession();
		ResponsableAppl respAppl = new ResponsableAppl(hSession);
		List<ExpensaCobroDTO> expensas = crearExpensaCobros();
		
		monto = calcularMonto(expensas);
		tasaMensual = expensas.get(0).getLiquidacion().getPropiedad().getTipoPropiedad().getEdificio().getTasa_anual() / (100*12);
		tipoAmort =  expensas.get(0).getLiquidacion().getPropiedad().getTipoPropiedad().getEdificio().getAmortizacion();
		tipoExp = expensas.get(0).getLiquidacion().getTipo();
		
		PlanBuilder pb = new PlanBuilder();
		pb.setTasa(tasaMensual);
		pb.setTipo(tipoExp);
		pb.setSistema(tipoAmort);
		pb.setFecha(fecha);
		pb.setCantidadCuotas(cantCuotas);
		pb.setResponsable(respAppl.buscar(responsableDNI));
		
		for (ExpensaCobroDTO cobro : expensas) {
			pb.addExpensaCobro(cobro);
		}
		
		PlanDTO plan = pb.calcularPlan();

		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("lista",plan);
		this.setSession(session);

		//TODO ver si anda con la sesion cerrada
		hSession.close();
		return SUCCESS;
	}

	private double calcularMonto(List<ExpensaCobroDTO> expensas) {
		double monto = 0;
		
		for (ExpensaCobroDTO e : expensas) {
			monto += e.getMontoPago();
		}
		
		return monto;
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


	public void setSession(Map<String,Object> session) {
		this.session = session;
	}


	public Map<String,Object> getSession() {
		return session;
	}

}
