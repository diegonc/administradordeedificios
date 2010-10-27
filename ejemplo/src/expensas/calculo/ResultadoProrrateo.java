package expensas.calculo;

import propiedades.TipoPropiedadDTO;

public class ResultadoProrrateo{
	
	private TipoPropiedadDTO tipoPropiedad;
	private double monto;
	
	public TipoPropiedadDTO getTipoPropiedad() {
		return tipoPropiedad;
	}
	public void setTipoPropiedad(TipoPropiedadDTO tipoPropiedad) {
		this.tipoPropiedad = tipoPropiedad;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
}