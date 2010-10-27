package propiedades;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
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
	private List<PropiedadDTO> propiedades = new ArrayList<PropiedadDTO>();
	private List<TipoPropiedadTipoGastoDTO> tipoGastos = new ArrayList<TipoPropiedadTipoGastoDTO>();

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

	@OneToMany(mappedBy = "tipoPropiedad")
	public List<TipoPropiedadTipoGastoDTO> getTipoGastos() {
		return tipoGastos;
	}

	public void setTipoGastos(List<TipoPropiedadTipoGastoDTO> tipoGastos) {
		this.tipoGastos = tipoGastos;
	}

	public void addTipoGasto(TipoPropiedadTipoGastoDTO tptg) {
		tptg.setTipoPropiedad(this);
		tipoGastos.add(tptg);
	}

	public TipoPropiedadTipoGastoDTO getTipoGasto(String codigo) {
		for (TipoPropiedadTipoGastoDTO tptg : tipoGastos) {
			if (tptg.getTipoGasto().getCodigo().equals(codigo))
				return tptg;
		}
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.nombreTipo.equals(((TipoPropiedadDTO)obj).getNombreTipo()) 
			&& this.edificio.equals(((TipoPropiedadDTO)obj).getEdificio());
	}
	
	@Override
	public int hashCode() {
		int valor = this.nombreTipo.hashCode()+this.edificio.getId();
		return valor;
	}
}
