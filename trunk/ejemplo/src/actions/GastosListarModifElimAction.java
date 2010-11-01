package actions;

import java.util.ArrayList;
import java.util.Map;

import org.hibernate.SessionFactory;
import utilidades.HibernateUtil;
import beans.GastosBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import gastos.appl.GastosAppl;
import gastos.dto.GastoPrevisionDTO;
import gastos.dto.GastoRealDTO;


@SuppressWarnings("serial")
public class GastosListarModifElimAction extends ActionSupport {
	@SuppressWarnings("unused")
	private Map<String,Object> session;
	private int id;
	
	public String execute() {
		GastosBean listaGastos = new GastosBean();
		GastosAppl gasAppl = new GastosAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try {
			ArrayList<GastoRealDTO> lista = (ArrayList<GastoRealDTO>) gasAppl.getGastosPendientesPorEdificio(factory, id);
			ArrayList<GastoPrevisionDTO> gastosPrevistos = (ArrayList<GastoPrevisionDTO>) gasAppl.getGastosPrevistoFuturosPorEdificio(factory, id);
			listaGastos.setGastosReales(lista);
			listaGastos.setGastosPrevistos(gastosPrevistos);
			Map<String, Object> session = ActionContext.getContext().getSession();
	        session.put("lista", listaGastos);
	        this.setSession(session);
	        return SUCCESS;
		} catch (Exception e) {
			String msg = "No se pueden listar gastos.";
			LOG.error(msg, e);
			addActionError(msg);
			return ERROR;
		}	
	}
	
	public void setSession(Map<String, Object> arg0) {
		this.session=arg0;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
