package propiedades;

import java.util.List;

import utilidades.AbstractAppl;

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

}
