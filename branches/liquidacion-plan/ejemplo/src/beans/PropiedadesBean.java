package beans;

import java.util.ArrayList;

import propiedades.PropiedadDTO;

public class PropiedadesBean {
	
	private ArrayList<PropiedadDTO> propiedades = new ArrayList<PropiedadDTO>();
	private int idPropiedad;
	private String tipo;
	private int dniResp;

	public void setIdPropiedad(int idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

	public int getIdPropiedad() {
		return idPropiedad;
	}

	public void setPropiedades(ArrayList<PropiedadDTO> propiedades) {
		this.propiedades = propiedades;
	}

	public ArrayList<PropiedadDTO> getPropiedades() {
		return propiedades;
	}
	
	public void agregarPropieadad(PropiedadDTO propieadad) {
		this.propiedades.add(propieadad);
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setDniResp(int dniResp) {
		this.dniResp = dniResp;
	}

	public int getDniResp() {
		return dniResp;
	}

}
