package usuarios.exception;

@SuppressWarnings("serial")
public class UsuarioPerfilExistenteException extends Exception {

	public UsuarioPerfilExistenteException(){}
	
	public UsuarioPerfilExistenteException(String mensaje)
	{
		super(mensaje);
	}
}
