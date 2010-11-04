package actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import permisos.AdministradorDePermisos;
import propiedades.PropiedadAppl;
import propiedades.PropiedadDTO;
import propiedades.Responsable;
import propiedades.ResponsableAppl;
import propiedades.TipoPropiedadDTO;

import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import edificio.EdificioAppl;
import edificio.EdificioDTO;

import utilidades.HibernateUtil;

@SuppressWarnings("serial")
public class PropiedadesAction extends ActionSupport implements Preparable {

	private static final Logger LOG = LoggerFactory.getLogger(PropiedadesAction.class);

	/* Parametros de la accion */
	/*
	 * Nombre del edificio seleccionado por el usuario. O null si no ha
	 * seleccionado ninguno.
	 */
	private String nombreEdificio;
	/*
	 * Nombre del tipo de propiedad seleccionado. O null si el usuario aún no ha
	 * especificado ninguno.
	 */
	private String nombreTipo;
	/*
	 * Clave de la propiedad a modificar o eliminar. O null si el usuario aún no
	 * ha especificado ninguna.
	 */
	private Integer nivel;
	private Integer orden;

	/* Atributos soporte */
	@SessionTarget
	private Session session;
	private PropiedadAppl dao = new PropiedadAppl();
	private ResponsableAppl daoResp = new ResponsableAppl();
	private EdificioAppl edificioAppl = new EdificioAppl();

	/* Lista de edificios disponibles. */
	private Collection<String> listaEdificios;
	/* Lista de propiedades disponibles. */
	private Collection<PropiedadDTO> listaPropiedades;
	/* Lista de tipos de propiedades disponibles. */
	private Collection<String> listaTiposPropiedades;
	/* Edificio seleccionado. */
	private EdificioDTO edificioActual;
	/* Tipo de propiedad seleccionado */
	private TipoPropiedadDTO tipoPropiedadActual;
	/* Propiedad editada o creada. */
	private PropiedadDTO entidad;
	/* Lista de responsables disponibles. */
	private Collection<String> listaResponsables;

	public String getNombreEdificio() {
		return nombreEdificio;
	}

	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
	}

	public String getNombreTipo() {
		return nombreTipo;
	}

	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}

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

	public Collection<String> getListaEdificios() {
		return listaEdificios;
	}

	public void setListaEdificios(Collection<String> listaEdificios) {
		this.listaEdificios = listaEdificios;
	}

	public Collection<PropiedadDTO> getListaPropiedades() {
		return listaPropiedades;
	}

	public void setListaPropiedades(Collection<PropiedadDTO> listaPropiedades) {
		this.listaPropiedades = listaPropiedades;
	}

	public Collection<String> getListaTiposPropiedades() {
		return listaTiposPropiedades;
	}

	public void setListaTiposPropiedades(
			Collection<String> listaTiposPropiedades) {
		this.listaTiposPropiedades = listaTiposPropiedades;
	}

	public PropiedadDTO getEntidad() {
		return entidad;
	}

	public void setEntidad(PropiedadDTO entidad) {
		this.entidad = entidad;
	}
	
	public Collection<String> getListaResponsables() {
		return listaResponsables;
	}

	public void setListaResponsables(Collection<String> listaResponsables) {
		this.listaResponsables = listaResponsables;
	}

	public void prepare() throws Exception {
	}

	private void cargarListaEdificios() {
		listaEdificios = new ArrayList<String>();
				
		try {
			if (AdministradorDePermisos.getInstancia().isAdministrador()){
				for (Object o : session.createQuery("from EdificioDTO").list()) {
					EdificioDTO edificio = (EdificioDTO) o;
					listaEdificios.add(edificio.getNombre());
				}
			}else{
				if (AdministradorDePermisos.getInstancia().isResponsableEdificios()){
					List<EdificioDTO> listaAux =  AdministradorDePermisos.getInstancia().getEdificiosResponsableEdificios();
					for (EdificioDTO edificioDTO : listaAux) {
						listaEdificios.add(edificioDTO.getNombre());
					}
				}
			}
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			throw e;
		}

	}

	@SkipValidation
	public String listar() {
		cargarListaEdificios();
		if (nombreEdificio != null) {
			edificioActual = edificioAppl.buscarEdificioPorNombre(session,	nombreEdificio);
			if (edificioActual != null) {
				listaPropiedades = new ArrayList<PropiedadDTO>();
				for (TipoPropiedadDTO tipo : edificioActual.getTipoPropiedades()) {
					for (PropiedadDTO propiedad : tipo.getPropiedades())
						listaPropiedades.add(propiedad);
				}
			} else {
				addActionError("No existe el edificio '" + nombreEdificio + "'");
			}
		}
		return SUCCESS;
	}

	@SkipValidation
	public String editar() {
		if (!tieneClave())
			return SUCCESS;

		cargarEdificio(nombreEdificio);
		cargarTipoPropiedad(nombreTipo);
		cargarPropiedad(nivel, orden);

		if (entidad == null) {
			addActionError(String.format(
					"La propiedad '%s %d/%d' no existe en el edificio '%s'.",
					nombreTipo, nivel, orden, nombreEdificio));
			return SUCCESS;
		}
		
		cargarListaTiposPropiedades();
		cargarResponsables();
		return "edicion";
	}

	private void cargarResponsables() {
		List<Responsable> listaResponsables = daoResp.listar();
		this.listaResponsables = new ArrayList<String>(); 
		for (Responsable r : listaResponsables) {
			Integer dni = r.getDni();
			String sdni = dni.toString();
			this.listaResponsables.add(sdni);
		}
	}

	private void cargarListaTiposPropiedades() {
		listaTiposPropiedades = new ArrayList<String>();
		if (edificioActual != null) {
			for (TipoPropiedadDTO tipo : edificioActual.getTipoPropiedades())
				listaTiposPropiedades.add(tipo.getNombreTipo());
		}
	}

	private void cargarEdificio(String nombreEdificio) {
		edificioActual = edificioAppl.buscarEdificioPorNombre(session,
				nombreEdificio);
	}

	private void cargarTipoPropiedad(String nombreTipo) {
		if (edificioActual != null)
			for (TipoPropiedadDTO tipo : edificioActual.getTipoPropiedades())
				if (tipo.getNombreTipo().equals(nombreTipo)) {
					tipoPropiedadActual = tipo;
					break;
				}
	}

	private void cargarPropiedad(Integer nivel, Integer orden) {
		entidad = null;
		if (tipoPropiedadActual != null)
			for (PropiedadDTO propiedad : tipoPropiedadActual.getPropiedades())
				if (nivel.equals(propiedad.getNivel())
						&& orden.equals(propiedad.getOrden())) {
					entidad = propiedad;
					break;
				}
	}

	private boolean tieneClave() {
		return tieneEdificio()
			&& tieneTipoPropiedad()
			&& tienePropiedad();
	}

	private boolean tieneEdificio() {
		if (nombreEdificio == null) {
			addActionError("No se ha seleccionado edificio.");
			return false;
		}
		return true;
	}

	private boolean tieneTipoPropiedad() {
		if (nombreTipo == null) {
			addActionError("No se ha seleccionado tipo de propiedad.");
			return false;
		}
		return true;
	}

	private boolean tienePropiedad() {
		if (nivel == null || orden == null) {
			addActionError("No se ha seleccionado una propiedad.");
			return false;
		}
		return true;
	}

	@SkipValidation
	public String crear() {
		if (tieneEdificio()) {
			cargarEdificio(nombreEdificio);
			if (edificioActual != null) {
				cargarListaTiposPropiedades();
				cargarResponsables();
				return "edicion";
			} else
				addActionError("El edificio '" + nombreEdificio + "' no existe.");
		}
		return "error";
	}

	public void prepareGrabar() throws Exception {
		cargarEdificio(nombreEdificio);
		cargarTipoPropiedad(entidad.getTipoPropiedad().getNombreTipo());
		cargarPropiedad(entidad.getNivel(), entidad.getOrden());
		if (entidad == null) {
			entidad = new PropiedadDTO();
			entidad.setTipoPropiedad(tipoPropiedadActual);
		}

	}

	private boolean asociarResponsables() {
		Integer dni;
		Responsable resp;
		boolean errores = false;

		if ((resp = daoResp.buscar(entidad.getPropietario().getDni())) != null)
			entidad.setPropietario(resp);
		else {
			addFieldError("entidad.propietario.dni", "No existe el responsable.");
			errores = true;
		}

		if ( (dni = entidad.getInquilino().getDni()) != null
		   &&(resp = daoResp.buscar(dni)) != null)
			entidad.setInquilino(resp);
		else if (dni == null)
			entidad.setInquilino(null);
		else {
			addFieldError("entidad.inquilino.dni", "No existe el responsable.");
			errores = true;
		}

		if ( (dni = entidad.getPoderPropietario().getDni()) != null
		   &&(resp = daoResp.buscar(dni)) != null)
			entidad.setPoderPropietario(resp);
		else if (dni == null)
			entidad.setPoderPropietario(null);
		else {
			addFieldError("entidad.poderInquilino.dni", "No existe el responsable.");
			errores = true;
		}

		if ( (dni = entidad.getPoderInquilino().getDni()) != null
		   &&(resp = daoResp.buscar(dni)) != null)
			entidad.setPoderInquilino(resp);
		else if (dni == null)
			entidad.setPoderInquilino(null);
		else {
			addFieldError("entidad.poderInquilino.dni", "No existe el responsable.");
			errores = true;
		}
		return !errores;
	}
	
	@InputConfig(methodName="validationErrors")
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "nombreEdificio", message = "El nombre del edificio es obligatorio."),
			@RequiredStringValidator(fieldName = "entidad.tipoPropiedad.nombreTipo", message = "El tipo de propiedad es obligatorio.")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "entidad.nivel", message = "El nivel es obligatorio."),
			@RequiredFieldValidator(fieldName = "entidad.orden", message = "El orden es obligatorio."),
			@RequiredFieldValidator(fieldName = "entidad.propietario.dni", message = "El propietario es obligatorio.")
		},
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "entidad.nivel", message = "El campo debe ser numerico."),
			@ConversionErrorFieldValidator(fieldName = "entidad.orden", message = "El campo debe ser numerico."),
			@ConversionErrorFieldValidator(fieldName = "entidad.propietario.dni", message = "El campo debe ser numerico."),
			@ConversionErrorFieldValidator(fieldName = "entidad.inquilino.dni", message = "El campo debe ser numerico."),
			@ConversionErrorFieldValidator(fieldName = "entidad.poderPropietario.dni", message = "El campo debe ser numerico."),
			@ConversionErrorFieldValidator(fieldName = "entidad.poderInquilino.dni", message = "El campo debe ser numerico."),
			@ConversionErrorFieldValidator(fieldName = "entidad.ctaExtSaldoExp", message = "El campo debe ser numerico."),
			@ConversionErrorFieldValidator(fieldName = "entidad.ctaExtSaldoInt", message = "El campo debe ser numerico."),
			@ConversionErrorFieldValidator(fieldName = "entidad.ctaOrdSaldoExp", message = "El campo debe ser numerico."),
			@ConversionErrorFieldValidator(fieldName = "entidad.ctaOrdSaldoInt", message = "El campo debe ser numerico."),
			@ConversionErrorFieldValidator(fieldName = "entidad.dividendo", message = "El campo debe ser numerico."),
		})
	public String grabar() {
		try {
			if ( asociarResponsables() ) {
				session.beginTransaction();
				dao.grabar(entidad);
				session.getTransaction().commit();
			} else return validationErrors();
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
			LOG.error("No se pudo guardar la propiedad.", e);
			addActionError(e.getMessage());
			/* La sesion de Hibernate ya no sirve. Se abre otra
			 * para que validationErrors pueda funcionar. */
			setSession(HibernateUtil.getSession());
			try {
				return validationErrors();
			} finally {
				session.close();
			}
		}
		return SUCCESS;
	}
	
	public String validationErrors() {
		cargarListaTiposPropiedades();
		cargarResponsables();
		return "edicion";
	}

	@SkipValidation
	public String borrar() {
		try {
			cargarEdificio(nombreEdificio);
			cargarTipoPropiedad(nombreTipo);
			cargarPropiedad(nivel, orden);
			session.beginTransaction();
			dao.eliminar(entidad);
			session.getTransaction().commit();
			return SUCCESS;
		} catch (HibernateException e) {
			addActionError(e.getMessage() + "\n" + e.toString());
			return "error";
		}
	}

	public void setSession(Session session) {
		this.session = session;
		dao.setSession(session);
		dao.setTransaction(session.getTransaction());
		daoResp.setSession(session);
		daoResp.setTransaction(session.getTransaction());
	}

}
