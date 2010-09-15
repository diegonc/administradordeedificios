package expensas;

import java.io.Serializable;

import javax.persistence.*;

import propiedades.PropiedadDTO;

@SuppressWarnings("serial")
@Entity
@Table(name="EXPENSA")
@Inheritance(strategy=InheritanceType.JOINED)
public class ExpensaDTO implements Serializable{

	private int id;
	private int numeroOperacion;
	private String tipo;
	private PropiedadDTO propiedad;
	private double monto;
	private double intereses;
	
	@Id
	@GeneratedValue
	@Column(name="ID",unique=true,nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="NRO_OP",nullable=false)
	public int getNumeroOperacion() {
		return numeroOperacion;
	}
	public void setNumeroOperacion(int numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}
	
	
	@Column(name="ORD_EXT",nullable=false)
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_PROPIEDAD",nullable=false)
	public PropiedadDTO getPropiedad() {
		return propiedad;
	}
	public void setIdPropiedad(PropiedadDTO propiedad) {
		this.propiedad = propiedad;
	}
	
	@Column(name="MONTO",nullable=false)
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	@Column(name="INTERESES",nullable=true)
	public double getIntereses() {
		return intereses;
	}
	public void setIntereses(double intereses) {
		this.intereses = intereses;
	}
		
}
