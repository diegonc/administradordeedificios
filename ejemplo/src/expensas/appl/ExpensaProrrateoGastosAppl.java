package expensas.appl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import propiedades.PropiedadDTO;
import propiedades.TipoPropiedadDTO;
import utilidades.NumberFormat;
import utilidades.Periodo;
import edificio.EdificioAppl;
import expensas.calculo.ResultadoProrrateo;
import expensas.dto.ExpensaDTO;
import gastos.dto.GastoDTO;
import gastos.dto.TipoGastoDTO;

public class ExpensaSinPrevisionAppl extends ExpensaCalculoAppl{
			
	public HashMap<TipoGastoDTO, List<GastoDTO>> obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(int idEdificio,Periodo periodo,String tipoExpensa){
		List<GastoDTO> gastos = obtenerGastosRealesPorEdificioYPeriodo(idEdificio, periodo,tipoExpensa);
		HashMap<TipoGastoDTO, List<GastoDTO>> tipoGastoGasto = new HashMap<TipoGastoDTO, List<GastoDTO>>();
		
		for(GastoDTO gastoActual: gastos){
			List<GastoDTO> listadoGastos = tipoGastoGasto.get(gastoActual.getTipoGasto());
			if(listadoGastos==null)
				listadoGastos = new ArrayList<GastoDTO>();
			listadoGastos.add(gastoActual);
			tipoGastoGasto.put(gastoActual.getTipoGasto(), listadoGastos);
		}
		
		Iterator<TipoGastoDTO> it = tipoGastoGasto.keySet().iterator();
	    while (it.hasNext()) {
	        TipoGastoDTO tipoGasto = (TipoGastoDTO) it.next();
	        List<GastoDTO> gastosEnTipo = (List<GastoDTO>) tipoGastoGasto.get(tipoGasto);
	        System.out.println(tipoGasto.getCodigo());
	        for (GastoDTO gastoActual : gastosEnTipo) {
	        	System.out.println(gastoActual.getDetalle()+" : "+gastoActual.getMonto());
	        }
	    }
		return tipoGastoGasto;
	}
		
	public HashMap<TipoPropiedadDTO, Double> obtenerProrrateoExpensas(List<GastoDTO> gastos ,String tipoExpensa){
		
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa = new HashMap<TipoPropiedadDTO, Double>();
		
		for(GastoDTO gastoActual: gastos){
			System.out.println("Codigo: " + gastoActual.getTipoGasto().getCodigo());
			System.out.println("Descripcion: " + gastoActual.getTipoGasto().getDescripcion());
			System.out.println("Detalle: " + gastoActual.getDetalle());
			System.out.println("Monto: " + gastoActual.getMonto());
			List<ResultadoProrrateo> resultadoProrrateo = generarResultadoDeProrrateo(gastoActual);
			 			
			for (ResultadoProrrateo resultadoActual : resultadoProrrateo) {
				Double montoActual = (tipoPropiedadMontoExpensa.get(resultadoActual.getTipoPropiedad())==null)?0:tipoPropiedadMontoExpensa.get(resultadoActual.getTipoPropiedad());
				tipoPropiedadMontoExpensa.put(resultadoActual.getTipoPropiedad(), NumberFormat.redondeoDouble(resultadoActual.getMonto()+ montoActual));
				System.out.println(resultadoActual.getTipoPropiedad().getNombreTipo()+" : "+resultadoActual.getMonto());
			}
			System.out.println("-----------------------------------------------------------");
		}
		Iterator<TipoPropiedadDTO> it = tipoPropiedadMontoExpensa.keySet().iterator();
	    while (it.hasNext()) {
	        TipoPropiedadDTO tipoPropiedad = (TipoPropiedadDTO) it.next();
	        Double monto = (Double) tipoPropiedadMontoExpensa.get(tipoPropiedad);
	        System.out.println(tipoPropiedad.getNombreTipo() + " : " + monto.doubleValue());
	    }
	    
	    return tipoPropiedadMontoExpensa; 
	}
	
	private HashMap<TipoPropiedadDTO, Double> obtenerProrrateoExpensasOrdinarias(int idEdificio,Periodo periodo){
		List<GastoDTO> gastos = obtenerGastosRealesPorEdificioYPeriodo(idEdificio, periodo,ExpensaDTO.tipoOrdinario);
		return obtenerProrrateoExpensas(gastos,ExpensaDTO.tipoOrdinario);
	}
	
	private HashMap<TipoPropiedadDTO, Double> obtenerProrrateoExpensasExtraordinarias(int idEdificio, Periodo periodo) {
		List<GastoDTO> gastos = obtenerGastosRealesPorEdificioYPeriodo(idEdificio, periodo,ExpensaDTO.tipoExtraordinario);
		return obtenerProrrateoExpensas(gastos,ExpensaDTO.tipoExtraordinario);
		
	}
	
	
	public List<ExpensaDTO> calcularExpensasOrdinariasDePropiedadesPorEdificioYPeriodo(int idEdificio,Periodo periodo){
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa = obtenerProrrateoExpensasOrdinarias(idEdificio,periodo);
		return obtenerExpensaPorTipoYEdificio(tipoPropiedadMontoExpensa,ExpensaDTO.tipoOrdinario,idEdificio);
	}
	
	
	public List<ExpensaDTO> calcularExpensasExtraordinariasDePropiedadesPorEdificioYPeriodo(int idEdificio,Periodo periodo){
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa = obtenerProrrateoExpensasExtraordinarias(idEdificio,periodo);
		return obtenerExpensaPorTipoYEdificio(tipoPropiedadMontoExpensa,ExpensaDTO.tipoExtraordinario,idEdificio);
	}
			
		
	/* 	
	 *	Visualizar
	 * 	HashMap<TipoGastoDTO, List<GastoDTO> tipoGastoElementoPrevisionGasto = obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(int idEdificio,Periodo periodo,String tipoExpensa);
	 * 
	 * 	ObtenerExpensas
	 * 	List<ExpensaDTO> calcularExpensasOrdinariasDePropiedadesPorEdificioYPeriodo(int idEdificio,Periodo periodo)
	 */
	
	private List<ExpensaDTO> obtenerExpensaPorTipoYEdificio(HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa,String tipoExpensa,int idEdificio){
		double montoExpensa = 0;
		double montoTotal=0;
		List<ExpensaDTO> expensas= new ArrayList<ExpensaDTO>();
		EdificioAppl edificioAppl = new EdificioAppl();
		ExpensaAppl expensaAppl = new ExpensaAppl();
		ExpensaDTO expensaActual;
		
		List<TipoPropiedadDTO> tiposDePropiedades = edificioAppl.obtenerTipoPropiedadPorEdificio(idEdificio);
		
		for (TipoPropiedadDTO tipoProp: tiposDePropiedades) {
			montoTotal = (tipoPropiedadMontoExpensa.get(tipoProp)==null)?0:tipoPropiedadMontoExpensa.get(tipoProp);
			for(PropiedadDTO propiedad: tipoProp.getPropiedades()){
				montoExpensa = montoTotal*(propiedad.getDividendo()/tipoProp.getDivisor());
				expensaActual = new ExpensaDTO();
				expensaActual.setPropiedad(propiedad);
				expensaActual.setMonto(NumberFormat.redondeoDouble(montoExpensa));
				expensaActual.setTipo(tipoExpensa);
				expensaActual.setNumeroOperacion(expensaAppl.obtenerNumeroDeOperacion(propiedad.getId(), tipoExpensa));
				expensas.add(expensaActual);
			}
		}	
		return expensas;
	}

	
			
		
		
	
}
