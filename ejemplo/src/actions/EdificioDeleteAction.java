package actions;

import org.hibernate.SessionFactory;
import utilidades.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;
import edificio.EdificioAppl;

@SuppressWarnings("serial")
public class EdificioDeleteAction extends ActionSupport{
	
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String execute() {
		EdificioAppl edifAppl = new EdificioAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try {
			edifAppl.deleteEdificio(factory, id);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}	
	}
}
