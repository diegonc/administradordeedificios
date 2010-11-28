package beans;

import java.util.List;

import gastos.dto.GastoPrevisionDTO;
import gastos.dto.GastoRealDTO;

public class GastosBean {
	private List<GastoRealDTO> gastosReales;
	private List<GastoPrevisionDTO> gastosPrevistos;
		
	public List<GastoRealDTO> getGastosReales() {
		return gastosReales;
	}

	public void setGastosReales(List<GastoRealDTO> gastos) {
		this.gastosReales = gastos;
	}

	public void setGastosPrevistos(List<GastoPrevisionDTO> gastosPrevistos) {
		this.gastosPrevistos = gastosPrevistos;
	}

	public List<GastoPrevisionDTO> getGastosPrevistos() {
		return gastosPrevistos;
	}
	
}
