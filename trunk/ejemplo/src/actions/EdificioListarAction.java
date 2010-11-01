package actions;

import java.util.ArrayList;
import java.util.Map;
import org.hibernate.SessionFactory;

import permisos.AdministradorDePermisos;
import utilidades.HibernateUtil;
import beans.EdificiosBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import edificio.EdificioAppl;
import edificio.EdificioDTO;

@SuppressWarnings("serial")
public class EdificioListarAction extends ActionSupport{
	
	private static final Logger LOG = LoggerFactory.getLogger(EdificioListarAction.class);
	
	@SuppressWarnings("unused")
	private Map<String,Object> session;
	private String redi;
	
	public String execute() {
		ArrayList<EdificioDTO> lista = new ArrayList<EdificioDTO>();
		EdificiosBean listaEdificios = new EdificiosBean();
		EdificioAppl edifAppl = new EdificioAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
			
		try {
			//TODO: ver que es redi edificiios
			if (AdministradorDePermisos.getInstancia().isAdministrador()){
				lista = (ArrayList<EdificioDTO>) edifAppl.getAllEdificios(factory);
			}else{
				if (AdministradorDePermisos.getInstancia().isResponsableEdificios()&& redi.equals("edificio")) {
					lista = (ArrayList<EdificioDTO>) AdministradorDePermisos.getInstancia().getEdificiosResponsableEdificios();	
				}
				if (AdministradorDePermisos.getInstancia().isResponsableGastos()&& redi.equals("gasto")) {
					lista = (ArrayList<EdificioDTO>) AdministradorDePermisos.getInstancia().getEdificiosResponsableGastos();	
				}
				if (AdministradorDePermisos.getInstancia().isResponsableCobros()&& redi.equals("expensa")){
					lista = (ArrayList<EdificioDTO>) AdministradorDePermisos.getInstancia().getEdificiosResponsableCobros();
				}
			}
			listaEdificios.setEdificios(lista);
			
			Map<String, Object> session = ActionContext.getContext().getSession();
	        session.put("lista",listaEdificios);
	        this.setSession(session);
	        if (redi.equals("edificio")) {
	        	return "edificios";
	        } else if (redi.equals("gasto")) {
	        	return "gastos";
	        } else if (redi.equals("expensa")) {
	        	return "expensas";
	        }
	        return SUCCESS;
		} catch (Exception e) {
			String msg = "No se pueden listar edificios.";
			LOG.error(msg, e);
			addActionError(msg);
			return ERROR;
		}
	}
	
	public void setSession(Map<String, Object> arg0) {
		this.session=arg0;
	}

	public void setRedi(String redi) {
		this.redi = redi;
	}

	public String getRedi() {
		return redi;
	}
}
