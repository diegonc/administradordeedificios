package actions;


import org.hibernate.SessionFactory;

import utilidades.HibernateUtil;

import edificio.EdificioAppl;
import edificio.EdificioDTO;
import gastos.appl.GastosAppl;
import gastos.dto.GastoRealDTO;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GastoRealConsolidarAction extends ActionSupport {
	private int id;
	private int idEdificio;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String execute(){
		SessionFactory factory = HibernateUtil.getSessionFactory();
		EdificioAppl edificioAppl = new EdificioAppl();
		EdificioDTO edificio = edificioAppl.getEdificio(factory, idEdificio);
		GastosAppl gastoAppl = new GastosAppl();
		GastoRealDTO gastoReal = gastoAppl.getGastosRealesPendientesPorid(factory, id);
		
		if (gastoReal.getTipoGasto().getTipo().equals("EXT")) {
			if(edificio.getFondo_extraordinario()< gastoReal.getMonto()) {
				addActionError("No hay fondos para afrontar el gasto extraordinario.");
				return ERROR;
			} else {
				edificio.setFondo_extraordinario(edificio.getFondo_extraordinario() - gastoReal.getMonto());
			}
		} else {
			if(edificio.getFondo_ordinario()< gastoReal.getMonto()) {
				addActionError("No hay fondos para afrontar el gasto ordinario.");
				return ERROR;
			} else {
				edificio.setFondo_ordinario(edificio.getFondo_ordinario() - gastoReal.getMonto());
			}
		}
		gastoReal.setEstado("C");
		try {
			gastoAppl.updateGastoReal(factory, gastoReal);
			edificioAppl.updateEdificio(factory, edificio);
			return SUCCESS;
		} catch (Exception e) {
			addActionError("No se ha podido realizar la operación pedida.");
			return ERROR;
		}
	}

	public void setIdEdificio(int idEdificio) {
		this.idEdificio = idEdificio;
	}

	public int getIdEdificio() {
		return idEdificio;
	}
}
