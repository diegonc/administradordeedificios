package planes;

import java.util.Date;
import java.util.List;
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
@Table(name="PLAN", uniqueConstraints=@UniqueConstraint(columnNames={
	"ORD_EXT", "FECHA", "RESPONSABLE_DNI" }))
public class PlanDTO {

	private int id;

	private Date fecha;
	private String tipo;
	private	Responsable responsable;

	private int cantidadCuotas;
	private double monto;
	private double saldoPlan;
	private double saldoIntereses;

	private Set<ExpensaCobroDTO> cobrosCancelados;
	private List<CuotaDTO> cuotas;

	
	@Id
	@Column(name="ID", nullable=false)
	public int getId() {
		return id;
	}
	
	@Column(name="MONTO", nullable=false)
	public double getMonto() {
		return monto;
	}
	
	@Column(name="SALDO_PLAN", nullable=false)
	public double getSaldoPlan() {
		return saldoPlan;
	}

	@Column(name="SALDO_INTERESES", nullable=false)
	public double getSaldoIntereses() {
		return saldoIntereses;
	}

	@Column(name="FECHA", nullable=false)
	public Date getFecha() {
		return fecha;
	}

	@Column(name="ORD_EXT", nullable=false)
	public String getTipo() {
		return tipo;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="RESPONSABLE_DNI")
	public Responsable getResponsable() {
		return responsable;
	}

	@Column(name="CANTIDAD_COUTAS", nullable=false)
	public int getCantidadCuotas() {
		return cantidadCuotas;

	}

	@OneToMany
	@JoinTable( name="PLAN_EXPENSA_COBRO",
	            joinColumns=@JoinColumn(name="PLAN_ID", referencedColumnName="ID"),
	            inverseJoinColumns=@JoinColumn(name="COBRO_ID", referencedColumnName="ID"))
	public Set<ExpensaCobroDTO> getCobrosCancelados() {
		return cobrosCancelados;
	}

	@OneToMany(mappedBy="plan")
	public List<CuotaDTO> getCuotas() {
		return cuotas;
	}

	public void setCobrosCancelados(Set<ExpensaCobroDTO> cobros) {
		this.cobrosCancelados = cobros;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	public void setSaldoPlan(double saldo) {
		this.saldoPlan = saldo;
	}

	public void setSaldoIntereses(double saldo) {
		this.saldoIntereses = saldo;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable; 
	}

	public void setCantidadCuotas(int cantidadCoutas) {
		this.cantidadCuotas = cantidadCoutas;
	}

	public void setId(int id) {
		this.id = id;
	}
}
