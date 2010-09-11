package gastos;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table (name="GASTO")
public class GastoDTO implements Serializable{

	private int numeroFolio;
	private double monto;
	private String detalle;
	//TODO traer los objetos para TIPOGASTO y EDIFICIO
	private String codigoTipoGasto;
	private String nombreEdificio;
	private int id;
	private int version;
	
	@Column(name="NRO_FOLIO",nullable=false)
	public int getNumeroFolio() {
		return numeroFolio;
	}
	public void setNumeroFolio(int numeroFolio) {
		this.numeroFolio = numeroFolio;
	}
	@Column (name="MONTO",nullable=false)
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	@Column(name="DETALLE",nullable=false)
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	@Column(name="CODIGO_TIPO_GASTO",nullable=false)
	public String getCodigoTipoGasto() {
		return codigoTipoGasto;
	}
	public void setCodigoTipoGasto(String codigoTipoGasto) {
		this.codigoTipoGasto = codigoTipoGasto;
	}
	@Column(name="NOMBRE_EDIFICIO",nullable=false)
	public String getNombreEdificio() {
		return nombreEdificio;
	}
	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
	}
	@Id @Column(name="ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Version @Column(name="VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

}
