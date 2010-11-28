package gastos.exception;


@SuppressWarnings("serial")
public class GastoExistenteException extends Exception {

	public GastoExistenteException() {
	}
	
	public GastoExistenteException(String mensaje) {
		super(mensaje);
	}
	
}
