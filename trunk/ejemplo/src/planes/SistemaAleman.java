package planes;

public class SistemaAleman {

	private double capital;
	private double tasa;
	private int numeroCuotas;
	
	private double amortizacionCapital;

	public SistemaAleman(double monto, double tasa, int numeroCuotas) {
		this.capital = monto;
		this.tasa = tasa;
		this.numeroCuotas = numeroCuotas;
		this.amortizacionCapital = this.capital / numeroCuotas;
	}
	
	public double getInteresesTotales() {
		return calcularInteresAcumulado(numeroCuotas);
	}
	
	public double getInteresCuota(int numeroCuota) {
		return calcularInteres(numeroCuota);
	}
	
	public double getCapitalCuota(int numeroCuota) {
		return amortizacionCapital;
	}
	
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
			return (tasa * capital)
			- (tasa * amortizacionCapital * (numeroCuota - 1));
		
		throw new IllegalArgumentException();
	}
}
