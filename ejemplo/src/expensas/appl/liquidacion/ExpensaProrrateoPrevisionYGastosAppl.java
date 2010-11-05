package expensas.appl.liquidacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import propiedades.PropiedadDTO;
import propiedades.TipoPropiedadDTO;
import utilidades.HibernateUtil;
import utilidades.NumberFormat;
import utilidades.Periodo;
import edificio.EdificioAppl;
import edificio.EdificioDTO;
import expensas.appl.ExpensaAppl;
import expensas.appl.ExpensaInteresesAppl;
import expensas.calculo.ElementoPrevisionGasto;
import expensas.calculo.ResultadoProrrateo;
import expensas.dto.ExpensaDTO;
import gastos.dto.GastoDTO;
import gastos.dto.TipoGastoDTO;

public class ExpensaProrrateoPrevisionYGastosAppl extends ExpensaCalculoAppl {

		
	/**
	 * Obtiene los gastos y previsiones agrupados por tipo para un edificio en un periodo determinado.
	 * @param idEdificio
	 * @param periodo
	 * @return
	 */
	public	HashMap<TipoGastoDTO, ElementoPrevisionGasto> obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(int idEdificio,Periodo periodo,String tipoExpensa){
		List<GastoDTO> gastosRealesMesAnterior;
		List<GastoDTO> previsionesMesAnterior;
		List<GastoDTO> previsionesMesActual;
		Periodo periodoAnterior = periodo.obtenerPeriodoAnterior();
				
		gastosRealesMesAnterior = obtenerGastosRealesPorEdificioYPeriodo(idEdificio, periodoAnterior,tipoExpensa);
		previsionesMesAnterior = obtenerPrevisionesPorEdificioYPeriodo(idEdificio, periodoAnterior,tipoExpensa);
		previsionesMesActual = obtenerPrevisionesPorEdificioYPeriodo(idEdificio, periodo,tipoExpensa);
		
		HashMap<TipoGastoDTO, ElementoPrevisionGasto> mapa = new HashMap<TipoGastoDTO, ElementoPrevisionGasto>();
		ElementoPrevisionGasto previsionGasto;
			
		for (GastoDTO gp : previsionesMesAnterior) {
			previsionGasto = mapa.get(gp.getTipoGasto());
			if(previsionGasto==null){
				previsionGasto = new ElementoPrevisionGasto();
			}
			previsionGasto.getPrevisionesMesAnterior().add(gp);
			mapa.put(gp.getTipoGasto(),previsionGasto);
		}
	
		for (GastoDTO gr : gastosRealesMesAnterior) {
			previsionGasto = mapa.get(gr.getTipoGasto());
			if(previsionGasto==null){
				previsionGasto = new ElementoPrevisionGasto();
			}
			previsionGasto.getGastosRealesMesAnterior().add(gr);
			mapa.put(gr.getTipoGasto(),previsionGasto);
		}
		
		for (GastoDTO gp : previsionesMesActual) {
			previsionGasto = mapa.get(gp.getTipoGasto());
			if(previsionGasto==null){
				previsionGasto = new ElementoPrevisionGasto();
			}
			previsionGasto.getPrevisiones().add(gp);
			mapa.put(gp.getTipoGasto(),previsionGasto);
		}
		return mapa;
	}
	
	public List<ExpensaDTO> obtenerExpensasPorTipoPorEdificioYPeriodo(HashMap<TipoGastoDTO, ElementoPrevisionGasto> tipoGastoElementoPrevisionGasto,int idEdificio,String tipoExpensa){
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa = obtenerProrrateoExpensas(tipoGastoElementoPrevisionGasto);
		return calcularExpensaPorTipoYEdificio(tipoPropiedadMontoExpensa,tipoExpensa,idEdificio);
	}
	
	/**
	 * Totaliza los gastos por tipo de gasto.
	 * @param tipoGastoElementoPrevisionGasto
	 * @return
	 */
	private List<GastoDTO> obtenerGastosTotalizadosPorTipo(HashMap<TipoGastoDTO, ElementoPrevisionGasto> tipoGastoElementoPrevisionGasto){
		ElementoPrevisionGasto elementoPrevisionGasto ;
		GastoDTO gastoActual;
		TipoGastoDTO tipoGasto;
		List<GastoDTO> gastos = new ArrayList<GastoDTO>();
		
		Iterator<TipoGastoDTO> it = tipoGastoElementoPrevisionGasto.keySet().iterator();
			
		while (it.hasNext()) {
	        tipoGasto = (TipoGastoDTO) it.next();
	        elementoPrevisionGasto = (ElementoPrevisionGasto) tipoGastoElementoPrevisionGasto.get(tipoGasto);
	        gastoActual = new GastoDTO();
	        gastoActual.setTipoGasto(tipoGasto);
	        gastoActual.setMonto(elementoPrevisionGasto.obtenerMontoTotal());
	        gastos.add(gastoActual);
	    }
		return gastos;
	}
			
	private HashMap<TipoPropiedadDTO, Double> obtenerProrrateoExpensas(HashMap<TipoGastoDTO, ElementoPrevisionGasto> tipoGastoElementoPrevisionGasto){
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa = new HashMap<TipoPropiedadDTO, Double>();
		List<GastoDTO> gastos = obtenerGastosTotalizadosPorTipo(tipoGastoElementoPrevisionGasto);
				
		for(GastoDTO gastoActual: gastos){
			System.out.println(gastoActual.getTipoGasto().getCodigo() + " : " + gastoActual.getMonto());
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
			
	private void actualizarSaldos(PropiedadDTO propiedadActual, ExpensaDTO expensa) {
		if(expensa.getTipo().equalsIgnoreCase("O")){
			propiedadActual.setCtaOrdSaldoExp(-(expensa.getDeudaPrevia()+expensa.getMonto()));
			propiedadActual.setCtaOrdSaldoInt(-expensa.getIntereses());
		}else{
			propiedadActual.setCtaExtSaldoExp(-(expensa.getDeudaPrevia()+expensa.getMonto()));
			propiedadActual.setCtaExtSaldoInt(-(expensa.getIntereses()));
		}
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.update(propiedadActual);
		session.getTransaction().commit();
	}
	
	private List<ExpensaDTO> calcularExpensaPorTipoYEdificio(HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa,String tipoExpensa,int idEdificio){
		double montoExpensa = 0;
		double montoTotal=0;
		Session session = HibernateUtil.getSession();
		List<ExpensaDTO> expensas= new ArrayList<ExpensaDTO>();
		EdificioAppl edificioAppl = new EdificioAppl();
		ExpensaAppl expensaAppl = new ExpensaAppl();
		ExpensaInteresesAppl expensasIntereses = new ExpensaInteresesAppl();
		ExpensaDTO expensaActual;
		EdificioDTO edificio = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(), idEdificio);
		
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
				
				if (edificio.getMora().equalsIgnoreCase(EdificioDTO.PUNITORIO))
					expensasIntereses.calcularInteresPunitorio(edificio, expensaActual);
				if (edificio.getMora().equalsIgnoreCase(EdificioDTO.A_FECHA))
					expensasIntereses.calcularInteresAFechaDePago(edificio, expensaActual);
				if (edificio.getMora().equalsIgnoreCase(EdificioDTO.DIFERIDO))
					expensasIntereses.calcularInteresDiferidoProximaLiquidacion(edificio, expensaActual);
				actualizarSaldos(propiedad, expensaActual);
				session.beginTransaction();
				session.saveOrUpdate(expensaActual);
				session.getTransaction().commit();			
				
				
				expensas.add(expensaActual);
			}
		}	
		return expensas;
	}
	
}
