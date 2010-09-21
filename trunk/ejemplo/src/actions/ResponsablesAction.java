package actions;

import java.util.List;

import propiedades.Responsable;
import propiedades.ResponsableDAO;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ResponsablesAction extends ActionSupport implements Preparable {

	private Responsable entidad;
	private List<Responsable> lista;
	private ResponsableDAO dao = new ResponsableDAO();
	private Integer dni;

	public Responsable getEntidad() {
		return entidad;
	}

	public void setEntidad(Responsable edificio) {
		this.entidad = edificio;
	}

	public List<Responsable> getLista() {
		return lista;
	}

	public void setLista(List<Responsable> lista) {
		this.lista = lista;
	}
	
	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}
	
	
	public void prepare() throws Exception {
		/* paramsPrepareParamsStack
		 *    Se carga el objeto en la sesión, para que la segunda vez
		 *    que se procesen los parámetros se actualice un objeto
		 *    persistente.
		 */
		if (entidad != null && entidad.getDni() != null)
			entidad = dao.buscar(entidad.getDni());
	}

	public String listar() {
		lista = dao.listar();
		return SUCCESS;
	}
	
	public String editar() {
		entidad = dao.buscar(dni);
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
		dao.eliminar(dao.buscar(dni));
		return SUCCESS;
	}

}
