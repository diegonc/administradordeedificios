package beans;

import java.util.HashMap;
import java.util.List;

import expensas.calculo.ElementoPrevisionGasto;
import expensas.dto.ExpensaDTO;
import gastos.dto.TipoGastoDTO;

public class LiquidacionBean {
	
	List<ExpensaDTO> expensasOrdinarias;
	List<ExpensaDTO> expensasExtraordinarias;
	HashMap<TipoGastoDTO, ElementoPrevisionGasto> gastosOrdinariosDelPeriodo;
	HashMap<TipoGastoDTO, ElementoPrevisionGasto> gastosExtraordinariosDelPeriodo;
	
	public List<ExpensaDTO> getExpensasOrdinarias() {
		return expensasOrdinarias;
	}

	public void setExpensasOrdinarias(List<ExpensaDTO> expensasOrdinarias) {
		this.expensasOrdinarias = expensasOrdinarias;
	}

	public List<ExpensaDTO> getExpensasExtraordinarias() {
		return expensasExtraordinarias;
	}

	public void setExpensasExtraordinarias(List<ExpensaDTO> expensasExtraordinarias) {
		this.expensasExtraordinarias = expensasExtraordinarias;
	}
	
	public HashMap<TipoGastoDTO, ElementoPrevisionGasto> getGastosOrdinariosDelPeriodo() {
		return gastosOrdinariosDelPeriodo;
	}

	public void setGastosOrdinariosDelPeriodo(
			HashMap<TipoGastoDTO, ElementoPrevisionGasto> gastosOrdinariosDelPeriodo) {
		this.gastosOrdinariosDelPeriodo = gastosOrdinariosDelPeriodo;
	}

	public HashMap<TipoGastoDTO, ElementoPrevisionGasto> getGastosExtraordinariosDelPeriodo() {
		return gastosExtraordinariosDelPeriodo;
	}

	public void setGastosExtraordinariosDelPeriodo(
			HashMap<TipoGastoDTO, ElementoPrevisionGasto> gastosExtraordinariosDelPeriodo) {
		this.gastosExtraordinariosDelPeriodo = gastosExtraordinariosDelPeriodo;
	}
	
	
}

