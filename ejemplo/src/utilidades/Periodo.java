package utilidades;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Periodo {

	private int mes;
	private int anio;
	
	public Periodo(Date fecha){
		Calendar cal = new GregorianCalendar();
		cal.setTime(fecha);
		this.mes = cal.get(Calendar.MONTH)+1;
		this.anio = cal.get(Calendar.YEAR);
		
	}
	
	public Periodo(){}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}
	
	public Date toDate(){
		return new GregorianCalendar(this.anio, this.mes-1, 1).getTime();
	}
	
	public String obtenerFechaInicio(){
		return this.anio+"-"+this.mes+"-00";
	}
	
	public String obtenerFechaFin(){
		return this.anio+"-"+this.mes+"-31";
	}
	
	public Periodo obtenerPeriodoAnterior(){
		Calendar calendar = new GregorianCalendar(this.anio, this.mes-2, 1);
		return new Periodo(calendar.getTime());
	}
	
	@Override
	public String toString() {
		return this.mes +" - "+this.anio;
	}
}
