package planes;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import expensas.dto.ExpensaCobroDTO;
import propiedades.Responsable;


@Entity
@Table(name="CUOTA", uniqueConstraints=@UniqueConstraint(columnNames={
	"NUMERO_CUOTA", "PLAN_ID"}))
public class CuotaDTO {

	private int id;
	
	private int numeroCuota;
	private PlanDTO plan;
	private double monto;
	private Double intereses;
	private Boolean atrasado;
	private Double interesSegundoVencimiento;
	
	
	public void setId(int id) {
		this.id = id;
	}

	public void setNumeroCuota(int numeroCuota) {
		this.numeroCuota = numeroCuota;
	}

	public void setPlan(PlanDTO plan) {
		this.plan = plan;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public void setIntereses(Double intereses) {
		this.intereses = intereses;
	}

	public void setAtrasado(Boolean atrasado) {
		this.atrasado = atrasado;
	}

	public void setInteresSegundoVencimiento(Double interesSegundoVencimiento) {
		this.interesSegundoVencimiento = interesSegundoVencimiento;
	}


	
	@Id
	@Column(name="ID")
	public int getId() {
		return id;
	}

	@Column(name="NUMERO_CUOTA")
	public int getNumeroCuota() {
		return numeroCuota;
	}
	
	@Column(name="MONTO")
	public double getMonto() {
		return monto;
	}
	
	@Column(name="INTERESES")
	public Double getIntereses() {
		return intereses;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="PLAN_ID")
	public PlanDTO getPlan() {
		return plan;
	}

	@Column(name="ATRASADO")
	public Boolean getAtrasado() {
		return atrasado;
	}

	@Column(name="INTERES_2VTO")
	public Double getInteresSegundoVencimiento() {
		return interesSegundoVencimiento;
	}

}
