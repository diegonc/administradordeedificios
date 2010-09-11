package gastos;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_GASTO_PERIODICO")
public class TipoGastoPeriodicoDTO implements Serializable {

	private String codigo;
	private String tipo;
	private String periodo;
	//TODO cambiar por EdificioDTO
	private String edificio;
	private int version;
	private TipoGastoOrdinarioDTO tipoGastoOrdinario;
	
	@Id @Column(name="CODIGO")
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="TIPO",nullable=false)
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Column(name="PERIODO",nullable=false)
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	//TODO MAPEAR CON EDIFICIO
	@Column(name="NOMBRE_EDIFICIO",nullable=false)
	public String getEdificio() {
		return edificio;
	}
	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}
	
	
	//Mapear con TIPO_GASTO_ORDINARIO
	public TipoGastoOrdinarioDTO getTipoGastoOrdinario() {
		return tipoGastoOrdinario;
	}
	public void setTipoGastoOrdinario(TipoGastoOrdinarioDTO tipoGastoOrdinario) {
		this.tipoGastoOrdinario = tipoGastoOrdinario;
	}
	@Version @Column(name="VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	

}
