package actions;

import org.hibernate.SessionFactory;
import utilidades.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;
import gastos.appl.GastosAppl;

@SuppressWarnings("serial")
public class GastoPrevisionDeleteAction extends ActionSupport{
	
	private int id;
	private int edificio_id;
	
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
	public void setEdificio_id(int edificio_id) {
		this.edificio_id = edificio_id;
	}
	public int getEdificio_id() {
		return edificio_id;
	}

}
