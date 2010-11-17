package actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import propiedades.PropiedadDTO;
import propiedades.Responsable;

import beans.LiquidacionBean;
import beans.ResponsablesBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import expensas.dto.ExpensaDTO;

public class ExpensasHistorialAction extends ActionSupport {
	
	private Map<String, Object> session;
	
	private int dni;
	
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public String mostrar(){
		LiquidacionBean bean = new LiquidacionBean();
		//TODO: ver de traer expensas por responsable
		List<ExpensaDTO> expensas = obtenerExpensas();
		bean.setExpensasOrdinarias(expensas);
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("liquidaciones", bean);
		return "mostrar";
	}
	private List<ExpensaDTO> obtenerExpensas() {
		List<ExpensaDTO> expensas = new ArrayList<ExpensaDTO>();
		ExpensaDTO exp1 = new ExpensaDTO();
		ExpensaDTO exp2 = new ExpensaDTO();
		exp1.setDeudaPrevia(1000);
		exp2.setDeudaPrevia(1100);
		exp1.setMonto(400);
		exp2.setMonto(200);
		exp1.setIntereses(122);
		exp2.setIntereses(122);
		exp1.setInteresSegundoVencimiento(12);
		exp2.setInteresSegundoVencimiento(12);
		exp1.setNumeroOperacion(1);
		exp2.setNumeroOperacion(2);
		exp1.setTipo(ExpensaDTO.tipoOrdinario);
		exp2.setTipo(ExpensaDTO.tipoExtraordinario);
		PropiedadDTO prop = new PropiedadDTO();
		prop.setNivel(1);
		prop.setOrden(2);
		exp1.setPropiedad(prop);
		exp2.setPropiedad(prop);				
		expensas.add(exp1);
		expensas.add(exp2);
		return expensas;
	}
	public String execute(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		List<Responsable> listaDeResponsablesExistentes = new ArrayList<Responsable>();
		//TODO: consultar responsables que tenga una propiedad asignada y alguna liquidacion
		ResponsablesBean bean = obtenerResponsables(listaDeResponsablesExistentes);
		bean.setResponsables(listaDeResponsablesExistentes);
		session.put("responsables",bean);
		
		
		
		return "success";
	}
	private ResponsablesBean obtenerResponsables(
			List<Responsable> listaDeResponsablesExistentes) {
		Responsable responsable1 = new Responsable();
		responsable1.setDni(new Integer(23243123));
		Responsable responsable2 = new Responsable();
		responsable2.setDni(new Integer(234242343));
		ResponsablesBean bean = new ResponsablesBean();		
		listaDeResponsablesExistentes.add(responsable1);
		listaDeResponsablesExistentes.add(responsable2);
		return bean;
	}
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}


}
