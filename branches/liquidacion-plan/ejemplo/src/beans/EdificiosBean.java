package beans;

import java.util.ArrayList;
import edificio.EdificioDTO;

public class EdificiosBean {
	
	private ArrayList<EdificioDTO> edificio;
	private int idEdificio;

	public ArrayList<EdificioDTO> getEdificios() {
		return edificio;
	}

	public void setEdificios(ArrayList<EdificioDTO> edif) {
		this.edificio = edif;
	}

	public int getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(int idEdificio) {
		this.idEdificio = idEdificio;
	}

}
