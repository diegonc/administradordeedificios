package gastos.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="GASTO_REAL")
@PrimaryKeyJoinColumn(name="GASTO_ID")
public class GastoRealDTO extends GastoDTO{

	private String razonSocial;
	private Date fechaPago;
	private int numeroFacturaPago;
	private String formaPago;
		
	
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
	
		
}
