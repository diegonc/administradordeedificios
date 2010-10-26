package expensas.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name="EXPENSA_LIQUIDACION")
@PrimaryKeyJoinColumn(name="EXPENSA_ID")
public class ExpensaLiquidacionDTO extends ExpensaDTO{

	private double deudaPrevia;
	private double interesSegundoVencimiento;
		
		
	@Column(name="DEUDA_PREVIA",nullable=true)
	public double getDeudaPrevia() {
		return deudaPrevia;
	}
	public void setDeudaPrevia(double deudaPrevia) {
		this.deudaPrevia = deudaPrevia;
	}
	
	@Column(name="INT_SEGUNDO_VTO",nullable=true)
	public double getInteresSegundoVencimiento() {
		return interesSegundoVencimiento;
	}
	public void setInteresSegundoVencimiento(double interesSegundoVencimiento) {
		this.interesSegundoVencimiento = interesSegundoVencimiento;
	}
		
}
