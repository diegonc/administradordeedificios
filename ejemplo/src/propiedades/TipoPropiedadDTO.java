package propiedades;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_PROPIEDAD")
public class TipoPropiedadDTO implements Serializable{

	private String nombreTipo;
	private EdificioDTO edificio;
	private int id;
		
	@Column(name="NOMBRE_TIPO",nullable=false)
	public String getNombreTipo() {
		return nombreTipo;
	}
	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
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
