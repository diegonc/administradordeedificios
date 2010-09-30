package actions;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import usuarios.appl.UsuarioAppl;
import usuarios.dto.UsuarioDTO;
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
		boolean error=false;
	
		this.usuarioAppl.addUsuario(user);
		
		if (error){
		addActionError("El usuario que quiere ingresar ya es existente.");
		return "error";
		}
	
		return "add";
	}
	
	
	public String execute() {
		
        if(this.id!=null){
        	user=usuarioAppl.getUsuario(this.id.intValue());
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
