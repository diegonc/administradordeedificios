package intereses;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

import planes.interes.DeudaAdapter;

public class InteresPunitorio {
	
	private Deuda deuda;
	private int primerVto;
	private int segundoVto;
	private double interes1vto = 0;
	private double interes2vto = 0;
	
	public InteresPunitorio(Deuda deuda, int primerVto, int segundoVto) {
		this.deuda = deuda;
		this.primerVto = primerVto;
		this.segundoVto = segundoVto;
	}
	
	public InteresPunitorio(int primerVto, int segundoVto) {
		this(null, primerVto, segundoVto);
	}

	public InteresPunitorio calcular(Date fecha) {
		double tasaInteres = deuda.getTasa();
		double monto = deuda.getMonto();
		Date fechaOrigen = deuda.getFecha();
		
		if ( fecha.before(fechaOrigen))
			throw new IllegalArgumentException();
		
		Days dias = Days.daysBetween(
				new DateTime(fechaOrigen).withDayOfMonth(1),
				new DateTime(fecha).withDayOfMonth(1));
		
		double interesAcumulado = 0;
		
		if (dias.getDays() > 0) {
			interesAcumulado = (tasaInteres / 360) * dias.getDays();
		}
		
		interes1vto = (interesAcumulado * monto);
		interes2vto = (interesAcumulado + (segundoVto - primerVto) * tasaInteres / 360) * monto;
		
		return this;
	}

	public double getInteres1vto() {
		return interes1vto;
	}

	public double getInteres2vto() {
		return interes2vto;
	}

	public InteresPunitorio setDeuda(Deuda deuda) {
		this.deuda = deuda;
		return this;
	}
}
