package edificios;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edificio.EdificioAppl;
import edificio.EdificioDTO;
import utilidades.HibernateUtil;

public class EdificioTest extends TestCase {
	private EdificioDTO edificio;
	private EdificioAppl ediTransacBD;
	
	public EdificioTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		edificio = new EdificioDTO();
		edificio.setNombre("Edificio del Sol");
		edificio.setFondo_ordinario(0);
		edificio.setFondo_extraordinario(222);
		edificio.setForma_liq_exp("Como se pueda");
		edificio.setApto_profesional(true);
		edificio.setTasa_anual(43);
		edificio.setAmortizacion("ALEMAN");
		edificio.setCalle("Medrano");
		edificio.setNumero(222);
		edificio.setLocalidad("CABA");
		edificio.setEncargado_nombre("Jose");
		edificio.setEncargado_telefono("434343");
		edificio.setEncargado_depto("primero");
		edificio.setEncargado_piso("A");
		edificio.setDia_primer_vto(1);
		
		ediTransacBD = new EdificioAppl();
	}

	protected void tearDown(int id, SessionFactory factory) throws Exception {
		super.tearDown();
	}

	public void testInsertGetAndDeleteEdificio() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		int id = ediTransacBD.insertEdificio(factory.openSession(), this.edificio);
		EdificioDTO edifCompara = ediTransacBD.getEdificio(factory, id);		
		assertEquals(this.edificio.getNombre(), edifCompara.getNombre());
		ediTransacBD.deleteEdificio(factory, id);
		factory.close();
	} 
	
	public void testInsertEdificioWithTheSameName() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		boolean notInsert = false;
		int id = ediTransacBD.insertEdificio(factory.openSession(), this.edificio);
		try {
			ediTransacBD.insertEdificio(factory.openSession(), this.edificio);
		} catch (Exception e) {
			if (e.getMessage().contains("could not insert")) {
				notInsert = true;
			}
		}
		assertTrue(notInsert);
		ediTransacBD.deleteEdificio(factory, id);
		factory.close();
	}
	
	public void testGetAndUpdateCalleEdif() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		int id = ediTransacBD.insertEdificio(factory.openSession(), this.edificio);
		EdificioDTO edifAModif = ediTransacBD.getEdificio(factory, id);
		edifAModif.setCalle("Nueva");
		ediTransacBD.updateEdificio(factory, edifAModif);
		EdificioDTO edifAModifSegudno =  ediTransacBD.getEdificio(factory, id);
		assertEquals(edifAModifSegudno.getCalle(), "Nueva");
		ediTransacBD.deleteEdificio(factory, id);
		factory.close();
	}
	
	public void testExecuteQuery() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
				
		//abro la sesion y cree el query con el sete de los atributos a buscar
		Session session = factory.openSession();	
		String calle = "Medrano";
		Query query = session.createQuery("SELECT edificio FROM EdificioDTO edificio WHERE edificio.calle = :calle");
		query.setString("calle", calle);
		
		
		int id = ediTransacBD.insertEdificio(factory.openSession(), this.edificio);
		this.edificio.setNombre("Edificio del Sol II");
		int id2 = ediTransacBD.insertEdificio(factory.openSession(), this.edificio);
		//ArrayList<EdificioDTO> result = (ArrayList<EdificioDTO>) ediTransacBD.executeQuery(factory ,query);
		//assertEquals(result.size(), 2);
		ediTransacBD.deleteEdificio(factory, id);
		ediTransacBD.deleteEdificio(factory, id2);
		factory.close();
		
	}
}
