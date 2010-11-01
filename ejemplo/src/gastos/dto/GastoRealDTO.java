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
	private Integer numeroFacturaPago;
	private String formaPago;
	private String estado;
	
	@Column(name="RAZON_SOCIAL",nullable=false)
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	@Column(name="FECHA_PAGO")
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	@Column(name="NRO_FACTURA_PAGO")
	public Integer getNumeroFacturaPago() {
		return numeroFacturaPago;
	}
	public void setNumeroFacturaPago(Integer numeroFacturaPago) {
		this.numeroFacturaPago = numeroFacturaPago;
	}
	
	@Column(name="FORMA_PAGO")
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEstado() {
		return estado;
	}
	public boolean estaConsolidado() {
		return "C".equals(estado);
	}
}
