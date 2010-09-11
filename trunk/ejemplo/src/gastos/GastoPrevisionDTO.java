package gastos;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="GASTO_PREVISION")
public class GastoPrevisionDTO implements Serializable {
	
	private GastoDTO gasto;
	private int anio;
	private int mes;
	private int id;
	private int version;
	
	//TODO mapear con gasto
	public GastoDTO getGasto() {
		return gasto;
	}
	public void setGasto(GastoDTO gasto) {
		this.gasto = gasto;
	}
	
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
