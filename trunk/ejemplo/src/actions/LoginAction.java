package actions;

import java.util.List;

import usuarios.appl.UsuarioAppl;
import usuarios.dto.UsuarioDTO;

import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")   
public class LoginAction extends ActionSupport {
	
    private String username;
    
    private String password;
    
    private UsuarioDTO user;
    
    private UsuarioAppl userAppl = new UsuarioAppl();
  
    public UsuarioDTO getUser() {
		return user;
	}

	public void setUser(UsuarioDTO user) {
		this.user = user;
	}

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
    
  
    public String checkLoggin(){
    	if (this.username.isEmpty()) addActionError("Debe ingresar un nombre de usuario");
    	if (this.password.isEmpty()) addActionError("Debe ingresar un password");
    	
    	if (!this.username.isEmpty()&&!this.password.isEmpty()){
    		
    		List<UsuarioDTO> unicoUser = userAppl.getUsuarioByName(username);
    		if (unicoUser.isEmpty()){
    			addActionError("Datos invalidos"); 
    			return "error";
    		}
    		if (((UsuarioDTO)unicoUser.get(0)).getPassword().equals(this.password) )
    			return "success";
    		
    	}
    	return "error";
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