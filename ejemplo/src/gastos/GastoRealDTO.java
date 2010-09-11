package gastos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="GASTO_REAL")
public class GastoRealDTO implements Serializable{

	private int id;
	private String razonSocial;
	private Date fechaPago;
	private int numeroFacturaPago;
	private String formaPago;
	private GastoDTO gasto;
	private int version;
	
	@Id @Column(name="ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="RAZON_SOCIAL",nullable=false)
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	@Column(name="FECHA_PAGO",nullable=false)
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	@Column(name="NRO_FACTURA_PAGO",nullable=false)
	public int getNumeroFacturaPago() {
		return numeroFacturaPago;
	}
	public void setNumeroFacturaPago(int numeroFacturaPago) {
		this.numeroFacturaPago = numeroFacturaPago;
	}
	
	@Column(name="FORMA_PAGO",nullable=false)
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	
	//TODO @OneToOne(mappedBy)
	public GastoDTO getGasto() {
		return gasto;
	}
	public void setGasto(GastoDTO gasto) {
		this.gasto = gasto;
	}
	
	@Column(name="VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
