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
import edificio.EdificioDTO;

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
	
	public void updateUsuario(UsuarioDTO nuevoUsuario,int idUsuario)	{
		Session session = HibernateUtil.getSession();
		org.hibernate.Transaction tr= session.beginTransaction();
		boolean com=false;
		try{
        
			UsuarioDTO usuario = (UsuarioDTO) session.load(UsuarioDTO.class, idUsuario);
			usuario.getApellido();
			usuario.setApellido(nuevoUsuario.getApellido());
            usuario.setNombre(nuevoUsuario.getNombre());
            usuario.setPassword(nuevoUsuario.getPassword());
            session.update(usuario);
            com = true;
            
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(com)
				tr.commit();
			else
				tr.rollback();
		}
            
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
	
	public UsuarioDTO getUsuarioByUsername(String username) throws UsuarioInexistenteException
	{
		UsuarioDTO usuario = null;
		Query q = session.createQuery("select u from UsuarioDTO u  where u.usuario= :username");
		q.setString("username",username);
		
		try{
			usuario =(UsuarioDTO)q.uniqueResult();
		 	if (usuario==null) throw new ObjectNotFoundException(usuario, username);
		}
		catch(ObjectNotFoundException e)
		{
			throw new UsuarioInexistenteException("El usuario buscado no existe");
		}
		return usuario;
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

	public UsuarioPerfilDTO getUsuarioPerfil(int idUsuario, String descripcionPerfil) {
		
		PerfilDTO perfil = getPerfilByDescripcion(descripcionPerfil);
		Query q = session.createQuery("select up from UsuarioPerfilDTO up where up.usuario =:usuario and up.perfil =:perfil");
		q.setInteger("usuario", idUsuario);
		q.setInteger("perfil", perfil.getId());
		
		UsuarioPerfilDTO usuarioPerfil = null;
		usuarioPerfil = (UsuarioPerfilDTO) q.uniqueResult();
		
		return  usuarioPerfil;
		
	}
	
	public void actualizarPerfiles(List<PerfilDTO> perfiles,int idUsuario) throws UsuarioInexistenteException
	{
		session.beginTransaction();
		UsuarioDTO usuario = null;
						
		try {
			usuario = getUsuario(idUsuario);
		} catch (UsuarioInexistenteException e) {
			throw new UsuarioInexistenteException("El usuario al que se le actualizaran los perfiles no existe");
		}
		
		limpiarEdificiosPorUsuario(usuario);
		session.beginTransaction();
		usuario.setPerfiles(perfiles);
		session.update(usuario);
		session.getTransaction().commit();
	}
	
	private void limpiarEdificiosPorUsuario(UsuarioDTO usuario)
	{
		UsuarioPerfilDTO usuarioPerfil = null;
		for(PerfilDTO p: usuario.getPerfiles())
		{
			usuarioPerfil = getUsuarioPerfil(usuario.getId(), p.getDescripcion());
			usuarioPerfil.setEdificios(null);
			session.update(usuarioPerfil);
		}
		session.getTransaction().commit();
	}
	
	public void actualizarEdificiosParaUsuarioPerfil(List<EdificioDTO> edificios,int idUsuario,String descripcionPerfil){
		
		session.beginTransaction();
		UsuarioPerfilDTO usuarioPerfil = getUsuarioPerfil(idUsuario, descripcionPerfil);
		usuarioPerfil.setEdificios(edificios);
		session.update(usuarioPerfil);
		session.getTransaction().commit();
	}
	
	
	
		
}
