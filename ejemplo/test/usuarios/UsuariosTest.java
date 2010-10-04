package usuarios;

import java.util.ArrayList;
import java.util.List;

import edificio.EdificioAppl;
import edificio.EdificioDTO;

import junit.framework.TestCase;
import usuarios.appl.UsuarioAppl;
import usuarios.dto.PerfilDTO;
import usuarios.dto.UsuarioDTO;
import usuarios.dto.UsuarioPerfilDTO;
import usuarios.exception.UsuarioExistenteException;
import usuarios.exception.UsuarioInexistenteException;
import utilidades.HibernateUtil;

public class UsuariosTest extends TestCase{

	private UsuarioDTO usuario;
	private UsuarioAppl usuarioAppl;
		
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.usuarioAppl = new UsuarioAppl();
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

	public void testActualizarUsuarioPerfilEdificio(){
		try {
			this.usuario = usuarioAppl.getUsuarioByUsername("adminuser");
		}catch (UsuarioInexistenteException e) {
			fail(e.getMessage());
		}
		
		PerfilDTO perfil1 = usuarioAppl.getPerfilByDescripcion(PerfilDTO.ADMINISTRADOR);
		PerfilDTO perfil2 = usuarioAppl.getPerfilByDescripcion(PerfilDTO.RESPONSABLE_COBROS);
		PerfilDTO perfil3 = usuarioAppl.getPerfilByDescripcion(PerfilDTO.RESPONSABLE_GASTOS);
		
		List<PerfilDTO> perfiles = new ArrayList<PerfilDTO>();
		
		perfiles.add(perfil1);
		perfiles.add(perfil2);
		perfiles.add(perfil3);
		
		try {
			usuarioAppl.actualizarPerfiles(perfiles, this.usuario.getId());
		} catch (UsuarioInexistenteException e) {
			fail(e.getMessage());
		}
		
		
		List<EdificioDTO> edificios = new ArrayList<EdificioDTO>();
		EdificioAppl edificioAppl = new EdificioAppl();
		
		EdificioDTO edificio1 = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(), 1);
		EdificioDTO edificio2 = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(), 2);
		
		edificios.add(edificio1);
		edificios.add(edificio2);
		
		
		usuarioAppl.actualizarEdificiosParaUsuarioPerfil(edificios, this.usuario.getId(), PerfilDTO.ADMINISTRADOR);
		
		
		List<EdificioDTO> edificios2 = new ArrayList<EdificioDTO>();
	
		edificios2.add(edificio1);
				
		usuarioAppl.actualizarEdificiosParaUsuarioPerfil(edificios2, this.usuario.getId(), PerfilDTO.RESPONSABLE_COBROS);
				 
		
	}

}
