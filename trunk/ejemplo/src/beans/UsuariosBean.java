package beans;

import java.util.LinkedList;
import java.util.List;

import usuarios.dto.UsuarioDTO;

public class UsuariosBean {
	
	private LinkedList<UsuarioDTO> users;

	public LinkedList<UsuarioDTO> getUsers() {
		return users;
	}

	public void setUsers(LinkedList<UsuarioDTO> users) {
		this.users = users;
	}
	
	

}
