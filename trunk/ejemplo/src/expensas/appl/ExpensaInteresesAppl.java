package expensas.appl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;

import propiedades.PropiedadDTO;
import utilidades.HibernateUtil;
import utilidades.NumberFormat;
import edificio.EdificioDTO;
import expensas.dto.ExpensaCobroDTO;
import expensas.dto.ExpensaDTO;

public class ExpensaInteresesAppl {

	private  long getDiferenciaEntreFechas(Date fecha1, Date fecha2){
		Date fechaMayor = null;
		Date	fechaMenor = null;
		long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
		long cantidadDeDiasResultantes =0;
		if (fecha1.compareTo(fecha2) > 0){
			fechaMayor = fecha1;
			fechaMenor = fecha2;
		}else{
			fechaMayor = fecha2;
			fechaMenor = fecha1;
		}
		cantidadDeDiasResultantes = ( fechaMayor.getTime() - fechaMenor.getTime())/MILLSECS_PER_DAY;
		return cantidadDeDiasResultantes;

	}

	private Date obtenerFechaUltimaLiquidacion(EdificioDTO edificio){
		Calendar fechaActual = new GregorianCalendar();
		int mes = fechaActual.get(Calendar.MONTH);
		int anio = fechaActual.get(Calendar.YEAR);
		int dia = fechaActual.get(Calendar.DATE);
		
		if(dia>=edificio.getDia_primer_vto()){
			mes++;
			if(mes==12) mes=0;
		}
		if (mes==0){
			anio--;
			mes=11;
		}else{
			mes--;
		}
		fechaActual.set(anio,mes, edificio.getDia_primer_vto());

		return fechaActual.getTime();
	}

	private Date obtenerFechaVencimiento(EdificioDTO edificio, boolean esPrimerVto){
		Calendar fechaActual = new GregorianCalendar();
		int mes = fechaActual.get(Calendar.MONTH);
		int anio = fechaActual.get(Calendar.YEAR);
		int dia = fechaActual.get(Calendar.DATE);
		
		if(dia>=edificio.getDia_primer_vto()){
			mes++;
			if(mes==12) mes=0;
		}
		
		if (esPrimerVto){
			fechaActual.set(anio,mes, edificio.getDia_primer_vto());
		}else{
			fechaActual.set(anio,mes, edificio.getDia_segundo_vto());
		}

		return fechaActual.getTime();

	}


	public void calcularInteresPunitorio(EdificioDTO edificio,ExpensaDTO expensa){
		
		double interesPorDia = edificio.getTasa_anual()/(36000);
		double deudaPrevia = 0.0;
		double montoLiquidacion= expensa.getMonto();
		PropiedadDTO propiedad = expensa.getPropiedad();
		if (expensa.getTipo().equalsIgnoreCase("O")){
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaOrdSaldoExp());			
		}else{
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaExtSaldoExp());
		}

		Date fechaUltimaLiquidacion = obtenerFechaUltimaLiquidacion(edificio);
		Date fechaPrimerVto = obtenerFechaVencimiento(edificio,true);
		Date fechaSegundoVto = obtenerFechaVencimiento(edificio,false);
		expensa.setFecha(fechaPrimerVto);

		//Primer Vencimiento
		long cantidadDiasDeudaPrimerVto = getDiferenciaEntreFechas(fechaPrimerVto, fechaUltimaLiquidacion);
		double interesesPrimerVto = deudaPrevia*(interesPorDia*cantidadDiasDeudaPrimerVto);
		expensa.setDeudaPrevia(deudaPrevia);
		interesesPrimerVto = NumberFormat.redondeoDouble(interesesPrimerVto);
		expensa.setIntereses(interesesPrimerVto);
		
		
		//Segundo Vencimiento		
		long cantidadDiasEntreVtos = getDiferenciaEntreFechas(fechaPrimerVto, fechaSegundoVto);
		double interesesDeudaSegundoVto = deudaPrevia*(interesPorDia*(cantidadDiasDeudaPrimerVto +cantidadDiasEntreVtos));
		double interesesTotalesSegundoVto = montoLiquidacion*cantidadDiasEntreVtos*interesPorDia;
		interesesTotalesSegundoVto+=interesesDeudaSegundoVto;
		interesesTotalesSegundoVto = NumberFormat.redondeoDouble(interesesTotalesSegundoVto);
		expensa.setInteresSegundoVencimiento(interesesTotalesSegundoVto);
				
	}
	private double obtenerDeudaPrevia(double cuenta){
		if(cuenta<0.0){
			return - cuenta;
		}else
			return 0.0;
	}
	
	public void calcularInteresDiferidoProximaLiquidacion(EdificioDTO edificio,ExpensaDTO expensa){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		double interesPorDia = edificio.getTasa_anual()/(36000);
		double deudaPrevia = 0.0;		
		PropiedadDTO propiedad = expensa.getPropiedad();
		if (expensa.getTipo().equalsIgnoreCase("O")){
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaOrdSaldoExp());			
		}else{
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaExtSaldoExp());
		}

		Date fechaUltimaLiquidacion = obtenerFechaUltimaLiquidacion(edificio);
		
		if (deudaPrevia>0.0){
			ExpensasCobroAppl cobroAppl = new ExpensasCobroAppl();
			ExpensaAppl expensaAppl = new ExpensaAppl();
			ExpensaDTO ultimaExpensa = expensaAppl.obtenerExpensaUltimaLiquidacion(propiedad.getId(), expensa.getTipo());
			
			if(ultimaExpensa!=null){
				List<ExpensaCobroDTO> cobros = cobroAppl.getCobroPorIdExpensas(ultimaExpensa.getId());
				if (!cobros.isEmpty()){
					ExpensaCobroDTO ultimoCobro = cobros.get(0);
					fechaUltimaLiquidacion = ultimoCobro.getFecha();			
				}
			}
		}
				
		Date fechaPrimerVto = obtenerFechaVencimiento(edificio,true);	
		expensa.setFecha(fechaPrimerVto);

		//Primer Vencimiento
		long cantidadDiasDeudaPrimerVto = getDiferenciaEntreFechas(fechaPrimerVto, fechaUltimaLiquidacion);
		double interesesPrimerVto = deudaPrevia*(interesPorDia*cantidadDiasDeudaPrimerVto);
		expensa.setDeudaPrevia(deudaPrevia);
		interesesPrimerVto = NumberFormat.redondeoDouble(interesesPrimerVto);
		expensa.setIntereses(interesesPrimerVto);	
				
	}


	public void calcularInteresAFechaDePago(EdificioDTO edificio,ExpensaDTO expensa){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		double interesPorDia = edificio.getTasa_anual()/(36000);
		double deudaPrevia = 0.0;		
		PropiedadDTO propiedad = expensa.getPropiedad();
		if (expensa.getTipo().equalsIgnoreCase("O")){
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaOrdSaldoExp());			
		}else{
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaExtSaldoExp());
		}
		
		Date fechaUltimaLiquidacion = obtenerFechaUltimaLiquidacion(edificio);
		ExpensaAppl expensaAppl = new ExpensaAppl();
		ExpensaDTO ultimaExpensa = expensaAppl.obtenerExpensaUltimaLiquidacion(propiedad.getId(), expensa.getTipo());
		
		if(ultimaExpensa!=null){
			if (deudaPrevia>0.0){
				ExpensasCobroAppl cobroAppl = new ExpensasCobroAppl();
				List<ExpensaCobroDTO> cobros = cobroAppl.getCobroPorIdExpensas(ultimaExpensa.getId());
				if (cobros!=null && !cobros.isEmpty()){
					ExpensaCobroDTO ultimoCobro = cobros.get(0);
					fechaUltimaLiquidacion = ultimoCobro.getFecha();
				}
			}
		}
		Date fechaPrimerVto = obtenerFechaVencimiento(edificio,true);	
		expensa.setFecha(fechaPrimerVto);
		//Primer Vencimiento
		long cantidadDiasDeudaPrimerVto = getDiferenciaEntreFechas(fechaPrimerVto, fechaUltimaLiquidacion);
		double interesesPrimerVto = deudaPrevia*(interesPorDia*cantidadDiasDeudaPrimerVto);
		expensa.setDeudaPrevia(deudaPrevia);
		interesesPrimerVto = NumberFormat.redondeoDouble(interesesPrimerVto);
		expensa.setIntereses(interesesPrimerVto);								
	}
	public void reliquidarConInteresAFechaDePago(EdificioDTO edificio,ExpensaDTO expensa){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		double interesPorDia = edificio.getTasa_anual()/(36000);
		double deudaPrevia = 0.0;		
		PropiedadDTO propiedad = expensa.getPropiedad();
		if (expensa.getTipo().equalsIgnoreCase("O")){
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaOrdSaldoExp());			
		}else{
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaExtSaldoExp());
		}
		
		//
		
		ExpensaAppl expensaAppl = new ExpensaAppl();
		ExpensaDTO ultimaExpensa = expensaAppl.obtenerExpensaUltimaLiquidacion(propiedad.getId(), expensa.getTipo());
		Date fechaUltimaLiquidacion =(Date)ultimaExpensa.getFecha();
		if(ultimaExpensa!=null){
			if (deudaPrevia>0.0){
				ExpensasCobroAppl cobroAppl = new ExpensasCobroAppl();
				List<ExpensaCobroDTO> cobros = cobroAppl.getCobroPorIdExpensas(ultimaExpensa.getId());
				if (cobros!=null && !cobros.isEmpty()){
					ExpensaCobroDTO ultimoCobro = cobros.get(0);
					fechaUltimaLiquidacion = ultimoCobro.getFecha();
				}
			}
		
		}
		expensa.setDeudaPrevia(deudaPrevia);
		Calendar fechaActual = new GregorianCalendar();
		Date fechaPrimerVto = fechaActual.getTime();	
		//TODO: ver si no va otra fecha
		expensa.setFecha(fechaPrimerVto);
		//Primer Vencimiento
		if(fechaUltimaLiquidacion.before(fechaPrimerVto)){
			long cantidadDiasDeudaPrimerVto = getDiferenciaEntreFechas(fechaPrimerVto, fechaUltimaLiquidacion);
			double interesesPrimerVto = deudaPrevia*(interesPorDia*cantidadDiasDeudaPrimerVto);
			
			interesesPrimerVto = NumberFormat.redondeoDouble(interesesPrimerVto);
			expensa.setIntereses(interesesPrimerVto);
		}else{
			
			expensa.setIntereses(0);
		}
	}

}
