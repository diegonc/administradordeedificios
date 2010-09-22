package actions;

import gastos.dto.GastoPrevisionDTO;
import gastos.dto.GastoRealDTO;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GastosAction extends ActionSupport {
	private String detalle;
	private double monto;
	private GastoRealDTO gastoReal;
	private GastoPrevisionDTO gastoPrevision;
	
	String excecute(){
		return SUCCESS;
	
	}
}
