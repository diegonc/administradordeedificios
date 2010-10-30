package expensas.appl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import expensas.dto.ExpensaCobroDTO;

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
	
	@SuppressWarnings("unchecked")
	public List<ExpensaCobroDTO> listarCobrosDePropiedad(Integer idPropiedad) {
		Criteria criteria = session.createCriteria(ExpensaCobroDTO.class)
			.createAlias("liquidacion", "liquidacion")
			.createAlias("liquidacion.propiedad", "liquidacion.propiedad")
			.add(Restrictions.eq("consolidado", false))
			.add(Restrictions.eq("liquidacion.propiedad.id", idPropiedad));
		return criteria.list();
	}

	public List<ExpensaCobroDTO> getCobroPorIdExpensas(Integer idExpensa)
	{
		Criteria criteria = session.createCriteria(ExpensaCobroDTO.class)
		.createAlias("liquidacion", "liquidacion")
		.add(Restrictions.eq("consolidado", false))
		.add(Restrictions.eq("liquidacion.id", idExpensa)).addOrder(Order.desc("fecha"));
		
		return criteria.list();
	}

	
	public void setSession(Session session) {
		this.session = session;
	}
}
