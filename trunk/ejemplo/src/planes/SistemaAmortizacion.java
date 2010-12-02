package planes;

public abstract class SistemaAmortizacion {

	/**
	 * Obtiene la porcion de la cuota <i>numeroCuota</i> que cancela
	 * intereses.
	 * 
	 * @param numeroCuota
	 * @return
	 */
	public abstract double getInteresCuota(int numeroCuota);

	/**
	 * Obtiene la porcion de la cuota <i>numeroCuota</i> que cancela
	 * capital.
	 * 
	 * @param numeroCuota
	 * @return
	 */
	public abstract double getCapitalCuota(int numeroCuota);

	/**
	 * Obtiene monto de la cuota <i>numeroCuota</i>.
	 * 
	 * @param numeroCuota
	 * @return
	 */
	public abstract double getTotalCuota(int numeroCuota);
	
	/**
	 * Obtiene la cantidad total de intereses pagados al final del plan.
	 * 
	 * @return
	 */
	public abstract double getInteresTotal();

	/**
	 * Obtiene monto del prestamo.
	 * 
	 * @return
	 */
	public abstract double getMonto();

	/**
	 * Obtiene monto total abonado al final del plan.
	 * 
	 * @return
	 */
	public abstract double getMontoTotal();
	

	/**
	 * Obtiene descuento del plan.
	 * 
	 * @return
	 */
	public abstract int getDescuento();
	
	/**
	 * Obtiene el monto del descuento del plan.
	 * 
	 * @return
	 */
	public abstract double getMontoDescuento();

	public static SistemaAmortizacion crear(String sistema, Double monto,
			Double tasa, Integer cuotas, int descuento) {
		if ("ALEMAN".equals(sistema))
			return new SistemaAleman(monto, tasa, cuotas, descuento);
		if ("FRANCES".equals(sistema))
			return new SistemaFrances(monto, tasa, cuotas, descuento);
		
		throw new IllegalArgumentException("Sitema de amortizacion '"+sistema+"' no soportado.");
	}
}
