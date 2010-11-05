package gastos;

import edificio.EdificioAppl;
import edificio.EdificioDTO;
import exception.DependenciasExistentesException;
import gastos.appl.GastosAppl;
import gastos.appl.TiposGastosAppl;
import gastos.dto.GastoPrevisionDTO;
import gastos.dto.TipoGastoDTO;
import gastos.dto.TipoGastoExtraordinarioDTO;
import gastos.exception.GastoExistenteException;
import gastos.exception.TipoGastoExistenteException;
import gastos.exception.TipoGastoInexistenteException;
import junit.framework.TestCase;
import utilidades.HibernateUtil;

public class GastosTest extends TestCase {

	private TiposGastosAppl tiposGastosAppl;
	private TipoGastoDTO tipoGasto;
	
	protected void setUp() throws Exception {
		this.tiposGastosAppl = new TiposGastosAppl();
	}
	
	public void testRemoveTipoGasto(){
		
		try {
			tipoGasto = tiposGastosAppl.getTipoGastoPorCodigo("PINTURA");
			tiposGastosAppl.removeTipoGasto(tipoGasto.getId());
		} 
		catch (TipoGastoInexistenteException e){}
		catch (DependenciasExistentesException e){}
	}
	
	public void testCrearTipoGasto(){
		
		tipoGasto = new TipoGastoExtraordinarioDTO();
		tipoGasto.setCodigo("PINTURA");
		tipoGasto.setDescripcion("Pintura general");
		tipoGasto.setTipo(TipoGastoDTO.tipoExtraordinario);
		try {
			tiposGastosAppl.addTipoGasto(tipoGasto);
		} catch (TipoGastoExistenteException e1) {
			fail(e1.getMessage());
		}
		
		tipoGasto = null;
		try {
			tipoGasto = tiposGastosAppl.getTipoGastoPorCodigo("PINTURA");
		} catch (TipoGastoInexistenteException e) {
			fail(e.getMessage());
		}
		
		assertEquals("PINTURA", tipoGasto.getCodigo());
		assertEquals("Pintura general", tipoGasto.getDescripcion());
	}
	
	
	public void testCrearTipoGastoExistente(){
		
		tipoGasto = new TipoGastoDTO();
		tipoGasto.setCodigo("PINTURA");
		tipoGasto.setDescripcion("Pintura");
		tipoGasto.setTipo(TipoGastoDTO.tipoExtraordinario);
		try {
			tiposGastosAppl.addTipoGasto(tipoGasto);
			fail("Debe lanzar una TipoGastoExistenteException");
		} catch (TipoGastoExistenteException e) {}
	}

	public void testmodificarTipoGastoExtraordinario(){
		
		try {
			tipoGasto = tiposGastosAppl.getTipoGastoPorCodigo("PINT");
		} catch (TipoGastoInexistenteException e1) {
			fail(e1.getMessage());
		}
		
		TipoGastoExtraordinarioDTO nuevoTipoGasto = new TipoGastoExtraordinarioDTO();
		nuevoTipoGasto.setDescripcion("Pintura general");
		nuevoTipoGasto.setTipo(TipoGastoDTO.tipoExtraordinario);
		
		try {
			tiposGastosAppl.updateTipoGasto(nuevoTipoGasto, tipoGasto.getId());
		}
		catch (TipoGastoInexistenteException e) 
		{
			fail(e.getMessage());
		}
	}
	
		
	public void testCrearGastoExistente(){
		
		try {
			tipoGasto = tiposGastosAppl.getTipoGastoPorCodigo("PINTURA");
		} catch (TipoGastoInexistenteException e1) {
			fail(e1.getMessage());
		}
		EdificioAppl edificioAppl = new EdificioAppl();
		EdificioDTO edificio = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(), 1);
		
		GastosAppl gastosAppl = new GastosAppl();
		GastoPrevisionDTO gasto = new GastoPrevisionDTO();
		gasto.setTipoGasto(tipoGasto);
		gasto.setAnio(2010);
		gasto.setDetalle("Pincel 22cm");
		gasto.setEdificio(edificio);
		gasto.setMes(11);
		gasto.setMonto(22.34);
		gasto.setNumeroFolio(2);
		
		try {
			gastosAppl.addGasto(gasto);
			fail("Debe lanzar una GastoExistenteException");
		} catch (GastoExistenteException e) 
		{}
	}
	
}
