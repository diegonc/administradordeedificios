package actions;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import propiedades.PropiedadDAO;
import propiedades.PropiedadDTO;
import propiedades.ResponsableDAO;
import propiedades.TipoPropiedadDTO;

import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import edificio.EdificioAppl;
import edificio.EdificioDTO;

public class PropiedadesAction extends ActionSupport implements Preparable {

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
	private PropiedadDAO dao = new PropiedadDAO();
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
	private ResponsableDAO daoResp;

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

	public void prepare() throws Exception {
	}

	private void cargarListaEdificios() {
		listaEdificios = new ArrayList<String>();

		try {
			for (Object o : session.createQuery("from EdificioDTO").list()) {
				EdificioDTO edificio = (EdificioDTO) o;
				listaEdificios.add(edificio.getNombre());
			}
		} catch (HibernateException e) {
			session.getTransaction().rollback();
		}

	}

	public String listar() {
		cargarListaEdificios();
		if (nombreEdificio != null) {
			edificioActual = edificioAppl.buscarEdificioPorNombre(session,
					nombreEdificio);
			listaPropiedades = new ArrayList<PropiedadDTO>();
			for (TipoPropiedadDTO tipo : edificioActual.getTipoPropiedades()) {
				for (PropiedadDTO propiedad : tipo.getPropiedades())
					listaPropiedades.add(propiedad);
			}
		}
		return SUCCESS;
	}

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
		
		listaTiposPropiedades = new ArrayList<String>();
		if (edificioActual != null) {
			for (TipoPropiedadDTO tipo : edificioActual.getTipoPropiedades())
				listaTiposPropiedades.add(tipo.getNombreTipo());
		}
		return "edicion";
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
		boolean claveCompleta = true;

		if (nombreEdificio == null) {
			addActionError("No se ha seleccionado edificio.");
			claveCompleta = false;
		}
		if (nombreTipo == null) {
			addActionError("No se ha seleccionado tipo de propiedad.");
			claveCompleta = false;
		}
		if (nivel == null || orden == null) {
			addActionError("No se ha seleccionado una propiedad.");
			claveCompleta = false;
		}
		return claveCompleta;
	}

	public String crear() {
		cargarEdificio(nombreEdificio);
		listaTiposPropiedades = new ArrayList<String>();
		if (edificioActual != null) {
			for (TipoPropiedadDTO tipo : edificioActual.getTipoPropiedades())
				listaTiposPropiedades.add(tipo.getNombreTipo());
		}
		return "edicion";
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

	public String grabar() {
		dao.grabar(entidad);
		return SUCCESS;
	}

	public String borrar() {
		cargarEdificio(nombreEdificio);
		cargarTipoPropiedad(entidad.getTipoPropiedad().getNombreTipo());
		cargarPropiedad(entidad.getNivel(), entidad.getOrden());
		dao.eliminar(entidad);
		return SUCCESS;
	}

}
