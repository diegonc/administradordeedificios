package test.expensas;

import java.util.ArrayList;
import java.util.Date;
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
import edificio.EdificioAppl;
import edificio.EdificioDTO;
import expensas.dto.ExpensaDTO;
import gastos.dto.GastoDTO;
import gastos.dto.TipoGastoDTO;

public class TestExpensas extends TestCase {
	
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
	
	public HashMap<TipoPropiedadDTO, Double> obtenerProrrateoExpensasOrdinarias(EdificioDTO edificio){
		Periodo periodo = new Periodo(new Date());
		List<GastoDTO> gastos = obtenerGastosOrdinariosPorEdificioYPeriodo(edificio.getId(), periodo);
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa = new HashMap<TipoPropiedadDTO, Double>();
		
		for(GastoDTO gastoActual: gastos){
			System.out.println("Codigo: " + gastoActual.getTipoGasto().getCodigo());
			System.out.println("Descripcion: " + gastoActual.getTipoGasto().getDescripcion());
			System.out.println("Detalle: " + gastoActual.getDetalle());
			System.out.println("Monto: " + gastoActual.getMonto());
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
	
	public void testExpensasPorEdificio(){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		EdificioAppl edificioAppl = new EdificioAppl();
		EdificioDTO edificio = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(), 1);
		double montoExpensa = 0;
		double montoTotal=0;
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa = obtenerProrrateoExpensasOrdinarias(edificio);
										
		for (TipoPropiedadDTO tipoProp: edificio.getTipoPropiedades()) {
			montoTotal = (tipoPropiedadMontoExpensa.get(tipoProp)==null)?0:tipoPropiedadMontoExpensa.get(tipoProp);
			System.out.println(tipoProp.getNombreTipo()+" - "+montoTotal);
			for(PropiedadDTO propiedad: tipoProp.getPropiedades()){
				montoExpensa = redondeoDouble(montoTotal*(propiedad.getDividendo()/tipoProp.getDivisor()));
				propiedad.setCtaOrdSaldoExp(montoExpensa);
				session.saveOrUpdate(propiedad);
				System.out.println(propiedad.getNivel()+" - " + propiedad.getOrden()+" : "+propiedad.getCtaOrdSaldoExp());
			}
		}
		session.getTransaction().commit();
	}
	
			
	public HashMap<TipoPropiedadDTO, Double> obtenerProrrateoExpensasExtraordinarias(){
		Periodo periodo = new Periodo(new Date());
		List<GastoDTO> gastos = obtenerGastosExtraordinariosPorEdificioYPeriodo(1, periodo);
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa = new HashMap<TipoPropiedadDTO, Double>();
		
		for(GastoDTO gastoActual: gastos){
			System.out.println("Codigo: " + gastoActual.getTipoGasto().getCodigo());
			System.out.println("Descripcion: " + gastoActual.getTipoGasto().getDescripcion());
			System.out.println("Detalle: " + gastoActual.getDetalle());
			System.out.println("Monto: " + gastoActual.getMonto());
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
			else elementoActual.setCoeficiente(0);
						
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
		return prorratear(elementosProrrateo,gasto.getMonto());
	}
	
	
	private List<ResultadoProrrateo> prorratear(List<ElementoProrrateo> elementosProrrateo,double monto){
		double sumaCoeficientesNoNulos = 0;
		double superficieTotal2 = 0;
		List<ResultadoProrrateo> resultadosProrrateo = new ArrayList<ResultadoProrrateo>();
		List<Double> coeficientesNoNulos = new ArrayList<Double>();
		double coeficienteActual = 0;
		
		for (ElementoProrrateo elementoProrrateo : elementosProrrateo) {
			coeficienteActual=elementoProrrateo.getCoeficiente();
			if(coeficienteActual!=0){
				sumaCoeficientesNoNulos+=coeficienteActual;
				coeficientesNoNulos.add(elementoProrrateo.getCoeficiente());
			}
			superficieTotal2+= elementoProrrateo.getSuperficieTotal();
		}
		for (ElementoProrrateo elementoProrrateo : elementosProrrateo) {
			for (TipoPropiedadDTO tp : elementoProrrateo.getTiposDePropiedades()) {
				ResultadoProrrateo resultadoProrrateo = new ResultadoProrrateo();
				resultadoProrrateo.tipoPropiedad = tp;
				coeficienteActual=elementoProrrateo.getCoeficiente();
				if(coeficienteActual!=0){
					resultadoProrrateo.monto = coeficienteActual*(tp.getDivisor()/superficieTotal2);
				}else{
					double montoActual = 0;
					for (Double coeficienteNoNulo: coeficientesNoNulos) {
						montoActual+= coeficienteNoNulo*(tp.getDivisor()/superficieTotal2);
					}
					resultadoProrrateo.monto = montoActual +((1-sumaCoeficientesNoNulos)*(tp.getDivisor()/elementoProrrateo.getSuperficieTotal()));
				}
				resultadoProrrateo.monto*=monto;
				resultadoProrrateo.monto=redondeoDouble(resultadoProrrateo.monto);
				resultadosProrrateo.add(resultadoProrrateo);
			}
		}
		return resultadosProrrateo;
	}
	
	private double redondeoDouble(double valor){
		return Math.round(valor*Math.pow(10,2))/Math.pow(10,2);
	}
			
	@SuppressWarnings("unchecked")
	public List<GastoDTO> obtenerGastosOrdinariosPorEdificioYPeriodo(int idEdificio,Periodo periodo){
		Session session = HibernateUtil.getSession();
		Query query = session
		.createQuery("select gr from GastoRealDTO gr where gr.fechaPago " +
				"between :fechaInicio and :fechaFin and gr.edificio =:idEdificio and gr.tipoGasto.tipo <> 'EXT' ");
		query.setString("fechaInicio", periodo.obtenerFechaInicio());
		query.setString("fechaFin", periodo.obtenerFechaFin());
		query.setInteger("idEdificio", idEdificio);
		return query.list(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<GastoDTO> obtenerGastosExtraordinariosPorEdificioYPeriodo(int idEdificio,Periodo periodo){
		Session session = HibernateUtil.getSession();
		Query query = session
		.createQuery("select gr from GastoRealDTO gr where gr.fechaPago " +
				"between :fechaInicio and :fechaFin and gr.edificio =:idEdificio and gr.tipoGasto.tipo = 'EXT' ");
		query.setString("fechaInicio", periodo.obtenerFechaInicio());
		query.setString("fechaFin", periodo.obtenerFechaFin());
		query.setInteger("idEdificio", idEdificio);
		return query.list(); 
	}
	
	
	public void testObtenerGastosPorEdificioAgrupadoPorTipo(){
		obtenerGastosPorEdificioAgrupadoPorTipo(1);
	}
	
	public HashMap<TipoGastoDTO, List<GastoDTO>> obtenerGastosPorEdificioAgrupadoPorTipo(int idEdificio){
		List<GastoDTO> gastos = obtenerGastosOrdinariosPorEdificioYPeriodo(idEdificio, new Periodo(new Date()));
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
	
	@SuppressWarnings("unchecked")
	private List<GastoDTO> obtenerTipoPropiedadTipoGastoPorTipoGasto(TipoGastoDTO tipoGasto){
		Session session = HibernateUtil.getSession();
		Query query = session
		.createQuery("select tpg from TipoPropiedadTipoGastoDTO tpg " +
				"where tpg.tipoGasto =:tipoGasto");
		
		query.setParameter("tipoGasto", tipoGasto);
		return query.list(); 
		
	}
	
	private double obtenerCoeficienteDeDistribucion(TipoPropiedadDTO tipoPropiedad, TipoGastoDTO tpg) {
		Session session = HibernateUtil.getSession();
		Query query = session
		.createQuery("select tpg.coeficienteDistribucion from TipoPropiedadTipoGastoDTO tpg " +
				"where tpg.tipoPropiedad =:tipoPropiedad and tpg.tipoGasto =:tipoGasto");
		
		query.setParameter("tipoPropiedad", tipoPropiedad);
		query.setParameter("tipoGasto", tpg);
		
		Double resultado = (Double) query.uniqueResult();
		
		if(resultado==null) return 0;
		
		return resultado.doubleValue();		
	}
	
	public void testObtenerExpensasFijas(){
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		EdificioAppl edificioAppl = new EdificioAppl();
		EdificioDTO edificio = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(), 1);
		List<ExpensaDTO> expensasFijas = new ArrayList<ExpensaDTO>();
		for (TipoPropiedadDTO tipoPropiedadActual : edificio.getTipoPropiedades()) {
			List<PropiedadDTO> propiedades = tipoPropiedadActual.getPropiedades();
			for (PropiedadDTO propiedadActual : propiedades) {
				ExpensaDTO expensa = new ExpensaDTO();
				expensa.setPropiedad(propiedadActual);
				expensa.setMonto(tipoPropiedadActual.getMontoExp());
				//TODO: ver intereses
				double deuda = propiedadActual.getCtaOrdSaldoExp()+ propiedadActual.getCtaOrdSaldoInt();
				expensa.setDeudaPrevia(deuda);
				expensasFijas.add(expensa);
			}
			
		}
		
		for(ExpensaDTO exp: expensasFijas){
			session.saveOrUpdate(exp);
			//System.out.println(exp.getPropiedad().getNivel() + "-"+exp.getPropiedad().getOrden()+"  "+ exp.getDeudaPrevia()+ "  "+ exp.getMonto() );
		}
		session.getTransaction().commit();
	}


		
	
}
