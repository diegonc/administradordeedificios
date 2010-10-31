package test.expensas;

import edificio.EdificioAppl;
import expensas.calculo.ElementoProrrateo;
import expensas.calculo.ResultadoProrrateo;
import expensas.dto.ExpensaDTO;
import gastos.dto.GastoDTO;
import gastos.dto.GastoPrevisionDTO;
import gastos.dto.GastoRealDTO;
import gastos.dto.TipoGastoDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;

import propiedades.PropiedadDTO;
import propiedades.TipoPropiedadDTO;
import propiedades.TipoPropiedadTipoGastoDTO;
import utilidades.HibernateUtil;
import utilidades.Periodo;

public class TestExpensaPorPrevision extends TestCase {
	
	class ElementoPrevisionGasto{
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
			return redondeoDouble(montoDiferencia);
		}
	
		public double obtenerMontoPrevisionMesAnterior(){
			double monto= 0;
			for (GastoPrevisionDTO g : this.previsionesMesAnterior) {
				monto+=g.getMonto();
			}
			return redondeoDouble(monto);
		}
		
		public double obtenerMontoGastosRealesMesAnterior(){
			double monto= 0;
			for (GastoRealDTO g : this.gastosRealesMesAnterior) {
				monto+=g.getMonto();
			}
			return redondeoDouble(monto);
		}
		
		public double obtenerMontoPrevisionMesActual(){
			double monto= 0;
			for (GastoPrevisionDTO g : this.previsiones) {
				monto+=g.getMonto();
			}
			return redondeoDouble(monto);
		}
		
		public double obtenerMontoTotal(){
			double monto = 0;
			monto = obtenerMontoDiferenciaPrevisionGastoMesAnterior()+obtenerMontoPrevisionMesActual();
			return redondeoDouble(monto);
		}
	}

	
	/**
	 * Utilidades
	 * @param valor
	 * @return
	 */
	private double redondeoDouble(double valor){
		return Math.round(valor*Math.pow(10,2))/Math.pow(10,2);
	}		
	
	
	@SuppressWarnings("unchecked")
	private List<GastoRealDTO> obtenerGastosRealesOrdinariosPorEdificioYPeriodo(int idEdificio,Periodo periodo){
		Session session = HibernateUtil.getSession();
		
		Query query = session.createQuery("select g from GastoRealDTO g where " +
							"g.fechaPago between :fechaInicio and :fechaFin and g.edificio =:idEdificio" +
							" and g.tipoGasto.tipo <> 'EXT' ");
		
		query.setString("fechaInicio", periodo.obtenerFechaInicio());
		query.setString("fechaFin", periodo.obtenerFechaFin());
		query.setInteger("idEdificio", idEdificio);
									
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	private List<GastoRealDTO> obtenerGastosRealesExtraordinariosPorEdificioYPeriodo(int idEdificio,Periodo periodo){
		Session session = HibernateUtil.getSession();
		
		Query query = session.createQuery("select g from GastoRealDTO g where " +
							"g.fechaPago between :fechaInicio and :fechaFin and g.edificio =:idEdificio" +
							" and g.tipoGasto.tipo = 'EXT' ");
		
		query.setString("fechaInicio", periodo.obtenerFechaInicio());
		query.setString("fechaFin", periodo.obtenerFechaFin());
		query.setInteger("idEdificio", idEdificio);
									
		return query.list();
	}
		
	@SuppressWarnings("unchecked")
	private List<GastoPrevisionDTO> obtenerPrevisionesPorEdificioYPeriodo(int idEdificio,Periodo periodo){
		Session session = HibernateUtil.getSession();
		
		Query	query = session.createQuery("select gp from GastoPrevisionDTO gp where " +
				"gp.anio =:anio and gp.mes =:mes and gp.edificio =:idEdificio" +
				" and gp.tipoGasto.tipo <> 'EXT' ");

		query.setInteger("anio", periodo.getAnio());
		query.setInteger("mes", periodo.getMes());
		query.setInteger("idEdificio", idEdificio);
										
		return query.list();
	}
	
			
	public	HashMap<TipoGastoDTO, ElementoPrevisionGasto> obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(int idEdificio,Periodo periodo){
		
		List<GastoRealDTO> gastosRealesMesAnterior;
		List<GastoPrevisionDTO> previsionesMesAnterior;
		List<GastoPrevisionDTO> previsionesMesActual;
		Periodo periodoAnterior = periodo.obtenerPeriodoAnterior();
				
		gastosRealesMesAnterior = obtenerGastosRealesOrdinariosPorEdificioYPeriodo(idEdificio, periodoAnterior);
		previsionesMesAnterior = obtenerPrevisionesPorEdificioYPeriodo(idEdificio, periodoAnterior);
		previsionesMesActual = obtenerPrevisionesPorEdificioYPeriodo(idEdificio, periodo);
		
		HashMap<TipoGastoDTO, ElementoPrevisionGasto> mapa = new HashMap<TipoGastoDTO, ElementoPrevisionGasto>();
		ElementoPrevisionGasto previsionGasto;
			
		for (GastoPrevisionDTO gp : previsionesMesAnterior) {
			previsionGasto = mapa.get(gp.getTipoGasto());
			if(previsionGasto==null){
				previsionGasto = new ElementoPrevisionGasto();
			}
			previsionGasto.getPrevisionesMesAnterior().add(gp);
			mapa.put(gp.getTipoGasto(),previsionGasto);
		}
	
		for (GastoRealDTO gr : gastosRealesMesAnterior) {
			previsionGasto = mapa.get(gr.getTipoGasto());
			if(previsionGasto==null){
				previsionGasto = new ElementoPrevisionGasto();
			}
			previsionGasto.getGastosRealesMesAnterior().add(gr);
			mapa.put(gr.getTipoGasto(),previsionGasto);
		}
		
		for (GastoPrevisionDTO gp : previsionesMesActual) {
			previsionGasto = mapa.get(gp.getTipoGasto());
			if(previsionGasto==null){
				previsionGasto = new ElementoPrevisionGasto();
			}
			previsionGasto.getPrevisiones().add(gp);
			mapa.put(gp.getTipoGasto(),previsionGasto);
		}
		return mapa;
	}
					
	/**
	 * ----------TEST testObtenerGastosPrevisionesPorEdificioAgrupadoPorTipo -------------------
	 */
	public void testObtenerGastosPrevisionesPorEdificioAgrupadoPorTipo(){
		Periodo periodo = new Periodo();
		periodo.setAnio(2010);
		periodo.setMes(11);
		HashMap<TipoGastoDTO, ElementoPrevisionGasto> tipoGastoElementoPrevisionGasto= obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(1, periodo);
		
		Iterator<TipoGastoDTO> it = tipoGastoElementoPrevisionGasto.keySet().iterator();
	    while (it.hasNext()) {
	        TipoGastoDTO tipoGasto = (TipoGastoDTO) it.next();
	        ElementoPrevisionGasto elementoPrevisionGasto = (ElementoPrevisionGasto) tipoGastoElementoPrevisionGasto.get(tipoGasto);
	        System.out.println(tipoGasto.getCodigo());
	        
	        System.out.println("******Previsiones mes anterior******");
	        for (GastoDTO gastoActual : elementoPrevisionGasto.previsionesMesAnterior) {
	        	System.out.println(gastoActual.getDetalle()+" : "+gastoActual.getMonto());
			}
	        System.out.println("Total previsiones mes anterior: " + elementoPrevisionGasto.obtenerMontoPrevisionMesAnterior());
	        	        
	        System.out.println("******Gastos mes anterior******");
	        for (GastoDTO gastoActual : elementoPrevisionGasto.gastosRealesMesAnterior) {
	        	System.out.println(gastoActual.getDetalle()+" : "+gastoActual.getMonto());
			}
	        System.out.println("Total gastos reales mes anterior: " + elementoPrevisionGasto.obtenerMontoGastosRealesMesAnterior());
	        System.out.println("Total diferencia prevision gasto mes anterior: " + elementoPrevisionGasto.obtenerMontoDiferenciaPrevisionGastoMesAnterior());
	        
	        System.out.println("******Previsiones mes actual******");
	        for (GastoDTO gastoActual : elementoPrevisionGasto.previsiones) {
	        	System.out.println(gastoActual.getDetalle()+" : "+gastoActual.getMonto());
			}
	        System.out.println("Total previsiones mes actual: " + elementoPrevisionGasto.obtenerMontoPrevisionMesActual());
	        System.out.println("Totalizacion Tipo Gasto : " + elementoPrevisionGasto.obtenerMontoTotal());
	        System.out.println(""); 
	    }
	}

	/****************************************************************************************************/
	public HashMap<TipoPropiedadDTO, Double> obtenerProrrateoExpensasOrdinarias(int idEdificio,Periodo periodo){
		HashMap<TipoGastoDTO, ElementoPrevisionGasto> tipoGastoElementoPrevisionGasto = obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(idEdificio, periodo);
		return obtenerProrrateoExpensasOrdinarias(tipoGastoElementoPrevisionGasto);
	}
	
	
	public HashMap<TipoPropiedadDTO, Double> obtenerProrrateoExpensasOrdinarias(HashMap<TipoGastoDTO, ElementoPrevisionGasto> tipoGastoElementoPrevisionGasto){
		
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa = new HashMap<TipoPropiedadDTO, Double>();
		
		List<GastoDTO> gastos = obtenerGastosTotalizadosPorTipo(tipoGastoElementoPrevisionGasto);
					
		
		for(GastoDTO gastoActual: gastos){
		
			System.out.println(gastoActual.getTipoGasto().getCodigo() + " : " + gastoActual.getMonto());
			List<ResultadoProrrateo> resultadoProrrateo = generarResultadoDeProrrateo(gastoActual);
			 			
			for (ResultadoProrrateo resultadoActual : resultadoProrrateo) {
				Double montoActual = (tipoPropiedadMontoExpensa.get(resultadoActual.getTipoPropiedad())==null)?0:tipoPropiedadMontoExpensa.get(resultadoActual.getTipoPropiedad());
				tipoPropiedadMontoExpensa.put(resultadoActual.getTipoPropiedad(), redondeoDouble(resultadoActual.getMonto()+ montoActual));
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
		
	
	private List<ResultadoProrrateo> generarResultadoDeProrrateo(GastoDTO gasto){
		int posicionElemento = 0;
		Double coeficienteActual;
		List<ElementoProrrateo> elementosProrrateo = new ArrayList<ElementoProrrateo>();
						
		for (TipoPropiedadTipoGastoDTO tipoPropiedadGasto : gasto.getTipoGasto().getTiposPropiedadesGastos()) {
	
			ElementoProrrateo elementoActual = new ElementoProrrateo();
			coeficienteActual = tipoPropiedadGasto.getCoeficienteDistribucion();
			
			if(coeficienteActual!=null){
				elementoActual.setCoeficiente(coeficienteActual.doubleValue());
			}
									
			posicionElemento = elementosProrrateo.indexOf(elementoActual);
			if(posicionElemento!= -1){
				elementoActual = elementosProrrateo.get(posicionElemento);
				elementoActual.getTiposDePropiedades().add(tipoPropiedadGasto.getTipoPropiedad());
			}
			else{
				elementoActual.getTiposDePropiedades().add(tipoPropiedadGasto.getTipoPropiedad());
				elementosProrrateo.add(elementoActual);
			}
		}
		Collections.sort(elementosProrrateo);
		return prorratear(elementosProrrateo,gasto.getMonto());
	}
	
	private List<ResultadoProrrateo> prorratear(List<ElementoProrrateo> elementosProrrateo,double monto){
		
		List<ResultadoProrrateo> resultadosProrrateo = new ArrayList<ResultadoProrrateo>();
		double coeficienteActual = 0;
		double ultimoCoeficiente = 0;
		
		List<ElementoProrrateo> elementosProrrateoAux = new ArrayList<ElementoProrrateo>();
		elementosProrrateoAux.addAll(elementosProrrateo);
		
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadTotal = new HashMap<TipoPropiedadDTO, Double>();
		
		for (ElementoProrrateo elementoProrrateo : elementosProrrateo) {
			for (TipoPropiedadDTO tp : elementoProrrateo.getTiposDePropiedades()) {
				tipoPropiedadTotal.put(tp, 0.0);
			}
		}
						
		for (ElementoProrrateo elementoProrrateo : elementosProrrateo) {
			coeficienteActual=elementoProrrateo.getCoeficiente();
			double superficieTotal = 0;
			for (ElementoProrrateo elementoProrrateoAux : elementosProrrateoAux) {
				if(elementoProrrateoAux.getCoeficiente()>=coeficienteActual){
					superficieTotal+=elementoProrrateoAux.getSuperficieTotal();
				}
			}
			for (ElementoProrrateo elementoProrrateoAux : elementosProrrateoAux) {
				if(elementoProrrateoAux.getCoeficiente()>=coeficienteActual){
					for(TipoPropiedadDTO tp: elementoProrrateoAux.getTiposDePropiedades()){
						double sumaActual = tipoPropiedadTotal.get(tp).doubleValue() + (coeficienteActual-ultimoCoeficiente)*(tp.getDivisor()/superficieTotal);
						tipoPropiedadTotal.put(tp, sumaActual);
					}
				}
			}
			ultimoCoeficiente = coeficienteActual;
		}
		
		Iterator<TipoPropiedadDTO> it = tipoPropiedadTotal.keySet().iterator();
		TipoPropiedadDTO tpActual=null;
		
		while (it.hasNext()) {
	        tpActual = (TipoPropiedadDTO) it.next();
	        ResultadoProrrateo resultadoProrrateo = new ResultadoProrrateo();
	        resultadoProrrateo.setTipoPropiedad(tpActual);
	        resultadoProrrateo.setMonto(redondeoDouble(monto*tipoPropiedadTotal.get(tpActual).doubleValue()));
	        resultadosProrrateo.add(resultadoProrrateo);
	    }
		
		return resultadosProrrateo;
	}
			
		
	
	
	/*************************************************************************************************************************/
	
	public List<ExpensaDTO> calcularExpensasDePropiedadesPorEdificioYPeriodo(int idEdificio,Periodo periodo){
		
		double montoExpensa = 0;
		double montoTotal=0;
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa = obtenerProrrateoExpensasOrdinarias(idEdificio,periodo);
		List<ExpensaDTO> expensas= new ArrayList<ExpensaDTO>();
		EdificioAppl edificioAppl = new EdificioAppl();
		ExpensaDTO expensaActual;
			
		List<TipoPropiedadDTO> tiposDePropiedades = edificioAppl.obtenerTipoPropiedadPorEdificio(1);
		
		for (TipoPropiedadDTO tipoProp: tiposDePropiedades) {
			montoTotal = (tipoPropiedadMontoExpensa.get(tipoProp)==null)?0:tipoPropiedadMontoExpensa.get(tipoProp);
			for(PropiedadDTO propiedad: tipoProp.getPropiedades()){
				montoExpensa = montoTotal*(propiedad.getDividendo()/tipoProp.getDivisor());
				expensaActual = new ExpensaDTO();
				expensaActual.setPropiedad(propiedad);
				expensaActual.setMonto(redondeoDouble(montoExpensa));
				expensaActual.setTipo(ExpensaDTO.tipoOrdinario);
				
				//TODO ver q debe tomar el numero siguiente por propiedad y tipo de expensa
				expensaActual.setNumeroOperacion(expensaActual.getId());
				
				expensas.add(expensaActual);
			}
		}
		return expensas;
	}
	
	public void testCalcularExpensasDePropiedadesPorEdificioYPeriodo(){
		Periodo periodo = new Periodo();
		periodo.setAnio(2010);
		periodo.setMes(11);
	
		List<ExpensaDTO> expensas = calcularExpensasDePropiedadesPorEdificioYPeriodo(1, periodo);
		
		System.out.println("Expensas Por Propiedad");
				
		PropiedadDTO propiedadActual;
		for (ExpensaDTO expensa: expensas) {
			propiedadActual = expensa.getPropiedad();
			System.out.println("Piso-Depto: " + propiedadActual.getNivel()+" - "+propiedadActual.getOrden()+" = "+expensa.getMonto());
		}
	}
}
