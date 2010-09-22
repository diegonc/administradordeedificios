package actions;
import gastos.appl.TiposGastosAppl;
import gastos.dto.TipoGastoDTO;
import gastos.dto.TipoGastoEventualDTO;
import gastos.dto.TipoGastoExtraordinarioDTO;
import gastos.dto.TipoGastoMontoFijoDTO;
import gastos.dto.TipoGastoMontoVariableDTO;

import com.opensymphony.xwork2.ActionSupport;


public class TipoDeGastosAction extends ActionSupport {
	
	private String codigo;
	
	private String descripcion;
	
	private String tipoGasto;
	
	private String gastoPlazo;
	
	private String tipoMonto;
	
	private TipoGastoExtraordinarioDTO tgExtraordinario;
	private TipoGastoEventualDTO tgEventual;
	private TipoGastoMontoFijoDTO tgMontoFijo;
	private TipoGastoMontoVariableDTO tgMontoVariable;
	
	private TiposGastosAppl tipoGastoAppl = new TiposGastosAppl();
	
	
	
	public TipoGastoExtraordinarioDTO getTgExtraordinario() {
		return tgExtraordinario;
	}

	public void setTgExtraordinario(TipoGastoExtraordinarioDTO tgExtraordinario) {
		this.tgExtraordinario = tgExtraordinario;
	}

	public TipoGastoEventualDTO getTgEventual() {
		return tgEventual;
	}

	public void setTgEventual(TipoGastoEventualDTO tgEventual) {
		this.tgEventual = tgEventual;
	}

	public TipoGastoMontoFijoDTO getTgMontoFijo() {
		return tgMontoFijo;
	}

	public void setTgMontoFijo(TipoGastoMontoFijoDTO tgMontoFijo) {
		this.tgMontoFijo = tgMontoFijo;
	}

	public TipoGastoMontoVariableDTO getTgMontoVariable() {
		return tgMontoVariable;
	}

	public void setTgMontoVariable(TipoGastoMontoVariableDTO tgMontoVariable) {
		this.tgMontoVariable = tgMontoVariable;
	}

	public String getTipoGasto() {
		return tipoGasto;
	}

	public void setTipoGasto(String tipoGasto) {
		this.tipoGasto = tipoGasto;
	}

	public String getGastoPlazo() {
		return gastoPlazo;
	}

	public void setGastoPlazo(String gastoPlazo) {
		this.gastoPlazo = gastoPlazo;
	}

	public String getTipoMonto() {
		return tipoMonto;
	}

	public void setTipoMonto(String tipoMonto) {
		this.tipoMonto = tipoMonto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	public String execute() {
		TipoGastoDTO tipoGasto = null;
		if (this.getTipoGasto().equals("extraordinario")){
			this.tgExtraordinario.setCodigo(this.codigo);
			this.tgExtraordinario.setDescripcion(this.descripcion);
			tipoGasto=this.tgExtraordinario;
			
		}
		else if (this.getTipoGasto().equals("ordinario")&&this.getGastoPlazo().equals("eventual")){
				this.tgEventual.setCodigo(this.codigo);
				this.tgEventual.setDescripcion(this.descripcion);
				tipoGasto=this.tgEventual;
						
		}
		else if (this.getTipoGasto().equals("ordinario")&&this.getGastoPlazo().equals("periodico")&&this.getTipoMonto().equals("fijo")){
				this.tgMontoFijo.setCodigo(this.codigo);
				this.tgMontoFijo.setDescripcion(this.descripcion);
				tipoGasto=this.tgMontoFijo;
		}
		else if (this.getTipoGasto().equals("ordinario")&&this.getGastoPlazo().equals("periodico")&&this.getTipoMonto().equals("variable")){
			this.tgMontoVariable.setCodigo(this.codigo);
			this.tgMontoVariable.setDescripcion(this.descripcion);
			this.tgMontoVariable.setPeriodo(this.tgMontoFijo.getPeriodo());
			tipoGasto=this.tgMontoVariable;
	}
		
		if (tipoGasto!=null)
		this.tipoGastoAppl.addTipoGasto(tipoGasto);
		
		System.out.println("codigo seguro: "+this.getCodigo());
		return "success";
	}
	

}
