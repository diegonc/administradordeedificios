package actions;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import usuarios.appl.UsuarioAppl;
import usuarios.dto.UsuarioDTO;
import usuarios.exception.UsuarioExistenteException;
import usuarios.exception.UsuarioInexistenteException;

import com.opensymphony.xwork2.ActionSupport;


public class UsuarioAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,Object> session;
	
	private UsuarioDTO user ;
	
	private Integer id;
	
	private ArrayList<String> lista ;
	
		
	public ArrayList<String> getLista() {
		return lista;
	}
	public void setLista(ArrayList<String> lista) {
		this.lista = lista;
	}
	
	UsuarioAppl usuarioAppl = new UsuarioAppl();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public UsuarioDTO getUser() {
		return user;
	}
	public void setUser(UsuarioDTO usuarioDTO) {
		this.user = usuarioDTO;
	}
	public String actualizar(){
		
		this.usuarioAppl.updateUsuario(user, this.getId());
		
		return "actualizacion";
	}
	
	public String grabar(){
			
		try {
			this.usuarioAppl.addUsuario(user);
		} catch (UsuarioExistenteException e) {
			addActionError("El usuario que quiere ingresar ya es existente.");
			return "error";
		}
		return "add";
	}
	
	
	public String execute() {
		
        if(this.id!=null){
        	try {
				user=usuarioAppl.getUsuario(this.id.intValue());
			} catch (UsuarioInexistenteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	  session.put("user",user);
        }
        this.setSession(session);
    	 return "success";
    	 
    }
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.session=arg0;
		
	}

}
