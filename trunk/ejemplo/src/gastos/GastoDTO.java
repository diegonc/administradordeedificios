package gastos;

import java.io.Serializable;

import javax.persistence.*;

import propiedades.EdificioDTO;

@SuppressWarnings("serial")
@Entity
@Table (name="GASTO")
@Inheritance(strategy=InheritanceType.JOINED)
public class GastoDTO implements Serializable{

	private int numeroFolio;
	private double monto;
	private String detalle;
	private TipoGastoDTO tipoGasto;
	private EdificioDTO edificio;
	private int id;
		
	@Column(name="NRO_FOLIO",nullable=false)
	public int getNumeroFolio() {
		return numeroFolio;
	}
	
	public void setNumeroFolio(int numeroFolio) {
		this.numeroFolio = numeroFolio;
	}
	
	@Column (name="MONTO",nullable=false)
	public double getMonto() {
		return monto;
	}
	
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	@Column(name="DETALLE",nullable=false)
	public String getDetalle() {
		return detalle;
	}
	
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	@ManyToOne
	@JoinColumn(name="CODIGO_TIPO_GASTO",nullable=false)
	public TipoGastoDTO getTipoGasto() {
		return tipoGasto;
	}
	
	public void setTipoGasto(TipoGastoDTO tipoGasto) {
		this.tipoGasto = tipoGasto;
	}
	
	
	@ManyToOne
	@JoinColumn(name="ID_EDIFICIO",nullable=false) 
	public EdificioDTO getEdificio() {
		return edificio;
	}
	
	public void setEdificio(EdificioDTO edificio) {
		this.edificio = edificio;
	}
	
	@Id 
	@GeneratedValue
	@Column(name="ID",unique=true,nullable=false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
		
}
