package test.expensas;

import gastos.dto.GastoDTO;
import gastos.dto.TipoGastoDTO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;

import propiedades.TipoPropiedadDTO;
import propiedades.TipoPropiedadTipoGastoDTO;
import utilidades.HibernateUtil;
import utilidades.Periodo;

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
	
	public void testObtenerGastosPorEdificio(){
		Periodo periodo = new Periodo(new Date());
		List<GastoDTO> gastos = obtenerGastosPorEdificioYPeriodo(1, periodo);
		
		GastoDTO gastoEncargado = gastos.get(0);
		TipoGastoDTO tg =  gastoEncargado.getTipoGasto();
		
		for(GastoDTO g: gastos){
			System.out.println("Codigo: " + tg.getCodigo());
			System.out.println("Descripcion: " + tg.getDescripcion());
			System.out.println("Detalle: " + g.getDetalle());
			System.out.println("Monto: " + g.getMonto());
		}
				
		int posicionElemento = 0;
		Double coeficienteActual;
		List<ElementoProrrateo> elementosProrrateo = new ArrayList<ElementoProrrateo>();
		
		for (TipoPropiedadTipoGastoDTO tipoPropiedadGasto : tg.getTiposPropiedadesGastos()) {
	
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
		
		List<ResultadoProrrateo> resultadoProrrateo = prorratear(elementosProrrateo,gastoEncargado.getMonto());
		
		for (ResultadoProrrateo resultadoActual : resultadoProrrateo) {
			System.out.println(resultadoActual.getTipoPropiedad().getNombreTipo()+" : "+resultadoActual.getMonto());
		}
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
				resultadoProrrateo.monto=(Math.round(resultadoProrrateo.monto*Math.pow(10,2))/Math.pow(10,2));
				resultadosProrrateo.add(resultadoProrrateo);
			}
		}
		return resultadosProrrateo;
	}
			
	@SuppressWarnings("unchecked")
	public List<GastoDTO> obtenerGastosPorEdificioYPeriodo(int idEdificio,Periodo periodo){
		Session session = HibernateUtil.getSession();
		Query query = session
		.createQuery("select gr from GastoRealDTO gr where gr.fechaPago " +
				"between :fechaInicio and :fechaFin and gr.edificio =:idEdificio");
		query.setString("fechaInicio", periodo.obtenerFechaInicio());
		query.setString("fechaFin", periodo.obtenerFechaFin());
		query.setInteger("idEdificio", idEdificio);
		return query.list(); 
		
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

		
	
}
