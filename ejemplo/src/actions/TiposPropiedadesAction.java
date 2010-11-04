package actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.HibernateException;

import permisos.AdministradorDePermisos;
import propiedades.TipoPropiedadAppl;
import propiedades.TipoPropiedadDTO;
import propiedades.TipoPropiedadTipoGastoDTO;
import propiedades.TipoPropiedadTipoGastoAppl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import edificio.EdificioAppl;
import edificio.EdificioDTO;
import gastos.appl.TiposGastosAppl;
import gastos.dto.TipoGastoDTO;
import gastos.exception.TipoGastoInexistenteException;
import utilidades.SessionAwareAction;

@SuppressWarnings("serial")
public class TiposPropiedadesAction extends SessionAwareAction implements Preparable {

	private static final Logger LOG = LoggerFactory.getLogger(TiposPropiedadesAction.class);

	/* Parametros de la accion */
	/*
	 * Nombre del edificio seleccionado por el usuario. O null si no ha
	 * seleccionado ninguno.
	 */
	private String nombreEdificio;
	/*
	 * Nombre del tipo de propiedad que se va a modificar o borrar. O null si el
	 * usuario a�n no ha especificado ninguno.
	 */
	private String nombreTipo;

	/* Atributos soporte */
	private TipoPropiedadAppl dao = new TipoPropiedadAppl();
	private TipoPropiedadTipoGastoAppl daoTPTG = new TipoPropiedadTipoGastoAppl();
	private EdificioAppl edificioAppl = new EdificioAppl();

	/** Edificio seleccionado. */
	private EdificioDTO edificioActual;
	/** Lista de edificios disponibles. */
	private Collection<String> edificios;

	private TipoPropiedadDTO entidad = new TipoPropiedadDTO();
	private Collection<TipoPropiedadDTO> lista;

	public TipoPropiedadDTO getEntidad() {
		return entidad;
	}

	public void setEntidad(TipoPropiedadDTO entidad) {
		this.entidad = entidad;
	}

	public Collection<TipoPropiedadDTO> getLista() {
		return lista;
	}

	public void setLista(Collection<TipoPropiedadDTO> lista) {
		this.lista = lista;
	}

	public String getNombreTipo() {
		return nombreTipo;
	}

	public void setNombreTipo(String nuevoNombre) {
		this.nombreTipo = nuevoNombre;
	}

	public String errorValidacion() {
		cargarTiposGastosDisponibles();
		return "edicion";
	}

	public void prepare() throws Exception {
	}

	@Override
	protected void onSetSession() {
		//XXX: la transaccion debe iniciarse para que los cambios persistan.
		getSession().beginTransaction();
		// XXX: todos los DAOs deben usar la misma sesion.
		dao.setSession(getSession());
		dao.setTransaction(getTransaction());
		daoTPTG.setSession(getSession());
		daoTPTG.setTransaction(getTransaction());
	}

	public void validate() {
		// XXX: no hay anotaciones para validar una coleccion
		for (TipoPropiedadTipoGastoDTO tptg : tiposGastos.values()) {
			try {
				TipoGastoDTO tg = tptg.getTipoGasto();
				String fieldName = "tiposGastos['"+tg.getCodigo()+"'].coeficienteDistribucion";
				Map<String,Object> conversionErrors = ActionContext.getContext().getConversionErrors();
				if (conversionErrors.containsKey(fieldName)) {
					addFieldError(fieldName, "El campo debe estar vacio o ser un numero.");
				} 
			} catch(NullPointerException e) {
				// no deberia pasar xD
				LOG.warn("El tipo de propiedad ha sido asociado a un tipo de gasto nulo.", e);
			}
		}
	}

	private void cargarListaEdificios() {
		edificios = new ArrayList<String>();
	//agrego la parte de los permisos
		try {
			if (AdministradorDePermisos.getInstancia().isAdministrador()){
				for (Object o : session.createQuery("from EdificioDTO").list()) {
					EdificioDTO edificio = (EdificioDTO) o;
					edificios.add(edificio.getNombre());
				}
			}else{
				if (AdministradorDePermisos.getInstancia().isResponsableEdificios()){
					List<EdificioDTO> listaAux =  AdministradorDePermisos.getInstancia().getEdificiosResponsableEdificios();
					for (EdificioDTO edificioDTO : listaAux) {
						edificios.add(edificioDTO.getNombre());
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
		try {
			cargarListaEdificios();
			if (nombreEdificio != null) {
				edificioActual = edificioAppl.buscarEdificioPorNombre(session,
					nombreEdificio);
				if (edificioActual != null)
					lista = edificioActual.getTipoPropiedades();
				else
					addActionError("No existe el edificio '" + nombreEdificio + "'");
			}
			return SUCCESS;
		} catch (Exception e) {
			LOG.error("Error en el metodo listar.", e);
			addActionError(e.getMessage());
			return "error";
		}
	}

	private void cargarTipoPropiedad(String nombreTipo) {
		edificioActual = edificioAppl.buscarEdificioPorNombre(session,
				nombreEdificio);
		for (TipoPropiedadDTO tipo : edificioActual.getTipoPropiedades())
			if (tipo.getNombreTipo().equals(nombreTipo)) {
				entidad = tipo;
				break;
			}
	}

	private boolean tieneClave() {
		return tieneEdificio() && tieneTipoPropiedad();

	}

	private boolean tieneTipoPropiedad() {
		if (nombreTipo == null) {
			addActionError("No se ha seleccionado tipo de propiedad.");
			return false;
		}
		return true;
	}

	private boolean tieneEdificio() {
		if (nombreEdificio == null) {
			addActionError("No se ha seleccionado edificio.");
			return false;
		}
		return true;
	}

	@SkipValidation
	public String crear() {
		if (tieneEdificio()) {
			cargarTiposGastosAsociados();
			cargarTiposGastosDisponibles();
			return "edicion";
		} else
			return "error";
	}

	public void prepareGrabar() throws Exception {
		cargarTipoPropiedad(entidad.getNombreTipo());

		for (String c : tiposGastos.keySet()) {
			TipoPropiedadTipoGastoDTO tptg = new TipoPropiedadTipoGastoDTO();
			tptg.setTipoGasto(new TiposGastosAppl().getTipoGastoPorCodigo(c));
			tiposGastos.put(c, tptg);
		}
	}

	@InputConfig(methodName="errorValidacion")
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "nombreEdificio", message = "El nombre del edificio es obligatorio."),
			@RequiredStringValidator(fieldName = "entidad.nombreTipo", message = "El nombre de tipo de propiedad es obligatorio.")
		},
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "entidad.montoExp", message = "El campo debe ser numerico."),
			@ConversionErrorFieldValidator(fieldName = "entidad.montoExpExt", message = "El campo debe ser numerico."),
			@ConversionErrorFieldValidator(fieldName = "entidad.divisor", message = "El campo debe ser numerico.")
		})
	public String grabar() {
		try {
			edificioActual.agregarTipo(entidad);
			dao.grabar(entidad);
			mergeTiposGastos();
			getTransaction().commit();
			return SUCCESS;
		} catch(Exception e) {
			LOG.error("Error al grabar tipo de propiedad.", e);
			return SUCCESS;
		}
	}

	@SkipValidation
	public String cancelar() {
		return SUCCESS;
	}

	public void mergeTiposGastos() {
		/* Se remueven los que no estan */
		Iterator<TipoPropiedadTipoGastoDTO> it = entidad.getTipoGastos().iterator();
		while (it.hasNext()) {
			TipoPropiedadTipoGastoDTO tptg = it.next();
			String codigo = tptg.getTipoGasto().getCodigo();
			if (!tiposGastos.containsKey(codigo)) {
				daoTPTG.eliminar(tptg);
				it.remove();
			}
		}

		/* Se actualizan los que estan y se agrega el resto */
		for (TipoPropiedadTipoGastoDTO tptg : tiposGastos.values()) {
			TipoPropiedadTipoGastoDTO actual = entidad.getTipoGasto(tptg.getTipoGasto().getCodigo());
			if (actual != null) {
				actual.setCoeficienteDistribucion(tptg.getCoeficienteDistribucion());
			} else {
				entidad.addTipoGasto(tptg);
				actual = tptg;
			}
			daoTPTG.grabar(actual);
		}
	}

	public void prepareBorrar() {
		cargarTipoPropiedad(nombreTipo);
	}

	public String borrar() {
		try {
			dao.eliminar(entidad);
			getTransaction().commit();
		} catch (HibernateException e) {
			addActionError("No se puede eliminar el tipo de propiedad mientras este asociado a propiedades o tipos de gastos.");
			LOG.error("Error al borrar entidad.", e);
			return "error";
		}
		return SUCCESS;
	}

	public EdificioDTO getEdificioActual() {
		return edificioActual;
	}

	public void setEdificioActual(EdificioDTO edificioActual) {
		this.edificioActual = edificioActual;
	}

	public Collection<String> getEdificios() {
		return edificios;
	}

	public void setEdificios(Collection<String> edificios) {
		this.edificios = edificios;
	}

	public String getNombreEdificio() {
		return nombreEdificio;
	}

	@RequiredFieldValidator(message="El nombre de edificio es obligatorio.")
	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
	}

	// ///////////////////////////////////////////////////////
	/*
	 * Edición de tipo de propiedad y asociaciones con tipos de gastos.
	 * 
	 * listar() --[usuario selecciona Agregar/Editar]--> editar()
	 * editar() --[usuario selecciona Acceptar]--> grabar()
	 * editar() --[usuario selecciona Agregar Tipo]--> agregarTipo()
	 * editar() --[usuario selecciona Borrar Tipo]--> borrarTipo()
	 * agregarTipo() --[usuario selecciona Agregar Tipo]--> agregarTipo()
	 * agregarTipo() --[usuario selecciona Borrar Tipo]--> borrarTipo()
	 * borrarTipo() --[usuario selecciona Agregar Tipo]--> agregarTipo()
	 * borrarTipo() --[usuario selecciona Borrar Tipo]--> borrarTipo()
	 * agregarTipo() --[usuario selecciona Acceptar]--> grabar()
	 * borrarTipo() --[usuario selecciona Acceptar]--> grabar()
	 * 
	 * editar(): Se carga la lista de tipos de gastos del objeto a editar en la
	 *           colección Action.tiposGastos.
	 * 
	 * agregarTipo(): Se modifica la colección Action.tiposGastos agregando el
	 *                tipo seleccionado. No se recupera la lista del objeto nuevamente.
	 * 
	 * borrarTipo(): Se modifica la colección Action.tiposGastos quitando el
	 *               tipo seleccionado. No se recupera la lista del objeto nuevamente.
	 * 
	 * grabar(): Reemplaza la lista del objeto con la colección
	 *           Action.tiposGastos realizando un merge.
	 */
	/* Codigos del tipo a agregar */
	private String[] codigosTipoGastoAAgregar = {};
	/* Codigos del tipo a borrar */
	private String[] codigosTipoGastoABorrar = {};
	/*
	 * Codigo->Descripcion de los tipos de gastos que el tipo de propiedad aun
	 * no tiene.
	 */
	private Map<String, String> mapaTiposGastosDisponibles = new HashMap<String, String>();
	/* Codigo->TPTGDTO de los tipos de gastos asociados al tipo de propiedad. */
	private Map<String, TipoPropiedadTipoGastoDTO> tiposGastos = new HashMap<String, TipoPropiedadTipoGastoDTO>();

	public Map<String, TipoPropiedadTipoGastoDTO> getTiposGastos() {
		return tiposGastos;
	}

	public void setTiposGastos(
			Map<String, TipoPropiedadTipoGastoDTO> tiposGastos) {
		this.tiposGastos = tiposGastos;
	}

	@SkipValidation
	public String editar() {
		if (!tieneClave())
			return SUCCESS;

		cargarTipoPropiedad(nombreTipo);

		if (entidad == null) {
			addActionError(String.format(
					"El tipo '%s' no existe en el edificio '%s'.", nombreTipo,
					nombreEdificio));
			return SUCCESS;
		}

		cargarTiposGastosAsociados();
		cargarTiposGastosDisponibles();

		return "edicion";
	}

	@SkipValidation
	public String agregarTipos() {

		for (String c : codigosTipoGastoAAgregar) {
			TipoPropiedadTipoGastoDTO tptg = new TipoPropiedadTipoGastoDTO();
			try {
				tptg.setTipoGasto(new TiposGastosAppl().getTipoGastoPorCodigo(c));
			} catch (TipoGastoInexistenteException e) {
				// TODO mensaje error
				e.printStackTrace();
			}
			tiposGastos.put(c, tptg);
		}

		cargarTiposGastosDisponibles();

		return "edicion";
	}

	@SkipValidation
	public String borrarTipos() {

		for (String c : codigosTipoGastoABorrar) {
			tiposGastos.remove(c);
		}

		cargarTiposGastosDisponibles();

		return "edicion";
	}

	private void cargarTiposGastosDisponibles() {
		mapaTiposGastosDisponibles = new HashMap<String, String>();
		for (TipoGastoDTO tg : listarTodosTiposGastos()) {
			if (!tiposGastos.containsKey(tg.getCodigo()))
				mapaTiposGastosDisponibles.put(tg.getCodigo(),
						tg.getDescripcion());
		}
	}

	private Collection<TipoGastoDTO> listarTodosTiposGastos() {
		return new TiposGastosAppl().getAllTipoGasto();
	}

	private void cargarTiposGastosAsociados() {
		tiposGastos = new HashMap<String, TipoPropiedadTipoGastoDTO>();
		for (TipoPropiedadTipoGastoDTO tg : entidad.getTipoGastos()) {
			tiposGastos.put(tg.getTipoGasto().getCodigo(), tg);
		}
	}

	public Map<String, String> getMapaTiposGastosDisponibles() {
		return mapaTiposGastosDisponibles;
	}

	public void setMapaTiposGastosDisponibles(
			Map<String, String> mapaTipoGastosDisponibles) {
		this.mapaTiposGastosDisponibles = mapaTipoGastosDisponibles;
	}

	public String[] getCodigosTipoGastoAAgregar() {
		return codigosTipoGastoAAgregar;
	}

	public void setCodigosTipoGastoAAgregar(String[] codigosTipoGastoAAgregar) {
		this.codigosTipoGastoAAgregar = codigosTipoGastoAAgregar;
	}

	public String[] getCodigosTipoGastoABorrar() {
		return codigosTipoGastoABorrar;
	}

	public void setCodigosTipoGastoABorrar(String[] codigosTipoGastoABorrar) {
		this.codigosTipoGastoABorrar = codigosTipoGastoABorrar;
	}

}
