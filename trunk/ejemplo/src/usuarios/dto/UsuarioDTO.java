package usuarios.dto;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table (name="USUARIO")
public class UsuarioDTO  implements Serializable{
	
	private String nombre;
	
	private String apellido;
	
	private String password;
	
	private String dni;
	
	private String usuario;
	
	private int perfil;
	
	@Column(name="NOMBRE",nullable=false)
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Column(name="APELLIDO",nullable=false)
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	@Column(name="PASSWORD",nullable=false)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="DNI",nullable=false)
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	@Column(name="USUARIO",nullable=false)
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	@Column(name="USUARIO_PERFIL",nullable=false)
	public int getPerfil() {
		return perfil;
	}
	
	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

	
	
		
}
