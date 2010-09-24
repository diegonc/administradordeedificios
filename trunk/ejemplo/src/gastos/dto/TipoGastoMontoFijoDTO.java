package gastos.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_GASTO_MONTO_FIJO")
@PrimaryKeyJoinColumn(name="TIPO_GASTO_ID")
public class TipoGastoMontoFijoDTO extends TipoGastoPeriodicoDTO {

	private int diaLimite;
	private double montoActual;
		
	@Column(name="DIA_LIMITE",nullable=false)
	public int getDiaLimite() {
		return diaLimite;
	}
	public void setDiaLimite(int diaLimite) {
		this.diaLimite = diaLimite;
	}
	
	@Column(name="MONTO_ACTUAL",nullable=false)
	public double getMontoActual() {
		return montoActual;
	}
	public void setMontoActual(double montoActual) {
		this.montoActual = montoActual;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Dia Limite: " + getDiaLimite() + " Monto Actual:" + getMontoActual();
	}
			
}
