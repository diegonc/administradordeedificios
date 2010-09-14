package actions;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import edificio.EdificioDAO;
import edificio.EdificioDTO;

public class ListadoEdificios extends ActionSupport implements
		ModelDriven<EdificioDTO> {

	private EdificioDTO edificio = new EdificioDTO();
	private List<EdificioDTO> listaEdificios = new ArrayList<EdificioDTO>();
	private EdificioDAO edificioDAO = new EdificioDAO();

	@Override
	public EdificioDTO getModel() {
		return edificio;
	}

	public String listar() {
		listaEdificios = edificioDAO.listar();
		return SUCCESS;
	}

	public EdificioDTO getEdificio() {
		return edificio;
	}

	public void setEdificio(EdificioDTO edificio) {
		this.edificio = edificio;
	}

	public List<EdificioDTO> getListaEdificios() {
		return listaEdificios;
	}

	public void setListaEdificios(List<EdificioDTO> listaEdificios) {
		this.listaEdificios = listaEdificios;
	}

}
