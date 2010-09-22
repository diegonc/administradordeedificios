package propiedades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@SuppressWarnings("serial")
@Entity
@Table(name="PROPIEDAD")
public class PropiedadDTO implements Serializable{
	
	private int nivel;
	private int orden;
	private double ctaOrdSaldoInt;
	private double ctaOrdSaldoExp;
	private double ctaExtSaldoInt;
	private double ctaExtSaldoExp;
	private int version;
	private int id;
	private TipoPropiedadDTO tipoPropiedad;
	private Responsable propietario;
	private Responsable inquilino;
	private Responsable poderPropietario;
	private Responsable poderInquilino;
	
		
	@Id 
	@GeneratedValue
	@Column(name="ID",unique=true,nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="NIVEL",nullable=false)
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	@Column(name="ORDEN",nullable=false)
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	@Column(name="CTA_ORD_SALDO_INT",nullable=false)
	public double getCtaOrdSaldoInt() {
		return ctaOrdSaldoInt;
	}
	public void setCtaOrdSaldoInt(double ctaOrdSaldoInt) {
		this.ctaOrdSaldoInt = ctaOrdSaldoInt;
	}
	
	@Column(name="CTA_ORD_SALDO_EXP",nullable=false)
	public double getCtaOrdSaldoExp() {
		return ctaOrdSaldoExp;
	}
	public void setCtaOrdSaldoExp(double ctaOrdSaldoExp) {
		this.ctaOrdSaldoExp = ctaOrdSaldoExp;
	}
	
	@Column(name="CTA_EXT_SALDO_INT",nullable=false)
	public double getCtaExtSaldoInt() {
		return ctaExtSaldoInt;
	}
	public void setCtaExtSaldoInt(double ctaExtSaldoInt) {
		this.ctaExtSaldoInt = ctaExtSaldoInt;
	}
	
	@Column(name="CTA_EXT_SALDO_EXP",nullable=false)
	public double getCtaExtSaldoExp() {
		return ctaExtSaldoExp;
	}
	public void setCtaExtSaldoExp(double ctaExtSaldoExp) {
		this.ctaExtSaldoExp = ctaExtSaldoExp;
	}
	
	@Version
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	@ManyToOne(optional=false)
	public Responsable getPropietario() {
		return propietario;
	}
	public void setPropietario(Responsable propietario) {
		this.propietario = propietario;
	}
	
	@ManyToOne
	public Responsable getInquilino() {
		return inquilino;
	}
	public void setInquilino(Responsable inquilino) {
		this.inquilino = inquilino;
	}
	
	@ManyToOne
	public Responsable getPoderPropietario() {
		return poderPropietario;
	}
	public void setPoderPropietario(Responsable poderPropietario) {
		this.poderPropietario = poderPropietario;
	}
	
	@ManyToOne
	public Responsable getPoderInquilino() {
		return poderInquilino;
	}
	public void setPoderInquilino(Responsable poderInquilino) {
		this.poderInquilino = poderInquilino;
	}
	
	@ManyToOne(optional=false)
	public TipoPropiedadDTO getTipoPropiedad() {
		return tipoPropiedad;
	}
	public void setTipoPropiedad(TipoPropiedadDTO tipoPropiedad) {
		this.tipoPropiedad = tipoPropiedad;
	}
}
