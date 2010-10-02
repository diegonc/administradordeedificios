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
@Table (name="USUARIO_PERFIL")
public class UsuarioPerfilDTO implements Serializable{
	
	private int id;
	private UsuarioDTO usuario;
	private PerfilDTO perfil;
	private List<EdificioDTO> edificios;	
	
	@Id
	@GeneratedValue
	@Column(name="ID",unique=true,nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_USUARIO",nullable=false)
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	
	@ManyToOne
	@JoinColumn(name="ID_PERFIL",nullable=false)
	public PerfilDTO getPerfil() {
		return perfil;
	}
	public void setPerfil(PerfilDTO perfil) {
		this.perfil = perfil;
	}
	
	@ManyToMany
	@JoinTable(name = "USUARIO_PERFIL_EDIFICIO",
	    joinColumns = {@JoinColumn(name="ID_USUARIO_PERFIL")},
	    inverseJoinColumns = {@JoinColumn(name="ID_EDIFICIO")})
	public List<EdificioDTO> getEdificios() {
		return edificios;
	}
	public void setEdificios(List<EdificioDTO> edificios) {
		this.edificios = edificios;
	}

	
}