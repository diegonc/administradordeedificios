package actions;

import org.hibernate.SessionFactory;
import utilidades.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;
import gastos.appl.GastosAppl;

@SuppressWarnings("serial")
public class GastoRealDeleteAction extends ActionSupport{
	
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String execute() {
		GastosAppl edifAppl = new GastosAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try {
			edifAppl.deleteGastoReal(factory, id);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}	
	}
}
