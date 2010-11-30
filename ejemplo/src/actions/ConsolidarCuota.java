package actions;

import com.opensymphony.xwork2.ActionSupport;

import planes.CuotaAppl;
import planes.CuotaCobroAppl;
import planes.CuotaDTO;
import utilidades.SessionAwareAction;

@SuppressWarnings("serial")
public class ConsolidarCuota  extends ActionSupport {

	private int cuota_id;
	private int idPlan;
	
	public String execute() {
		CuotaCobroAppl cuotaCobroAppl = new CuotaCobroAppl();
		try {
			cuotaCobroAppl.consolidarCobroCuota(cuota_id);
		} catch(Exception e) {
			addActionError("No se pudo consolidar el cobro de la cuota");
			return ERROR;
		}
		CuotaAppl cuotaAppl = new CuotaAppl();
		this.idPlan = cuotaAppl.getCuotaById(cuota_id).getPlan().getId();
		return SUCCESS;
	}

	public void setCuota_id(int cuota_id) {
		this.cuota_id = cuota_id;
	}

	public int getCuota_id() {
		return cuota_id;
	}

	public void setIdPlan(int idPlan) {
		this.idPlan = idPlan;
	}

	public int getIdPlan() {
		return idPlan;
	}
}
