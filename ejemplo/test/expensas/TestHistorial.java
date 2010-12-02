package expensas;

import java.util.List;

import expensas.appl.ExpensaAppl;
import expensas.dto.ExpensaDTO;
import junit.framework.TestCase;

public class TestHistorial extends TestCase {

	public void historialExpensasTest(){
		ExpensaAppl expensaAppl = new ExpensaAppl();
		Integer dniResponsable = 30761872;
		String tipoExpensa = ExpensaDTO.tipoOrdinario;
		Integer idEdificio = 1;
		Integer orden = null;
		Integer nivel = -1;
		List<ExpensaDTO> expensas = expensaAppl.historialExpensas(dniResponsable, tipoExpensa, idEdificio, orden, nivel);
		for(ExpensaDTO ex: expensas){
			Integer nivelActual = ex.getPropiedad().getNivel();
			Integer ordenActual = ex.getPropiedad().getOrden();
			System.out.println(nivelActual+";"+ordenActual+";"+ex.getNumeroOperacion()+";"+ex.getMonto()+";"+ex.getIntereses()+";"+ex.getDeudaPrevia());
		}
	}	
}
