package actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import propiedades.PropiedadDTO;
import propiedades.Responsable;
import propiedades.ResponsableAppl;
import utilidades.HibernateUtil;

import beans.LiquidacionBean;
import beans.ResponsablesBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import expensas.appl.ExpensaAppl;
import expensas.dto.ExpensaDTO;

public class ExpensasHistorialAction extends ActionSupport {
	
	private Map<String, Object> session;
	
	private int dni;
	
	private int dniElegido;
	
	private int idEdificio;
	
	private String consultarEdificio;
	
	private String tipoExpensaElegida;
	
	private Integer nivel=null;
	
	private Integer orden = null;
	
	
	
	public Integer getNivel() {
		return nivel;
	}
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	public String getTipoExpensaElegida() {
		return tipoExpensaElegida;
	}
	public void setTipoExpensaElegida(String tipoExpensaElegida) {
		this.tipoExpensaElegida = tipoExpensaElegida;
	}
	public String getConsultarEdificio() {
		return consultarEdificio;
	}
	public void setConsultarEdificio(String consultarEdificio) {
		this.consultarEdificio = consultarEdificio;
	}
	public int getIdEdificio() {
		return idEdificio;
	}
	public void setIdEdificio(int idEdificio) {
		this.idEdificio = idEdificio;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	
	public int getDniElegido() {
		return dniElegido;
	}
	public void setDniElegido(int dniElegido) {
		this.dniElegido = dniElegido;
	}
	public String mostrar(){
		LiquidacionBean bean = new LiquidacionBean();
		ExpensaAppl expensaAppl = new ExpensaAppl();
		List<ExpensaDTO> expensas = new ArrayList<ExpensaDTO>();
		if (consultarEdificio==null){
			expensas = expensaAppl.historialExpensas(dniElegido, tipoExpensaElegida, null, orden, nivel);	
		}else{
			expensas = expensaAppl.historialExpensas(dniElegido, tipoExpensaElegida,Integer.valueOf(idEdificio), orden, nivel);
		}
			
		
		
		bean.setExpensasOrdinarias(expensas);
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("liquidaciones", bean);
		
		//TODO: borrar para control
		System.out.println("Dni del responsable: " + this.dniElegido);
		System.out.println("Elegio consultar por edificio: "+this.consultarEdificio);
		System.out.println("Edificio elegido: "+this.idEdificio);
		System.out.println("Nivel ingresado: "+this.nivel);
		System.out.println("Orden ingresado: "+this.orden);
		System.out.println("Tipo de expensa elegida: "+this.tipoExpensaElegida);
		
		return "mostrar";
	}
	
	public String execute(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		ResponsablesBean responsableBean = new ResponsablesBean();
		ResponsableAppl responsableAppl = new ResponsableAppl(HibernateUtil.getSessionFactory().openSession());		
		List<Integer> listaDeResponsablesExistentes = new ArrayList<Integer>();		
		listaDeResponsablesExistentes = responsableAppl.obtenerResponsablesConExpensas();
		responsableBean.setListaDNIs(listaDeResponsablesExistentes);
		session.put("responsables",responsableBean);
		
		
		
		return "success";
	}
	
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}


}
