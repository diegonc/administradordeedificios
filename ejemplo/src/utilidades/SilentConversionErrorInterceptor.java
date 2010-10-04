package utilidades;

@SuppressWarnings("serial")
public class SilentConversionErrorInterceptor extends ConversionErrorInterceptor {

	@Override
	protected boolean shouldAddError(String propertyName, Object value) {
		return false;
	}
}
