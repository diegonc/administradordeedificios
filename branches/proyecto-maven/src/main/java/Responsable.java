
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class Responsable {

	private String dni;
	private String telefono;
	private String email;

	public Responsable(String dni) {
		this(dni, null, null);
	}

	public Responsable(String dni, String telefono) {
		this(dni, telefono, null);
	}

	public Responsable(String dni, String telefono, String email) {
		this.dni = dni;
		this.telefono = telefono;
		this.email = email;
	}

	@Id
	public String getDni() { return dni; }
	private void setDni(String dni) { this.dni = dni; }

	public String getTelefono() { return telefono; }
	private void setTelefono(String telefono) { this.telefono = telefono; }

	public String getEmail() { return email; }
	private void setEmail(String email) { this.email = email; }
}
