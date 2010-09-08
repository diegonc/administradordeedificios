package propiedades;

import java.io.Serializable;

import javax.persistence.*;

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
	
	@Id @Column(name="CTA_ORD_SALDO_INT")
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
	
	@Column(name="VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	
	

}
