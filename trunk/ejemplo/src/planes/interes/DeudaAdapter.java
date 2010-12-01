package planes.interes;

import intereses.Deuda;

import java.util.Date;

import org.joda.time.DateTime;

import planes.CuotaDTO;

public class DeudaAdapter implements Deuda {

	private final CuotaDTO cuota;

	public DeudaAdapter(CuotaDTO cuota) {
		this.cuota = cuota;
	}

	@Override
	public double getMonto() {
		return cuota.getMonto();
	}

	@Override
	public double getTasa() {
		return cuota.getPlan().getCobrosCancelados().iterator().next()
				.getLiquidacion().getPropiedad().getTipoPropiedad()
				.getEdificio().getTasa_anual() / 100;
	}

	@Override
	public Date getFecha() {
		Date fechaPlan = cuota.getPlan().getFecha();
		int numeroCuota = cuota.getNumeroCuota();
		return new DateTime(fechaPlan).plusMonths(numeroCuota).toDate();
	}

}
