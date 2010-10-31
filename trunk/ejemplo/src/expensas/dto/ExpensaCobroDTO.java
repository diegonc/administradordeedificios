package expensas.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import usuarios.dto.UsuarioDTO;

@Entity
@Table(name="EXPENSA_COBRO")
@PrimaryKeyJoinColumn(name="EXPENSA_ID")
public class ExpensaCobroDTO {

	private Date fecha;
	private String comprobante;
	private boolean consolidado = false;
	private UsuarioDTO responsableCobro;
	private double montoPago;
	private ExpensaDTO liquidacion;
	private int id;
	
	@Column(name="FECHA",nullable=false)
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name="COMPROBANTE",nullable=false)
	public String getComprobante() {
		return comprobante;
	}
	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	@Column(name="CONSOLIDADO",nullable=false)
	public boolean getConsolidado() {
		return consolidado;
	}
	public void setConsolidado(boolean consolidado) {
		this.consolidado = consolidado;
	}

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="RESPONSABLECOBRO_ID", updatable=false)
	public UsuarioDTO getResponsableCobro() {
		return responsableCobro;
	}
	public void setResponsableCobro(UsuarioDTO usuario) {
		responsableCobro = usuario;
	}
	public void setMontoPago(double montoPago) {
		this.montoPago = montoPago;
	}
	public double getMontoPago() {
		return montoPago;
	}
	public void setLiquidacion(ExpensaDTO liquidacion) {
		this.liquidacion = liquidacion;
	}
	
	@OneToOne
	@JoinColumn(name = "EXPENSA_ID", unique = true, nullable = false)
	public ExpensaDTO getLiquidacion() {
		return liquidacion;
	}
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
