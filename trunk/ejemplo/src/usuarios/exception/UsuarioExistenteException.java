package usuarios.exception;

@SuppressWarnings("serial")
public class UsuarioExistenteException extends Exception {

	public UsuarioExistenteException() {
	
	}
	
	public UsuarioExistenteException(String mensaje)
	{
		super(mensaje);
	}
}
