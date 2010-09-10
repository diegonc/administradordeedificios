package test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edificio.EdificioDTO;
import edificio.EdificioTransacBD;
import junit.framework.TestCase;

public class edificioTest extends TestCase {
	private EdificioDTO edificio;
	
	public edificioTest(String name) {
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
		edificio.setAmortizacion(100);
		edificio.setCalle("Medrano");
		edificio.setNumero(222);
		edificio.setLocalidad("CABA");
		edificio.setEncargado_nombre("Jose");
		edificio.setEncargado_telefono("434343");
		edificio.setEncargado_depto("primero");
		edificio.setEncargado_piso("A");
		edificio.setDia_primer_vto(1);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInsertAndGetEdificio() {
		EdificioTransacBD ediTransacBD = new EdificioTransacBD();
		SessionFactory factory = ediTransacBD.createSessionFactory();
		int id = ediTransacBD.insertObject(factory.openSession(), this.edificio);
		EdificioDTO edifCompara = ediTransacBD.getObject(factory, id);		
		assertEquals(this.edificio.getNombre(), edifCompara.getNombre());
		ediTransacBD.deleteObject(factory, id);
	} 

}
