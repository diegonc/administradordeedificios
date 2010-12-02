package propiedades;

import java.util.List;

import utilidades.AbstractAppl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

public class ResponsableAppl extends AbstractAppl {

	public ResponsableAppl() {}
	public ResponsableAppl(Session s) {
		session = s;
	}
	
	@SuppressWarnings("unchecked")
	public List<Responsable> listar() {
		return session.createQuery("from Responsable").list();
	}

	public Responsable buscar(Integer dni) {
		return (Responsable) session.get(Responsable.class, dni);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> listarDNIs() {
		return session.createCriteria(Responsable.class)
			.setProjection(Projections.property("dni"))
			.list();
	}
	
	public List<Integer> obtenerResponsablesConExpensas() {
		 Query query = session.createQuery("select distinct r.dni from Responsable r , PropiedadDTO p, ExpensaDTO ex where ex.propiedad=p and "+
				 			" (p.propietario.dni=r.dni " +
							"or p.inquilino.dni=r.dni "+
							"or p.poderPropietario.dni=r.dni " +
							"or p.poderInquilino.dni=r.dni) ");
		 return query.list();
	}
}
