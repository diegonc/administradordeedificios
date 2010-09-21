package beans;

import java.util.ArrayList;

import gastos.dto.GastoDTO;

public class GastosBean {
	private ArrayList<GastoDTO> gastos;

	public ArrayList<GastoDTO> getEdificios() {
		return gastos;
	}

	public void setEdificios(ArrayList<GastoDTO> gastos) {
		this.gastos = gastos;
	}
	
}
