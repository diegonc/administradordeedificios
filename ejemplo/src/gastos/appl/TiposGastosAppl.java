package gastos.appl;

import gastos.dto.TipoGastoDTO;
import gastos.dto.TipoGastoMontoFijoDTO;

import java.util.Date;
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
	
	@SuppressWarnings("unchecked")
	public List<TipoGastoDTO> getAllTipoGasto()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("select tg from TipoGastoDTO tg order by tg.descripcion ");
		HibernateUtil.getSessionFactory().close();
		return q.list();
	}

	/*	public void updateTipoGasto(int idTipoGasto,TipoGastoDTO tipoGastoNuevo)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        TipoGastoDTO tipoGasto = (TipoGastoDTO) session.load(TipoGastoDTO.class, idTipoGasto);
        tipoGasto.setDescripcion(tipoGastoNuevo.getDescripcion());
        session.update(tipoGasto);
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
	}

	public TipoGastoMontoFijoDTO getTipoGastoMontoFijo(int idTipoGasto)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		TipoGastoMontoFijoDTO tgmf = (TipoGastoMontoFijoDTO) session.load(TipoGastoMontoFijoDTO.class, idTipoGasto);
        HibernateUtil.getSessionFactory().close();
        return tgmf;
	}
	*/
	
	public static void main(String[] args) {
		
		TiposGastosAppl gastosAppl = new TiposGastosAppl();
		
		TipoGastoMontoFijoDTO tgmf = new TipoGastoMontoFijoDTO();
		
		//TipoGastoMontoVariableDTO tgmv = new TipoGastoMontoVariableDTO();
		
		tgmf.setDescripcion("Agua");
		tgmf.setCodigo("AGUA");
		tgmf.setDiaLimite(new Date());
		tgmf.setMontoActual(76.45);
		tgmf.setPeriodo("3/2010");
				
		//tgmv.setMontoPrevision(234.45);
		//tgmv.setProximoVencimiento(new Date());
		
		
		//gastosAppl.addTipoGasto(tgmf);		
		
		gastosAppl.removeTipoGasto(1);
		gastosAppl.removeTipoGasto(2);
		gastosAppl.removeTipoGasto(3);
		gastosAppl.removeTipoGasto(4);
		gastosAppl.removeTipoGasto(8);
		
		//TipoGastoDTO tg = gastosAppl.getTipoGasto(6);
		
	}
}
