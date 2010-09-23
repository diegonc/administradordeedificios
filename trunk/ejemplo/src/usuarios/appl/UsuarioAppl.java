package usuarios.appl;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import usuarios.dto.PerfilDTO;
import usuarios.dto.UsuarioDTO;
import utilidades.HibernateUtil;

public class UsuarioAppl {
		
	private Session session;
	
	public UsuarioAppl() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public void addUsuario(UsuarioDTO usuario)
	{
		session.beginTransaction();
        session.save(usuario);
        session.getTransaction().commit();
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
        usuario.setEdificio(nuevoUsuario.getEdificio());
        session.update(usuario);
        session.getTransaction().commit();
    }
	
	public void removeUsuario(int idUsuario)
	{
	    session.beginTransaction();
        UsuarioDTO usuario = (UsuarioDTO) session.load(UsuarioDTO.class, idUsuario);
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
		PerfilDTO perfil = (PerfilDTO) session.load(PerfilDTO.class, idPerfil);
		return perfil;
	}
		
	@SuppressWarnings("unchecked")
	public List<PerfilDTO> getPerfiles()
	{
		Query q = session.createQuery("select p from PerfilDTO p order by p.id ");
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
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
	
	public UsuarioDTO getUsuario(Integer id)
	{
		Query q = session.createQuery("select u from UsuarioDTO u  where u.id= :id");
		q.setInteger("id",id);
		if (!q.list().isEmpty())
		return (UsuarioDTO) q.list().get(0);
		return new UsuarioDTO();
	}
	
	public static void main(String[] args) {
		
		UsuarioAppl usuarioAppl = new UsuarioAppl();
		
		PerfilDTO perfil1 = usuarioAppl.getPerfil(3);
		PerfilDTO perfil2 = usuarioAppl.getPerfil(4);
		PerfilDTO perfil3 = usuarioAppl.getPerfil(2);
		
		List<PerfilDTO> perfiles = new ArrayList<PerfilDTO>();
		
		perfiles.add(perfil1);
		perfiles.add(perfil2);
		perfiles.add(perfil3);
		
			
		UsuarioDTO usuario = new UsuarioDTO();
		
		usuario.setApellido("Chelotti");
		usuario.setNombre("Adriana Gretel");
		usuario.setDni(31026053);
		usuario.setPassword("achelotti");
		usuario.setUsuario("achelotti");
		usuario.setPerfiles(perfiles);
		
		usuarioAppl.addUsuario(usuario);
		//usuario.setNombre("Adriana");
		//usuarioAppl.updateUsuario(usuario, 9);
		//usuarioAppl.removeUsuario(9);
						
	}
	
}
