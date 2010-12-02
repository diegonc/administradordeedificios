package beans;

import java.util.List;

import propiedades.Responsable;

public class ResponsablesBean {
	
	List<Integer> listaDNIs;
	
	public List<Integer> getListaDNIs() {
		return listaDNIs;
	}

	public void setListaDNIs(List<Integer> listaDNIs) {
		this.listaDNIs = listaDNIs;
	}

	List<Responsable> responsables ;

	public List<Responsable> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<Responsable> responsables) {
		this.responsables = responsables;
	}

}
