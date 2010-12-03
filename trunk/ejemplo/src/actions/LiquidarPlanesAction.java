package actions;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

import planes.PlanDTO;
import planes.PlanesAppl;
import utilidades.SessionAwareAction;

public class LiquidarPlanesAction extends SessionAwareAction {

	private static final long serialVersionUID = 6264558227602407504L;
	
	private int idEdificio;
	private List<Integer> idPlanes;
	
	private Collection<PlanDTO> planes;
	private PlanesAppl planesAppl = new PlanesAppl();

	private Date fecha;
	
	@SkipValidation
	public String execute() {
		return input();
	}
	
	public String input() {
		planes = planesAppl.listar();
		return "confirmar";
	}
	
	@InputConfig(methodName="input")
	public String liquidar() {
		try {
			getTransaction().begin();
			for(Integer planId : idPlanes) {
				PlanDTO plan = (PlanDTO)session.get(PlanDTO.class, planId) /*TODO: appl */;
				if (plan != null)
					plan.liquidar(fecha);
			}
			getTransaction().commit();
		} catch (Exception e) {
			getTransaction().rollback();
			LOG.error("No se pudo liquidar planes.", e);
		}
		return SUCCESS;
	}

	@Override
	protected void onSetSession() {
		planesAppl.setSession(getSession());
	}

	public Collection<PlanDTO> getPlanes() {
		return planes;
	}

	public void setPlanes(Collection<PlanDTO> planes) {
		this.planes = planes;
	}

	public void setIdEdificio(int idEdificio) {
		this.idEdificio = idEdificio;
	}

	public int getIdEdificio() {
		return idEdificio;
	}

	@RequiredFieldValidator(message="Debe seleccionar algún plan.")
	public void setIdPlanes(List<Integer> idPlanes) {
		this.idPlanes = idPlanes;
	}

	public List<Integer> getIdPlanes() {
		return idPlanes;
	}

	public Date getFecha() {
		return fecha;
	}

	@RequiredFieldValidator(message="Debe seleccionar la fecha de liquidación.")
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
