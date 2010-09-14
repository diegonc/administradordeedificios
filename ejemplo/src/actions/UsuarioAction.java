package actions;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;

import org.apache.struts2.interceptor.SessionAware;

import usuarios.dto.UsuarioDTO;


import beans.UsuariosBean;

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
	
	public String execute() {
		
    	UsuarioDTO user1 = new UsuarioDTO();
    	UsuarioDTO user2 = new UsuarioDTO();
    	
    	user1.setUsuario("aChelotti");
    	user1.setPerfil(0);
    	user1.setPassword("aChelotti");
    	user1.setNombre("Adriana");
    	user1.setApellido("Chelotti");
    	user1.setDni(31026053);
    	
    	
    	
    	user2.setUsuario("dStaltari");
    	user2.setPerfil(0);
    	user2.setPassword("dStaltari");
    	user2.setNombre("Dario");
    	user2.setApellido("Staltari");    	
    	user2.setDni(30672871);
    	
    	LinkedList<UsuarioDTO> lista = new LinkedList<UsuarioDTO>();
    	lista.add(user1);
    	lista.add(user2);
    	UsuariosBean listaUsuarios = new UsuariosBean();
    	listaUsuarios.setUsers(lista);
    	
    	Map session = ActionContext.getContext().getSession();
         session.put("lista",listaUsuarios);
        this.setSession(session);
         
                       

    	 return "success";
    }
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.session=arg0;
		
	}

}
