package actions;

import java.util.Collection;
import java.util.Date;

import planes.PlanDTO;
import planes.PlanesAppl;
import utilidades.SessionAwareAction;

public class LiquidarPlanesAction extends SessionAwareAction {

	private static final long serialVersionUID = 6264558227602407504L;
	
	private int idEdificio;
	private int[] idPlanes;
	
	private Collection<PlanDTO> planes;
	private PlanesAppl planesAppl = new PlanesAppl();

	private Date fecha;
	
	public String execute() {
		planes = planesAppl.listar();
		return "confirmar";
	}
	
	public String liquidar() {
		getTransaction().begin();
		for(int i=0; i < idPlanes.length; i++) {
			PlanDTO plan = (PlanDTO)session.get(PlanDTO.class, idPlanes[i]) /*TODO: appl */;
			if (plan != null)
				plan.liquidar(fecha);
		}
		getTransaction().commit();
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

	public void setIdPlanes(int[] idPlanes) {
		this.idPlanes = idPlanes;
	}

	public int[] getIdPlanes() {
		return idPlanes;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
