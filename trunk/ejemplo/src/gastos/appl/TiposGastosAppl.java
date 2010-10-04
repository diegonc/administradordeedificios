package gastos.appl;

import gastos.dto.TipoGastoDTO;
import gastos.dto.TipoGastoEventualDTO;
import gastos.dto.TipoGastoExtraordinarioDTO;
import gastos.dto.TipoGastoMontoFijoDTO;
import gastos.dto.TipoGastoMontoVariableDTO;
import gastos.exception.TipoGastoExistenteException;
import gastos.exception.TipoGastoInexistenteException;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import utilidades.HibernateUtil;

public class TiposGastosAppl {

	private Session session;
	
	public TiposGastosAppl() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public void addTipoGasto(TipoGastoDTO tipoGasto) throws TipoGastoExistenteException
	{
		session.beginTransaction();
		try{
			session.save(tipoGasto);
		}
		catch(ConstraintViolationException e)
		{
			throw new TipoGastoExistenteException("Ya existe un tipo de Gasto con ese codigo.");
		}
		
        
        session.getTransaction().commit();
    }
	
		
	public void updateTipoGasto(TipoGastoDTO nuevoTipoGasto,int idTipoGasto) throws TipoGastoInexistenteException
	{
	    session.beginTransaction();
        
	    try{
		    if(nuevoTipoGasto.getTipo().equals(TipoGastoDTO.tipoExtraordinario)){
	        	TipoGastoExtraordinarioDTO tipoGastoExtraordinario = getTipoGastoExtraordinario(idTipoGasto);
	        	tipoGastoExtraordinario.setDescripcion(nuevoTipoGasto.getDescripcion());
	        	session.update(tipoGastoExtraordinario);
	        }
	        else if(nuevoTipoGasto.getTipo().equals(TipoGastoDTO.tipoEventual)){
	        	TipoGastoEventualDTO tipoGastoEventual = getTipoGastoEventual(idTipoGasto);
	        	tipoGastoEventual.setDescripcion(nuevoTipoGasto.getDescripcion());
	        	session.update(tipoGastoEventual);
	        }
	        else if(nuevoTipoGasto.getTipo().equals(TipoGastoDTO.tipoPeriodicoMontoFijo)){
	        	TipoGastoMontoFijoDTO tipoGastoMontoFijo = getTipoGastoMontoFijo(idTipoGasto);
	        	tipoGastoMontoFijo.setDescripcion(nuevoTipoGasto.getDescripcion());
	        	tipoGastoMontoFijo.setDiaLimite(((TipoGastoMontoFijoDTO)nuevoTipoGasto).getDiaLimite());
	        	tipoGastoMontoFijo.setEdificio(((TipoGastoMontoFijoDTO)nuevoTipoGasto).getEdificio());
	        	tipoGastoMontoFijo.setMontoActual(((TipoGastoMontoFijoDTO)nuevoTipoGasto).getMontoActual());
	        	tipoGastoMontoFijo.setPeriodo(((TipoGastoMontoFijoDTO)nuevoTipoGasto).getPeriodo());
	        	session.update(tipoGastoMontoFijo);
	        }
	        else if(nuevoTipoGasto.getTipo().equals(TipoGastoDTO.tipoPeriodicoMontoVariable)){
	        	TipoGastoMontoVariableDTO tipoGastoMontoVariable = getTipoGastoMontoVariable(idTipoGasto);
	        	tipoGastoMontoVariable.setDescripcion(nuevoTipoGasto.getDescripcion());
	        	tipoGastoMontoVariable.setProximoVencimiento(((TipoGastoMontoVariableDTO)nuevoTipoGasto).getProximoVencimiento());
	        	tipoGastoMontoVariable.setEdificio(((TipoGastoMontoVariableDTO)nuevoTipoGasto).getEdificio());
	        	tipoGastoMontoVariable.setMontoPrevision(((TipoGastoMontoVariableDTO)nuevoTipoGasto).getMontoPrevision());
	        	tipoGastoMontoVariable.setPeriodo(((TipoGastoMontoVariableDTO)nuevoTipoGasto).getPeriodo());
	        	session.update(tipoGastoMontoVariable);
	        }
	    }
	    catch(TipoGastoInexistenteException e)
	    {
	    	throw new TipoGastoInexistenteException("El tipo de gasto a modificar no existe");
	    }
        session.getTransaction().commit();
    }
	
	public void removeTipoGasto(int idTipoGasto) throws TipoGastoInexistenteException
	{
	    session.beginTransaction();
        TipoGastoDTO tipoGasto = null;
        try{
        	tipoGasto = (TipoGastoDTO) session.load(TipoGastoDTO.class, idTipoGasto);
        	session.delete(tipoGasto);
        }
        catch(ObjectNotFoundException e)
        {
        	throw new TipoGastoInexistenteException("El tipo de Gasto a eliminar no existe.");
        }
        
        session.getTransaction().commit();
    }
	
	public TipoGastoDTO getTipoGasto(int idTipoGasto) throws TipoGastoInexistenteException
	{
		TipoGastoDTO tipoGasto = null;
		
		try{
			tipoGasto =(TipoGastoDTO) session.load(TipoGastoDTO.class, idTipoGasto);
			tipoGasto.getCodigo();
		}
		catch(ObjectNotFoundException e)
		{
			throw new TipoGastoInexistenteException("El tipo de Gasto no existe.");
		}
		
		return tipoGasto; 
        
	}
	
	public TipoGastoMontoFijoDTO getTipoGastoMontoFijo(int id) throws TipoGastoInexistenteException {
		TipoGastoMontoFijoDTO tgmf = null;
		
		try{
			tgmf = (TipoGastoMontoFijoDTO) session.load(TipoGastoMontoFijoDTO.class, id);
			tgmf.getCodigo();
		}catch(ObjectNotFoundException e)
		{
			throw new TipoGastoInexistenteException("El tipo de Gasto no existe.");
		}
		
		return tgmf;
    }
	
	public TipoGastoMontoVariableDTO getTipoGastoMontoVariable(int id) throws TipoGastoInexistenteException {
		TipoGastoMontoVariableDTO tgmv = null;
		
		try{
			tgmv = (TipoGastoMontoVariableDTO) session.load(TipoGastoMontoVariableDTO.class, id);
			tgmv.getCodigo();
		}catch(ObjectNotFoundException e)
		{
			throw new TipoGastoInexistenteException("El tipo de Gasto no existe.");
		}
		
		return tgmv;
	}
	
	private TipoGastoEventualDTO getTipoGastoEventual(int id) throws TipoGastoInexistenteException {
		TipoGastoEventualDTO tge = null;
		
		try{
			tge = (TipoGastoEventualDTO) session.load(TipoGastoEventualDTO.class, id);
			tge.getCodigo();
		}catch(ObjectNotFoundException e)
		{
			throw new TipoGastoInexistenteException("El tipo de Gasto no existe.");
		}
		
		return tge;
    }
	
	private TipoGastoExtraordinarioDTO getTipoGastoExtraordinario(int id) throws TipoGastoInexistenteException {
		TipoGastoExtraordinarioDTO tge = null;
		
		try{
			tge = (TipoGastoExtraordinarioDTO) session.load(TipoGastoExtraordinarioDTO.class, id);
			tge.getCodigo();
		}catch(ObjectNotFoundException e)
		{
			throw new TipoGastoInexistenteException("El tipo de Gasto no existe.");
		}
		
		return tge;
    }
	
	public TipoGastoDTO getTipoGastoPorCodigo(String codigo) throws TipoGastoInexistenteException {

		TipoGastoDTO tipoGasto = null;
		Query q = session.createQuery("from TipoGastoDTO t where t.codigo = :c");
		q.setParameter("c", codigo);
		try{
			tipoGasto = (TipoGastoDTO) q.uniqueResult();
			if (tipoGasto==null) throw new ObjectNotFoundException(tipoGasto, codigo);
		}
		catch (ObjectNotFoundException e)
		{
			throw new TipoGastoInexistenteException("El tipo de Gasto no existe.");
		}
		return tipoGasto;
	}

	@SuppressWarnings("unchecked")
	public List<TipoGastoDTO> getAllTipoGasto()
	{
		Query q = session.createQuery("select tg from TipoGastoDTO tg order by tg.descripcion ");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<TipoGastoDTO> getTiposGastosPorEdificio(int idEdificio) {
		
		Query query = session.createQuery("select tg from TipoGastoDTO tg where tg.id not in " +
					"(select tgPeriodico.id from TipoGastoPeriodicoDTO tgPeriodico " +
					"where tgPeriodico.edificio.id <> :edificio)");	
								
		query.setParameter("edificio", idEdificio); 
				
		return  query.list(); 
	}
		
}
