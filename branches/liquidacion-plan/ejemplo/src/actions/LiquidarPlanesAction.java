package actions;

import java.util.Collection;

import planes.PlanDTO;
import planes.PlanesAppl;

import utilidades.SessionAwareAction;

public class LiquidarPlanesAction extends SessionAwareAction {

	private static final long serialVersionUID = 6264558227602407504L;
	
	private int idEdificio;
	private Collection<PlanDTO> planes;
	private PlanesAppl planesAppl = new PlanesAppl();
	
	public String execute() {
		planes = planesAppl.listar();
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
}
