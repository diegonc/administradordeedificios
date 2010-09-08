package expensas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="EXPENSA_COBRO")
public class ExpensaCobroDTO implements Serializable{

	private int id;
	private Date fecha;
	private String comprobante;
	private ExpensaDTO expensa;
	private int version;
	
	@Id @Column(name="ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
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
	
	//TODO mapear con ExpensaDTO
	public ExpensaDTO getExpensa() {
		return expensa;
	}
	public void setExpensa(ExpensaDTO expensa) {
		this.expensa = expensa;
	}
	
	@Version @Column(name="VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
