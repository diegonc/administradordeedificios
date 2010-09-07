package ar.uba.fi.taller2.nicolinoroche.modelo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import junit.framework.TestCase;

public class TestResponsable extends TestCase {

	private static final String PERSISTENCE_UNIT = "unit-testing-pu";

	private EntityManagerFactory emf;
	private EntityManager em;

	public void setUp() {
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		em = emf.createEntityManager();
	}

	public void tearDown() {
		em.close();
		emf.close();
	}

	private static final String dni = "15236984";
	private static final String email = "pepe@mail.com";
	private static final String telefono = "5487844554";

	public void testLecturaDeEntidadHidratada() {

		crearResponsable();

		Responsable recuperado = em.find(Responsable.class, dni);

		assertNotNull(recuperado);

		assertEquals(dni, recuperado.getDni());
		assertEquals(email, recuperado.getEmail());
		assertEquals(telefono, recuperado.getTelefono());
	}

	private void crearResponsable() {
		Responsable pepe = new Responsable(dni, telefono, email);
		em.persist(pepe);
	}
}
// vim: ts=4 sw=4 noet:
