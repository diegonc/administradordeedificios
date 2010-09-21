package propiedades;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

import edificio.EdificioDTO;

@SuppressWarnings("serial")
@Entity
@Table(name = "TIPO_PROPIEDAD")
public class TipoPropiedadDTO implements Serializable {

	private String nombreTipo;
	private EdificioDTO edificio;
	private int id;
	private double montoExp;
	private double divisor;
	private List<PropiedadDTO> propiedades;

	@Column(name = "NOMBRE_TIPO", nullable = false)
	public String getNombreTipo() {
		return nombreTipo;
	}

	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}

	@ManyToOne
	@JoinColumn(name = "ID_EDIFICIO", nullable = false)
	public EdificioDTO getEdificio() {
		return edificio;
	}

	public void setEdificio(EdificioDTO edificio) {
		this.edificio = edificio;
	}

	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getMontoExp() {
		return montoExp;
	}

	public void setMontoExp(double montoExp) {
		this.montoExp = montoExp;
	}

	public double getDivisor() {
		return divisor;
	}

	public void setDivisor(double divisor) {
		this.divisor = divisor;
	}

	@OneToMany(mappedBy = "tipoPropiedad")
	public List<PropiedadDTO> getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(List<PropiedadDTO> propiedades) {
		this.propiedades = propiedades;
	}
}
