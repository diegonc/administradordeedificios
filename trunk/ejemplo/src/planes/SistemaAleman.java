package planes;
/**
 * Sistema Alemán
 * --------------
 * 
 * La cuota se compone de una porción constante del monto del prestamo y del
 * interés sobre el monto restante a la fecha.
 * 
 * M: monto del prestamo
 * n: cantidad de periodos (coutas)
 * t: tasa de interes del periodo
 * 
 * A: amortizacion de capital
 * Ci: monto de la cuota i
 * Ii: interes acumulado hasta la cuota i
 * 
 * ---------------
 * 
 * A = M/n
 * 
 * Ci = A + t*(M - (i - 1)*A)     ; 1 <= i <= n
 * 
 * Ii = t*M*i - t*A*i*(i - 1)/2   ; 1 <= i <= n
 *    = 0                         ; i = 0
 * 
 * Interes total: In   = t*M/2 * (1 + n)
 * --------------
 * 
 * Total a pagar: M + In
 * --------------
 * 
 **/
public class SistemaAleman extends SistemaAmortizacion {

	private double capital;
	private double tasa;
	private int numeroCuotas;
	private double montoDescuento;
	
	private double amortizacionCapital;
	private int descuento;

	public SistemaAleman(double monto, double tasa, int numeroCuotas, int descuento) {
		this.capital = monto;
		this.tasa = tasa;
		this.numeroCuotas = numeroCuotas;
		
		this.descuento = descuento;
		this.montoDescuento = this.getInteresTotal() * descuento / 100;
		
		double descPorCuota = this.montoDescuento / this.numeroCuotas;
		this.amortizacionCapital = (this.capital / numeroCuotas) - descPorCuota;
	}
	
	@Override
	public double getInteresTotal() {
		return calcularInteresAcumulado(numeroCuotas);
	}

	@Override
	public double getMonto() {
		return capital;
	}

	@Override
	public double getMontoTotal() {
		return capital + getInteresTotal() - this.montoDescuento;
	}
	
	@Override
	public double getInteresCuota(int numeroCuota) {
		return calcularInteres(numeroCuota);
	}
	
	@Override
	public double getCapitalCuota(int numeroCuota) {
		return amortizacionCapital;
	}
	
	@Override
	public double getTotalCuota(int numeroCuota) {
		return getCapitalCuota(numeroCuota) + getInteresCuota(numeroCuota);
	}

	private double calcularInteresAcumulado(int numeroCuota) {
		if (numeroCuota > 0)
			return (tasa * capital * numeroCuota)
			- (tasa * amortizacionCapital * numeroCuota * (numeroCuota - 1) / 2);
		else
			return 0;
	}
	
	private double calcularInteres(int numeroCuota) {
		if (numeroCuota >= 1)
			return ((tasa * capital)
			- (tasa * amortizacionCapital * (numeroCuota - 1)));
		
		throw new IllegalArgumentException("El numero de cuota debe estar entre 1 y " + numeroCuotas);
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	public int getDescuento() {
		return descuento;
	}

	public void setMontoDescuento(double montoDescueto) {
		this.montoDescuento = montoDescueto;
	}

	public double getMontoDescuento() {
		return montoDescuento;
	}
}
