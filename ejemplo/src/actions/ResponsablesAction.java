package actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.HibernateException;

import propiedades.Responsable;
import propiedades.ResponsableAppl;
import utilidades.SessionAwareAction;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

@SuppressWarnings("serial")
public class ResponsablesAction extends SessionAwareAction implements Preparable {

	private Responsable entidad = new Responsable();
	private List<Responsable> lista = new ArrayList<Responsable>();
	private ResponsableAppl dao = new ResponsableAppl();

	public Integer getDni() { return entidad.getDni(); }
	@ConversionErrorFieldValidator(message="El campo debe ser numerico.", shortCircuit=true)
	@RequiredFieldValidator(message="El campo es obligatorio.")
	public void setDni(Integer dni) { entidad.setDni(dni); }

	public String getNombre() { return entidad.getNombre(); }
	public void setNombre(String nombre) { entidad.setNombre(nombre); }

	public String getTelefono() { return entidad.getTelefono(); }
	public void setTelefono(String telefono) { entidad.setTelefono(telefono); }

	public String getEmail() { return entidad.getEmail(); }
	@EmailValidator(message="No es un email valido")
	public void setEmail(String email) { entidad.setEmail(email); }

	public String getLocalidad() { return entidad.getLocalidad(); }
	public void setLocalidad(String localidad) { entidad.setLocalidad(localidad); }

	public String getCalle() { return entidad.getCalle(); }
	public void setCalle(String calle) { entidad.setCalle(calle); }

	public String getUbicacion() { 	return entidad.getUbicacion(); }
	public void setUbicacion(String ubicacion) { entidad.setUbicacion(ubicacion); }

	public Boolean getAutoridad() { return entidad.getAutoridad(); }

	public void setAutoridad(Boolean autoridad) { entidad.setAutoridad(autoridad); }

	public List<Responsable> getLista() {
		return lista;
	}

	public void setLista(List<Responsable> lista) {
		this.lista = lista;
	}
	
	public void prepare() throws Exception {
		/* paramsPrepareParamsStack
		 *    Se carga el objeto en la sesión, para que la segunda vez
		 *    que se procesen los parámetros se actualice un objeto
		 *    persistente.
		 */
		try {
			if (entidad != null && entidad.getDni() != null) {
				Responsable tmp = dao.buscar(entidad.getDni());
				if (tmp != null)
					entidad = tmp;
			}
        } catch (Exception e) {
			addActionError(e.getMessage());
			if (e instanceof HibernateException)
				renewSession();
		}
	}

	@SkipValidation
	public String listar() {
		try {
			lista = dao.listar();
		} catch (Exception e) {
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}
	
	@SkipValidation
	public String editar() {
		return "edicion";
	}
	
	@SkipValidation
	public String crear() {
		return "edicion";
	}

	@InputConfig(methodName="errorValidacion")
	public String grabar() {
		try {
			getSession().beginTransaction();
			dao.grabar(entidad);
			getTransaction().commit();
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			return "edicion";
		}
	}

	@InputConfig(methodName="listar")
	public String borrar() {
		try {
			getSession().beginTransaction();
			dao.eliminar(entidad);
			getTransaction().commit();
		} catch (Exception e) {
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	public String errorValidacion() {
		return "edicion";
	}

	@Override
	protected void onSetSession() {
		dao.setSession(getSession());
		dao.setTransaction(getTransaction());
	}

}
