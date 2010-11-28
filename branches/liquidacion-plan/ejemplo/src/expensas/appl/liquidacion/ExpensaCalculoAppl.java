package expensas.appl.liquidacion;

import expensas.calculo.ElementoProrrateo;
import expensas.calculo.ResultadoProrrateo;
import expensas.dto.ExpensaDTO;
import gastos.dto.GastoDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import propiedades.TipoPropiedadDTO;
import propiedades.TipoPropiedadTipoGastoDTO;
import utilidades.HibernateUtil;
import utilidades.NumberFormat;
import utilidades.Periodo;

public abstract class ExpensaCalculoAppl {

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
			
	
	
	
}
