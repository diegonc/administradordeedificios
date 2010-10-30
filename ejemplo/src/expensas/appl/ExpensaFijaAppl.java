package expensas.appl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import propiedades.PropiedadDTO;
import propiedades.TipoPropiedadDTO;
import utilidades.HibernateUtil;
import edificio.EdificioAppl;
import edificio.EdificioDTO;
import expensas.dto.ExpensaDTO;

public class ExpensaFijaAppl {
	private final static String A_FECHA ="afecha";
	private final static String PUNITORIO ="punitorio";
	private final static String DIFERIDO ="diferido";
public List<ExpensaDTO> obtenerExpensasFijas(int id){
		
		Session session = HibernateUtil.getSession();
//		session.beginTransaction();
		EdificioAppl edificioAppl = new EdificioAppl();
		ExpensaInteresesAppl expensasIntereses = new ExpensaInteresesAppl();
		EdificioDTO edificio = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(), id);
		
		List<ExpensaDTO> expensasFijas = new ArrayList<ExpensaDTO>();
		
		for (TipoPropiedadDTO tipoPropiedadActual : edificio.getTipoPropiedades()) {
			List<PropiedadDTO> propiedades = tipoPropiedadActual.getPropiedades();
			for (PropiedadDTO propiedadActual : propiedades) {
				ExpensaDTO expensa = new ExpensaDTO();
				expensa.setPropiedad(propiedadActual);
				expensa.setMonto(tipoPropiedadActual.getMontoExp());
				expensa.setTipo("O");
				System.out.println("idExpensa:"+expensa.getId());
				if (edificio.getMora().equalsIgnoreCase(PUNITORIO))
					expensasIntereses.calcularInteresPunitorio(edificio, expensa);
				if (edificio.getMora().equalsIgnoreCase(A_FECHA))
					expensasIntereses.calcularInteresAFechaDePago(edificio, expensa);
				if (edificio.getMora().equalsIgnoreCase(DIFERIDO))
					expensasIntereses.calcularInteresDiferidoProximaLiquidacion(edificio, expensa);
					//TODO:ver el nro de operacion de donde sacarlo
				actualizarSaldos(propiedadActual, expensa);
				expensa.setNumeroOperacion(expensa.getId());				
				expensasFijas.add(expensa);
				
			}
			
		}
		
		for(ExpensaDTO exp: expensasFijas){
			session.beginTransaction();
			session.saveOrUpdate(exp);
			session.getTransaction().commit();	
			//System.out.println(exp.getPropiedad().getNivel() + "-"+exp.getPropiedad().getOrden()+"  "+ exp.getDeudaPrevia()+ "  "+ exp.getMonto() );
		}
		
		
		return expensasFijas;
	}
private void actualizarSaldos(PropiedadDTO propiedadActual, ExpensaDTO expensa) {
	if(expensa.getTipo().equalsIgnoreCase("O")){
		propiedadActual.setCtaOrdSaldoExp(expensa.getDeudaPrevia()+expensa.getMonto());
	}else{
		propiedadActual.setCtaExtSaldoExp(expensa.getDeudaPrevia()+expensa.getMonto());
	}
}

}
