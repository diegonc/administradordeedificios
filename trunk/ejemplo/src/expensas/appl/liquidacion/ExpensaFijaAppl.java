package expensas.appl.liquidacion;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import propiedades.PropiedadDTO;
import propiedades.TipoPropiedadDTO;
import utilidades.HibernateUtil;
import edificio.EdificioAppl;
import edificio.EdificioDTO;
import expensas.appl.ExpensaAppl;
import expensas.appl.ExpensaInteresesAppl;
import expensas.dto.ExpensaDTO;

public class ExpensaFijaAppl {
	private final static String A_FECHA ="afecha";
	private final static String PUNITORIO ="punitorio";
	private final static String DIFERIDO ="diferido";
	
	public List<ExpensaDTO> obtenerExpensasFijas(int id, String tipoExpensa){
		Session session = HibernateUtil.getSession();
		EdificioAppl edificioAppl = new EdificioAppl();
		ExpensaAppl expensaAppl = new ExpensaAppl();
		ExpensaInteresesAppl expensasIntereses = new ExpensaInteresesAppl();
		EdificioDTO edificio = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(), id);
		
		List<ExpensaDTO> expensasFijas = new ArrayList<ExpensaDTO>();
		
		for (TipoPropiedadDTO tipoPropiedadActual : edificioAppl.obtenerTipoPropiedadPorEdificio(id)) {
			List<PropiedadDTO> propiedades = tipoPropiedadActual.getPropiedades();
			for (PropiedadDTO propiedadActual : propiedades) {
				ExpensaDTO expensa = new ExpensaDTO();
				expensa.setPropiedad(propiedadActual);
				if (tipoExpensa.equalsIgnoreCase(ExpensaDTO.tipoOrdinario)){
					expensa.setMonto(tipoPropiedadActual.getMontoExp());
				}else{
					
					expensa.setMonto(tipoPropiedadActual.getMontoExpExt());
				}
				expensa.setTipo(tipoExpensa);
				
				if (edificio.getMora().equalsIgnoreCase(PUNITORIO))
					expensasIntereses.calcularInteresPunitorio(edificio, expensa);
				if (edificio.getMora().equalsIgnoreCase(A_FECHA))
					expensasIntereses.calcularInteresAFechaDePago(edificio, expensa);
				if (edificio.getMora().equalsIgnoreCase(DIFERIDO))
					expensasIntereses.calcularInteresDiferidoProximaLiquidacion(edificio, expensa);
					
				actualizarSaldos(propiedadActual, expensa);
				expensa.setNumeroOperacion(expensaAppl.obtenerNumeroDeOperacion(propiedadActual.getId(), ExpensaDTO.tipoOrdinario));
				session.beginTransaction();
				session.saveOrUpdate(expensa);
				session.getTransaction().commit();	
				expensasFijas.add(expensa);
			}
		}
		return expensasFijas;
	}
	
	private void actualizarSaldos(PropiedadDTO propiedadActual, ExpensaDTO expensa) {
		if(expensa.getTipo().equalsIgnoreCase(ExpensaDTO.tipoOrdinario)){
			propiedadActual.setCtaOrdSaldoExp(-(expensa.getDeudaPrevia()+expensa.getMonto()));
			propiedadActual.setCtaOrdSaldoInt(-(propiedadActual.getCtaOrdSaldoInt()+expensa.getIntereses()));
		}else{
			propiedadActual.setCtaExtSaldoExp(-(expensa.getDeudaPrevia()+expensa.getMonto()));
			propiedadActual.setCtaExtSaldoInt(-(propiedadActual.getCtaExtSaldoInt()+(expensa.getIntereses())));
		}
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		session.update(propiedadActual);
		session.getTransaction().commit();
	}
		

}
