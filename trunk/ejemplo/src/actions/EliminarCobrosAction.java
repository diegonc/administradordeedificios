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

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.Session;

@SuppressWarnings("serial")
public class EliminarCobrosAction extends SessionAwareAction {

	private static final Logger LOG = LoggerFactory.getLogger(EliminarCobrosAction.class);

	/* Parametros de la accion */
	private Integer idCobro;
	private Integer idPropiedad;

	/* Atributos soporte */
	
	@SkipValidation
	public String back() {
		return SUCCESS /*"volver-a-listado"*/;
	}

	@InputConfig(methodName="errorValidacion")
	public String execute() {
		try {
			Session session = getSession();
			ExpensaCobroDTO cobro = (ExpensaCobroDTO) session.get(ExpensaCobroDTO.class, idCobro);
			session.beginTransaction();
			session.delete(cobro);
			session.getTransaction().commit();
			return SUCCESS;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return errorValidacion();
		}
	}

	public String errorValidacion() {
		return back();
	}

	public Integer getIdPropiedad() {
		return idPropiedad;
	}

	@ConversionErrorFieldValidator(message="El valor debe ser numerico.")
	@RequiredFieldValidator(message="No hay propiedad.")
	public void setIdPropiedad(Integer idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

	public Integer getIdCobro() {
		return idCobro;
	}

	@ConversionErrorFieldValidator(message="El valor debe ser numerico.")
	@RequiredFieldValidator(message="No hay cobro.")
	public void setIdCobro(Integer idCobro) {
		this.idCobro = idCobro;
	}

}
