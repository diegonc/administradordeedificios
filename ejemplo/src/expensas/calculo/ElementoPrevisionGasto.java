package expensas.calculo;

import gastos.dto.GastoPrevisionDTO;
import gastos.dto.GastoRealDTO;

import java.util.ArrayList;
import java.util.List;

import utilidades.NumberFormat;

public class ElementoPrevisionGasto {

		List<GastoPrevisionDTO> previsiones;
		List<GastoPrevisionDTO> previsionesMesAnterior;
		List<GastoRealDTO> gastosRealesMesAnterior;
				
		public ElementoPrevisionGasto(){
			this.previsiones = new ArrayList<GastoPrevisionDTO>();
			this.previsionesMesAnterior = new ArrayList<GastoPrevisionDTO>();
			this.gastosRealesMesAnterior = new ArrayList<GastoRealDTO>();
		}
		
		public List<GastoPrevisionDTO> getPrevisiones() {
			return previsiones;
		}
		
		public void setPrevisiones(List<GastoPrevisionDTO> previsiones) {
			this.previsiones = previsiones;
		}
		
		public List<GastoPrevisionDTO> getPrevisionesMesAnterior() {
			return previsionesMesAnterior;
		}

		public void setPrevisionesMesAnterior(
				List<GastoPrevisionDTO> previsionesMesAnterior) {
			this.previsionesMesAnterior = previsionesMesAnterior;
		}
		
		public List<GastoRealDTO> getGastosRealesMesAnterior() {
			return gastosRealesMesAnterior;
		}

		public void setGastosRealesMesAnterior(
				List<GastoRealDTO> gastosRealesMesAnterior) {
			this.gastosRealesMesAnterior = gastosRealesMesAnterior;
		}
		
		public double obtenerMontoDiferenciaPrevisionGastoMesAnterior(){
			double montoDiferencia = 0;
			montoDiferencia = obtenerMontoGastosRealesMesAnterior();
			montoDiferencia -= obtenerMontoPrevisionMesAnterior();
			return NumberFormat.redondeoDouble(montoDiferencia);
		}
	
		public double obtenerMontoPrevisionMesAnterior(){
			double monto= 0;
			for (GastoPrevisionDTO g : this.previsionesMesAnterior) {
				monto+=g.getMonto();
			}
			return NumberFormat.redondeoDouble(monto);
		}
		
		public double obtenerMontoGastosRealesMesAnterior(){
			double monto= 0;
			for (GastoRealDTO g : this.gastosRealesMesAnterior) {
				monto+=g.getMonto();
			}
			return NumberFormat.redondeoDouble(monto);
		}
		
		public double obtenerMontoPrevisionMesActual(){
			double monto= 0;
			for (GastoPrevisionDTO g : this.previsiones) {
				monto+=g.getMonto();
			}
			return NumberFormat.redondeoDouble(monto);
		}
		
		public double obtenerMontoTotal(){
			double monto = 0;
			monto = obtenerMontoDiferenciaPrevisionGastoMesAnterior()+obtenerMontoPrevisionMesActual();
			return NumberFormat.redondeoDouble(monto);
		}
}


	

