package beans;

import expensas.dto.ExpensaDTO;
import gastos.dto.GastoDTO;
import gastos.dto.TipoGastoDTO;

import java.util.HashMap;
import java.util.List;

public class LiquidacionBean {
	
	List<ExpensaDTO> expensas;
	
	public List<ExpensaDTO> getExpensas() {
		return expensas;
	}

	public void setExpensas(List<ExpensaDTO> expensas) {
		this.expensas = expensas;
	}

	HashMap<TipoGastoDTO, List<GastoDTO>> gastosDelPeriodo;
	
	public HashMap<TipoGastoDTO, List<GastoDTO>> getGastosDelPeriodo() {
		return gastosDelPeriodo;
	}

	public void setGastosDelPeriodo(
			HashMap<TipoGastoDTO, List<GastoDTO>> gastosDelPeriodo) {
		this.gastosDelPeriodo = gastosDelPeriodo;
	}
	
	

}
