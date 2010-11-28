package expensas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import propiedades.PropiedadDTO;
import propiedades.TipoPropiedadDTO;
import propiedades.TipoPropiedadTipoGastoDTO;
import utilidades.HibernateUtil;
import utilidades.NumberFormat;
import utilidades.Periodo;
import edificio.EdificioAppl;
import edificio.EdificioDTO;
import expensas.appl.ExpensaAppl;
import expensas.appl.ExpensaInteresesAppl;
import expensas.calculo.ElementoPrevisionGasto;
import expensas.calculo.ElementoProrrateo;
import expensas.calculo.ResultadoProrrateo;
import expensas.dto.ExpensaDTO;
import gastos.dto.GastoDTO;
import gastos.dto.TipoGastoDTO;
import junit.framework.TestCase;

public class TestExpensaPorPrevision extends TestCase {
	
	@SuppressWarnings("unchecked")
	protected List<GastoDTO> obtenerGastosRealesPorEdificioYPeriodo(int idEdificio,Periodo periodo,String tipoExpensa){
		Session session = HibernateUtil.getSession();
		String consulta = "select gr from GastoRealDTO gr where gr.fechaPago between :fechaInicio and :fechaFin and gr.edificio =:idEdificio";
		
		if(tipoExpensa.equals(ExpensaDTO.tipoOrdinario)) consulta+=	" and gr.tipoGasto.tipo <> 'EXT' ";
		else if(tipoExpensa.equals(ExpensaDTO.tipoExtraordinario)) consulta+=" and gr.tipoGasto.tipo = 'EXT' ";
		
		Query query = session.createQuery(consulta);
		query.setString("fechaInicio", periodo.obtenerFechaInicio());
		query.setString("fechaFin", periodo.obtenerFechaFin());
		query.setInteger("idEdificio", idEdificio);
		return query.list(); 
	}
	
	@SuppressWarnings("unchecked")
	protected List<GastoDTO> obtenerPrevisionesPorEdificioYPeriodo(int idEdificio,Periodo periodo,String tipoExpensa){
		Session session = HibernateUtil.getSession();
		String consulta = "select gp from GastoPrevisionDTO gp where gp.anio =:anio and gp.mes =:mes and gp.edificio =:idEdificio";
		
		if(tipoExpensa.equals(ExpensaDTO.tipoOrdinario)) consulta+=	" and gp.tipoGasto.tipo <> 'EXT' ";
		else if(tipoExpensa.equals(ExpensaDTO.tipoExtraordinario)) consulta+=" and gp.tipoGasto.tipo = 'EXT' ";
		
		Query query = session.createQuery(consulta);

		query.setInteger("anio", periodo.getAnio());
		query.setInteger("mes", periodo.getMes());
		query.setInteger("idEdificio", idEdificio);
										
		return query.list();
	}

	protected List<ResultadoProrrateo> generarResultadoDeProrrateo(GastoDTO gasto){
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
	
	protected List<ResultadoProrrateo> prorratear(List<ElementoProrrateo> elementosProrrateo,double monto){
		
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
	        resultadoProrrateo.setMonto(NumberFormat.redondeoDouble(monto*tipoPropiedadTotal.get(tpActual).doubleValue()));
	        resultadosProrrateo.add(resultadoProrrateo);
	    }
		
		return resultadosProrrateo;
	}
			
	
	public HashMap<TipoGastoDTO, List<GastoDTO>> obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(int idEdificio,Periodo periodo,String tipoExpensa){
		List<GastoDTO> previsiones = obtenerPrevisionesPorEdificioYPeriodo(idEdificio, periodo,tipoExpensa);
		HashMap<TipoGastoDTO, List<GastoDTO>> tipoGastoGasto = new HashMap<TipoGastoDTO, List<GastoDTO>>();
		
		for(GastoDTO gastoActual: previsiones){
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
	
			
	/**
	 * Totaliza los gastos por tipo de gasto.
	 * @param tipoGastoElementoPrevisionGasto
	 * @return
	 */
	public List<GastoDTO> obtenerGastosTotalizadosPorTipo(HashMap<TipoGastoDTO, ElementoPrevisionGasto> tipoGastoElementoPrevisionGasto){
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
			
	public HashMap<TipoPropiedadDTO, Double> obtenerProrrateoExpensas(List<GastoDTO> gastos, String tipoExpensa){
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
		List<GastoDTO> gastos = obtenerPrevisionesPorEdificioYPeriodo(idEdificio, periodo,ExpensaDTO.tipoOrdinario);
		return obtenerProrrateoExpensas(gastos,ExpensaDTO.tipoOrdinario);
	}
	
	private HashMap<TipoPropiedadDTO, Double> obtenerProrrateoExpensasExtraordinarias(int idEdificio, Periodo periodo) {
		List<GastoDTO> gastos = obtenerPrevisionesPorEdificioYPeriodo(idEdificio, periodo,ExpensaDTO.tipoExtraordinario);
		return obtenerProrrateoExpensas(gastos,ExpensaDTO.tipoExtraordinario);
		
	}
	
	public List<ExpensaDTO> calcularExpensasOrdinariasDePropiedadesPorEdificioYPeriodo(int idEdificio,Periodo periodo){
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa = obtenerProrrateoExpensasOrdinarias(idEdificio,periodo);
		return calcularExpensaPorTipoYEdificio(tipoPropiedadMontoExpensa,ExpensaDTO.tipoOrdinario,idEdificio);
	}
	
	
	public List<ExpensaDTO> calcularExpensasExtraordinariasDePropiedadesPorEdificioYPeriodo(int idEdificio,Periodo periodo){
		HashMap<TipoPropiedadDTO, Double> tipoPropiedadMontoExpensa = obtenerProrrateoExpensasExtraordinarias(idEdificio,periodo);
		return calcularExpensaPorTipoYEdificio(tipoPropiedadMontoExpensa,ExpensaDTO.tipoExtraordinario,idEdificio);
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
