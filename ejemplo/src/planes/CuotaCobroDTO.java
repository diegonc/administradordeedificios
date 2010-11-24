package planes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="CUOTA_COBRO", uniqueConstraints=@UniqueConstraint(columnNames={"CUOTA_ID"}))
public class CuotaCobroDTO {

	private int id;

	private CuotaDTO cuota;

	private Date fecha;
	private String comprobante;
	
	public void setId(int id) {
		this.id = id;
	}

	public void setCuota(CuotaDTO cuota) {
		this.cuota = cuota;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	@Id
	@Column(name="ID")
	@GeneratedValue
	public int getId() {
		return id;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="CUOTA_ID")
	public CuotaDTO getCuota() {
		return cuota;
	}
	
	@Column(name="FECHA", nullable=false)
	public Date getFecha() {
		return fecha;
	}

	@Column(name="COMPROBANTE", nullable=false)
	public String getComprobante() {
		return comprobante;
	}

}
