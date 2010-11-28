package utilidades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.XWorkException;

@SuppressWarnings("rawtypes")
public class NumberConverter extends StrutsTypeConverter {

	/* TODO: Por ahora, se convierten wrappers como si fueran primitivos.
	 * ver:  - http://jira.opensymphony.com/browse/XW-490
	 *       - http://jira.opensymphony.com/browse/XW-606
	 *       - http://jira.opensymphony.com/browse/XW-512
	 */ 
	public Object convertFromString(Map context, String[] values, Class toType) {
		if (Number.class.isAssignableFrom(toType) || toType.isPrimitive()) {
			return doConvertToNumber(context, values, toType);
		}
		return performFallbackConversion(context, values, toType);
	}

	public String convertToString(Map context, Object o) {
		return (String)performFallbackConversion(context, o, String.class);
	}

	/* copiado de com/opensymphony/xwork2/conversion/impl/XWorkBasicConverter.java  */
	private Object doConvertToNumber(Map context, Object value, Class toType) {
        if (value instanceof String) {
            if (toType == BigDecimal.class) {
                return new BigDecimal((String) value);
            } else if (toType == BigInteger.class) {
                return new BigInteger((String) value);
            } else {
            	String stringValue = (String) value;
            	if (!toType.isPrimitive() && (stringValue == null || stringValue.length() == 0)) {
            		return null;
            	} else {
            		Object convertedValue = performFallbackConversion(context, value, toType);
            		if (!isInRange((Number)convertedValue, stringValue,  toType))
            			throw new XWorkException("Overflow or underflow casting: \"" + stringValue + "\" into class " + convertedValue.getClass().getName());
            		return convertedValue;
            	}
            }
        } else if (value instanceof Object[]) {
            Object[] objArray = (Object[]) value;

            if (objArray.length == 1) {
                return doConvertToNumber(context, objArray[0], toType);
            }
        }

        // pass it through DefaultTypeConverter
        return super.convertValue(context, value, toType);
    }
	
	/* copiado de com/opensymphony/xwork2/conversion/impl/XWorkBasicConverter.java  */
	protected boolean isIntegerType(Class type) {
        if (double.class == type || float.class == type || Double.class == type || Float.class == type
                || char.class == type || Character.class == type) {
            return false;
        }

        return true;
    }
	
	/* copiado de com/opensymphony/xwork2/conversion/impl/XWorkBasicConverter.java  */
	@SuppressWarnings("unchecked")
	private boolean isInRange(Number value, String stringValue, Class toType) {
		Number bigValue = null;
		Number lowerBound = null;
		Number upperBound = null;

		try {
			if (double.class == toType || Double.class == toType) {
				bigValue = new BigDecimal(stringValue);
				// Double.MIN_VALUE is the smallest positive non-zero number
				lowerBound = BigDecimal.valueOf(Double.MAX_VALUE).negate();
				upperBound = BigDecimal.valueOf(Double.MAX_VALUE);
			} else if (float.class == toType || Float.class == toType) {
				bigValue = new BigDecimal(stringValue);
				// Float.MIN_VALUE is the smallest positive non-zero number
				lowerBound = BigDecimal.valueOf(Float.MAX_VALUE).negate();
				upperBound = BigDecimal.valueOf(Float.MAX_VALUE);
			} else if (byte.class == toType || Byte.class == toType) {
				bigValue = new BigInteger(stringValue);
				lowerBound = BigInteger.valueOf(Byte.MIN_VALUE);
				upperBound = BigInteger.valueOf(Byte.MAX_VALUE);
			} else if (char.class == toType || Character.class == toType) {
				bigValue = new BigInteger(stringValue);
				lowerBound = BigInteger.valueOf(Character.MIN_VALUE);
				upperBound = BigInteger.valueOf(Character.MAX_VALUE);
			} else if (char.class == toType || Character.class == toType) {
				bigValue = new BigInteger(stringValue);
				lowerBound = BigInteger.valueOf(Character.MIN_VALUE);
				upperBound = BigInteger.valueOf(Character.MAX_VALUE);
			} else if (short.class == toType || Short.class == toType) {
				bigValue = new BigInteger(stringValue);
				lowerBound = BigInteger.valueOf(Short.MIN_VALUE);
				upperBound = BigInteger.valueOf(Short.MAX_VALUE);
			} else if (int.class == toType || Integer.class == toType) {
				bigValue = new BigInteger(stringValue);
				lowerBound = BigInteger.valueOf(Integer.MIN_VALUE);
				upperBound = BigInteger.valueOf(Integer.MAX_VALUE);
			} else if (long.class == toType || Long.class == toType) {
				bigValue = new BigInteger(stringValue);
				lowerBound = BigInteger.valueOf(Long.MIN_VALUE);
				upperBound = BigInteger.valueOf(Long.MAX_VALUE);
			}
		} catch (NumberFormatException e) {
			//shoult it fail here? BigInteger doesnt seem to be so nice parsing numbers as NumberFormat
			return true;
		}
		return ((Comparable)bigValue).compareTo(lowerBound) >= 0 && ((Comparable)bigValue).compareTo(upperBound) <= 0;
	}
}
