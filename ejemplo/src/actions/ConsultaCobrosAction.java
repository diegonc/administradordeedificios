package actions;

import expensas.appl.ExpensasCobroAppl;
import expensas.dto.ExpensaCobroDTO;

import java.util.List;

import utilidades.SessionAwareAction;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;


@SuppressWarnings("serial")
public class ConsultaCobrosAction extends SessionAwareAction {

	private static final Logger LOG = LoggerFactory.getLogger(ConsultaCobrosAction.class);

	/* Parametros de la accion */
	private Integer idEdificio;
	private Integer idPropiedad;

	/* Atributos soporte */
	
	/* Lista de cobros de la propiedad. */
	private List<ExpensaCobroDTO> cobros;

	public String back() {
		return errorValidacion();
	}

	@InputConfig(methodName="errorValidacion")
	public String listar() {
		try {
			cobros = new ExpensasCobroAppl(getSession())
				.listarCobrosDePropiedad(idPropiedad);
			return SUCCESS;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return errorValidacion();
		}
	}

	public String errorValidacion() {
		if (idEdificio != null)
			return "no-hay-propiedad";
		else
			return "no-hay-edificio";
	}

	public Integer getIdPropiedad() {
		return idPropiedad;
	}

	@ConversionErrorFieldValidator(message="El valor debe ser numerico.")
	@RequiredFieldValidator(message="No hay propiedad.")
	public void setIdPropiedad(Integer idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

	public Integer getIdEdificio() {
		return idEdificio;
	}

	@ConversionErrorFieldValidator(message="El valor debe ser numerico.")
	public void setIdEdificio(Integer idEdificio) {
		this.idEdificio = idEdificio;
	}

	public List<ExpensaCobroDTO> getCobros() {
		return cobros;
	}
	public void setCobros(List<ExpensaCobroDTO> cobros) {
		this.cobros = cobros;
	}

	@ConversionErrorFieldValidator(message="El valor debe ser numerico.")
	@RequiredFieldValidator(message="No hay propiedad.")
	public void setPropElegida(Integer prop) {
		this.idPropiedad = prop;
	}
	public Integer getPropElegida() {
		return this.idPropiedad;
	}
}
