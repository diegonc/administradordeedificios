package usuarios.appl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.opensymphony.xwork2.inject.Factory;

import usuarios.dto.UsuarioDTO;
import utilidades.UtilidadesConexion;

public class UsuarioAppl {
	
	/**
	 * 
	 * @param session
	 * @param usuario
	 */
	  private void insertObject( Session session, UsuarioDTO usuario )
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
	  }
	
	
	/**
	 * 
	 * @param factory
	 * @return
	 */
	 private String insertObject( SessionFactory factory, UsuarioDTO usuario ) {
	    Session session = factory.openSession();
	    try{
	      insertObject( session, usuario );
	    }finally{
	      session.close();
	    }
	    return usuario.getDni();
	  }
	 
	 public static void main(String[] args) {
		    UsuarioDTO usuario = new UsuarioDTO();
		    usuario.setNombre( "Dario" );
		    usuario.setApellido("Perez Staltari");
		    usuario.setUsuario("dmpstaltari");
		    usuario.setPassword("dmpstaltari");
		    usuario.setDni("30982871");
		    usuario.setPerfil(0);
		    SessionFactory factory = UtilidadesConexion.createSessionFactory();
		    UsuarioAppl user = new UsuarioAppl();
		    user.insertObject(factory, usuario);
		    
	}
	

}
