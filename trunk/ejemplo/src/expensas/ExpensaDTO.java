package expensas;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="EXPENSA")
public class ExpensaDTO implements Serializable{

	private int id;
	private int numeroOperacion;
	private String tipo;
	private int idPropiedad;
	private double monto;
	private double intereses;
	private int version;
	
	@Id @Column(name="ID")
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
	
	//TODO mapear con la PROPIEDAD
	@Column(name="ID_PROPIEDAD",nullable=false)
	public int getIdPropiedad() {
		return idPropiedad;
	}
	public void setIdPropiedad(int idPropiedad) {
		this.idPropiedad = idPropiedad;
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
	
	@Version @Column(name="VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
