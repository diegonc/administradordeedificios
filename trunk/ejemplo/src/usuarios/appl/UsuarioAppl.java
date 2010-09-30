package usuarios.appl;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import edificio.EdificioAppl;
import edificio.EdificioDTO;

import usuarios.dto.PerfilDTO;
import usuarios.dto.UsuarioDTO;
import usuarios.dto.UsuarioPerfilEdificioDTO;
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
	
	
	public void addEdificiosByUsuarioPerfil(int idUsuario,int idPerfil,List<Integer> edificios){
		session.beginTransaction();
		
		UsuarioDTO usuario = (UsuarioDTO) session.load(UsuarioDTO.class, idUsuario);
		PerfilDTO perfil = (PerfilDTO) session.load(PerfilDTO.class, idPerfil);
		
		for(int idEdificio: edificios)
		{
			EdificioDTO edificio = (EdificioDTO) session.load(EdificioDTO.class, idEdificio);
			UsuarioPerfilEdificioDTO usuarioPerfilEdificio = new UsuarioPerfilEdificioDTO();
			
			usuarioPerfilEdificio.setUsuario(usuario);
			usuarioPerfilEdificio.setPerfil(perfil);
			usuarioPerfilEdificio.setEdificio(edificio);
			
			session.save(usuarioPerfilEdificio);
		}
	
        session.getTransaction().commit();
	}
	
	public void addUsuarioPerfilEdificio(int idUsuario,int idPerfil,Integer idEdificio){
		session.beginTransaction();
		
		UsuarioDTO usuario = (UsuarioDTO) session.load(UsuarioDTO.class, idUsuario);
		PerfilDTO perfil = (PerfilDTO) session.load(PerfilDTO.class, idPerfil);
		
        
		UsuarioPerfilEdificioDTO usuarioPerfilEdificio = new UsuarioPerfilEdificioDTO();
		
		usuarioPerfilEdificio.setUsuario(usuario);
		usuarioPerfilEdificio.setPerfil(perfil);
		
		if(idEdificio!=null)
		{
			EdificioDTO edificio = (EdificioDTO) session.load(EdificioDTO.class, idEdificio);
			usuarioPerfilEdificio.setEdificio(edificio);
		}
		
		session.save(usuarioPerfilEdificio);
        session.getTransaction().commit();
	}
	
	public void removeUsuarioPerfilEdificio(int idUsuario,int idPerfil,Integer idEdificio){
		session.beginTransaction();
		
		String consulta = "delete upe from UsuarioPerfilEdificioDTO upe where upe.usuario =:usuario";
		
		if(idEdificio != null)
		{
			consulta = consulta + " and upe.edificio =:edificio";
		}
		
        Query q = session.createQuery("delete upe from UsuarioPerfilEdificioDTO upe where upe.usuario =:usuario" +
        							  " and upe.perfil =:perfil");
        
        q.setParameter("usuario", idUsuario);
        q.setParameter("perfil", idPerfil);
        
        if(idEdificio != null)
        {	
        	q.setParameter("edificio", idEdificio);
        }
        
        q.executeUpdate();
        session.getTransaction().commit();
	}
	
	public void removeUsuarioPerfil(int idUsuario,int idPerfil){
		session.beginTransaction();
        Query q = session.createQuery("delete upe from UsuarioPerfilEdificioDTO upe where upe.usuario =:usuario" +
        							  " and upe.perfil =:perfil");
        q.setParameter("usuario", idUsuario);
        q.setParameter("perfil", idPerfil);
        q.executeUpdate();
        session.getTransaction().commit();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<EdificioDTO> getEdificiosByUsuarioPerfil(int idUsuario,int idPerfil){
		Query q = session.createQuery("select upe from UsuarioPerfilEdificioDTO upe where upe.usuario =:usuario" +
        							  " and upe.perfil =:perfil");
        q.setParameter("usuario", idUsuario);
        q.setParameter("perfil", idPerfil);
        return q.list();
	}
	
	public static void main(String[] args) {
		
		UsuarioAppl usuarioAppl = new UsuarioAppl();
		EdificioAppl edificioAppl = new EdificioAppl();
		
		List<EdificioDTO> edificios = new ArrayList<EdificioDTO>();
		EdificioDTO edificio1 = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(), 1);
		EdificioDTO edificio2 = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(), 2);
		
		edificios.add(edificio1);
		edificios.add(edificio2);
		
		List<Integer> listEdificios = new ArrayList<Integer>();
		
		for(EdificioDTO edif: edificios){
			listEdificios.add(edif.getId());
		}
				
		
		PerfilDTO perfil1 = usuarioAppl.getPerfil(3);
		PerfilDTO perfil2 = usuarioAppl.getPerfil(4);
		PerfilDTO perfil3 = usuarioAppl.getPerfil(2);
		
		UsuarioDTO usuario = usuarioAppl.getUsuario(1);
		
		
		usuarioAppl.addEdificiosByUsuarioPerfil(usuario.getId(), perfil1.getId(), listEdificios);
		usuarioAppl.addEdificiosByUsuarioPerfil(usuario.getId(), perfil2.getId(), listEdificios);
		
		listEdificios.remove(1);
		
		usuarioAppl.addEdificiosByUsuarioPerfil(usuario.getId(), perfil3.getId(), listEdificios);
		
		
		
			
		/*UsuarioDTO usuario = new UsuarioDTO();
		
		usuario.setApellido("Chelotti");
		usuario.setNombre("Adriana Gretel");
		usuario.setDni(31026053);
		usuario.setPassword("achelotti");
		usuario.setUsuario("achelotti");
		usuario.setPerfiles(perfiles);
		
		usuarioAppl.addUsuario(usuario);*/
		//usuario.setNombre("Adriana");
		//usuarioAppl.updateUsuario(usuario, 9);
		//usuarioAppl.removeUsuario(9);
						
	}
	
}
