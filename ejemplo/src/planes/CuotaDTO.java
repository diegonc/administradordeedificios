package planes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javassist.bytecode.Descriptor.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import expensas.dto.ExpensaCobroDTO;


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
	
	private double interesMora;
	private Date fechaLiquidacion;
	
	
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

	public boolean sePuedePagar() {
		Date fechaInicio = this.plan.getFecha();
		
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaActual = calendar.getTime();
		
		java.util.Iterator<ExpensaCobroDTO> ite = this.getPlan().getCobrosCancelados().iterator();
		ExpensaCobroDTO expensa = ite.next();
		int diaVto = expensa.getLiquidacion().getPropiedad().getTipoPropiedad().getEdificio().getDia_primer_vto();
		
		Date fechaVtoCuotaAnt = new Date();
		fechaVtoCuotaAnt.setYear(fechaInicio.getYear());
		fechaVtoCuotaAnt.setMonth(fechaInicio.getMonth() + this.numeroCuota - 1);
		fechaVtoCuotaAnt.setDate(diaVto);
		
		if (fechaVtoCuotaAnt.before(fechaActual)) {
			return true;
		} 
		return false;
	}
	
	public boolean estaVencida() {
		Date fechaInicio = this.plan.getFecha();
		
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaActual = calendar.getTime();
		
		java.util.Iterator<ExpensaCobroDTO> ite = this.getPlan().getCobrosCancelados().iterator();
		ExpensaCobroDTO expensa = ite.next();
		int diaVto = expensa.getLiquidacion().getPropiedad().getTipoPropiedad().getEdificio().getDia_primer_vto();
		
		Date fechaVtoCuota = new Date();
		fechaVtoCuota.setYear(fechaInicio.getYear());
		fechaVtoCuota.setMonth(fechaInicio.getMonth() + this.numeroCuota);
		fechaVtoCuota.setDate(diaVto);
		
		if (fechaVtoCuota.before(fechaActual)) {
			return true;
		} 
		return false;
	}
	
	@Id
	@Column(name="ID")
	@GeneratedValue
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

	@Column(name="INTERES_MORA")
	public double getInteresMora() {
		return interesMora;
	}

	public void setInteresMora(double interesMora) {
		this.interesMora = interesMora;
	}

	@Column(name="FECHA_LIQUIDACION")
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

}
