package actions;

import java.util.ArrayList;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import utilidades.HibernateUtil;
import beans.GastosBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import gastos.appl.GastosAppl;
import gastos.dto.GastoDTO;


@SuppressWarnings("serial")
public class GastosListarModifElimAction extends ActionSupport {
	private Map<String,Object> session;
	
	public String execute() {
		ArrayList<GastoDTO> lista = new ArrayList<GastoDTO>();
		GastosBean listaGastos = new GastosBean();
		GastosAppl gasAppl = new GastosAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try {
			//lista = (ArrayList<GastoDTO>) gasAppl.getAllEdificios(factory);
			//listaGastos.setEdificios(lista);
			//Map session = ActionContext.getContext().getSession();
	        //session.put("lista", listaGastos);
	        //this.setSession(session);
	        return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}	
	}
	
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.session=arg0;
	}

}
