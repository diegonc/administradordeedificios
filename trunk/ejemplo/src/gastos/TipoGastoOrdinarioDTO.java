package gastos;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_GASTO_ORDINARIO")
public class TipoGastoOrdinarioDTO implements Serializable {
	
	private String codigo;
	private String tipo;
	private TipoGastoDTO tipoGasto;
	private int version;
	
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
	
	//@OneToOne
	public TipoGastoDTO getTipoGasto() {
		return tipoGasto;
	}
	public void setTipoGasto(TipoGastoDTO tipoGasto) {
		this.tipoGasto = tipoGasto;
	}
	
	@Column(name="VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	

}
