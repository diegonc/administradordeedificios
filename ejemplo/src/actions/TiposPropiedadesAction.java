package actions;

import java.util.Collection;

import propiedades.TipoPropiedadDAO;
import propiedades.TipoPropiedadDTO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import edificio.EdificioDTO;

public class TiposPropiedadesAction extends ActionSupport implements Preparable {

	private TipoPropiedadDTO entidad;
	private Collection<TipoPropiedadDTO> lista;
	private TipoPropiedadDAO dao = new TipoPropiedadDAO();
	private String nombreTipo;
	private EdificioDTO edificioActual;

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
		edificioActual = (EdificioDTO)ActionContext.getContext()
			.getSession()
			.get("edificio-seleccionado");
		/* paramsPrepareParamsStack
		 *    Se carga el objeto en la sesión, para que la segunda vez
		 *    que se procesen los parámetros se actualice un objeto
		 *    persistente.
		 */
		if (entidad != null && entidad.getNombreTipo() != null)
			entidad = dao.buscar(entidad.getNombreTipo());
	}

	public String listar() {
		// lista = edificioActual.getTipoPropiedades()
		lista = dao.listar();
		return SUCCESS;
	}
	
	public String editar() {
		entidad = dao.buscar(nombreTipo);
		return "edicion";
	}
	
	public String crear() {
		return "edicion";
	}
	
	public String grabar() {
		dao.grabar(entidad);
		return SUCCESS;
	}
	
	public String borrar() {
		dao.eliminar(dao.buscar(nombreTipo));
		return "borrar";
	}

}
