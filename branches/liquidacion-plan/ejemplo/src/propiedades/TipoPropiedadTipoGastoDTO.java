package propiedades;

import gastos.dto.TipoGastoDTO;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_PROPIEDAD_TIPO_GASTO",
	uniqueConstraints=@UniqueConstraint(columnNames={"ID_TIPO_PROPIEDAD", "ID_TIPO_GASTO"}))
public class TipoPropiedadTipoGastoDTO implements Serializable{

	private TipoPropiedadDTO tipoPropiedad;
	private TipoGastoDTO tipoGasto;
	private Double coeficienteDistribucion;
	private int id;
	
	
	@ManyToOne
	@JoinColumn(name="ID_TIPO_PROPIEDAD",nullable=false)
	public TipoPropiedadDTO getTipoPropiedad() {
		return tipoPropiedad;
	}
	public void setTipoPropiedad(TipoPropiedadDTO tipoPropiedad) {
		this.tipoPropiedad = tipoPropiedad;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_TIPO_GASTO",nullable=false)
	public TipoGastoDTO getTipoGasto() {
		return tipoGasto;
	}
	public void setTipoGasto(TipoGastoDTO tipoGasto) {
		this.tipoGasto = tipoGasto;
	}
	
	@Column(name="COEFICIENTE_DISTRIBUCION",nullable=true)
	public Double getCoeficienteDistribucion() {
		return coeficienteDistribucion;
	}
	public void setCoeficienteDistribucion(Double coeficienteDistribucion) {
		this.coeficienteDistribucion = coeficienteDistribucion;
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
		
}
