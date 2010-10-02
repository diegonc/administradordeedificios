package usuarios.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edificio.EdificioDTO;

@SuppressWarnings("serial")
@Entity
@Table (name="USUARIO_PERFIL_EDIFICIO")
public class UsuarioPerfilEdificioDTO implements Serializable{
	
	private int id;
	private UsuarioPerfilDTO usuarioPerfil;
	private EdificioDTO edificio;
	
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
	@JoinColumn(name="ID_USUARIO_PERFIL",nullable=false)
	public UsuarioPerfilDTO getUsuarioPerfil() {
		return usuarioPerfil;
	}
	public void setUsuarioPerfil(UsuarioPerfilDTO usuarioPerfil) {
		this.usuarioPerfil = usuarioPerfil;
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
