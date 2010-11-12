package actions;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import propiedades.PropiedadDTO;

import utilidades.SessionAwareAction;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

import edificio.EdificioAppl;
import edificio.EdificioDTO;
import expensas.dto.ExpensaCobroDTO;

@SuppressWarnings("serial")
public class ConsolidarCobrosAction extends SessionAwareAction {

	private static final Logger LOG = LoggerFactory.getLogger(ConsolidarCobrosAction.class);

	/* Parametros de la accion */
	private Integer idCobro;
	private Integer idPropiedad;

	/* Atributos soporte */
	private Integer idEdificio;
	
	@SkipValidation
	public String back() {
		if (idPropiedad != null) {
			PropiedadDTO propiedad = (PropiedadDTO) getSession().get(PropiedadDTO.class, idPropiedad);
			setIdEdificio(propiedad.getTipoPropiedad()
				.getEdificio()
				.getId());
			return "volver-a-listado";
		}
		return "no-hay-edificio";
	}

	@InputConfig(methodName="errorValidacion")
	public String execute() {
		try {
			Session session = getSession();
			PropiedadDTO propiedad = (PropiedadDTO) getSession().get(PropiedadDTO.class, idPropiedad);
			ExpensaCobroDTO cobro = (ExpensaCobroDTO) session.get(ExpensaCobroDTO.class, idCobro);
			EdificioAppl edifAppl = new EdificioAppl();
			EdificioDTO edificio = cobro.getLiquidacion().getPropiedad().getTipoPropiedad().getEdificio();
			
			//primero le suma al saldo de intereses y si este pasa el 0 se lo suma al del saldo..
			if (cobro.getLiquidacion().getTipo().equals("O")) {
				edificio.setFondo_ordinario(edificio.getFondo_ordinario() + cobro.getMontoPago());
				propiedad.setCtaOrdSaldoInt(propiedad.getCtaOrdSaldoInt() + cobro.getMontoPago());
				if (propiedad.getCtaOrdSaldoInt() > 0) {
					propiedad.setCtaOrdSaldoExp(propiedad.getCtaOrdSaldoExp() + propiedad.getCtaOrdSaldoInt());
					propiedad.setCtaOrdSaldoInt(0);
				}
			} else {
				edificio.setFondo_extraordinario(edificio.getFondo_extraordinario() + cobro.getMontoPago());
				propiedad.setCtaExtSaldoInt(propiedad.getCtaExtSaldoInt() + cobro.getMontoPago());
				if (propiedad.getCtaExtSaldoInt() > 0) {
					propiedad.setCtaExtSaldoExp(propiedad.getCtaExtSaldoExp() + propiedad.getCtaExtSaldoInt());
					propiedad.setCtaExtSaldoInt(0);
				}
			}
			edifAppl.updateEdifcio(session, edificio);
			cobro.setConsolidado(true);
			session.beginTransaction();
			session.update(propiedad);
			session.update(cobro);
			session.getTransaction().commit();
			return back();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			if (e instanceof HibernateException)
				renewSession();
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

	public void setIdEdificio(Integer idEdificio) {
		this.idEdificio = idEdificio;
	}

	public Integer getIdEdificio() {
		return idEdificio;
	}

}
