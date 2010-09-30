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
	private UsuarioDTO usuario;
	private PerfilDTO perfil;
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
	
	@ManyToOne
	@JoinColumn(name="ID_EDIFICIO",nullable=true)
	public EdificioDTO getEdificio() {
		return edificio;
	}
	public void setEdificio(EdificioDTO edificio) {
		this.edificio = edificio;
	}
		
	

}
