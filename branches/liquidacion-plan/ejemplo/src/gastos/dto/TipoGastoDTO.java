package gastos.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import propiedades.TipoPropiedadDTO;
import propiedades.TipoPropiedadTipoGastoDTO;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_GASTO")
@Inheritance(strategy=InheritanceType.JOINED)
public class TipoGastoDTO implements Serializable {

	public static final String tipoPeriodicoMontoFijo = "PMF";
	public static final String tipoPeriodicoMontoVariable = "PMV";
	public static final String tipoExtraordinario = "EXT";
	public static final String tipoEventual = "EVE";
	private int id;
	private String codigo;
	private String descripcion;
	private String tipo;
	private List<TipoPropiedadDTO> tiposDePropiedades;
	private List<TipoPropiedadTipoGastoDTO> tiposPropiedadesGastos;
	
	@Column(name="TIPO",nullable=false)
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Id 
	@GeneratedValue
	@Column(name="ID",unique=true,nullable=false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="CODIGO",nullable=false)
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="DESCRIPCION",nullable=false)
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
		
	@ManyToMany
	@JoinTable(name = "TIPO_PROPIEDAD_TIPO_GASTO",
	    joinColumns = {@JoinColumn(name="ID_TIPO_GASTO")},
	    inverseJoinColumns = {@JoinColumn(name="ID_TIPO_PROPIEDAD")})
	public List<TipoPropiedadDTO> getTiposDePropiedades() {
		return tiposDePropiedades;
	}

	public void setTiposDePropiedades(List<TipoPropiedadDTO> tiposDePropiedades) {
		this.tiposDePropiedades = tiposDePropiedades;
	}

	@OneToMany(mappedBy = "tipoGasto")
	public List<TipoPropiedadTipoGastoDTO> getTiposPropiedadesGastos() {
		return tiposPropiedadesGastos;
	}

	public void setTiposPropiedadesGastos(
			List<TipoPropiedadTipoGastoDTO> tiposPropiedadesGastos) {
		this.tiposPropiedadesGastos = tiposPropiedadesGastos;
	}

	@Override
	public String toString() {
		return "Codigo: " + getCodigo() + " Descripcion: " + getDescripcion();
	}
			
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TipoGastoDTO)
			return this.codigo.equals(((TipoGastoDTO)obj).getCodigo());
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		if(this.codigo==null)
				return super.hashCode();
		return this.codigo.hashCode();
	}
	
}
