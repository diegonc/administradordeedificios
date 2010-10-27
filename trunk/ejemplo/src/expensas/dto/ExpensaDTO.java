package expensas.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import propiedades.PropiedadDTO;


@Entity
@Table(name="EXPENSA")
@PrimaryKeyJoinColumn(name="EXPENSA_ID")
public class ExpensaDTO {

	private double deudaPrevia;
	private double interesSegundoVencimiento;
	private int id;
	private int numeroOperacion;
	private String tipo;
	private PropiedadDTO propiedad;
	private double monto;
	private double intereses;
		
		
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
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name = "NRO_OP", nullable = false)
	public int getNumeroOperacion() {
		return numeroOperacion;
	}
	public void setNumeroOperacion(int numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}
	@Column(name = "ORD_EXT", nullable = false)
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@ManyToOne
	@JoinColumn(name = "ID_PROPIEDAD", nullable = false)
	public PropiedadDTO getPropiedad() {
		return propiedad;
	}
	public void setPropiedad(PropiedadDTO propiedad) {
		this.propiedad = propiedad;
	}
	@Column(name = "MONTO", nullable = false)
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	@Column(name = "INTERESES", nullable = true)
	public double getIntereses() {
		return intereses;
	}
	public void setIntereses(double intereses) {
		this.intereses = intereses;
	}
		
}
