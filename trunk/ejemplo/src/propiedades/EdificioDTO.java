package propiedades;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="EDIFICIO")
public class EdificioDTO implements Serializable{
	
	private int id;
	private String nombre;
	private double fondoOrdinario;
	private double fondoExtraordinario;
	private String formaLiquidacionExpensa;
	private boolean aptoProfesional;
	private double tasaAnual;
	private double amortizacion;
	private String calle;
	private int numero;
	private String localidad;
	private String encargadoNombre;
	private String encargadoTelefono;
	private String encargoDepto;
	private String encargadoPiso;	
	private int diaPrimerVto;
	private int diaSegundoVto;
	
	@Id 
	@GeneratedValue
	@Column(name="ID",unique=true,nullable=false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="NOMBRE",unique=true,nullable=false)
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="FONDO_ORDINARIO",nullable=false)
	public double getFondoOrdinario() {
		return fondoOrdinario;
	}
	
	public void setFondoOrdinario(double fondoOrdinario) {
		this.fondoOrdinario = fondoOrdinario;
	}
	
	@Column(name="FONDO_EXTRAORDINARIO",nullable=false)
	public double getFondoExtraordinario() {
		return fondoExtraordinario;
	}
	
	public void setFondoExtraordinario(double fondoExtraordinario) {
		this.fondoExtraordinario = fondoExtraordinario;
	}
	
	@Column(name="FORMA_LIQ_EXP",nullable=false)
	public String getFormaLiquidacionExpensa() {
		return formaLiquidacionExpensa;
	}
	
	public void setFormaLiquidacionExpensa(String formaLiquidacionExpensa) {
		this.formaLiquidacionExpensa = formaLiquidacionExpensa;
	}
	
	@Column(name="APTO_PROFESIONAL",nullable=false)
	public boolean isAptoProfesional() {
		return aptoProfesional;
	}
	
	public void setAptoProfesional(boolean aptoProfesional) {
		this.aptoProfesional = aptoProfesional;
	}
	
	@Column(name="TASA_ANUAL",nullable=false)
	public double getTasaAnual() {
		return tasaAnual;
	}
	
	public void setTasaAnual(double tasaAnual) {
		this.tasaAnual = tasaAnual;
	}
	
	@Column(name="AMORTIZACION",nullable=false)
	public double getAmortizacion() {
		return amortizacion;
	}
	
	public void setAmortizacion(double amortizacion) {
		this.amortizacion = amortizacion;
	}
	
	@Column(name="CALLE",nullable=false)
	public String getCalle() {
		return calle;
	}
	
	public void setCalle(String calle) {
		this.calle = calle;
	}
	
	@Column(name="NUMERO",nullable=false)
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	@Column(name="LOCALIDAD",nullable=false)
	public String getLocalidad() {
		return localidad;
	}
	
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	@Column(name="ENCARGADO_NOMBRE",nullable=false)
	public String getEncargadoNombre() {
		return encargadoNombre;
	}
	
	public void setEncargadoNombre(String encargadoNombre) {
		this.encargadoNombre = encargadoNombre;
	}
	
	@Column(name="ENCARGADO_TELEFONO",nullable=false)
	public String getEncargadoTelefono() {
		return encargadoTelefono;
	}
	
	public void setEncargadoTelefono(String encargadoTelefono) {
		this.encargadoTelefono = encargadoTelefono;
	}
	
	@Column(name="ENCARGADO_DEPTO",nullable=false)
	public String getEncargoDepto() {
		return encargoDepto;
	}
	
	public void setEncargoDepto(String encargoDepto) {
		this.encargoDepto = encargoDepto;
	}
	
	@Column(name="ENCARGADO_PISO",nullable=false)
	public String getEncargadoPiso() {
		return encargadoPiso;
	}
	
	public void setEncargadoPiso(String encargadoPiso) {
		this.encargadoPiso = encargadoPiso;
	}
	
	@Column(name="DIA_PRIMER_VTO",nullable=false)
	public int getDiaPrimerVto() {
		return diaPrimerVto;
	}
	
	public void setDiaPrimerVto(int diaPrimerVto) {
		this.diaPrimerVto = diaPrimerVto;
	}
	
	@Column(name="DIA_SEGUNDO_VTO",nullable=true)
	public int getDiaSegundoVto() {
		return diaSegundoVto;
	}
	
	public void setDiaSegundoVto(int diaSegundoVto) {
		this.diaSegundoVto = diaSegundoVto;
	}
}	
