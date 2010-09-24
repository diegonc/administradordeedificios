package usuarios.dto;

public class AdministradorDePermisos {
	
	private UsuarioDTO user;
	
	public final static String ADMINISTRADOR = "Administrador";
	
	public final static String RESPONSABLE_CONSORCIO = "Concejero de Administracion";
	
	public final static String EMPLEADO = "Empleado";
	
	public final static String PRESIDENTE_DE_CONSORCIO = "Responsable de consorcio";
	
	public final static String ENCARGADO_DE_EDIFICIO = "Encargado de Edificio";
	
	private static AdministradorDePermisos administrador;
	
	
	private AdministradorDePermisos(){
		
	}
	public static AdministradorDePermisos getInstancia(){
		if (administrador==null)
			administrador = new AdministradorDePermisos();
		return administrador;
	}
	public UsuarioDTO getUser() {
		return user;
	}
	public void setUser(UsuarioDTO user) {
		this.user = user;
	}
	public boolean tienePermisos(String perfil){
		boolean permiso = false;
		for (PerfilDTO p : this.user.getPerfiles()) {
			if (p.getDescripcion().equalsIgnoreCase(perfil))
				permiso= true;
		}
		return permiso;
	}
	
	
	

}
