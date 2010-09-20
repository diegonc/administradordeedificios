package actions;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;

import propiedades.TipoPropiedadDAO;
import propiedades.TipoPropiedadDTO;

import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import edificio.EdificioAppl;
import edificio.EdificioDTO;

public class TiposPropiedadesAction extends ActionSupport implements Preparable {

	/* Parametros de la accion */
	/*
	 * Nombre del edificio seleccionado por el usuario. O null si no ha
	 * seleccionado ninguno.
	 */
	private String nombreEdificio;
	/*
	 * Nombre del tipo de propiedad que se va a modificar o borrar. O null si el
	 * usuario aún no ha especificado ninguno.
	 */
	private String nombreTipo;

	/* Atributos soporte */
	@SessionTarget
	private Session session;
	private TipoPropiedadDAO dao = new TipoPropiedadDAO();
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
		for (Object o : session.createQuery("from EdificioDTO").list()) {
			EdificioDTO edificio = (EdificioDTO) o;
			edificios.add(edificio.getNombre());
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
		return "edicion";
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
		return "edicion";
	}

	public void prepareGrabar() throws Exception {
		cargarTipoPropiedad(entidad.getNombreTipo());
	}

	public String grabar() {
		edificioActual.agregarTipo(entidad);
		dao.grabar(entidad);
		return SUCCESS;
	}

	public String borrar() {
		cargarTipoPropiedad(nombreTipo);
		dao.eliminar(entidad);
		return "borrar";
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

}
