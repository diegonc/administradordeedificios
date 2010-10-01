package propiedades;

import java.util.List;

import utilidades.AbstractAppl;

public class ResponsableAppl extends AbstractAppl {
	
	@SuppressWarnings("unchecked")
	public List<Responsable> listar() {
		return session.createQuery("from Responsable").list();
	}

	public Responsable buscar(Integer dni) {
		return (Responsable) session.get(Responsable.class, dni);
	}

}
