package actions;

import java.util.List;
import java.util.Map;

import planes.PlanDTO;
import planes.PlanesAppl;

import beans.PlanesBeans;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class PlanesConsultaAction extends ActionSupport{
	private int dni;
	private Map<String,Object> session;
	
	public String execute(){
		PlanesAppl planAppl = new PlanesAppl();
		List<PlanDTO> planes = planAppl.getPlanByDNI(dni);
		
		if (planes.isEmpty()) {
			addActionError("No existen planes para el responsable elegido");
			return "sinplan";	
		}
		
		PlanesBeans listaPlanes = new PlanesBeans();
		listaPlanes.setPlanes(planes);
		
		session = ActionContext.getContext().getSession();
		session.put("lista",listaPlanes);
		this.setSession(session);
		
		return SUCCESS;	
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public int getDni() {
		return dni;
	}

	public void setSession(Map<String,Object> session) {
		this.session = session;
	}

	public Map<String,Object> getSession() {
		return session;
	}
}
