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
public List<ExpensaDTO> obtenerExpensasFijas(int id){
		
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		EdificioAppl edificioAppl = new EdificioAppl();
		EdificioDTO edificio = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(), id);
		List<ExpensaDTO> expensasFijas = new ArrayList<ExpensaDTO>();
		for (TipoPropiedadDTO tipoPropiedadActual : edificio.getTipoPropiedades()) {
			List<PropiedadDTO> propiedades = tipoPropiedadActual.getPropiedades();
			for (PropiedadDTO propiedadActual : propiedades) {
				ExpensaDTO expensa = new ExpensaDTO();
				expensa.setPropiedad(propiedadActual);
				expensa.setMonto(tipoPropiedadActual.getMontoExp());
				//TODO: ver intereses
				double deuda = propiedadActual.getCtaOrdSaldoExp()+ propiedadActual.getCtaOrdSaldoInt();
				expensa.setDeudaPrevia(deuda);
				expensa.setTipo("0");
				expensa.setNumeroOperacion(0);
				expensasFijas.add(expensa);
				
			}
			
		}
		
		for(ExpensaDTO exp: expensasFijas){
			session.saveOrUpdate(exp);
			//System.out.println(exp.getPropiedad().getNivel() + "-"+exp.getPropiedad().getOrden()+"  "+ exp.getDeudaPrevia()+ "  "+ exp.getMonto() );
		}
		session.getTransaction().commit();
		return expensasFijas;
	}

}
