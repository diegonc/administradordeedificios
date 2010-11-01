package actions;


import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;

import utilidades.HibernateUtil;
import utilidades.Periodo;
import beans.LiquidacionBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edificio.EdificioAppl;
import edificio.EdificioDTO;
import expensas.appl.ExpensaFijaAppl;
import expensas.appl.ExpensaPrevisionAppl;
import expensas.calculo.ElementoPrevisionGasto;
import expensas.dto.ExpensaDTO;
import gastos.dto.TipoGastoDTO;

@SuppressWarnings("serial")
public class ExpensasLiquidacionResultanteAction extends ActionSupport {
	
	private int id;
	private int mes;
	private int anio;
	@SuppressWarnings("unused")
	private Map<String, Object> session;
	private EdificioAppl edifAppl = new EdificioAppl();
	private ExpensaFijaAppl expensasFijasAppl = new ExpensaFijaAppl();
	private ExpensaPrevisionAppl expensasPrevisionAppl = new ExpensaPrevisionAppl();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String execute() {
		
		Periodo periodo = new Periodo(mes,anio); 
		
		//TODO: ver si la liquidacion es del mes
		if (mes==1 && anio==2005){
			addActionError("Ese mes se encuentra liquidado.");
			return "error";
		}else{
			HashMap<TipoGastoDTO, ElementoPrevisionGasto> gastosLiquidacion = null;
			SessionFactory factory = HibernateUtil.getSessionFactory();
			EdificioDTO edificio = edifAppl.getEdificio(factory, id);
			LiquidacionBean expensaDetalle = new LiquidacionBean();
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("edificio",edificio);
			//Si es fijo no paso los gastos
			if (edificio.getForma_liq_exp().equals("PRORRATEO")){
				gastosLiquidacion = expensasPrevisionAppl.obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(id, periodo, ExpensaDTO.tipoOrdinario);
				expensaDetalle.setGastosOrdinariosDelPeriodo(gastosLiquidacion);		
				expensaDetalle.setExpensasOrdinarias(expensasPrevisionAppl.obtenerExpensasPorTipoPorEdificioYPeriodo(gastosLiquidacion,id, ExpensaDTO.tipoOrdinario));
				
				gastosLiquidacion = expensasPrevisionAppl.obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(id, periodo, ExpensaDTO.tipoExtraordinario);
				expensaDetalle.setGastosOrdinariosDelPeriodo(gastosLiquidacion);		
				expensaDetalle.setExpensasOrdinarias(expensasPrevisionAppl.obtenerExpensasPorTipoPorEdificioYPeriodo(gastosLiquidacion,id, ExpensaDTO.tipoExtraordinario));
			}else{
				expensaDetalle.setExpensasOrdinarias(expensasFijasAppl.obtenerExpensasFijas(id));
			}
			
			session.put("detalleExpensa",expensaDetalle);		
	        this.setSession(session);
		}
		return "success";		
		
	}
	public void setSession(Map<String, Object> arg0) {
		this.session=arg0;
	}
}
