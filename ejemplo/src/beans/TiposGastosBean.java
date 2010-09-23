package beans;

import gastos.dto.TipoGastoDTO;

import java.util.List;

public class TiposGastosBean {
	
	private List<TipoGastoDTO> tiposGastos;

	private TipoGastoDTO tipoGastoUnico;

	public List<TipoGastoDTO> getTiposGastos() {
		return tiposGastos;
	}

	public void setTiposGastos(List<TipoGastoDTO> tiposGastos) {
		this.tiposGastos = tiposGastos;
	}

	public TipoGastoDTO getTipoGastoUnico() {
		return tipoGastoUnico;
	}

	public void setTipoGastoUnico(TipoGastoDTO tipoGastoUnico) {
		this.tipoGastoUnico = tipoGastoUnico;
	}
			
}
