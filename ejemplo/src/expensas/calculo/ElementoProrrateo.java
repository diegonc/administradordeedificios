package expensas.calculo;

import java.util.ArrayList;
import java.util.List;

import propiedades.TipoPropiedadDTO;

public class ElementoProrrateo{
	private double coeficiente;
	private List<TipoPropiedadDTO> tiposDePropiedades = new ArrayList<TipoPropiedadDTO>();
			
	public double getCoeficiente() {
		return coeficiente;
	}
	public void setCoeficiente(double coeficiente) {
		this.coeficiente = coeficiente;
	}
	
	public List<TipoPropiedadDTO> getTiposDePropiedades() {
		return tiposDePropiedades;
	}
	
	public void setTiposDePropiedades(List<TipoPropiedadDTO> tiposDePropiedades) {
		this.tiposDePropiedades = tiposDePropiedades;
	}
	
	public double getSuperficieTotal() {
		double superficieActual = 0;
		for(TipoPropiedadDTO tp: tiposDePropiedades){
			superficieActual+= tp.getDivisor();
		}
		return superficieActual;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.coeficiente==((ElementoProrrateo)obj).getCoeficiente();
	}
					
}
