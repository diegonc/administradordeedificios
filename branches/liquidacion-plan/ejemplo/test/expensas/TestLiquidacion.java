package expensas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import propiedades.PropiedadDTO;
import utilidades.Periodo;
import expensas.appl.liquidacion.ExpensaProrrateoPrevisionAppl;
import expensas.dto.ExpensaDTO;
import gastos.dto.GastoDTO;
import gastos.dto.TipoGastoDTO;

public class TestLiquidacion extends TestCase{

	public void testLiquidar(){
		int idEdificio = 1;
		Periodo periodo = new Periodo(11,2010);
		ExpensaProrrateoPrevisionAppl expensaProrrateoPrevisionAppl = new ExpensaProrrateoPrevisionAppl();
		
		HashMap<TipoGastoDTO, List<GastoDTO>> tipoGastoGastos = expensaProrrateoPrevisionAppl.obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(idEdificio, periodo, ExpensaDTO.tipoOrdinario);
		
		Iterator<TipoGastoDTO> it = tipoGastoGastos.keySet().iterator();
	    while (it.hasNext()) {
	        double montoAcumulado =0;
	    	TipoGastoDTO tipoGasto = (TipoGastoDTO) it.next();
	        List<GastoDTO> gastos = (List<GastoDTO>) tipoGastoGastos.get(tipoGasto);
	        System.out.println(tipoGasto.getCodigo());
	        System.out.println("******Previsiones mes actual******");
	        for (GastoDTO gastoActual : gastos) {
	        	System.out.println(gastoActual.getDetalle()+" : "+gastoActual.getMonto());
	        	montoAcumulado+=gastoActual.getMonto();
			}
	        System.out.println("Totalizacion Tipo Gasto : " + montoAcumulado);
	    }
	    
	    List<ExpensaDTO> expensas = expensaProrrateoPrevisionAppl. obtenerExpensasPorTipoPorEdificioYPeriodo(tipoGastoGastos,idEdificio, ExpensaDTO.tipoOrdinario);
	    
	    System.out.println("\nExpensas Por Propiedad");
	    System.out.println("***********************ORDINARIOS**************************");
		
		PropiedadDTO propiedadActual;
		for (ExpensaDTO expensa: expensas) {
			propiedadActual = expensa.getPropiedad();
			System.out.println(propiedadActual.getNivel()+";"+propiedadActual.getOrden()+";"+expensa.getMonto());
		}
		
		/***************************************************************************************************************************/
		
		
		tipoGastoGastos = expensaProrrateoPrevisionAppl.obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(idEdificio, periodo, ExpensaDTO.tipoExtraordinario);
		it = tipoGastoGastos.keySet().iterator();
		System.out.println();
		System.out.println("***********************EXTRAORDINARIOS**************************");
		while (it.hasNext()) {
	    	double montoAcumulado =0;
	    	TipoGastoDTO tipoGasto = (TipoGastoDTO) it.next();
	        List<GastoDTO> gastos = (List<GastoDTO>) tipoGastoGastos.get(tipoGasto);
	        System.out.println(tipoGasto.getCodigo());
	        System.out.println("******Previsiones mes actual******");
	        for (GastoDTO gastoActual : gastos) {
	        	System.out.println(gastoActual.getDetalle()+" : "+gastoActual.getMonto());
	        	montoAcumulado+=gastoActual.getMonto();
			}
	        System.out.println("Totalizacion Tipo Gasto : " + montoAcumulado);
	    }
		System.out.println("*****************************************************");
	    
	    expensas = expensaProrrateoPrevisionAppl.obtenerExpensasPorTipoPorEdificioYPeriodo(tipoGastoGastos, idEdificio, ExpensaDTO.tipoExtraordinario);
	    System.out.println("Expensas Por Propiedad");
				
		for (ExpensaDTO expensa: expensas) {
			propiedadActual = expensa.getPropiedad();
			System.out.println("Piso-Depto: " + propiedadActual.getNivel()+" - "+propiedadActual.getOrden()+" = "+expensa.getMonto());
		}
		
		
	}
}
