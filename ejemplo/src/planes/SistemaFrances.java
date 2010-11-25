package planes;

public class SistemaFrances extends SistemaAmortizacion {

	private double monto;
	private double tasaMensual;
	private int cantCuotas;
	
	private Double dividendo = 0.0;
	private Double divisor = 0.0;
	private Double MontoCuota = 0.0;
	
	public SistemaFrances(double monto, double tasaMensual, int cantCuotas) {
		this.monto = monto;
		this.tasaMensual = tasaMensual;
		this.cantCuotas = cantCuotas;
		
		dividendo = Math.pow(1+tasaMensual,cantCuotas)-1;
		divisor = tasaMensual * Math.pow((1 + tasaMensual),cantCuotas);
		MontoCuota = monto / (dividendo/divisor);
	}

	@Override
	public double getInteresCuota(int i) {
		return MontoCuota * (1 - (1/Math.pow(1+tasaMensual, cantCuotas - i + 1)));
	}

	@Override
	public double getCapitalCuota(int i) {
		return MontoCuota * (1 / Math.pow(1+tasaMensual, cantCuotas - i + 1));
	}

	@Override
	public double getInteresTotal() {
		Double saldoTotal = cantCuotas * MontoCuota;
		return saldoTotal - monto;
	}

	@Override
	public double getMonto() {
		return monto;
	}

	@Override
	public double getMontoTotal() {
		return MontoCuota * cantCuotas;
	}

	@Override
	public double getTotalCuota(int numeroCuota) {
		return MontoCuota;
	}
}
