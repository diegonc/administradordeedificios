package actions;

import java.util.List;
import java.util.Map;

import usuarios.appl.UsuarioAppl;
import usuarios.dto.UsuarioDTO;
import beans.UsuariosBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GetListadoVinculacionAction extends ActionSupport {
	
	private UsuarioAppl userAppl = new UsuarioAppl();
	
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@SuppressWarnings("unused")
	private Map<String, Object> session;
	
	
	public String execute() {
			
			
			List<UsuarioDTO> listaUsuario = userAppl.getUsuarios() ;
			UsuariosBean listado = new UsuariosBean();
			listado.setUsers(listaUsuario);		
			Map<String,Object> session = ActionContext.getContext().getSession();
	        session.put("listado",listado);
	        setSession(session);
	              
	    	 return "listar";
	    }
	
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

}
