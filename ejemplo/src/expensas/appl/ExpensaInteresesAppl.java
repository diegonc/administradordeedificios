package expensas.appl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.Session;

import propiedades.PropiedadDTO;
import utilidades.HibernateUtil;
import utilidades.ManejadorDeFechasUtil;
import utilidades.NumberFormat;
import edificio.EdificioDTO;
import expensas.dto.ExpensaDTO;

public class ExpensaInteresesAppl {

	
	private Date obtenerFechaUltimaLiquidacion(EdificioDTO edificio){
		Calendar fechaActual = new GregorianCalendar();
		int mes = fechaActual.get(Calendar.MONTH);
		int anio = fechaActual.get(Calendar.YEAR);
		int dia = fechaActual.get(Calendar.DATE);
		
		if(dia<edificio.getDia_primer_vto()){
			mes--;
			if(mes==-1){
				mes=11;
				anio--;
			}
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
			if(mes==12){
				mes=0;
				anio++;
			}
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
		PropiedadDTO propiedad = expensa.getPropiedad();
		double deudaPrevia = obtenerDeudaPreviaSaldo(expensa, propiedad);
		double montoLiquidacion= expensa.getMonto();
		double deudaPreviaControlInteres = 0.0;
		
		if(deudaPrevia>0.0) deudaPreviaControlInteres=deudaPrevia;
		
		Date fechaUltimaLiquidacion = obtenerFechaUltimaLiquidacion(edificio);
		Date fechaPrimerVto = obtenerFechaVencimiento(edificio,true);
		Date fechaSegundoVto = obtenerFechaVencimiento(edificio,false);
		expensa.setFecha(fechaSegundoVto);

		//Primer Vencimiento
		double deudaPreviaIntereses = obtenerDeudaPreviaIntereses(expensa, propiedad);
		long cantidadDiasDeudaPrimerVto = ManejadorDeFechasUtil.getDiferenciaEntreFechas(fechaPrimerVto, fechaUltimaLiquidacion);
		double interesesPrimerVto = deudaPreviaControlInteres*(interesPorDia*cantidadDiasDeudaPrimerVto)+ deudaPreviaIntereses;
		expensa.setDeudaPrevia(NumberFormat.redondeoDouble(deudaPrevia));
		expensa.setIntereses(NumberFormat.redondeoDouble(interesesPrimerVto));
			
		//Segundo Vencimiento		
		long cantidadDiasEntreVtos = ManejadorDeFechasUtil.getDiferenciaEntreFechas(fechaPrimerVto, fechaSegundoVto);
		double interesesTotalesSegundoVto = montoLiquidacion*cantidadDiasEntreVtos*interesPorDia+interesesPrimerVto;
		expensa.setInteresSegundoVencimiento(NumberFormat.redondeoDouble(interesesTotalesSegundoVto));
	}
	
	private double obtenerDeudaPrevia(double cuenta){
		return NumberFormat.redondeoDouble(-cuenta);
	}
	
	public void calcularInteresDiferidoProximaLiquidacion(EdificioDTO edificio,ExpensaDTO expensa){
		double interesPorDia = edificio.getTasa_anual()/(36000);
		PropiedadDTO propiedad = expensa.getPropiedad();
		double deudaPreviaSaldo = obtenerDeudaPreviaSaldo(expensa, propiedad);
		double deudaPreviaIntereses = obtenerDeudaPreviaIntereses(expensa, propiedad);
		double deudaPreviaControlInteres = 0.0;
		
		if(deudaPreviaSaldo>0.0) deudaPreviaControlInteres=deudaPreviaSaldo;
		
		Date fechaUltimaLiquidacion = obtenerFechaUltimaLiquidacion(edificio);
		Date fechaVto = obtenerFechaVencimiento(edificio,true);	
		expensa.setFecha(fechaVto);
				
		long cantidadDiasDeudaPrimerVto = ManejadorDeFechasUtil.getDiferenciaEntreFechas(fechaVto, fechaUltimaLiquidacion);
		double interesesVto = deudaPreviaControlInteres*(interesPorDia*cantidadDiasDeudaPrimerVto) + deudaPreviaIntereses;
		
		expensa.setDeudaPrevia(NumberFormat.redondeoDouble(deudaPreviaSaldo));
		expensa.setIntereses(NumberFormat.redondeoDouble(interesesVto));	
	}

	private double obtenerDeudaPreviaSaldo(ExpensaDTO expensa,PropiedadDTO propiedad){
		double deudaPrevia = 0;
		if (expensa.getTipo().equalsIgnoreCase(ExpensaDTO.tipoOrdinario)){
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaOrdSaldoExp());			
		}else{
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaExtSaldoExp());
		}
		return deudaPrevia;
	}
	
	private double obtenerDeudaPreviaIntereses(ExpensaDTO expensa,PropiedadDTO propiedad){
		double deudaPrevia = 0.0;
		if (expensa.getTipo().equalsIgnoreCase(ExpensaDTO.tipoOrdinario)){
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaOrdSaldoInt());
		}else{
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaExtSaldoInt());
		}
		
		if(deudaPrevia<0.0) return 0.0;		
		return deudaPrevia;
	}
	
	
	public void calcularInteresAFechaDePago(EdificioDTO edificio,ExpensaDTO expensa){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		double interesPorDia = edificio.getTasa_anual()/(36000);
		PropiedadDTO propiedad = expensa.getPropiedad();
		double deudaPreviaSaldo = obtenerDeudaPreviaSaldo(expensa, propiedad);
		double deudaPreviaIntereses = obtenerDeudaPreviaIntereses(expensa, propiedad);
				
		Date fechaUltimaLiquidacion = obtenerFechaUltimaLiquidacion(edificio);
		Date fechaVto = obtenerFechaVencimiento(edificio,true);	
		expensa.setFecha(fechaVto);
		//Primer Vencimiento
		long cantidadDiasDeudaPrimerVto = ManejadorDeFechasUtil.getDiferenciaEntreFechas(fechaVto, fechaUltimaLiquidacion);
		double interesesVto = deudaPreviaSaldo*(interesPorDia*cantidadDiasDeudaPrimerVto)+ deudaPreviaIntereses;
		expensa.setDeudaPrevia(NumberFormat.redondeoDouble(deudaPreviaSaldo));
		expensa.setIntereses(NumberFormat.redondeoDouble(interesesVto));								
	}
		
	public void reliquidarConInteresAFechaDePago(EdificioDTO edificio,ExpensaDTO expensa,Date fechaActual){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		double interesPorDia = edificio.getTasa_anual()/(36000);
		double deudaPrevia = 0.0;		
		PropiedadDTO propiedad = expensa.getPropiedad();
		if (expensa.getTipo().equalsIgnoreCase(ExpensaDTO.tipoOrdinario)){
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaOrdSaldoExp());			
		}else{
			deudaPrevia = obtenerDeudaPrevia(propiedad.getCtaExtSaldoExp());
		}
		
		Date fechaVto = obtenerFechaUltimaLiquidacion(edificio);	
		expensa.setFecha(fechaActual);
		long cantidadDiasDeudaPrimerVto = ManejadorDeFechasUtil.getDiferenciaEntreFechas(fechaVto, fechaActual);
		double interesesVto = deudaPrevia*(interesPorDia*cantidadDiasDeudaPrimerVto);
		expensa.setDeudaPrevia(NumberFormat.redondeoDouble(deudaPrevia));
		expensa.setIntereses(NumberFormat.redondeoDouble(interesesVto));
	}

}
