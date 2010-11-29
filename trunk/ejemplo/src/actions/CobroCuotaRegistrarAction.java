package actions;

import java.util.Date;

import org.hibernate.Session;

import planes.CuotaAppl;
import planes.CuotaCobroAppl;
import planes.CuotaCobroDTO;

import utilidades.HibernateUtil;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CobroCuotaRegistrarAction extends ActionSupport  {
	
	private int id;
	private Date fecha;
	private String comprobante;
	private int cuota_id;
	private boolean consolidado;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}
	public String getComprobante() {
		return comprobante;
	}
	public void setConsolidado(boolean consolidado) {
		this.consolidado = consolidado;
	}
	public boolean isConsolidado() {
		return consolidado;
	}
	public void setCuota_id(int cuota_id) {
		this.cuota_id = cuota_id;
	}
	public int getCuota_id() {
		return cuota_id;
	}
	
	public String execute() {
		try {
			CuotaAppl cuotaAppl = new CuotaAppl();
			CuotaCobroAppl cobroAppl = new CuotaCobroAppl();
			CuotaCobroDTO cobroCuota = new CuotaCobroDTO();
			cobroCuota.setComprobante(comprobante);
			cobroCuota.setConsolidado(false);
			cobroCuota.setCuota(cuotaAppl.getCuotaById(cuota_id));
			cobroCuota.setFecha(fecha);
			cobroAppl.insertCobro(cobroCuota);
		} catch(Exception e) {
			addActionError("No se ha cargado el cobro");
			return "error";
		}
		addActionError("Se cargo el cobro");
		return SUCCESS;	
	}

	

}
