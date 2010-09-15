package expensas;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="EXPENSA_LIQUIDACION")
public class ExpensaLiquidacionDTO implements Serializable{
@PrimaryKeyJoinColumn(name="EXPENSA_ID")
public class ExpensaLiquidacionDTO extends ExpensaDTO{

	private int id;
	private double deudaPrevia;
	private double interesSegundoVencimiento;
	private ExpensaDTO expensa;
	private int version;
	
	@Id @Column(name="ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
		
		
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
	
	//Mapear con ExpensaDTO	
	public ExpensaDTO getExpensa() {
		return expensa;
	}
	public void setExpensa(ExpensaDTO expensa) {
		this.expensa = expensa;
	}
	@Version @Column(name="VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
		
}
