package gastos.exception;

@SuppressWarnings("serial")
public class TipoGastoExistenteException extends Exception {

	public TipoGastoExistenteException() {
	}
	
	public TipoGastoExistenteException(String mensaje) {
		super(mensaje);
	}
}
