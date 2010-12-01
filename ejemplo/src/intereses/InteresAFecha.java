package intereses;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

import planes.interes.DeudaAdapter;

public class InteresAFecha {

	private Deuda deuda;
	private double interes = 0;

	public InteresAFecha(Deuda deuda) {
		this.deuda = deuda;
	}

	public InteresAFecha() {
		this(null);
	}

	public InteresAFecha calcular(Date fecha) {
		double tasaInteres = deuda.getTasa();
		double monto = deuda.getMonto();
		Date fechaOrigen = deuda.getFecha();

		if (fecha.before(fechaOrigen))
			throw new IllegalArgumentException();

		Days dias = Days.daysBetween(new DateTime(fechaOrigen),
				new DateTime(fecha));

		double interesAcumulado = 0;

		if (dias.getDays() > 0) {
			interesAcumulado = (tasaInteres / 360) * dias.getDays();
		}

		interes = (interesAcumulado * monto);
		
		return this;
	}

	public double getInteres() {
		return interes;
	}

	public InteresAFecha setDeuda(Deuda deuda) {
		this.deuda = deuda;
		return this;
	}

}
