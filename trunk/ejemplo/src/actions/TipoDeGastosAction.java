package actions;
import java.util.ArrayList;
import java.util.Map;

import org.hibernate.SessionFactory;

import utilidades.HibernateUtil;
import edificio.EdificioAppl;
import edificio.EdificioDTO;
import gastos.appl.TiposGastosAppl;
import gastos.dto.TipoGastoDTO;
import gastos.dto.TipoGastoEventualDTO;
import gastos.dto.TipoGastoExtraordinarioDTO;
import gastos.dto.TipoGastoMontoFijoDTO;
import gastos.dto.TipoGastoMontoVariableDTO;

import beans.EdificiosBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
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
	
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		
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

	@SuppressWarnings("unchecked")
	public String cargaEdificios()
	{
		EdificiosBean listaEdificios = new EdificiosBean();
		EdificioAppl edifAppl = new EdificioAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		ArrayList<EdificioDTO> listaE = (ArrayList<EdificioDTO>) edifAppl.getAllEdificios(factory);
		listaEdificios.setEdificios(listaE);	
		Map session = ActionContext.getContext().getSession();
	    session.put("edificios",listaEdificios);
	    return "cargaEdificios";
	}
	
	public String actualizar(){
		TipoGastoDTO tipoGasto = null;
		if (this.getTipoGasto().equals(TipoGastoDTO.tipoExtraordinario)){
			this.tgExtraordinario.setTipo(TipoGastoDTO.tipoExtraordinario);
			this.tgExtraordinario.setCodigo(this.codigo);
			this.tgExtraordinario.setDescripcion(this.descripcion);
			tipoGasto=this.tgExtraordinario;
		}
		else if (this.getTipoGasto().equals(TipoGastoDTO.tipoEventual)){
			this.tgEventual.setTipo(TipoGastoDTO.tipoEventual);	
			this.tgEventual.setCodigo(this.codigo);
			this.tgEventual.setDescripcion(this.descripcion);
			tipoGasto=this.tgEventual;
		}
		else if (this.getTipoGasto().equals(TipoGastoDTO.tipoPeriodicoMontoFijo)){
			this.tgMontoFijo.setTipo(TipoGastoDTO.tipoPeriodicoMontoFijo);	
			this.tgMontoFijo.setCodigo(this.codigo);
			this.tgMontoFijo.setDescripcion(this.descripcion);
			tipoGasto=this.tgMontoFijo;
		}
		else if (this.getTipoGasto().equals(TipoGastoDTO.tipoPeriodicoMontoVariable)){
			this.tgMontoVariable.setTipo(TipoGastoDTO.tipoPeriodicoMontoVariable);
			this.tgMontoVariable.setCodigo(this.codigo);
			this.tgMontoVariable.setDescripcion(this.descripcion);
			this.tgMontoVariable.setPeriodo(this.tgMontoVariable.getPeriodo());
			this.tgMontoVariable.setEdificio(this.tgMontoVariable.getEdificio());
			tipoGasto=this.tgMontoVariable;
		}
	
		this.tipoGastoAppl.updateTipoGasto(tipoGasto, id);
		return "actualizacion";
	}
	
	public String execute(){
		
		TipoGastoDTO tipoGasto = null;
		if (this.getTipoGasto().equals("extraordinario")){
			this.tgExtraordinario.setTipo(TipoGastoDTO.tipoExtraordinario);
			this.tgExtraordinario.setCodigo(this.codigo);
			this.tgExtraordinario.setDescripcion(this.descripcion);
			tipoGasto=this.tgExtraordinario;
			
		}
		else if (this.getTipoGasto().equals("ordinario")&&this.getGastoPlazo().equals("eventual")){
			this.tgEventual.setTipo(TipoGastoDTO.tipoEventual);	
			this.tgEventual.setCodigo(this.codigo);
			this.tgEventual.setDescripcion(this.descripcion);
			tipoGasto=this.tgEventual;
						
		}
		else if (this.getTipoGasto().equals("ordinario")&&this.getGastoPlazo().equals("periodico")&&this.getTipoMonto().equals("fijo")){
			this.tgMontoFijo.setTipo(TipoGastoDTO.tipoPeriodicoMontoFijo);	
			this.tgMontoFijo.setCodigo(this.codigo);
			this.tgMontoFijo.setDescripcion(this.descripcion);
			tipoGasto=this.tgMontoFijo;
		}
		else if (this.getTipoGasto().equals("ordinario")&&this.getGastoPlazo().equals("periodico")&&this.getTipoMonto().equals("variable")){
			this.tgMontoVariable.setTipo(TipoGastoDTO.tipoPeriodicoMontoVariable);
			this.tgMontoVariable.setCodigo(this.codigo);
			this.tgMontoVariable.setDescripcion(this.descripcion);
			this.tgMontoVariable.setPeriodo(this.tgMontoFijo.getPeriodo());
			this.tgMontoVariable.setEdificio(this.tgMontoFijo.getEdificio());
			tipoGasto=this.tgMontoVariable;
		}
	
		this.tipoGastoAppl.addTipoGasto(tipoGasto);
		return "success";
	}
	

}
