package edificio;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;

@SuppressWarnings("serial")
@Entity 
@Table(name="EDIFICIO")
@Proxy( lazy=false )
public class EdificioDTO implements Serializable{
	private int id;
	private String nombre;
	private double fondo_ordinario;
	private double fondo_extraordinario;
	private String forma_liq_exp;
	private boolean apto_profesional;
	private double tasa_anual;
	private double amortizacion;
	private String calle;
	private int numero;
	private String localidad;
	private String encargado_nombre;
	private String encargado_telefono;
	private String encargado_depto;
	private String encargado_piso;
	private int dia_primer_vto;
	private int dia_segundo_vto;
	private int version;
	
	
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="NOMBRE",nullable=false)
	public String getNombre() {
		return nombre;
	}
	
	public void setFondo_ordinario(double fondo_ordinario) {
		this.fondo_ordinario = fondo_ordinario;
	}
	
	@Column(name="FONDO_ORDINARIO",nullable=false)
	public double getFondo_ordinario() {
		return fondo_ordinario;
	}
	
	
	public void setFondo_extraordinario(double fondo_extraordinario) {
		this.fondo_extraordinario = fondo_extraordinario;
	}
	
	@Column(name="FONDO_EXTRAORDINARIO",nullable=false)
	public double getFondo_extraordinario() {
		return fondo_extraordinario;
	}
	
	
	public void setForma_liq_exp(String forma_liq_exp) {
		this.forma_liq_exp = forma_liq_exp;
	}
	
	@Column(name="FORMA_LIQ_EXP",nullable=false)
	public String getForma_liq_exp() {
		return forma_liq_exp;
	}
	
	public void setApto_profesional(boolean apto_profesional) {
		this.apto_profesional = apto_profesional;
	}
	
	@Column(name="APTO_PROFESIONAL",nullable=false)
	public boolean getApto_profesional() {
		return apto_profesional;
	}
	
	public void setTasa_anual(double tasa_anual) {
		this.tasa_anual = tasa_anual;
	}
	
	@Column(name="TASA_ANUAL",nullable=false)
	public double getTasa_anual() {
		return tasa_anual;
	}
	
	public void setAmortizacion(double amortizacion) {
		this.amortizacion = amortizacion;
	}
	
	@Column(name="AMORTIZACION",nullable=false)
	public double getAmortizacion() {
		return amortizacion;
	}
	
	public void setCalle(String calle) {
		this.calle = calle;
	}
	
	@Column(name="CALLE",nullable=false)
	public String getCalle() {
		return calle;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	@Column(name="NUMERO",nullable=false)
	public int getNumero() {
		return numero;
	}
	
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	@Column(name="LOCALIDAD",nullable=false)
	public String getLocalidad() {
		return localidad;
	}
	
	public void setEncargado_nombre(String encargado_nombre) {
		this.encargado_nombre = encargado_nombre;
	}
	
	@Column(name="ENCARGADO_NOMBRE",nullable=false)
	public String getEncargado_nombre() {
		return encargado_nombre;
	}
	
	public void setEncargado_telefono(String encargado_telefono) {
		this.encargado_telefono = encargado_telefono;
	}
	
	@Column(name="ENCARGADO_TELEFONO",nullable=false)
	public String getEncargado_telefono() {
		return encargado_telefono;
	}
	
	public void setEncargado_depto(String encargado_depto) {
		this.encargado_depto = encargado_depto;
	}
	
	@Column(name="ENCARGADO_DEPTO",nullable=false)
	public String getEncargado_depto() {
		return encargado_depto;
	}
	
	public void setEncargado_piso(String encargado_piso) {
		this.encargado_piso = encargado_piso;
	}
	
	@Column(name="ENCARGADO_PISO",nullable=false)
	public String getEncargado_piso() {
		return encargado_piso;
	}
	
	public void setDia_primer_vto(int dia_primer_vto) {
		this.dia_primer_vto = dia_primer_vto;
	}
	
	@Column(name="DIA_PRIMER_VTO",nullable=false)
	public int getDia_primer_vto() {
		return dia_primer_vto;
	}
	
	public void setDia_segundo_vto(int dia_segundo_vto) {
		this.dia_segundo_vto = dia_segundo_vto;
	}
	
	@Column(name="DIA_SEGUNDO_VTO")
	public int getDia_segundo_vto() {
		return dia_segundo_vto;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	@Id @Column(name="ID") @GeneratedValue( strategy=IDENTITY )  
	public int getId() {
		return id;
	}
	public void setVersion(int version) {
		this.version = version;
	}
    @Version @Column(name="VERSION")
	public int getVersion() {
		return version;
	}
	
}
