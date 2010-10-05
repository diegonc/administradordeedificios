package permisos;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import edificio.EdificioAppl;
import edificio.EdificioDTO;

import usuarios.appl.UsuarioAppl;
import usuarios.dto.PerfilDTO;
import usuarios.dto.UsuarioDTO;
import utilidades.HibernateUtil;

public class AdministradorDePermisos {
	
	private UsuarioDTO usuario;
	
	private UsuarioAppl usuarioAppl = new UsuarioAppl() ;
	
	private static AdministradorDePermisos adminitradorInstancia;
	
	private AdministradorDePermisos(){
		
	}
	public static AdministradorDePermisos getInstancia(){
		if (adminitradorInstancia==null){
			adminitradorInstancia= new AdministradorDePermisos();
		}
		return adminitradorInstancia;
	}
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuarioLogeado) {
		usuario = usuarioLogeado;
	}
	public boolean isAdministrador(){
		if (this.usuario==null) return false;
		
		for (PerfilDTO perfil:this.usuario.getPerfiles()){
			if (perfil.getDescripcion().equals(PerfilDTO.ADMINISTRADOR)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isResponsableGastos(){
		if (this.usuario==null) return false;
		
		for (PerfilDTO perfil:this.usuario.getPerfiles()){
			if (perfil.getDescripcion().equals(PerfilDTO.RESPONSABLE_GASTOS)){
				return true;
			}
		}
		return false;
	}
	public boolean isResponsableCobros(){
		if (this.usuario==null) return false;
		
		for (PerfilDTO perfil:this.usuario.getPerfiles()){
			if (perfil.getDescripcion().equals(PerfilDTO.RESPONSABLE_COBROS)){
				return true;
			}
		}
		return false;
	}
	public boolean isResponsableEdificios(){
		if (this.usuario==null) return false;
		
		for (PerfilDTO perfil:this.usuario.getPerfiles()){
			if (perfil.getDescripcion().equals(PerfilDTO.RESPONSABLE_EDIFICIO)){
				return true;
			}
		}
		return false;
	}
	public List<EdificioDTO> getEdificiosResponsableCobros(){
		return this.usuarioAppl.getUsuarioPerfil(usuario.getId(),PerfilDTO.RESPONSABLE_COBROS).getEdificios();	
	}
	
	public List<EdificioDTO> getEdificiosResponsableEdificios(){
		return this.usuarioAppl.getUsuarioPerfil(usuario.getId(),PerfilDTO.RESPONSABLE_EDIFICIO).getEdificios();	
	}
	
	public List<EdificioDTO> getEdificiosResponsableGastos(){
		return this.usuarioAppl.getUsuarioPerfil(usuario.getId(),PerfilDTO.RESPONSABLE_GASTOS).getEdificios();	
	}
	public List<EdificioDTO> getEdificiosAdministrador(){
		EdificioAppl edificioAppl = new EdificioAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		return  edificioAppl.getAllEdificios(factory);
	}
	
}
