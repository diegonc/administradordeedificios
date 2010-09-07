package ar.uba.fi.taller2.nicolinoroche;

import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")   
public class Ejemplo extends ActionSupport {
	
    private String username;
    
    private String password;
  
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {   
        return username;   
    }   
  
    public void setUsername(String nombre) {   
        this.username = nombre;   
    }   
    
  
    public String execute() {   
        System.out.println(username);   
        if (this.password.equals(this.username))
        {
        	return SUCCESS;
        
        }else
        	return "error";
           
    }   
}  
