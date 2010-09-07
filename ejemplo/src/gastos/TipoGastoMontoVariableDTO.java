package gastos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_GASTO_MONTO_VARIABLE")
public class TipoGastoMontoVariableDTO implements Serializable{
	
	private String codigo;
	private double montoPrevision;
	private Date proximoVencimiento;
	private TipoGastoPeriodicoDTO tipoGastoPeriodico;
	private int version;
	
	@Id @Column(name="CODIGO")
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="MONTO_PREV",nullable=false)
	public double getMontoPrevision() {
		return montoPrevision;
	}
	public void setMontoPrevision(double montoPrevision) {
		this.montoPrevision = montoPrevision;
	}
	
	@Column(name="PROXIMO_VENCIMIENTO",nullable=false)
	public Date getProximoVencimiento() {
		return proximoVencimiento;
	}
	public void setProximoVencimiento(Date proximoVencimiento) {
		this.proximoVencimiento = proximoVencimiento;
	}
	
	//TODO mapear con TipoGAstoPeriodico
	public TipoGastoPeriodicoDTO getTipoGastoPeriodico() {
		return tipoGastoPeriodico;
	}
	public void setTipoGastoPeriodico(TipoGastoPeriodicoDTO tipoGastoPeriodico) {
		this.tipoGastoPeriodico = tipoGastoPeriodico;
	}
	
	@Version @Column(name="VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	

}
