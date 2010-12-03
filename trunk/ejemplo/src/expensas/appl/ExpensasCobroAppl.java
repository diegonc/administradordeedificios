package expensas.appl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import propiedades.PropiedadDTO;
import utilidades.HibernateUtil;
import utilidades.ManejadorDeFechasUtil;
import utilidades.NumberFormat;
import edificio.EdificioDTO;
import expensas.dto.ExpensaCobroDTO;
import expensas.dto.ExpensaDTO;
import gastos.exception.GastoExistenteException;

public class ExpensasCobroAppl {

	private Session session;

	public ExpensasCobroAppl(Session session) {
		this.session = session;
	}

	public ExpensasCobroAppl() {
	}

	public void addCobroExpensas(ExpensaCobroDTO cobro) throws GastoExistenteException{
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try{
        	session.save(cobro);
        }catch(ConstraintViolationException e){
        	throw new GastoExistenteException("Error al cargar el cobro.");
        }
        session.getTransaction().commit();
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

	@SuppressWarnings("unchecked")
	public List<ExpensaCobroDTO> getCobroPorIdExpensas(Integer idExpensa)
	{
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(ExpensaCobroDTO.class)
		.createAlias("liquidacion", "liquidacion")
		.add(Restrictions.eq("liquidacion.id", idExpensa)).addOrder(Order.desc("fecha"));
		
		return criteria.list();
	}

	
	public void setSession(Session session) {
		this.session = session;
	}
	
	private double obtenerCuentaSaldoActual(PropiedadDTO propiedad,String tipoDeExpensa){
		double valorCuenta=0;
		if(tipoDeExpensa.equals(ExpensaDTO.tipoOrdinario)) valorCuenta = propiedad.getCtaOrdSaldoExp(); 
		else if(tipoDeExpensa.equals(ExpensaDTO.tipoExtraordinario)) valorCuenta = propiedad.getCtaExtSaldoExp();
		return valorCuenta;
	}
	
	private double obtenerCuentaInteresesActual(PropiedadDTO propiedad,String tipoDeExpensa){
		double valorCuenta=0;
		if(tipoDeExpensa.equals(ExpensaDTO.tipoOrdinario)) valorCuenta = propiedad.getCtaOrdSaldoInt(); 
		else if(tipoDeExpensa.equals(ExpensaDTO.tipoExtraordinario)) valorCuenta = propiedad.getCtaExtSaldoInt();
		return valorCuenta;
	}
	
	private void actualizarCuentas(PropiedadDTO propiedad,double cuentaSaldoActual,double cuentaInteresesActual,String tipoDeExpensa){
		if(tipoDeExpensa.equals(ExpensaDTO.tipoOrdinario)){
			propiedad.setCtaOrdSaldoExp(NumberFormat.redondeoDouble(cuentaSaldoActual));
			propiedad.setCtaOrdSaldoInt(NumberFormat.redondeoDouble(cuentaInteresesActual));
		} 
		else if(tipoDeExpensa.equals(ExpensaDTO.tipoExtraordinario)){
			propiedad.setCtaExtSaldoExp(NumberFormat.redondeoDouble(cuentaSaldoActual));
			propiedad.setCtaExtSaldoInt(NumberFormat.redondeoDouble(cuentaInteresesActual));
		}
	}
	
	private void actualizarCuentasInteresesDiferidos(ExpensaCobroDTO cobro,EdificioDTO edificio){
		Date fechaVencimiento = cobro.getLiquidacion().getFecha();
		Date fechaCobro = cobro.getFecha();
		
		String tipoDeExpensa = cobro.getLiquidacion().getTipo();
		double interesPorDia = edificio.getTasa_anual()/(36000);
		PropiedadDTO propiedad = cobro.getLiquidacion().getPropiedad();
		double cuentaInteresesActual = obtenerCuentaInteresesActual(propiedad, tipoDeExpensa);
		double cuentaSaldoActual = obtenerCuentaSaldoActual(propiedad,tipoDeExpensa);
		double interesesDeCobro = 0.0;
		long cantidadDeDias = 0;
		
		if(fechaCobro.after(fechaVencimiento)){
			cantidadDeDias = ManejadorDeFechasUtil.getDiferenciaEntreFechas(fechaVencimiento, fechaCobro);
			interesesDeCobro = cuentaSaldoActual*interesPorDia*cantidadDeDias;
		}
		
		cuentaInteresesActual+=interesesDeCobro;
		
		double diferenciaSaldo = cobro.getMontoPago()+cuentaSaldoActual;
		diferenciaSaldo= NumberFormat.redondeoDouble(diferenciaSaldo);
		
		if(diferenciaSaldo>0.0){
			cuentaSaldoActual = 0.0;
			cuentaInteresesActual+=diferenciaSaldo;
			if(cuentaInteresesActual>0){
				cuentaSaldoActual+=cuentaInteresesActual;
				cuentaInteresesActual=0.0;
			}
		}else{
			cuentaSaldoActual+=cobro.getMontoPago();
		}
		
		actualizarCuentas(propiedad, cuentaSaldoActual, cuentaInteresesActual, tipoDeExpensa);
	}
	
	@SuppressWarnings("deprecation")
	private void actualizarCuentasInteresesPunitorios(ExpensaCobroDTO cobro,EdificioDTO edificio){
		Date fechaSegundoVencimiento = cobro.getLiquidacion().getFecha();
		Date fechaPrimerVencimiento = (Date) fechaSegundoVencimiento.clone();
		fechaPrimerVencimiento.setDate(edificio.getDia_primer_vto());
		Date fechaCobro = cobro.getFecha();
		
		String tipoDeExpensa = cobro.getLiquidacion().getTipo();
		double interesPorDia = edificio.getTasa_anual()/(36000);
		PropiedadDTO propiedad = cobro.getLiquidacion().getPropiedad();
		double cuentaInteresesActual = obtenerCuentaInteresesActual(propiedad, tipoDeExpensa);
		double cuentaSaldoActual = obtenerCuentaSaldoActual(propiedad, tipoDeExpensa);
		double interesesDeCobro = 0.0;
		long cantidadDeDias = 0;		
		
		if(fechaCobro.after(fechaSegundoVencimiento)){
			cantidadDeDias = ManejadorDeFechasUtil.getDiferenciaEntreFechas(fechaPrimerVencimiento, fechaCobro);
			interesesDeCobro= cuentaSaldoActual*interesPorDia*cantidadDeDias;
		}
		else if(fechaCobro.after(fechaPrimerVencimiento)){
			interesesDeCobro = (-1)*cobro.getLiquidacion().getInteresSegundoVencimiento();
		}
		cuentaInteresesActual+=interesesDeCobro;
		
		double diferenciaSaldo = cobro.getMontoPago()+cuentaSaldoActual;
		diferenciaSaldo= NumberFormat.redondeoDouble(diferenciaSaldo);
		
		if(diferenciaSaldo>0.0){
			cuentaSaldoActual = 0.0;
			cuentaInteresesActual+=diferenciaSaldo;
			if(cuentaInteresesActual>0){
				cuentaSaldoActual+=cuentaInteresesActual;
				cuentaInteresesActual=0.0;
			}
		}else{
			cuentaSaldoActual+=cobro.getMontoPago();
		}
			
		actualizarCuentas(propiedad, cuentaSaldoActual, cuentaInteresesActual, tipoDeExpensa);
	}
	
	private void actualizarFondoEdificio(EdificioDTO edificio, ExpensaCobroDTO cobro,String tipoDeExpensa){
		if (tipoDeExpensa.equals(ExpensaDTO.tipoOrdinario))
			edificio.setFondo_ordinario(edificio.getFondo_ordinario() + cobro.getMontoPago());
		else if (tipoDeExpensa.equals(ExpensaDTO.tipoExtraordinario))
			edificio.setFondo_extraordinario(edificio.getFondo_extraordinario() + cobro.getMontoPago()); 
	}
	
	public void consolidarCobro(ExpensaCobroDTO cobro){
		PropiedadDTO propiedad = cobro.getLiquidacion().getPropiedad();
		String tipoDeExpensa = cobro.getLiquidacion().getTipo();
		EdificioDTO edificio = propiedad.getTipoPropiedad().getEdificio();
		String tipoDeMora = edificio.getMora();
		double montoPagado =  cobro.getMontoPago();
			
		actualizarFondoEdificio(edificio, cobro, tipoDeExpensa);
		if(tipoDeMora.equals(EdificioDTO.A_FECHA)){				
			montoPagado+= obtenerCuentaInteresesActual(propiedad, tipoDeExpensa);
			montoPagado+=obtenerCuentaSaldoActual(propiedad, tipoDeExpensa);
			actualizarCuentas(propiedad, montoPagado, 0, tipoDeExpensa);
		}
		else if(tipoDeMora.equals(EdificioDTO.DIFERIDO)){				
			actualizarCuentasInteresesDiferidos(cobro, edificio);
		}
		else if(tipoDeMora.equals(EdificioDTO.PUNITORIO)){				
			actualizarCuentasInteresesPunitorios(cobro, edificio);
		}
	}
	
}
