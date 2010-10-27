package actions;


import java.util.List;

import org.hibernate.SessionFactory;

import utilidades.HibernateUtil;

import com.opensymphony.xwork2.ActionSupport;

import expensas.appl.ExpensasCobroAppl;
import expensas.dto.ExpensaCobroDTO;
import expensas.dto.ExpensaDTO;

@SuppressWarnings("serial")
public class ExpensasPropiedadesListadoAction extends ActionSupport {
	
	/* Parametros de la accion */
	private Integer idEdificio;
	private Integer propElegida;

	public String consultarCobros() {
		return "consultar-cobros";
	}

	public Integer getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Integer idEdificio) {
		this.idEdificio = idEdificio;
	}

	public void setPropElegida(Integer prop) {
		this.propElegida = prop;
	}
	public Integer getPropElegida() {
		return this.propElegida;
	}
	
	public String mostrarFormulario() {
		return "registrar-cobro";
	}
	
	
	
}
