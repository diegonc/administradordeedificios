package actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import usuarios.appl.UsuarioAppl;
import usuarios.dto.PerfilDTO;
import usuarios.dto.UsuarioDTO;
import usuarios.exception.UsuarioInexistenteException;
import utilidades.HibernateUtil;
import beans.UsuariosBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edificio.EdificioAppl;
import edificio.EdificioDTO;

public class VincularUsuarioPerfilEdificios extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Integer id;
	
	private UsuarioAppl userAppl = new UsuarioAppl();
	
	private UsuarioDTO user;
	
	private String administrador;
	private String responsableGastos;
	private String responsableCobros;
	private String responsableEdificios;
	
	private List<String> listaEdificiosDefault;
	private List<String> listaEdificiosDefault1;
	private List<String> listaEdificiosDefault2;
	
	private List<String> edificiosResponsableGastosResult;
	private List<String> edificiosResponsableCobrosResult;
	private List<String> edificiosResponsableEdificioResult;
	
	@SuppressWarnings("unused")
	private Map<String, Object> session;

	


	public List<String> getEdificiosResponsableEdificioResult() {
		return edificiosResponsableEdificioResult;
	}

	public void setEdificiosResponsableEdificioResult(
			List<String> edificiosResponsableEdificioResult) {
		this.edificiosResponsableEdificioResult = edificiosResponsableEdificioResult;
	}

	public List<String> getListaEdificiosDefault() {
		return listaEdificiosDefault;
	}

	public void setListaEdificiosDefault(List<String> listaEdificiosDefault) {
		this.listaEdificiosDefault = listaEdificiosDefault;
	}
	
	public List<String> getListaEdificiosDefault1() {
		return listaEdificiosDefault1;
	}

	public void setListaEdificiosDefault1(List<String> listaEdificiosDefault1) {
		this.listaEdificiosDefault1 = listaEdificiosDefault1;
	}

	public List<String> getEdificiosResponsableGastosResult() {
		return edificiosResponsableGastosResult;
	}

	public void setEdificiosResponsableGastosResult(
			List<String> edificiosResponsableGastosResult) {
		this.edificiosResponsableGastosResult = edificiosResponsableGastosResult;
	}

	public List<String> getListaEdificiosDefault2() {
		return listaEdificiosDefault2;
	}

	public void setListaEdificiosDefault2(List<String> listaEdificiosDefault2) {
		this.listaEdificiosDefault2 = listaEdificiosDefault2;
	}

	public List<String> getEdificiosResponsableCobrosResult() {
		return edificiosResponsableCobrosResult;
	}

	public void setEdificiosResponsableCobrosResult(
			List<String> edificiosResponsableCobrosResult) {
		this.edificiosResponsableCobrosResult = edificiosResponsableCobrosResult;
	}

	public String getAdministrador() {
		return administrador;
	}

	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}

	public String getResponsableGastos() {
		return responsableGastos;
	}

	public void setResponsableGastos(String responsableGastos) {
		this.responsableGastos = responsableGastos;
	}

	public String getResponsableCobros() {
		return responsableCobros;
	}

	public void setResponsableCobros(String responsableCobros) {
		this.responsableCobros = responsableCobros;
	}

	public String getResponsableEdificios() {
		return responsableEdificios;
	}

	public void setResponsableEdificios(String responsableEdificios) {
		this.responsableEdificios = responsableEdificios;
	}

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

	@SuppressWarnings("unchecked")
	public String vincular(){
		
		if(this.id!=null)
			try {
				this.user = userAppl.getUsuario(id.intValue());
		
			} catch (UsuarioInexistenteException e) {
				// TODO mostrar erros
				e.printStackTrace();
			}
		Map session = ActionContext.getContext().getSession();
		UsuariosBean userEditar = new UsuariosBean();
		userEditar.setUsuarioUnico(this.user);
		obtenerTodosEdificios();
        session.put("usuarioBean",userEditar);
        setSession(session);
		return "vincular";
	}
	
	
	private List<String> restarListas(List<EdificioDTO> listaTotal,List<EdificioDTO> listaSacar){
		ArrayList<String> listaRestante = new ArrayList<String>();
		for (EdificioDTO edificioDTO : listaTotal) {
			boolean seRepite=false;
			for (EdificioDTO edificio2 : listaSacar) {
				if (edificioDTO.getId()==edificio2.getId()){
					seRepite=true;
				}
				
			}
			if(!seRepite){
				listaRestante.add(edificioDTO.getNombre());
			}
		}
		return listaRestante;
		
		
	}
	
	private ArrayList<String> getListaDeNombres(List<EdificioDTO> edificios){
		ArrayList<String> nombres= new ArrayList<String>();
		for (EdificioDTO edif : edificios) {
			nombres.add(edif.getNombre());
			
		}
		return nombres;
	}
	private void obtenerTodosEdificios(){
		System.out.println(this.id);
		EdificioAppl edifAppl = new EdificioAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		UsuarioDTO usuario = null;
		try {
			usuario = this.userAppl.getUsuario(this.id);
		} catch (UsuarioInexistenteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//listado de izquierad
		List<PerfilDTO> perfilesDelUsuario =usuario.getPerfiles();		
		for (PerfilDTO perfilDTO : perfilesDelUsuario) {
			if(perfilDTO.getDescripcion().equals(PerfilDTO.RESPONSABLE_EDIFICIO))this.edificiosResponsableEdificioResult = this.getListaDeNombres(this.userAppl.getUsuarioPerfil(this.id,PerfilDTO.RESPONSABLE_EDIFICIO ).getEdificios());
			if(perfilDTO.getDescripcion().equals(PerfilDTO.RESPONSABLE_GASTOS))this.edificiosResponsableGastosResult = this.getListaDeNombres(this.userAppl.getUsuarioPerfil(this.id,PerfilDTO.RESPONSABLE_GASTOS ).getEdificios());
			if(perfilDTO.getDescripcion().equals(PerfilDTO.RESPONSABLE_COBROS))this.edificiosResponsableCobrosResult = this.getListaDeNombres(this.userAppl.getUsuarioPerfil(this.id,PerfilDTO.RESPONSABLE_COBROS ).getEdificios());
		}
		if (this.edificiosResponsableEdificioResult==null)this.edificiosResponsableEdificioResult= new ArrayList<String>();
		if (this.edificiosResponsableGastosResult==null)this.edificiosResponsableGastosResult= new ArrayList<String>();
		if (this.edificiosResponsableCobrosResult==null)this.edificiosResponsableCobrosResult= new ArrayList<String>();
		
		try {
			//TODO: permisos
			ArrayList<EdificioDTO> lista = (ArrayList<EdificioDTO>) edifAppl.getAllEdificios(factory);			
			this.listaEdificiosDefault= getListaDeNombres(lista);
			this.listaEdificiosDefault1= getListaDeNombres(lista);
			this.listaEdificiosDefault2= getListaDeNombres(lista);
			for (PerfilDTO perfilDTO : perfilesDelUsuario) {
				if(perfilDTO.getDescripcion().equals(PerfilDTO.RESPONSABLE_EDIFICIO))this.listaEdificiosDefault=restarListas(lista,this.userAppl.getUsuarioPerfil(this.id,PerfilDTO.RESPONSABLE_EDIFICIO ).getEdificios());
				if(perfilDTO.getDescripcion().equals(PerfilDTO.RESPONSABLE_GASTOS))this.listaEdificiosDefault1=restarListas(lista,this.userAppl.getUsuarioPerfil(this.id,PerfilDTO.RESPONSABLE_GASTOS ).getEdificios());
				if(perfilDTO.getDescripcion().equals(PerfilDTO.RESPONSABLE_COBROS))this.listaEdificiosDefault2=restarListas(lista,this.userAppl.getUsuarioPerfil(this.id,PerfilDTO.RESPONSABLE_COBROS ).getEdificios());		
				
				
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private List<EdificioDTO> getEdificiosByName(List<String> listaNombreEdificios){
		EdificioAppl edifAppl = new EdificioAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		ArrayList<EdificioDTO> todosLosEdificios = (ArrayList<EdificioDTO>) edifAppl.getAllEdificios(factory);
		List<EdificioDTO> listaResultado = new ArrayList<EdificioDTO>();
		
		for (EdificioDTO edificio: todosLosEdificios){
			for (String edificioNombre : listaNombreEdificios) {
				if (edificio.getNombre().equals(edificioNombre)){
					listaResultado.add(edificio);
				}
			
			}
		}
		return listaResultado;
	}
	
	public String execute(){
		@SuppressWarnings("unused")
		UsuarioDTO usuario= null;
		List<PerfilDTO> perfiles= new ArrayList<PerfilDTO>();
		try {
			 usuario= userAppl.getUsuario(this.id);
		} catch (UsuarioInexistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if (this.administrador!=null){
			perfiles.add( userAppl.getPerfilByDescripcion(PerfilDTO.ADMINISTRADOR));
		}
		if (this.responsableEdificios!=null){
			perfiles.add( userAppl.getPerfilByDescripcion(PerfilDTO.RESPONSABLE_EDIFICIO));
		}
		if (this.responsableGastos!=null){
			perfiles.add( userAppl.getPerfilByDescripcion(PerfilDTO.RESPONSABLE_GASTOS));
		}
		if (this.responsableCobros!=null){
			perfiles.add( userAppl.getPerfilByDescripcion(PerfilDTO.RESPONSABLE_COBROS));
		}
		
		
		try {
			userAppl.actualizarPerfiles(perfiles, this.id);
		} catch (UsuarioInexistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (this.administrador!=null){
			EdificioAppl edifAppl = new EdificioAppl();
			SessionFactory factory = HibernateUtil.getSessionFactory();
			ArrayList<EdificioDTO> todosLosEdificios = (ArrayList<EdificioDTO>) edifAppl.getAllEdificios(factory);
			userAppl.actualizarEdificiosParaUsuarioPerfil(todosLosEdificios, this.id,PerfilDTO.ADMINISTRADOR );
	
		}
		if ((this.edificiosResponsableEdificioResult!=null) && (this.responsableEdificios!=null)){
			List<EdificioDTO> todosLosEdificios = this.getEdificiosByName(this.edificiosResponsableEdificioResult);
			userAppl.actualizarEdificiosParaUsuarioPerfil(todosLosEdificios, this.id, PerfilDTO.RESPONSABLE_EDIFICIO);
		}
		
		if ((this.edificiosResponsableCobrosResult!=null) && (this.responsableCobros!=null)){
			List<EdificioDTO> todosLosEdificios = this.getEdificiosByName(this.edificiosResponsableCobrosResult);
			userAppl.actualizarEdificiosParaUsuarioPerfil(todosLosEdificios, this.id, PerfilDTO.RESPONSABLE_COBROS);
		}
		
		if ((this.edificiosResponsableGastosResult!=null) && (this.responsableGastos!=null)){
			List<EdificioDTO> todosLosEdificios = this.getEdificiosByName(this.edificiosResponsableGastosResult);
			userAppl.actualizarEdificiosParaUsuarioPerfil(todosLosEdificios, this.id, PerfilDTO.RESPONSABLE_GASTOS);
		}
//		
//			
//		if((this.edificiosResponsableEdificioResult!=null)){
//			for (String ed2 : this.edificiosResponsableEdificioResult) {
//				System.out.println(ed2);			
//			}
//		}
//		System.out.println("Responsable Cobros ------Edificios seleccionados");
//		if((this.edificiosResponsableCobrosResult!=null)){
//			for (String ed3 : this.edificiosResponsableCobrosResult) {
//				System.out.println(ed3);			
//			}
//		}
//		System.out.println("Responsable Gastos ------Edificios seleccionados");
//		if((this.edificiosResponsableGastosResult!=null)){
//			for (String ed4 : this.edificiosResponsableGastosResult) {
//				System.out.println(ed4);			
//			}
//		}

		return "success";
		
	}

	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

}
