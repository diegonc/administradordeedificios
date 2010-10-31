package expensas.calculo;

import java.util.ArrayList;
import java.util.List;

import propiedades.TipoPropiedadDTO;

public class ElementoProrrateo implements Comparable<ElementoProrrateo>{
	private double coeficiente;
	private List<TipoPropiedadDTO> tiposDePropiedades;
			
	public ElementoProrrateo(){
		tiposDePropiedades = new ArrayList<TipoPropiedadDTO>();
		coeficiente = 1;
	}
	
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

	@Override
	public int compareTo(ElementoProrrateo o) {
		if(this.coeficiente>o.getCoeficiente()) 
			return 1;
		else if(this.coeficiente<o.getCoeficiente()) 
			return -1;
		return 0;
	}
					
}
