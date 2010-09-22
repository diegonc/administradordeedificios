package actions;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.SessionFactory;

import usuarios.appl.UsuarioAppl;
import usuarios.dto.PerfilDTO;
import usuarios.dto.UsuarioDTO;
import utilidades.HibernateUtil;
import beans.EdificiosBean;
import beans.PerfilesBean;
import beans.UsuariosBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edificio.EdificioAppl;
import edificio.EdificioDTO;

public class GetListadoUsuariosAction extends ActionSupport implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	
	private Integer id;
	
	private UsuarioAppl userAppl = new UsuarioAppl();
	
	private UsuarioDTO user;
	
	private ArrayList<String> lista ;
	
	public ArrayList<String> getLista() {
		return lista;
	}

	public void setLista(ArrayList<String> lista) {
		this.lista = lista;
	}

	public String getPerfilesSeleccionados() {
		return perfilesSeleccionados;
	}

	public void setPerfilesSeleccionados(String perfilesSeleccionados) {
		this.perfilesSeleccionados = perfilesSeleccionados;
	}

	private String perfilesSeleccionados;
	

	public UsuarioDTO getUser() {
		return user;
	}

	public void setUser(UsuarioDTO user) {
		this.user = user;
	}

	public Integer getId() {
		return id;	
		}

	public void setId(Integer id) {
		this.id = id;
	}
	public String eliminar(){
		if (this.id!=null)
			userAppl.removeUsuario(this.id.intValue());		
		return "eliminar";
	}
	
	
	public String editar(){
		 //recupero los edificios
		EdificiosBean listaEdificios = new EdificiosBean();
		EdificioAppl edifAppl = new EdificioAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		ArrayList<EdificioDTO> listaE = (ArrayList<EdificioDTO>) edifAppl.getAllEdificios(factory);
		listaEdificios.setEdificios(listaE);
		   
		
		if(this.id!=null)
		this.user = userAppl.getUsuario(id.intValue());
		
		List<PerfilDTO> listaPerfiles =  userAppl.getPerfiles();
        this.lista= new ArrayList<String>();
        for (PerfilDTO p :listaPerfiles) {
        	lista.add(p.getDescripcion());
			
		}

		Map session = ActionContext.getContext().getSession();
	    session.put("edificios",listaEdificios);
        
		UsuariosBean userEditar = new UsuariosBean();
		userEditar.setUsuarioUnico(this.user);
		
        session.put("usuarioBean",userEditar);
        setSession(session);
    
		return "edicion";
	}
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
