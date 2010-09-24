package usuarios.dto;

public class AdministradorDePermisos {
	
	private UsuarioDTO user;
	
	public final static String ADMINISTRADOR = "Administrador";
	
	public final static String CONCEJERO_ADMINISTRACION = "Concejero de Administracion";
	
	public final static String EMPLEADO = "Empleado";
	
	public final static String PRESIDENTE_DE_CONSORCIO = "Presidente de consorcio";
	
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
	public boolean visibleEdificioMenu(){
		
		if((this.user.getPerfiles().size()==1)&&(this.user.getPerfiles().get(0).equals(AdministradorDePermisos.ENCARGADO_DE_EDIFICIO))){
			return false;
		}
		return true;
	}
	
	public boolean visibleTodosLosEdificios(){
		if (tienePermisos(ADMINISTRADOR)||tienePermisos(EMPLEADO)){
			return true;
		}
		return false;
	}
	
	

}
