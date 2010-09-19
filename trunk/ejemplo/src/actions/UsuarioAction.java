package actions;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.struts2.interceptor.SessionAware;
import usuarios.appl.UsuarioAppl;
import usuarios.dto.PerfilDTO;
import usuarios.dto.UsuarioDTO;
import beans.PerfilesBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class UsuarioAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,Object> session;
	private UsuarioDTO user ;
	private PerfilDTO perfil;
	
	public UsuarioDTO getUser() {
		return user;
	}
	public void setUser(UsuarioDTO usuarioDTO) {
		this.user = usuarioDTO;
	}
		
	public String grabar(){
		boolean error=false;
		System.out.println(this.user.getApellido());
		UsuarioAppl usuarioAppl = new UsuarioAppl();
		PerfilDTO perfil1 = new PerfilDTO();
		perfil1.setId(1);				
		this.user.setPerfil(perfil1);
		usuarioAppl.addUsuario(user);
		if (error){
		addActionError("El usuario que quiere ingresar ya es existente.");
		return "error";
		}
		return "add";
	}
	@SuppressWarnings("unchecked")
	public String execute() {
		UsuarioAppl usuarioAppl = new UsuarioAppl();
		PerfilesBean perfilesBean = new PerfilesBean();
		List<PerfilDTO> listaPerfiles =  usuarioAppl.getPerfiles();
		perfilesBean.setPerfiles(listaPerfiles);	
		Map session = ActionContext.getContext().getSession();
        session.put("perfilesBean",perfilesBean);
        this.setSession(session);        
    	 return "success";
    }
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.session=arg0;
		
	}

}
