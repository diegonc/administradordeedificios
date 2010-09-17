package beans;

import java.util.LinkedList;
import edificio.EdificioDTO;

public class EdificiosBean {
	
	private LinkedList<EdificioDTO> edificio;

	public LinkedList<EdificioDTO> getEdificios() {
		return edificio;
	}

	public void setUsers(LinkedList<EdificioDTO> edif) {
		this.edificio = edif;
	}
	
	

}
