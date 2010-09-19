package usuarios.appl;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import usuarios.dto.PerfilDTO;
import usuarios.dto.UsuarioDTO;
import utilidades.HibernateUtil;

public class UsuarioAppl {
	
	/*
	  public int insertObject( Session session, UsuarioDTO usuario )
	  {
	    boolean commit = false;
	    Transaction transaction = session.beginTransaction();

	    try
	    {
	      session.save( usuario );

	      commit = true;
	    }
	    finally
	    {
	      if ( commit )
	        transaction.commit();
	      else
	        transaction.rollback();
	    }
	    return usuario.getDni();
	  }


	
	public SessionFactory createSessionFactory() {
		AnnotationConfiguration configuration = new AnnotationConfiguration();
		configuration.configure("/hibernate.cfg.xml");
		return configuration.buildSessionFactory();
	}

	
	public UsuarioDTO getObject(SessionFactory factory, String dni) {
		Session session = factory.openSession();
		try {
			return (UsuarioDTO) session.load(UsuarioDTO.class, dni);
		} finally {
			session.close();
		}
	}

	public UsuarioDTO queryObject(SessionFactory factory, String dni) {
		Session session = factory.openSession();
		try {
			Criteria criteria = session.createCriteria(UsuarioDTO.class);
			criteria.add(Expression.eq("DNI", dni));
			List<?> results = criteria.list();
			return (UsuarioDTO) results.get(0);
		} finally {
			session.close();
		}
	}

	public void updateObject(SessionFactory factory, UsuarioDTO edificio) {
		Session session = factory.openSession();
		try {
			updateObject(session, edificio);
		} finally {
			session.close();
		}
	}

	public void updateObject(Session session, UsuarioDTO edificio) {
		boolean commit = false;
		Transaction transaction = session.beginTransaction();
		try {
			session.update(edificio);
			commit = true;
		} finally {
			if (commit) {
				transaction.commit();
			} else {
				transaction.rollback();
			}
		}
	}


	public void deleteObject(SessionFactory factory, int dni) {
		Session session = factory.openSession();
		try {
			deleteObject(session, dni);
		} finally {
			session.close();
		}
	}

	public void deleteObject(Session session, int dni) {
		boolean commit = false;
		Transaction transaction = session.beginTransaction();

		try {
			Query query = session
					.createQuery("DELETE FROM USUARIO us WHERE us.dni = :dni");
			query.setInteger("dni", dni);
			query.executeUpdate();
			commit = true;
		} finally {
			if (commit) {
				transaction.commit();
			}
			else {
				transaction.rollback();
			}
		}
	}
*/
	public void addUsuario(UsuarioDTO usuario)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(usuario);
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
	}
	
	public void removeUsuario(int idUsuario)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        UsuarioDTO usuario = (UsuarioDTO) session.load(UsuarioDTO.class, idUsuario);
        session.delete(usuario);
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
	}
	
	public void addPerfil(PerfilDTO perfil)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(perfil);
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
	}
	
	public void removePerfil(int idPerfil)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        PerfilDTO perfil = (PerfilDTO) session.load(PerfilDTO.class, idPerfil);
        session.delete(perfil);
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
	}
	
	public void updatePerfil(int idPerfil,PerfilDTO perfilNuevo)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        PerfilDTO perfil = (PerfilDTO) session.load(PerfilDTO.class, idPerfil);
        perfil.setDescripcion(perfilNuevo.getDescripcion());
        session.update(perfil);
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
		
	}
	
	public PerfilDTO getPerfil(int idPerfil)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        PerfilDTO perfil = (PerfilDTO) session.load(PerfilDTO.class, idPerfil);
        HibernateUtil.getSessionFactory().close();
        return perfil;
	}
		
	@SuppressWarnings("unchecked")
	public List<PerfilDTO> getPerfiles()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("select p from PerfilDTO p order by p.descripcion ");
		HibernateUtil.getSessionFactory().close();
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> getUsuarios()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("select u from UsuarioDTO u order by u.usuario");
		HibernateUtil.getSessionFactory().close();
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> getUsuarios(String username)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("select u from UsuarioDTO u  where u.usuario= :username");
		q.setString("username",username);
		HibernateUtil.getSessionFactory().close();
		return q.list();
	}
	
	
	public static void main(String[] args) {
		
		UsuarioAppl usuarioAppl = new UsuarioAppl();
		
		//PerfilDTO perfil = new PerfilDTO();
		//perfil.setDescripcion("Responsable de Gastos");
		//usuarioAppl.addPerfil(perfil);
		
		
		//usuarioAppl.removePerfil(5);
		
		//usuarioAppl.updatePerfil(5, perfil);
		
		
		/*PerfilDTO perfil = usuarioAppl.getPerfil(3);
		System.out.println(perfil.toString());
		
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setPerfil(perfil);
		usuario.setDni(30761872);
		usuario.setApellido("Perez Staltari");
		usuario.setNombre("Dario");
		usuario.setPassword("dario");
		usuario.setUsuario("user2");
		
		usuarioAppl.addUsuario(usuario);*/
		
		List<PerfilDTO> perfiles = usuarioAppl.getPerfiles();
		
		for(PerfilDTO p: perfiles)
		{
			System.out.println(p.toString());
		}
		
		
		
		//usuarioAppl.removeUsuario(1);
		
		
		
	}
}
