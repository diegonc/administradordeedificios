package gastos.dto;

import java.io.Serializable;

import javax.persistence.*;

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
	
	@Override
	public String toString() {
		return "Codigo: " + getCodigo() + " Descripcion: " + getDescripcion();
	}
			
}
