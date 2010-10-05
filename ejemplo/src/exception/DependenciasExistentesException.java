package exception;

@SuppressWarnings("serial")
public class DependenciasExistentesException extends Exception {

	public DependenciasExistentesException(){
	}
	
	public DependenciasExistentesException(String mensaje){
		super(mensaje);
	}
}
