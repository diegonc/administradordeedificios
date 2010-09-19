package actions;



import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import usuarios.appl.UsuarioAppl;
import usuarios.dto.UsuarioDTO;
import beans.UsuariosBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetListadoUsuariosAction extends ActionSupport implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;

	public String execute() {
		UsuarioAppl usuarioAppl = new UsuarioAppl();
		List<UsuarioDTO> listaUsuario = usuarioAppl.getUsuarios() ;
			
		UsuariosBean listado = new UsuariosBean();
		listado.setUsers(listaUsuario);
		
		Map<String,Object> session = ActionContext.getContext().getSession();
        session.put("listado",listado);
        setSession(session);
        
        
    	 return "success";
    }

	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.session = arg0;
	}

}
