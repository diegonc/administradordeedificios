package propiedades;

import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class TestResponsable extends TestCase {

	private SessionFactory sessionFactory;
	private Session session;

	public void setUp() {
		sessionFactory = new AnnotationConfiguration()
				.addAnnotatedClass(Responsable.class).configure()
				.setProperty("hibernate.hbm2ddl.auto", "update")
				.buildSessionFactory();
	}

	public void tearDown() {
		sessionFactory.close();
	}

	private static final String dni = "15236984";
	private static final String email = "pepe@mail.com";
	private static final String telefono = "5487844554";

	public void testLecturaDeEntidadHidratada() {
		crearResponsable();

		session = sessionFactory.openSession();
		Responsable recuperado = (Responsable) session.get(Responsable.class,
				dni);
		
		assertNotNull(recuperado);
		assertEquals(dni, recuperado.getDni());
		assertEquals(email, recuperado.getEmail());
		assertEquals(telefono, recuperado.getTelefono());
	}

	private void crearResponsable() {
		Responsable pepe = new Responsable(dni, telefono, email);

		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(pepe);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
		}
		finally {
			session.close();
		}
	}
}
