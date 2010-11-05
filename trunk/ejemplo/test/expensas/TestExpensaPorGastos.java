package expensas;

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
import expensas.calculo.ElementoProrrateo;
import expensas.calculo.ResultadoProrrateo;
import gastos.dto.GastoDTO;
import gastos.dto.TipoGastoDTO;

public class TestExpensas extends TestCase {
	
	private double redondeoDouble(double valor){
		return Math.round(valor*Math.pow(10,2))/Math.pow(10,2);
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
				resultadoProrrateo.setTipoPropiedad(tp);
				coeficienteActual=elementoProrrateo.getCoeficiente();
				if(coeficienteActual!=0){
					resultadoProrrateo.setMonto(coeficienteActual*(tp.getDivisor()/superficieTotal2));
				}else{
					double montoActual = 0;
					for (Double coeficienteNoNulo: coeficientesNoNulos) {
						montoActual+= coeficienteNoNulo*(tp.getDivisor()/superficieTotal2);
					}
					resultadoProrrateo.setMonto(montoActual +((1-sumaCoeficientesNoNulos)*(tp.getDivisor()/elementoProrrateo.getSuperficieTotal())));
				}
				resultadoProrrateo.setMonto(redondeoDouble(monto*resultadoProrrateo.getMonto()));
				resultadosProrrateo.add(resultadoProrrateo);
			}
		}
		return resultadosProrrateo;
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
			
	
}
