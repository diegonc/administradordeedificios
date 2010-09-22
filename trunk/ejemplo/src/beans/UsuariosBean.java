package beans;

import java.util.List;
import usuarios.dto.UsuarioDTO;

public class UsuariosBean {
	
	private List<UsuarioDTO> users;

	private UsuarioDTO usuarioUnico;
	
	public UsuarioDTO getUsuarioUnico() {
		return usuarioUnico;
	}

	public void setUsuarioUnico(UsuarioDTO usuarioUnico) {
		this.usuarioUnico = usuarioUnico;
	}

	public List<UsuarioDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UsuarioDTO> users) {
		this.users = users;
	}
	
	

}
