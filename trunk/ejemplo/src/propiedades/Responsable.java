package propiedades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class Responsable {

	private Integer dni;
	private String telefono;
	private String email;
	
	@SuppressWarnings("unused")
	private Responsable() {
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
	private void setDni(Integer dni) { this.dni = dni; }

	public String getTelefono() { return telefono; }
	@SuppressWarnings("unused")
	private void setTelefono(String telefono) { this.telefono = telefono; }

	public String getEmail() { return email; }
	@SuppressWarnings("unused")
	private void setEmail(String email) { this.email = email; }
}
