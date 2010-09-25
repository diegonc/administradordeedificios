package actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import propiedades.TipoPropiedadDAO;
import propiedades.TipoPropiedadDTO;
import propiedades.TipoPropiedadTipoGastoDTO;
import propiedades.TipoPropiedadTipoGastoDAO;
import usuarios.dto.AdministradorDePermisos;

import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import edificio.EdificioAppl;
import edificio.EdificioDTO;
import gastos.appl.TiposGastosAppl;
import gastos.dto.TipoGastoDTO;

public class TiposPropiedadesAction extends ActionSupport implements Preparable {

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
	@SessionTarget
	private Session session;
	private TipoPropiedadDAO dao = new TipoPropiedadDAO();
	private TipoPropiedadTipoGastoDAO daoTPTG = new TipoPropiedadTipoGastoDAO();
	private EdificioAppl edificioAppl = new EdificioAppl();

	/** Edificio seleccionado. */
	private EdificioDTO edificioActual;
	/** Lista de edificios disponibles. */
	private Collection<String> edificios;

	private TipoPropiedadDTO entidad;
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

	public void prepare() throws Exception {
	}

	private void cargarListaEdificios() {
		edificios = new ArrayList<String>();

		//Carga la lista segun el perfil que tiene el usuario que se logea
		AdministradorDePermisos administrador = AdministradorDePermisos.getInstancia();
		if (!administrador.visibleTodosLosEdificios()){
			edificios.add(administrador.getUser().getEdificio().getNombre());
		}else{
			try {
				for (Object o : session.createQuery("from EdificioDTO").list()) {
					EdificioDTO edificio = (EdificioDTO) o;
					edificios.add(edificio.getNombre());
				}
			} catch (HibernateException e) {
				session.getTransaction().rollback();
			}
		}
	}

	public String listar() {
		cargarListaEdificios();
		if (nombreEdificio != null) {
			edificioActual = edificioAppl.buscarEdificioPorNombre(session,
					nombreEdificio);
			lista = edificioActual.getTipoPropiedades();
		}
		return SUCCESS;
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
		if (nombreEdificio == null || nombreTipo == null) {
			if (nombreEdificio == null)
				addActionError("No se ha seleccionado edificio.");
			if (nombreTipo == null)
				addActionError("No se ha seleccionado tipo de propiedad.");
			return false;
		}
		return true;
	}

	public String crear() {
		entidad = new TipoPropiedadDTO();
		cargarTiposGastosAsociados();
		cargarTiposGastosDisponibles();
		return "edicion";
	}

	public void prepareGrabar() throws Exception {
		cargarTipoPropiedad(entidad.getNombreTipo());
		
		for (String c : tiposGastos.keySet()) {
			TipoPropiedadTipoGastoDTO tptg = new TipoPropiedadTipoGastoDTO();
			tptg.setTipoGasto(new TiposGastosAppl().getTipoGastoPorCodigo(c));
			tiposGastos.put(c, tptg);
		}
	}

	public String grabar() {
		edificioActual.agregarTipo(entidad);
		mergeTiposGastos();
		dao.grabar(entidad);
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

	public String borrar() {
		cargarTipoPropiedad(nombreTipo);
		try {
			dao.eliminar(entidad);
		} catch (HibernateException e) {
			addActionError("No se puede eliminar el tipo de propiedad mientras este asociado a propiedades o tipos de gastos.");
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

	public String agregarTipos() {

		for (String c : codigosTipoGastoAAgregar) {
			TipoPropiedadTipoGastoDTO tptg = new TipoPropiedadTipoGastoDTO();
			tptg.setTipoGasto(new TiposGastosAppl().getTipoGastoPorCodigo(c));
			tiposGastos.put(c, tptg);
		}

		cargarTiposGastosDisponibles();

		return "edicion";
	}

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
