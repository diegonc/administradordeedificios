package expensas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import propiedades.PropiedadDTO;

import utilidades.Periodo;
import expensas.appl.ExpensaPrevisionAppl;
import expensas.calculo.ElementoPrevisionGasto;
import expensas.dto.ExpensaDTO;
import gastos.dto.GastoDTO;
import gastos.dto.TipoGastoDTO;

public class TestLiquidacion extends TestCase{

	public void testLiquidar(){
		Periodo periodo = new Periodo(11,2010);
		ExpensaPrevisionAppl expensaPrevisionAppl = new ExpensaPrevisionAppl();
		
		HashMap<TipoGastoDTO, ElementoPrevisionGasto> tipoGastoElementoPrevisionGasto = expensaPrevisionAppl.obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(1, periodo, ExpensaDTO.tipoOrdinario);
		
		Iterator<TipoGastoDTO> it = tipoGastoElementoPrevisionGasto.keySet().iterator();
	    while (it.hasNext()) {
	        TipoGastoDTO tipoGasto = (TipoGastoDTO) it.next();
	        ElementoPrevisionGasto elementoPrevisionGasto = (ElementoPrevisionGasto) tipoGastoElementoPrevisionGasto.get(tipoGasto);
	        System.out.println(tipoGasto.getCodigo());
	        
	        System.out.println("******Previsiones mes anterior******");
	        for (GastoDTO gastoActual : elementoPrevisionGasto.getPrevisionesMesAnterior()) {
	        	System.out.println(gastoActual.getDetalle()+" : "+gastoActual.getMonto());
			}
	        System.out.println("Total previsiones mes anterior: " + elementoPrevisionGasto.obtenerMontoPrevisionMesAnterior());
	        	        
	        System.out.println("******Gastos mes anterior******");
	        for (GastoDTO gastoActual : elementoPrevisionGasto.getGastosRealesMesAnterior()) {
	        	System.out.println(gastoActual.getDetalle()+" : "+gastoActual.getMonto());
			}
	        System.out.println("Total gastos reales mes anterior: " + elementoPrevisionGasto.obtenerMontoGastosRealesMesAnterior());
	        System.out.println("Total diferencia prevision gasto mes anterior: " + elementoPrevisionGasto.obtenerMontoDiferenciaPrevisionGastoMesAnterior());
	        
	        System.out.println("******Previsiones mes actual******");
	        for (GastoDTO gastoActual : elementoPrevisionGasto.getPrevisiones()) {
	        	System.out.println(gastoActual.getDetalle()+" : "+gastoActual.getMonto());
			}
	        System.out.println("Total previsiones mes actual: " + elementoPrevisionGasto.obtenerMontoPrevisionMesActual());
	        System.out.println("Totalizacion Tipo Gasto : " + elementoPrevisionGasto.obtenerMontoTotal());
	        System.out.println(""); 
	    }
	    
	    List<ExpensaDTO> expensas = expensaPrevisionAppl.obtenerExpensasPorTipoPorEdificioYPeriodo(tipoGastoElementoPrevisionGasto, 1, ExpensaDTO.tipoOrdinario);
	    System.out.println("Expensas Por Propiedad");
		
		PropiedadDTO propiedadActual;
		for (ExpensaDTO expensa: expensas) {
			propiedadActual = expensa.getPropiedad();
			System.out.println("Piso-Depto: " + propiedadActual.getNivel()+" - "+propiedadActual.getOrden()+" = "+expensa.getMonto());
		}
		System.out.println();
		System.out.println("***********************EXTAORDINARIOS**************************");
		tipoGastoElementoPrevisionGasto = expensaPrevisionAppl.obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(1, periodo, ExpensaDTO.tipoExtraordinario);
		
		it = tipoGastoElementoPrevisionGasto.keySet().iterator();
	    while (it.hasNext()) {
	        TipoGastoDTO tipoGasto = (TipoGastoDTO) it.next();
	        ElementoPrevisionGasto elementoPrevisionGasto = (ElementoPrevisionGasto) tipoGastoElementoPrevisionGasto.get(tipoGasto);
	        System.out.println(tipoGasto.getCodigo());
	        
	        System.out.println("******Previsiones mes anterior******");
	        for (GastoDTO gastoActual : elementoPrevisionGasto.getPrevisionesMesAnterior()) {
	        	System.out.println(gastoActual.getDetalle()+" : "+gastoActual.getMonto());
			}
	        System.out.println("Total previsiones mes anterior: " + elementoPrevisionGasto.obtenerMontoPrevisionMesAnterior());
	        	        
	        System.out.println("******Gastos mes anterior******");
	        for (GastoDTO gastoActual : elementoPrevisionGasto.getGastosRealesMesAnterior()) {
	        	System.out.println(gastoActual.getDetalle()+" : "+gastoActual.getMonto());
			}
	        System.out.println("Total gastos reales mes anterior: " + elementoPrevisionGasto.obtenerMontoGastosRealesMesAnterior());
	        System.out.println("Total diferencia prevision gasto mes anterior: " + elementoPrevisionGasto.obtenerMontoDiferenciaPrevisionGastoMesAnterior());
	        
	        System.out.println("******Previsiones mes actual******");
	        for (GastoDTO gastoActual : elementoPrevisionGasto.getPrevisiones()) {
	        	System.out.println(gastoActual.getDetalle()+" : "+gastoActual.getMonto());
			}
	        System.out.println("Total previsiones mes actual: " + elementoPrevisionGasto.obtenerMontoPrevisionMesActual());
	        System.out.println("Totalizacion Tipo Gasto : " + elementoPrevisionGasto.obtenerMontoTotal());
	        System.out.println(""); 
	    }
	    
	    expensas = expensaPrevisionAppl.obtenerExpensasPorTipoPorEdificioYPeriodo(tipoGastoElementoPrevisionGasto, 1, ExpensaDTO.tipoExtraordinario);
	    System.out.println("Expensas Por Propiedad");
				
		for (ExpensaDTO expensa: expensas) {
			propiedadActual = expensa.getPropiedad();
			System.out.println("Piso-Depto: " + propiedadActual.getNivel()+" - "+propiedadActual.getOrden()+" = "+expensa.getMonto());
		}

		
	}
}
