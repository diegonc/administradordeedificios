package gastos.appl;

import gastos.dto.TipoGastoDTO;
import gastos.dto.TipoGastoEventualDTO;
import gastos.dto.TipoGastoExtraordinarioDTO;
import gastos.dto.TipoGastoMontoFijoDTO;
import gastos.dto.TipoGastoMontoVariableDTO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import utilidades.HibernateUtil;

public class TiposGastosAppl {

	public void addTipoGasto(TipoGastoDTO tipoGasto)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(tipoGasto);
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
	}
	
		
	public void updateTipoGasto(TipoGastoDTO nuevoTipoGasto,int idTipoGasto)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        if(nuevoTipoGasto.getTipo().equals(TipoGastoDTO.tipoExtraordinario)){
        	TipoGastoExtraordinarioDTO tipoGastoExtraordinario = (TipoGastoExtraordinarioDTO) session.load(TipoGastoExtraordinarioDTO.class, idTipoGasto);
        	tipoGastoExtraordinario.setDescripcion(nuevoTipoGasto.getDescripcion());
        	session.update(tipoGastoExtraordinario);
        }
        else if(nuevoTipoGasto.getTipo().equals(TipoGastoDTO.tipoEventual)){
        	TipoGastoEventualDTO tipoGastoEventual = (TipoGastoEventualDTO) session.load(TipoGastoEventualDTO.class, idTipoGasto);
        	tipoGastoEventual.setDescripcion(nuevoTipoGasto.getDescripcion());
        	session.update(tipoGastoEventual);
        }
        else if(nuevoTipoGasto.getTipo().equals(TipoGastoDTO.tipoPeriodicoMontoFijo)){
        	TipoGastoMontoFijoDTO tipoGastoMontoFijo = (TipoGastoMontoFijoDTO) session.load(TipoGastoMontoFijoDTO.class, idTipoGasto);
        	tipoGastoMontoFijo.setDescripcion(nuevoTipoGasto.getDescripcion());
        	tipoGastoMontoFijo.setDiaLimite(((TipoGastoMontoFijoDTO)nuevoTipoGasto).getDiaLimite());
        	tipoGastoMontoFijo.setEdificio(((TipoGastoMontoFijoDTO)nuevoTipoGasto).getEdificio());
        	tipoGastoMontoFijo.setMontoActual(((TipoGastoMontoFijoDTO)nuevoTipoGasto).getMontoActual());
        	tipoGastoMontoFijo.setPeriodo(((TipoGastoMontoFijoDTO)nuevoTipoGasto).getPeriodo());
        	session.update(tipoGastoMontoFijo);
        }
        else if(nuevoTipoGasto.getTipo().equals(TipoGastoDTO.tipoPeriodicoMontoVariable)){
        	TipoGastoMontoVariableDTO tipoGastoMontoVariable = (TipoGastoMontoVariableDTO) session.load(TipoGastoMontoVariableDTO.class, idTipoGasto);
        	tipoGastoMontoVariable.setDescripcion(nuevoTipoGasto.getDescripcion());
        	tipoGastoMontoVariable.setProximoVencimiento(((TipoGastoMontoVariableDTO)nuevoTipoGasto).getProximoVencimiento());
        	tipoGastoMontoVariable.setEdificio(((TipoGastoMontoVariableDTO)nuevoTipoGasto).getEdificio());
        	tipoGastoMontoVariable.setMontoPrevision(((TipoGastoMontoVariableDTO)nuevoTipoGasto).getMontoPrevision());
        	tipoGastoMontoVariable.setPeriodo(((TipoGastoMontoVariableDTO)nuevoTipoGasto).getPeriodo());
        	session.update(tipoGastoMontoVariable);
        }

        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
	}
	
	public void removeTipoGasto(int idTipoGasto)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        TipoGastoDTO tipoGasto = (TipoGastoDTO) session.load(TipoGastoDTO.class, idTipoGasto);
        session.delete(tipoGasto);
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
	}
	
	public TipoGastoDTO getTipoGasto(int idTipoGasto)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		TipoGastoDTO tipoGasto = (TipoGastoDTO) session.load(TipoGastoDTO.class, idTipoGasto);
        HibernateUtil.getSessionFactory().close();
        return tipoGasto;
	}
	
	public TipoGastoMontoFijoDTO getTipoGastoMontoFijo(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		TipoGastoMontoFijoDTO tipoGasto = (TipoGastoMontoFijoDTO) session.load(TipoGastoMontoFijoDTO.class, id);
        HibernateUtil.getSessionFactory().close();
        return tipoGasto;
	}
	
	public TipoGastoMontoVariableDTO getTipoGastoMontoVariable(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		TipoGastoMontoVariableDTO tipoGasto = (TipoGastoMontoVariableDTO) session.load(TipoGastoMontoVariableDTO.class, id);
        HibernateUtil.getSessionFactory().close();
        return tipoGasto;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TipoGastoDTO> getAllTipoGasto()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("select tg from TipoGastoDTO tg order by tg.descripcion ");
		HibernateUtil.getSessionFactory().close();
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<TipoGastoDTO> getTiposGastosPorEdificio(int idEdificio) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query query = session
			.createQuery("select tg from TipoGastoDTO tg where tg.id not in " +
					"(select tgPeriodico.id from TipoGastoPeriodicoDTO tgPeriodico " +
					"where tgPeriodico.edificio.id <> :edificio)");	
								
			query.setParameter("edificio", idEdificio); 
			List<TipoGastoDTO> results = query.list();
		
			return results; 
		
		}finally {
			session.close();
		}
	}
	
	
	public static void main(String[] args) {
		
		TiposGastosAppl gastosAppl = new TiposGastosAppl();
		
		TipoGastoMontoFijoDTO tgmf = new TipoGastoMontoFijoDTO();
		
		//TipoGastoMontoVariableDTO tgmv = new TipoGastoMontoVariableDTO();
		
		for(TipoGastoDTO tg: gastosAppl.getTiposGastosPorEdificio(1)){
			System.out.println(tg.getDescripcion());
		};
		
		
		tgmf.setDescripcion("Agua");
		tgmf.setCodigo("AGUA");
		tgmf.setDiaLimite(1);
		tgmf.setMontoActual(76.45);
		tgmf.setPeriodo("3/2010");
				
		//tgmv.setMontoPrevision(234.45);
		//tgmv.setProximoVencimiento(new Date());
		
		
		//gastosAppl.addTipoGasto(tgmf);		
		
		/*gastosAppl.removeTipoGasto(1);
		gastosAppl.removeTipoGasto(2);
		gastosAppl.removeTipoGasto(3);
		gastosAppl.removeTipoGasto(4);
		gastosAppl.removeTipoGasto(8);*/
		
		gastosAppl.getTipoGasto(11);
		
		//TipoGastoDTO tg = gastosAppl.getTipoGasto(6);
		
	}

	public TipoGastoDTO getTipoGastoPorCodigo(String codigo) {
		Session session = HibernateUtil.getSession();
		try {
			TipoGastoDTO tipoGasto = (TipoGastoDTO) session
					.createQuery("from TipoGastoDTO t where t.codigo = :c")
					.setString("c", codigo).list().get(0);
			return tipoGasto;
		} finally {
			session.close();
		}
	}

}
