package propiedades;

import gastos.TipoGastoDTO;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_PROPIEDAD_TIPO_GASTO")
public class TipoPropiedadTipoGastoDTO implements Serializable{

	private TipoPropiedadDTO tipoPropiedad;
	private TipoGastoDTO tipoGasto;
	private double coeficienteDistribucion;
	private int id;
	private int version;
	
	//@ mapear con tipoPropiedadDTO
	public TipoPropiedadDTO getTipoPropiedad() {
		return tipoPropiedad;
	}
	public void setTipoPropiedad(TipoPropiedadDTO tipoPropiedad) {
		this.tipoPropiedad = tipoPropiedad;
	}
	
	//mapear con tipoGastoDTO
	public TipoGastoDTO getTipoGasto() {
		return tipoGasto;
	}
	public void setTipoGasto(TipoGastoDTO tipoGasto) {
		this.tipoGasto = tipoGasto;
	}
	
	@Column(name="COEFICIENTE_DISTRIBUCION",nullable=true)
	public double getCoeficienteDistribucion() {
		return coeficienteDistribucion;
	}
	public void setCoeficienteDistribucion(double coeficienteDistribucion) {
		this.coeficienteDistribucion = coeficienteDistribucion;
	}
	
	@Id @Column(name="ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Version @Column(name="VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
}
