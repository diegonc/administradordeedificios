package propiedades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="RESPONSABLE")
public class Responsable {

	private Integer dni;
	private String nombre;
	private String telefono;
	private String email;
	private String localidad;
	private String calle;
	private String ubicacion;
	private Boolean autoridad;
	private Integer version;
	
	public Responsable() {
		this(null,null,null);
	}

	public Responsable(Integer dni) {
		this(dni, null, null);
	}

	public Responsable(Integer dni, String telefono) {
		this(dni, telefono, null);
	}

	public Responsable(Integer dni, String telefono, String email) {
		this.dni = dni;
		this.telefono = telefono;
		this.email = email;
	}

	@Id @Column(name="DNI", nullable=false)
	public Integer getDni() { return dni; }
	public void setDni(Integer dni) { this.dni = dni; }

	@Column(name="NOMBRE")
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	@Column(name="TELEFONO")
	public String getTelefono() { return telefono; }
	public void setTelefono(String telefono) { this.telefono = telefono; }

	@Column(name="EMAIL")
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	@SuppressWarnings("unused")
	@Version
	private Integer getVersion() { return version; }
	@SuppressWarnings("unused")
	private void setVersion(Integer version) { this.version = version; }

	@Column(name="LOCALIDAD")
	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Column(name="CALLE")
	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	@Column(name="UBICACION")
	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	@Column(name="AUTORIDAD")
	public Boolean getAutoridad() {
		return autoridad;
	}

	public void setAutoridad(Boolean autoridad) {
		this.autoridad = autoridad;
	}
}
