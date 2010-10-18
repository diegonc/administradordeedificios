package expensas;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import utilidades.HibernateUtil;
import gastos.exception.GastoExistenteException;

public class ExpensasCobroAppl {

	private Session session;

	public ExpensasCobroAppl(Session session) {
		this.session = session;
	}

	public ExpensasCobroAppl() {
	}

	public void addCobroExpensas(ExpensaCobroDTO cobro) throws GastoExistenteException 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try{
        	session.save(cobro);
        }catch(ConstraintViolationException e){
        	throw new GastoExistenteException("Error al cargar el cobro.");
        }
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
	}
	
	public List<ExpensaCobroDTO> listarCobrosDePropiedad(Integer idPropiedad) {
		Criteria criteria = session.createCriteria(ExpensaCobroDTO.class)
			.createAlias("propiedad", "propiedad")
			.add(Restrictions.eq("consolidado", false))
			.add(Restrictions.eq("propiedad.id", idPropiedad));
		return criteria.list();
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
