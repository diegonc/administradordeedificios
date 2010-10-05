package actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import usuarios.appl.UsuarioAppl;
import usuarios.dto.UsuarioDTO;
import usuarios.dto.UsuarioPerfilEdificioDTO;
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
	
	private List<UsuarioPerfilEdificioDTO> perfiles;
	
	
	
	private boolean administrador;
	
	private boolean responsableGastos;
	
	private boolean responsableCobros;
	
	private boolean responsableEdificios;
	
	private List<String> listaEdificiosDefault;
	private List<String> listaEdificiosDefault1;
	private List<String> listaEdificiosDefault2;
	
	private List<String> edificiosResponsableGastosResult;
	private List<String> edificiosResponsableCobrosResult;
	private List<String> edificiosResponsableEdificioResult;
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

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public boolean isResponsableGastos() {
		return responsableGastos;
	}

	public void setResponsableGastos(boolean responsableGastos) {
		this.responsableGastos = responsableGastos;
	}

	public boolean isResponsableCobros() {
		return responsableCobros;
	}

	public void setResponsableCobros(boolean responsableCobros) {
		this.responsableCobros = responsableCobros;
	}

	public boolean isResponsableEdificios() {
		return responsableEdificios;
	}

	public void setResponsableEdificios(boolean responsableEdificios) {
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
		
		this.responsableEdificios =true;
		userEditar.setUsuarioUnico(this.user);
		
		
		obtenerTodosEdificios();
        session.put("usuarioBean",userEditar);
        
        setSession(session);
		return "vincular";
	}
	
	
	private ArrayList<EdificioDTO> restarListas(ArrayList<EdificioDTO> listaTotal,ArrayList<EdificioDTO> listaSacar){
		ArrayList<EdificioDTO> listaRestante = new ArrayList<EdificioDTO>();
		for (EdificioDTO edificioDTO : listaTotal) {
			boolean seRepite=false;
			for (EdificioDTO edificio2 : listaSacar) {
				if (edificioDTO.getId()==edificio2.getId()){
					seRepite=true;
				}
				
			}
			if(!seRepite){
				listaRestante.add(edificioDTO);
			}
		}
		return listaRestante;
		
		
	}
	private void obtenerTodosEdificios(){
		
		EdificioAppl edifAppl = new EdificioAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		this.listaEdificiosDefault = new ArrayList<String>();
		this.listaEdificiosDefault1 = new ArrayList<String>();
		this.listaEdificiosDefault2 = new ArrayList<String>();
			
		try {
			//TODO: permisos
			ArrayList<EdificioDTO> lista = (ArrayList<EdificioDTO>) edifAppl.getAllEdificios(factory);
			
			for (EdificioDTO edificioDTO : lista) {
		
				this.listaEdificiosDefault.add(edificioDTO.getNombre());
				this.listaEdificiosDefault1.add(edificioDTO.getNombre());
				this.listaEdificiosDefault2.add(edificioDTO.getNombre());
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public String execute(){
		System.out.println(this.administrador);
		System.out.println(this.responsableCobros);
		
		System.out.println("Responsable de Edificios ------Edificios seleccionados");
		if((this.edificiosResponsableEdificioResult!=null)){
			for (String ed2 : this.edificiosResponsableEdificioResult) {
				System.out.println(ed2);			
			}
		}
		System.out.println("Responsable Cobros ------Edificios seleccionados");
		if((this.edificiosResponsableCobrosResult!=null)){
			for (String ed3 : this.edificiosResponsableCobrosResult) {
				System.out.println(ed3);			
			}
		}
		System.out.println("Responsable Gastos ------Edificios seleccionados");
		if((this.edificiosResponsableGastosResult!=null)){
			for (String ed4 : this.edificiosResponsableGastosResult) {
				System.out.println(ed4);			
			}
		}

		return "success";
		
	}

	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

}
