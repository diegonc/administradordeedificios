package actions;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.struts2.interceptor.SessionAware;
import usuarios.appl.UsuarioAppl;
import usuarios.dto.PerfilDTO;
import beans.PerfilesBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class UsuarioAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,Object> session;
	private String nombre;
	private String apellido;
	private String dni;
	private String contrasena;
    public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	@SuppressWarnings("unchecked")
	public String execute() {
		UsuarioAppl usuarioAppl = new UsuarioAppl();
		PerfilesBean perfiles = new PerfilesBean();
		List<PerfilDTO> listaPerfiles =  usuarioAppl.getPerfiles();
		perfiles.setPerfiles(listaPerfiles);
		
		
		
		
		Map session = ActionContext.getContext().getSession();
        session.put("perfiles",perfiles);
        this.setSession(session);
         
                       

    	 return "success";
    }
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.session=arg0;
		
	}

}
