package usuarios.dto;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edificio.EdificioDTO;


@SuppressWarnings("serial")
@Entity
@Table (name="USUARIO")
public class UsuarioDTO  implements Serializable{
	
	private String nombre;
	
	private String apellido;
	
	private String password;
	
	private int dni;
	
	private String usuario;
	
	private EdificioDTO edificio;
	
	private List<PerfilDTO> perfiles;
	
	private int id;
	
	@Id
	@GeneratedValue
	@Column(name="ID",unique=true,nullable=false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToMany
	@JoinTable(name = "USUARIO_PERFIL",
	    joinColumns = {@JoinColumn(name="ID_USUARIO")},
	    inverseJoinColumns = {@JoinColumn(name="ID_PERFIL")})
	public List<PerfilDTO> getPerfiles() {
		return perfiles;
	}
	
	public void setPerfiles(List<PerfilDTO> perfiles) {
		this.perfiles = perfiles;
	}

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
	public int getDni() {
		return dni;
	}
	
	public void setDni(int i) {
		this.dni = i;
	}
	
	@Column(name="USUARIO",nullable=false)
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_EDIFICIO",nullable=true)
	public EdificioDTO getEdificio() {
		return edificio;
	}
	
	public void setEdificio(EdificioDTO edificio) {
		this.edificio = edificio;
	}

				
}
