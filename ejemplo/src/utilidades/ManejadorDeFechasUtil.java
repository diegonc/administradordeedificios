package utilidades;

import java.util.Date;

public class ManejadorDeFechasUtil {

	private final static long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
	
	public static long getDiferenciaEntreFechas(Date fecha1, Date fecha2){
		Date fechaMayor = null;
		Date	fechaMenor = null;
		
		long cantidadDeDiasResultantes =0;
		if (fecha1.compareTo(fecha2) > 0){
			fechaMayor = fecha1;
			fechaMenor = fecha2;
		}else{
			fechaMayor = fecha2;
			fechaMenor = fecha1;
		}
		cantidadDeDiasResultantes = ( fechaMayor.getTime() - fechaMenor.getTime())/MILLSECS_PER_DAY;
		return ++cantidadDeDiasResultantes;

	}
	
	@SuppressWarnings("deprecation")
	public static long diferenciaEntreFechasSinHorario(Date fechaMayor, Date fechaMenor){
		fechaMayor.setHours(0);
		fechaMayor.setMinutes(0);
		fechaMayor.setSeconds(0);
		fechaMenor.setHours(0);
		fechaMenor.setMinutes(0);
		fechaMenor.setSeconds(0);
		long cantidadDeDiasResultantes = ( fechaMayor.getTime() - fechaMenor.getTime())/MILLSECS_PER_DAY;
		return cantidadDeDiasResultantes;
	}

}
