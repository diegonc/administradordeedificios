package actions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.SessionFactory;

import usuarios.appl.UsuarioAppl;
import usuarios.dto.PerfilDTO;
import usuarios.dto.UsuarioDTO;
import utilidades.HibernateUtil;
import beans.EdificiosBean;
import beans.PerfilesBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edificio.EdificioAppl;
import edificio.EdificioDTO;


public class UsuarioAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,Object> session;
	
	private UsuarioDTO user ;
	
	private PerfilDTO perfil;
	
	private Integer id;
	
	private ArrayList<String> lista ;
	
	private String perfilesSeleccionados;
	
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
	public String getPerfilesSeleccionados() {
		return perfilesSeleccionados;
	}
	public void setPerfilesSeleccionados(String perfilesSeleccionados) {
		this.perfilesSeleccionados = perfilesSeleccionados;
	}
	public UsuarioDTO getUser() {
		return user;
	}
	public void setUser(UsuarioDTO usuarioDTO) {
		this.user = usuarioDTO;
	}
	public String actualizar(){
		
		return "actualizacion";
	}
	public String grabar(){
		boolean error=false;
		System.out.println(this.user.getApellido());
		List<PerfilDTO> perfiles = new ArrayList<PerfilDTO>();
		String[] perfilesTokens = this.perfilesSeleccionados.split(", ");
		for (int i = 0; i < perfilesTokens.length; i++) {
			PerfilDTO perfil =this.usuarioAppl.getPerfilByDescripcion(perfilesTokens[i]);
			perfiles.add(perfil);
		}
		user.setPerfiles(perfiles);
		this.usuarioAppl.addUsuario(user);
		
		if (error){
		addActionError("El usuario que quiere ingresar ya es existente.");
		return "error";
		}
		System.out.println(this.perfilesSeleccionados);
		return "add";
	}
	
	@SuppressWarnings("unchecked")
	public String execute() {
		 //recupero los edificios
		EdificiosBean listaEdificios = new EdificiosBean();
		EdificioAppl edifAppl = new EdificioAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		ArrayList<EdificioDTO> listaE = (ArrayList<EdificioDTO>) edifAppl.getAllEdificios(factory);
		listaEdificios.setEdificios(listaE);
		   
		
		List<PerfilDTO> listaPerfiles =  usuarioAppl.getPerfiles();
			
		Map session = ActionContext.getContext().getSession();
	    session.put("edificios",listaEdificios);
               
        this.lista= new ArrayList<String>();
        for (PerfilDTO p :listaPerfiles) {
        	lista.add(p.getDescripcion());
			
		}

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
