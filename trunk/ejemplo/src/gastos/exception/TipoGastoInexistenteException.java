package gastos.exception;

@SuppressWarnings("serial")
public class TipoGastoInexistenteException extends Exception {

	public TipoGastoInexistenteException() {
	}
	
	public TipoGastoInexistenteException(String mensaje) {
		super(mensaje);
	}
}
