package actions;

import org.hibernate.SessionFactory;
import utilidades.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;
import edificio.EdificioAppl;
import gastos.appl.GastosAppl;

@SuppressWarnings("serial")
public class GastoPrevisionDeleteAction extends ActionSupport{
	
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
			edifAppl.deleteGastoPrevision(factory, id);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}	
	}
}
