package actions;

import edificio.EdificioAppl;
import edificio.EdificioDTO;
import gastos.appl.GastosAppl;
import gastos.appl.TiposGastosAppl;
import gastos.dto.GastoDTO;
import gastos.dto.GastoPrevisionDTO;
import gastos.dto.GastoRealDTO;
import gastos.dto.TipoGastoDTO;
import gastos.exception.GastoExistenteException;

import java.util.List;
import java.util.Map;

import utilidades.HibernateUtil;

import beans.EdificiosBean;
import beans.TiposGastosBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GastosAction extends ActionSupport {
	
	private int nroFolio;
	private String detalle;
	private double monto;
	private String tipo;
	private TipoGastoDTO tipoGasto;
	private int idEdificio;
	private GastoRealDTO gastoReal;
	private GastoPrevisionDTO gastoPrevision;
	private GastosAppl gastoAppl = new GastosAppl();
	
	
	public int getNroFolio() {
		return nroFolio;
	}


	public void setNroFolio(int nroFolio) {
		this.nroFolio = nroFolio;
	}


	public int getIdEdificio() {
		return idEdificio;
	}


	public void setIdEdificio(int idEdificio) {
		this.idEdificio = idEdificio;
	}

	public TipoGastoDTO getTipoGasto() {
		return tipoGasto;
	}


	public void setTipoGasto(TipoGastoDTO tipoGasto) {
		this.tipoGasto = tipoGasto;
	}


	public String getDetalle() {
		return detalle;
	}


	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}


	public double getMonto() {
		return monto;
	}


	public void setMonto(double monto) {
		this.monto = monto;
	}


	public GastoRealDTO getGastoReal() {
		return gastoReal;
	}


	public void setGastoReal(GastoRealDTO gastoReal) {
		this.gastoReal = gastoReal;
	}


	public GastoPrevisionDTO getGastoPrevision() {
		return gastoPrevision;
	}


	public void setGastoPrevision(GastoPrevisionDTO gastoPrevision) {
		this.gastoPrevision = gastoPrevision;
	}
	
	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@SuppressWarnings("unchecked")
	public String cargaTiposDeGastos()
	{
		Map session = ActionContext.getContext().getSession();
		TiposGastosBean listaTipoGastos= new TiposGastosBean();
		TiposGastosAppl tipoGastosAppl = new TiposGastosAppl();
		List<TipoGastoDTO> listaG = (List<TipoGastoDTO>) tipoGastosAppl.getTiposGastosPorEdificio(this.idEdificio);
		listaTipoGastos.setTiposGastos(listaG);	
		session.put("tiposGastos",listaTipoGastos);
	    
		EdificiosBean edificioBean = new EdificiosBean();
	    edificioBean.setIdEdificio(this.idEdificio);
		session.put("edificioBean", edificioBean);
	    
	    return "cargaTiposDeGastos";
	}

	public String execute(){
		
		GastoDTO gasto = null;
		
		//TODO no me convence el pasaje de esta forma ya que tengo el edificio
		EdificioAppl edificioAppl = new EdificioAppl();
		EdificioDTO edificio = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(),this.idEdificio);
		
		if (this.getTipo().equals("previsional")){
			this.gastoPrevision.setNumeroFolio(this.nroFolio);
			this.gastoPrevision.setMonto(this.monto);
			this.gastoPrevision.setDetalle(this.detalle);
			this.gastoPrevision.setEdificio(edificio);
			this.gastoPrevision.setTipoGasto(this.tipoGasto);
			gasto=this.gastoPrevision;
		}
		else if (this.getTipo().equals("real")){
			this.gastoReal.setNumeroFolio(this.nroFolio);
			this.gastoReal.setMonto(this.monto);
			this.gastoReal.setDetalle(this.detalle);
			this.gastoReal.setEdificio(edificio);
			this.gastoReal.setTipoGasto(this.tipoGasto);
			gasto=this.gastoReal;
		}
		
		try{
			this.gastoAppl.addGasto(gasto);
		}catch(GastoExistenteException e)
		{
			addActionError(e.getMessage());
			return "error";
		}	
		return "success";
	}
}
