package usuarios.exception;

@SuppressWarnings("serial")
public class UsuarioInexistenteException extends Exception {

	
	public UsuarioInexistenteException() {
		
	}
	
	public UsuarioInexistenteException(String mensaje)
	{
		super(mensaje);
	}
	
}
