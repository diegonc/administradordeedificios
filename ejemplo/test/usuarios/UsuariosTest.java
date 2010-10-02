package usuarios;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import usuarios.appl.UsuarioAppl;
import usuarios.dto.PerfilDTO;
import usuarios.dto.UsuarioDTO;
import usuarios.dto.UsuarioPerfilDTO;
import usuarios.dto.UsuarioPerfilEdificioDTO;
import usuarios.exception.UsuarioExistenteException;
import usuarios.exception.UsuarioInexistenteException;
import edificio.EdificioAppl;
import edificio.EdificioDTO;

public class UsuariosTest extends TestCase{

	private UsuarioDTO usuario;
	private UsuarioPerfilDTO usuarioPerfil;
	private UsuarioPerfilEdificioDTO usuarioPerfilEdificio;
	private EdificioDTO edificio;
	private UsuarioAppl usuarioAppl;
	private EdificioAppl edificioAppl;	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.usuarioAppl = new UsuarioAppl();
		this.edificioAppl = new EdificioAppl();
		
	}
	
	public void testRemoveUsuarioInexistente()
	{
		try {
			usuarioAppl.removeUsuario(0);
			fail("Debe lanzar una UsuarioInexistenteException");
		} catch (UsuarioInexistenteException e) {
					
		}
	}
	
	public void testUsuarioInexistente(){
		
		try {
			this.usuario = usuarioAppl.getUsuario(0);
			fail("Debe lanzar una UsuarioInexistenteException");
			
		} catch (UsuarioInexistenteException e) {
			
		}
				
	}
	
	public void testAgregarUsuarioExistente(){
		
		this.usuario = new UsuarioDTO();
		usuario.setDni(1);
		usuario.setApellido("Galeano");
		usuario.setNombre("Leonel");
		usuario.setPassword("lgaleano");
		usuario.setUsuario("lgaleano");
		
		try {
			usuarioAppl.addUsuario(usuario);
			fail("Debe lanzar una UsuarioExistenteException");
		} catch (UsuarioExistenteException e) {
			
		}		
	}
	
			
	public void testSetearPerfilesParaUsuario(){
		
		PerfilDTO perfil1 = usuarioAppl.getPerfil(1);
		PerfilDTO perfil2 = usuarioAppl.getPerfil(2);
		PerfilDTO perfil4 = usuarioAppl.getPerfil(4);
		
		List<PerfilDTO> perfiles = new ArrayList<PerfilDTO>();
		
		perfiles.add(perfil1);
		perfiles.add(perfil2);
		perfiles.add(perfil4);
	
		UsuarioDTO nuevoUsuario = new UsuarioDTO();
		
		nuevoUsuario.setPerfiles(perfiles);
		nuevoUsuario.setApellido("Roche");
		nuevoUsuario.setNombre("Nicolino");
		nuevoUsuario.setPassword("adminpass");
		nuevoUsuario.setUsuario("adminuser");
				
		usuarioAppl.updateUsuario(nuevoUsuario, 1);
		
		try {
			this.usuario = usuarioAppl.getUsuario(1);
		} catch (UsuarioInexistenteException e) {
			fail(e.getMessage());
		}
		
		assertEquals(perfiles,this.usuario.getPerfiles());
				
	}

		

}
