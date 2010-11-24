package actions;

import java.awt.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import planes.CuotaDTO;
import planes.PlanDTO;
import propiedades.ResponsableAppl;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import expensas.appl.ExpensaAppl;
import expensas.dto.ExpensaDTO;




@SuppressWarnings("serial")
public class CalculoCuotasAction  extends ActionSupport {
	
	private Date fecha;
	private int responsableDNI;
	private String expElegidas;
	private int cantCuotas;
	
	private Double monto = 0.0;
	private Double tasaMensual = 0.0;
	private String tipoAmort;
	private String tipoExp;
	private ArrayList<CuotaDTO> ListaCuotas = new ArrayList<CuotaDTO>();
	private Map<String,Object> session;
	
	public String execute() {
		PlanDTO plan = new PlanDTO();
		
		ExpensaAppl expAppl = new ExpensaAppl();
		ArrayList<ExpensaDTO> expensas = new ArrayList<ExpensaDTO>();
		ArrayList<Integer> listaId = new ArrayList<Integer>();
		for (String numero: expElegidas.split (", "))
			listaId.add (new Integer (numero));
		java.util.Iterator<Integer> iteId = listaId.iterator();
		
		int expId;
		while (iteId.hasNext()) {
			expId = iteId.next();
			ExpensaDTO exp = expAppl.getExpensasById(expId);
			expensas.add(exp);
			monto = monto + exp.getMonto();
		}
		
		tasaMensual = expensas.get(0).getPropiedad().getTipoPropiedad().getEdificio().getTasa_anual() / (100*12);
		tipoAmort =  expensas.get(0).getPropiedad().getTipoPropiedad().getEdificio().getAmortizacion();
		tipoExp = expensas.get(0).getTipo();
		
		Double dividendo = 0.0;
		Double divisor = 0.0;
		Double MontoCuota = 0.0;
		if (tipoAmort.equals("FRANCES")) {
			dividendo = Math.pow(1+tasaMensual,cantCuotas)-1;
			divisor = tasaMensual * Math.pow((1 + tasaMensual),cantCuotas);
			MontoCuota = monto / (dividendo/divisor);
			int i = 0;
			while (i<cantCuotas) {
				CuotaDTO cuota = new CuotaDTO();
				cuota.setIntereses(MontoCuota * (1 - (1/Math.pow(1+tasaMensual, cantCuotas - i + 1))));
				cuota.setMonto(MontoCuota * (1 / Math.pow(1+tasaMensual, cantCuotas - i + 1)));
				cuota.setNumeroCuota(i+1);
				ListaCuotas.add(cuota);
				i++;
			}
			Double saldoTotal = cantCuotas * MontoCuota;
			Double intereses = saldoTotal - monto;
			
			//TODO buscar el responsable por DNI que viene de planesExpensasListado.jsp... tambien TODO
			//plan.setResponsable(respAppl.buscar(30761872));
			plan.setFecha(fecha);
			plan.setTipo(tipoExp);
			plan.setCantidadCuotas(cantCuotas);
			plan.setMonto(monto);
			plan.setSaldoIntereses(intereses);
			plan.setSaldoPlan(saldoTotal);
			plan.setCuotas(ListaCuotas);
			
		} else if (tipoAmort.equals("ALEMAN")) {
			
		}
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("lista",plan);
		this.setSession(session);
		return SUCCESS;
	}


	public void setExpElegidas(String expElegidas) {
		this.expElegidas = expElegidas;
	}


	public String getExpElegidas() {
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
