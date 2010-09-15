package expensas;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="EXPENSA_COBRO")
@PrimaryKeyJoinColumn(name="EXPENSA_ID")
public class ExpensaCobroDTO extends ExpensaDTO{

	private Date fecha;
	private String comprobante;
	
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
		
}
