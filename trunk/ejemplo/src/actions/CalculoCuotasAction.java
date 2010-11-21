package actions;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CalculoCuotasAction  extends ActionSupport {
	private String expElegidas;
	
	
	public String execute() {
		return SUCCESS;
	}


	public void setExpElegidas(String expElegidas) {
		this.expElegidas = expElegidas;
	}


	public String getExpElegidas() {
		return expElegidas;
	}
}
