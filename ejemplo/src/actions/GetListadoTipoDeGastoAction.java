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
import beans.TiposGastosBean;
import beans.UsuariosBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edificio.EdificioAppl;
import edificio.EdificioDTO;
import gastos.appl.TiposGastosAppl;
import gastos.dto.TipoGastoDTO;



public class GetListadoTipoDeGastoAction extends ActionSupport implements SessionAware{

		private static final long serialVersionUID = 1L;
		
		private TiposGastosAppl tipoGastoAppl = new TiposGastosAppl();
		
		private Map<String, Object> session;
		
		private int id;
				
		private TipoGastoDTO tipoGasto;
		
		private ArrayList<String> lista ;

		
		public Integer getId() {
			return id;
		}


		public void setId(Integer id) {
			this.id = id;
		}


		public TipoGastoDTO getTipoGasto() {
			return tipoGasto;
		}


		public void setTipoGasto(TipoGastoDTO tipoGasto) {
			this.tipoGasto = tipoGasto;
		}


		public ArrayList<String> getLista() {
			return lista;
		}


		public void setLista(ArrayList<String> lista) {
			this.lista = lista;
		}


		public String eliminar(){
			tipoGastoAppl.removeTipoGasto(this.id);		
			return "eliminar";
		}
				
		public String editar(){
			 //recupero los edificios
			EdificiosBean listaEdificios = new EdificiosBean();
			EdificioAppl edifAppl = new EdificioAppl();
			SessionFactory factory = HibernateUtil.getSessionFactory();
			ArrayList<EdificioDTO> listaE = (ArrayList<EdificioDTO>) edifAppl.getAllEdificios(factory);
			listaEdificios.setEdificios(listaE);
			   			
			this.tipoGasto = tipoGastoAppl.getTipoGasto(this.id);
			
			Map session = ActionContext.getContext().getSession();
		    session.put("edificios",listaEdificios);
	        
			TiposGastosBean tipoGastoEditar = new TiposGastosBean();
			tipoGastoEditar.setTipoGastoUnico(this.tipoGasto);
			
	        session.put("tipoGastoBean",tipoGastoEditar);
	        setSession(session);
	    
			return "edicion";
		}
		
		public String execute() {
			
			List<TipoGastoDTO> listaTipoGasto = tipoGastoAppl.getAllTipoGasto();
							
			TiposGastosBean listado = new TiposGastosBean();
			listado.setTiposGastos(listaTipoGasto);
			
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
