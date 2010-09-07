package gastos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_GASTO_MONTO_FIJO")
public class TipoGastoMontoFijoDTO implements Serializable {

	private String codigo;
	private Date diaLimite;
	private double montoActual;
	private TipoGastoPeriodicoDTO tipoGastoPeriodico;
	private int version;
	
	@Id @Column(name="CODIGO")
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="DIA_LIMITE",nullable=false)
	public Date getDiaLimite() {
		return diaLimite;
	}
	public void setDiaLimite(Date diaLimite) {
		this.diaLimite = diaLimite;
	}
	
	@Column(name="MONTO_ACTUAL",nullable=false)
	public double getMontoActual() {
		return montoActual;
	}
	public void setMontoActual(double montoActual) {
		this.montoActual = montoActual;
	}
	
	@Version @Column(name="VERSION",nullable=false)
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	//TODO mapeo con TipoGastoPeriodicoDTO
	public TipoGastoPeriodicoDTO getTipoGastoPeriodico() {
		return tipoGastoPeriodico;
	}
	public void setTipoGastoPeriodico(TipoGastoPeriodicoDTO tipoGastoPeriodico) {
		this.tipoGastoPeriodico = tipoGastoPeriodico;
	}

	
	
}
