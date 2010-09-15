package gastos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_GASTO_MONTO_VARIABLE")
@PrimaryKeyJoinColumn(name="TIPO_GASTO_ID")
public class TipoGastoMontoVariableDTO extends TipoGastoPeriodicoDTO{
	
	private double montoPrevision;
	private Date proximoVencimiento;
	
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
		
}
