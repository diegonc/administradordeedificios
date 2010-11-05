package beans;

import java.util.HashMap;
import java.util.List;

import expensas.calculo.ElementoPrevisionGasto;
import expensas.dto.ExpensaDTO;
import gastos.dto.GastoDTO;
import gastos.dto.TipoGastoDTO;

public class LiquidacionBean {
	
	List<ExpensaDTO> expensasOrdinarias;
	List<ExpensaDTO> expensasExtraordinarias;
	HashMap<TipoGastoDTO, List<GastoDTO>> gastosOrdinariosDelPeriodo;
	HashMap<TipoGastoDTO, List<GastoDTO>> gastosExtraordinariosDelPeriodo;
	
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

	public HashMap<TipoGastoDTO, List<GastoDTO>> getGastosOrdinariosDelPeriodo() {
		return gastosOrdinariosDelPeriodo;
	}

	public void setGastosOrdinariosDelPeriodo(
			HashMap<TipoGastoDTO, List<GastoDTO>> gastosOrdinariosDelPeriodo) {
		this.gastosOrdinariosDelPeriodo = gastosOrdinariosDelPeriodo;
	}

	public HashMap<TipoGastoDTO, List<GastoDTO>> getGastosExtraordinariosDelPeriodo() {
		return gastosExtraordinariosDelPeriodo;
	}

	public void setGastosExtraordinariosDelPeriodo(
			HashMap<TipoGastoDTO, List<GastoDTO>> gastosExtraordinariosDelPeriodo) {
		this.gastosExtraordinariosDelPeriodo = gastosExtraordinariosDelPeriodo;
	}
	
	
	
	
}

