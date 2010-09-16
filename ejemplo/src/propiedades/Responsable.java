package propiedades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Responsable {

	private Integer dni;
	private String telefono;
	private String email;
	private String localidad;
	private String calle;
	private String ubicacion;
	private Boolean autoridad;
	private Integer version;
	
	@SuppressWarnings("unused")
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

	@Id
	public Integer getDni() { return dni; }
	@SuppressWarnings("unused")
	public void setDni(Integer dni) { this.dni = dni; }

	public String getTelefono() { return telefono; }
	@SuppressWarnings("unused")
	public void setTelefono(String telefono) { this.telefono = telefono; }

	public String getEmail() { return email; }
	@SuppressWarnings("unused")
	public void setEmail(String email) { this.email = email; }
	
	@SuppressWarnings("unused")
	@Version
	private Integer getVersion() { return version; }
	@SuppressWarnings("unused")
	private void setVersion(Integer version) { this.version = version; }

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Boolean getAutoridad() {
		return autoridad;
	}

	public void setAutoridad(Boolean autoridad) {
		this.autoridad = autoridad;
	}
}
