package actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.SessionFactory;

import utilidades.HibernateUtil;
import beans.EdificiosBean;
import beans.TiposGastosBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edificio.EdificioAppl;
import edificio.EdificioDTO;
import exception.DependenciasExistentesException;
import gastos.appl.TiposGastosAppl;
import gastos.dto.TipoGastoDTO;
import gastos.dto.TipoGastoMontoFijoDTO;
import gastos.dto.TipoGastoMontoVariableDTO;
import gastos.exception.TipoGastoInexistenteException;



public class GetListadoTipoDeGastoAction extends ActionSupport implements SessionAware{

		private static final long serialVersionUID = 1L;
		
		private TiposGastosAppl tipoGastoAppl = new TiposGastosAppl();
		
		@SuppressWarnings("unused")
		private Map<String, Object> session;
		
		private int id;
				
		private TipoGastoDTO tipoGasto;
		
		private TipoGastoMontoFijoDTO tipoGastoMontoFijo;
		
		private TipoGastoMontoVariableDTO tipoGastoMontoVariable;
		
		private ArrayList<String> lista ;

				
		public TipoGastoMontoFijoDTO getTipoGastoMontoFijo() {
			return tipoGastoMontoFijo;
		}


		public void setTipoGastoMontoFijo(TipoGastoMontoFijoDTO tipoGastoMontoFijo) {
			this.tipoGastoMontoFijo = tipoGastoMontoFijo;
		}


		public TipoGastoMontoVariableDTO getTipoGastoMontoVable() {
			return tipoGastoMontoVariable;
		}


		public void setTipoGastoMontoVable(TipoGastoMontoVariableDTO tipoGastoMontoVable) {
			this.tipoGastoMontoVariable = tipoGastoMontoVable;
		}


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
			try {
				tipoGastoAppl.removeTipoGasto(this.id);
			} catch (TipoGastoInexistenteException e) {
				addActionError(e.getMessage()); 
				return "error";
			} catch (DependenciasExistentesException e) {
				addActionError(e.getMessage()); 
				return "error";
			}		
			return "eliminar";
		}
				
		@SuppressWarnings("unchecked")
		public String editar(){
			 //recupero los edificios
			EdificiosBean listaEdificios = new EdificiosBean();
			EdificioAppl edifAppl = new EdificioAppl();
			SessionFactory factory = HibernateUtil.getSessionFactory();
			ArrayList<EdificioDTO> listaE = (ArrayList<EdificioDTO>) edifAppl.getAllEdificios(factory);
			listaEdificios.setEdificios(listaE);
			   			
			try {
				this.tipoGasto = tipoGastoAppl.getTipoGasto(this.id);
			} catch (TipoGastoInexistenteException e) {
				// TODO mostrar error
				e.printStackTrace();
			}
			
			TiposGastosBean tipoGastoEditar = new TiposGastosBean();
			
			if(tipoGasto.getTipo().equals(TipoGastoDTO.tipoPeriodicoMontoFijo))
			{
				try {
					this.tipoGastoMontoFijo = tipoGastoAppl.getTipoGastoMontoFijo(this.id);
				} catch (TipoGastoInexistenteException e) {
					// TODO mostrar error
					e.printStackTrace();
				}
				tipoGastoEditar.setTipoGastoUnico(this.tipoGastoMontoFijo);
			}
			else if(tipoGasto.getTipo().equals(TipoGastoDTO.tipoPeriodicoMontoVariable))
			{
				try {
					this.tipoGastoMontoVariable = tipoGastoAppl.getTipoGastoMontoVariable(this.id);
				} catch (TipoGastoInexistenteException e) {
					// TODO mostrar error
					e.printStackTrace();
				}
				tipoGastoEditar.setTipoGastoUnico(this.tipoGastoMontoVariable);
			}
			else tipoGastoEditar.setTipoGastoUnico(this.tipoGasto);
			
			
			Map session = ActionContext.getContext().getSession();
		    session.put("edificios",listaEdificios);
	        			
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
			this.session = arg0;
		}

	
	
}
