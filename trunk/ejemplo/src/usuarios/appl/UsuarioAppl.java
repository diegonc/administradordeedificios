package usuarios.appl;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import usuarios.dto.PerfilDTO;
import usuarios.dto.UsuarioDTO;
import usuarios.dto.UsuarioPerfilDTO;
import usuarios.exception.UsuarioExistenteException;
import usuarios.exception.UsuarioInexistenteException;
import utilidades.HibernateUtil;

public class UsuarioAppl {
		
	private Session session;
	
	public UsuarioAppl() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public void addUsuario(UsuarioDTO usuario) throws UsuarioExistenteException 
	{
		session.beginTransaction();
		try
		{
			session.save(usuario);
			session.getTransaction().commit();
		}
		catch(ConstraintViolationException e)
		{
			throw new UsuarioExistenteException("El usuario que desea crear ya existe.");
		}
    }
	
	public void updateUsuario(UsuarioDTO nuevoUsuario,int idUsuario)
	{
		session.beginTransaction();
        UsuarioDTO usuario = (UsuarioDTO) session.load(UsuarioDTO.class, idUsuario);
        usuario.setApellido(nuevoUsuario.getApellido());
        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setPassword(nuevoUsuario.getPassword());
        usuario.setUsuario(nuevoUsuario.getUsuario());
        usuario.setPerfiles(nuevoUsuario.getPerfiles());
        session.update(usuario);
        session.getTransaction().commit();
    }
	
	public void removeUsuario(int idUsuario) throws UsuarioInexistenteException
	{
	    session.beginTransaction();
        UsuarioDTO usuario=null;
		try {
			usuario = this.getUsuario(idUsuario);
		} catch (UsuarioInexistenteException e) {
			
			throw new UsuarioInexistenteException("El usuario a eliminar no existe.");
		}
        session.delete(usuario);
        session.getTransaction().commit();
    }
	
	public void addPerfil(PerfilDTO perfil)
	{
	    session.beginTransaction();
        session.save(perfil);
        session.getTransaction().commit();
    }
	
	public void removePerfil(int idPerfil)
	{
	    session.beginTransaction();
        PerfilDTO perfil = (PerfilDTO) session.load(PerfilDTO.class, idPerfil);
        session.delete(perfil);
        session.getTransaction().commit();
    }
	
	public void updatePerfil(int idPerfil,PerfilDTO perfilNuevo)
	{
	    session.beginTransaction();
        PerfilDTO perfil = (PerfilDTO) session.load(PerfilDTO.class, idPerfil);
        perfil.setDescripcion(perfilNuevo.getDescripcion());
        session.update(perfil);
        session.getTransaction().commit();		
	}
	
	public PerfilDTO getPerfil(int idPerfil)
	{
		return (PerfilDTO) session.load(PerfilDTO.class, idPerfil);
	}
		
	@SuppressWarnings("unchecked")
	public List<PerfilDTO> getPerfiles()
	{
		Query q = session.createQuery("select p from PerfilDTO p order by p.id ");
		return q.list();
	}
	
	public PerfilDTO getPerfilByDescripcion(String descripcion)
	{
		System.out.println();
		Query q = session.createQuery("select p from PerfilDTO p where p.descripcion =:descrip ");
		q.setString("descrip", descripcion);
		return (PerfilDTO) q.uniqueResult();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> getUsuarios()
	{
		Query q = session.createQuery("select u from UsuarioDTO u order by u.usuario");
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> getUsuarioByName(String username)
	{
		Query q = session.createQuery("select u from UsuarioDTO u  where u.usuario= :username");
		q.setString("username",username);
		return q.list();
	}
	
		
	public UsuarioDTO getUsuario(Integer id) throws UsuarioInexistenteException
	{
		UsuarioDTO usuario = null;
		try{
			usuario = (UsuarioDTO) session.load(UsuarioDTO.class, id);
			usuario.getUsuario();
		}
		catch(ObjectNotFoundException e)
		{
			throw new UsuarioInexistenteException("El usuario buscado no existe");
		}
		return usuario;
	}
	
		
	public UsuarioPerfilDTO getUsuarioPerfil(int idUsuarioPerfil){
		UsuarioPerfilDTO usuarioPerfil = (UsuarioPerfilDTO) session.load(UsuarioPerfilDTO.class, idUsuarioPerfil);
		return usuarioPerfil;
	}
	
	
	
	
	
		
}
