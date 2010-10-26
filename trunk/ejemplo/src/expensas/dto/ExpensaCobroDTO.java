package expensas.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


import usuarios.dto.UsuarioDTO;

@SuppressWarnings("serial")
@Entity
@Table(name="EXPENSA_COBRO")
@PrimaryKeyJoinColumn(name="EXPENSA_ID")
public class ExpensaCobroDTO extends ExpensaDTO{

	private Date fecha;
	private String comprobante;
	private boolean consolidado = false;
	private UsuarioDTO responsableCobro;
	
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

}
