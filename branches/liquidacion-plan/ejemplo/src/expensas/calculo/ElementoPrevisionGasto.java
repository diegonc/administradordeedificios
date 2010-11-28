package expensas.calculo;

import gastos.dto.GastoDTO;

import java.util.ArrayList;
import java.util.List;

import utilidades.NumberFormat;

public class ElementoPrevisionGasto {

		List<GastoDTO> previsiones;
		List<GastoDTO> previsionesMesAnterior;
		List<GastoDTO> gastosRealesMesAnterior;
				
		public ElementoPrevisionGasto(){
			this.previsiones = new ArrayList<GastoDTO>();
			this.previsionesMesAnterior = new ArrayList<GastoDTO>();
			this.gastosRealesMesAnterior = new ArrayList<GastoDTO>();
		}
		
		public List<GastoDTO> getPrevisiones() {
			return previsiones;
		}
		
		public void setPrevisiones(List<GastoDTO> previsiones) {
			this.previsiones = previsiones;
		}
		
		public List<GastoDTO> getPrevisionesMesAnterior() {
			return previsionesMesAnterior;
		}

		public void setPrevisionesMesAnterior(
				List<GastoDTO> previsionesMesAnterior) {
			this.previsionesMesAnterior = previsionesMesAnterior;
		}
		
		public List<GastoDTO> getGastosRealesMesAnterior() {
			return gastosRealesMesAnterior;
		}

		public void setGastosRealesMesAnterior(
				List<GastoDTO> gastosRealesMesAnterior) {
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
			for (GastoDTO g : this.previsionesMesAnterior) {
				monto+=g.getMonto();
			}
			return NumberFormat.redondeoDouble(monto);
		}
		
		public double obtenerMontoGastosRealesMesAnterior(){
			double monto= 0;
			for (GastoDTO g : this.gastosRealesMesAnterior) {
				monto+=g.getMonto();
			}
			return NumberFormat.redondeoDouble(monto);
		}
		
		public double obtenerMontoPrevisionMesActual(){
			double monto= 0;
			for (GastoDTO g : this.previsiones) {
				monto+=g.getMonto();
			}
			return NumberFormat.redondeoDouble(monto);
		}
		
		public double obtenerMontoTotal(){
			double monto = 0;
			monto = obtenerMontoDiferenciaPrevisionGastoMesAnterior()+obtenerMontoPrevisionMesActual();
			return NumberFormat.redondeoDouble(monto);
		}
		
		public boolean existenPrevisionesMesAnterior(){
			return !previsionesMesAnterior.isEmpty();
		}
		
		public boolean existenPrevisionesMesActual(){
			return !previsiones.isEmpty();
		}
		
		public boolean existenGastosRealesMesAnterior(){
			return !gastosRealesMesAnterior.isEmpty();
		}
		
}


	

