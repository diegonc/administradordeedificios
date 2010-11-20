package actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import propiedades.PropiedadDTO;
import propiedades.TipoPropiedadDTO;
import utilidades.HibernateUtil;
import beans.PropiedadesBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import edificio.EdificioDTO;
import expensas.appl.ExpensaAppl;
import expensas.appl.ExpensasCobroAppl;
import expensas.dto.ExpensaCobroDTO;
import expensas.dto.ExpensaDTO;

@SuppressWarnings("serial")
public class PlanesPropListarAction extends ActionSupport{
	
	private static final Logger LOG = LoggerFactory.getLogger(PlanesPropListarAction.class);
	private int id;
	private String tipo;
	
	@SuppressWarnings("unused")
	private Map<String,Object> session;
	private String redi;
	
	public String execute() {
		Session hSession = HibernateUtil.getSession();
		PropiedadesBean listaPropiedades = new PropiedadesBean();
		EdificioDTO edificio = (EdificioDTO) hSession.load(
				EdificioDTO.class, id);
		Set<TipoPropiedadDTO> tipos = edificio.getTipoPropiedades();
			
		try {
			if(tipos!=null){
				Iterator<TipoPropiedadDTO>	 iteradorTipos = tipos.iterator();
				ExpensaAppl expAppl = new ExpensaAppl(); 
				SessionFactory factory = HibernateUtil.getSessionFactory();	
				listaPropiedades.setTipo(tipo);
				while (iteradorTipos.hasNext()) {
					TipoPropiedadDTO tipoProp = iteradorTipos.next();
					List<PropiedadDTO> propiedades = tipoProp.getPropiedades();
					for (PropiedadDTO propiedadDTO : propiedades) {
						List<ExpensaDTO> expensas = new ArrayList<ExpensaDTO>();
						if (tipo.equals("ord")) {
							expensas = expAppl.getExpensaActivaPorIdProp(factory, propiedadDTO.getId());
						} else if (tipo.equals("ext")) {
							expensas = expAppl.getExpensaExtActivaPorIdProp(factory, propiedadDTO.getId());
						}
						if (!expensas.isEmpty()) {
							ExpensaDTO exp = expensas.get(0);
							Session session = HibernateUtil.getSession();
							ExpensasCobroAppl expCobrosAppl = new ExpensasCobroAppl(session);
							List<ExpensaCobroDTO> cobros = expCobrosAppl.getCobroPorIdExpensas(exp.getId());
							session.close();
							if (cobros.isEmpty() && exp.getMonto()!=0) {
								listaPropiedades.agregarPropieadad(propiedadDTO);
							}
						}
					}
				}
				if (!listaPropiedades.getPropiedades().isEmpty()) {
					Map<String, Object> session = ActionContext.getContext().getSession();
					session.put("lista",listaPropiedades);
					this.setSession(session);
					return SUCCESS;
				}
			}
		} catch (Exception e) {
			String msg = "No se pueden listar propiedades.";
			LOG.error(msg, e);
			addActionError(msg);
			addActionError("Error las listar las propiedades");
			return ERROR;
		}
		addActionError("No existen propiedades con deudas");
		return ERROR;
	}
	
	public void setSession(Map<String, Object> arg0) {
		this.session=arg0;
	}

	public void setRedi(String redi) {
		this.redi = redi;
	}

	public String getRedi() {
		return redi;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
}
