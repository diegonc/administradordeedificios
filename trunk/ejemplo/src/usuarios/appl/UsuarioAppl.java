package usuarios.appl;
import java.util.List;
import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Expression;


import usuarios.dto.UsuarioDTO;
import utilidades.UtilidadesConexion;

public class UsuarioAppl {
	
	/**
	 * 
	 * @param session
	 * @param usuario
	 */
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


	/**
	 * Obtener un SessionFactory de un archivo de configuración local.
	 */
	public SessionFactory createSessionFactory() {
		AnnotationConfiguration configuration = new AnnotationConfiguration();
		configuration.configure("/hibernate.cfg.xml");
		return configuration.buildSessionFactory();
	}

	

	/**
	 * Perform SELECT statements using Hibernate's load() method
	 */
	public UsuarioDTO getObject(SessionFactory factory, String dni) {
		Session session = factory.openSession();
		try {
			return (UsuarioDTO) session.load(UsuarioDTO.class, dni);
		} finally {
			session.close();
		}
	}

	/**
	 * Perform SELECT statements using Hibernate's Criteria API
	 */
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

	

	/**
	 * Perform UPDATE statements using Hibernate's update() method
	 */
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

	/**
	 * Perform DELETE statements using Hibernate's Query API
	 */
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
	public static void main(String[] args) {
		UsuarioDTO usuarioTest = new UsuarioDTO();
		usuarioTest= new UsuarioDTO();
		usuarioTest.setDni(31026053);
		usuarioTest.setApellido("Chelotti");
		usuarioTest.setNombre("Adriana");
		usuarioTest.setPassword("adriana");
		usuarioTest.setPerfil(1);
		usuarioTest.setUsuario("user");
		UsuarioAppl usuarioServ = new UsuarioAppl();
		SessionFactory factory = usuarioServ.createSessionFactory();
		int dniPrueba=usuarioServ.insertObject(factory.openSession(), usuarioTest);
				
		
		
		
	}
}
