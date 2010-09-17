package gastos.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="GASTO_PREVISION")
@PrimaryKeyJoinColumn(name="GASTO_ID")
public class GastoPrevisionDTO extends GastoDTO {
	
	private int anio;
	private int mes;
		
	@Column(name="ANIO",nullable=false)
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	@Column(name="MES",nullable=false)
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
		
}
