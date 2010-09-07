package propiedades;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_PROPIEDAD")
public class TipoPropiedadDTO implements Serializable{

	private String nombreTipo;
	private String nombreEdificio;
	private int id;
	private int version;
	
	@Column(name="NOMBRE_TIPO",nullable=false)
	public String getNombreTipo() {
		return nombreTipo;
	}
	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}
	
	//Mapeo con edificioDTO
	@Column(name="NOMBRE_EDIFICIO",nullable=false)
	public String getNombreEdificio() {
		return nombreEdificio;
	}
	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
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
