package gastos.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import edificio.EdificioDTO;


@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_GASTO_PERIODICO")
@PrimaryKeyJoinColumn(name="TIPO_GASTO_ID")
public class TipoGastoPeriodicoDTO extends TipoGastoOrdinarioDTO {

	private String periodo;
	private EdificioDTO edificio;
	
	
	@Column(name="PERIODO",nullable=false)
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_EDIFICIO",nullable=true)
	public EdificioDTO getEdificio() {
		return edificio;
	}
	
	public void setEdificio(EdificioDTO edificio) {
		this.edificio = edificio;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Periodo: " + getPeriodo();
	}



}
