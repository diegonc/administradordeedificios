package expensas;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import utilidades.HibernateUtil;
import gastos.exception.GastoExistenteException;

public class ExpensasCobroAppl {

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
	
	
}
