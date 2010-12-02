package planes;

public class SistemaFrances extends SistemaAmortizacion {

	private double monto;
	private double tasaMensual;
	private int cantCuotas;
	private int descuento;
	private double montoDescuento;
	
	private Double dividendo = 0.0;
	private Double divisor = 0.0;
	private Double MontoCuota = 0.0;
	
	public SistemaFrances(double monto, double tasaMensual, int cantCuotas, int descuento) {
		this.monto = monto;
		this.tasaMensual = tasaMensual;
		this.descuento = descuento;
		this.cantCuotas = cantCuotas;
		
		dividendo = Math.pow(1+tasaMensual,cantCuotas)-1;
		divisor = tasaMensual * Math.pow((1 + tasaMensual),cantCuotas);
		MontoCuota = monto / (dividendo/divisor);
		
		montoDescuento = this.getInteresTotal() * descuento / 100;
		MontoCuota = MontoCuota - (montoDescuento/cantCuotas);
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
		return saldoTotal - monto + montoDescuento;
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

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	public int getDescuento() {
		return descuento;
	}

	public void setMontoDescuento(double montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	public double getMontoDescuento() {
		return montoDescuento;
	}
}
