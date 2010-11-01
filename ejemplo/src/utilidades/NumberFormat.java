package utilidades;

public class NumberFormat {

	public static double redondeoDouble(double valor){
		return Math.round(valor*Math.pow(10,2))/Math.pow(10,2);
	}
}
