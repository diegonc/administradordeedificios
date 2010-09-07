import com.opensymphony.xwork2.ActionSupport;


public class Principal extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String contraseña;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public String execute() {  
	 
		if (this.username.equals("Adriana")) return "error";
	    return SUCCESS;  
	}  
		
}
