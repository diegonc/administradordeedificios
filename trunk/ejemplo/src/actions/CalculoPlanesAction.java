package actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import propiedades.ResponsableAppl;

import utilidades.HibernateUtil;
import beans.LiquidacionBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import expensas.appl.ExpensaAppl;
import expensas.dto.ExpensaDTO;

public class CalculoPlanesAction extends ActionSupport {

	private String elegido;
	private String tipo;
	private Map<String,Object> session;
	private static final long serialVersionUID = 1L;
	
	private List<Integer> responsables;

	public String execute() {
		ExpensaAppl expAppl = new ExpensaAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();	
		ArrayList<ExpensaDTO> listaExpensas = new ArrayList<ExpensaDTO>();
		LiquidacionBean expensas = new LiquidacionBean();
		
		if (elegido == null) {
			return ERROR;
		}
		
		//parceo el string a array de Integer..
		ArrayList<Integer> listaId = new ArrayList<Integer>();
		for (String numero: elegido.split (", "))
			listaId.add (new Integer (numero));
		java.util.Iterator<Integer> iteId = listaId.iterator();
		
		int propId;
		while (iteId.hasNext()) {
			propId = iteId.next();
			if (tipo.equals("ord")) {
				listaExpensas.addAll(expAppl.getExpensaActivaPorIdProp(factory, propId));
			} else {
				listaExpensas.addAll(expAppl.getExpensaExtActivaPorIdProp(factory, propId));
			}
		}
		if (tipo.equals("ord")) {
			expensas.setExpensasOrdinarias(listaExpensas);
		} else {
			expensas.setExpensasExtraordinarias(listaExpensas);
		}
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("lista",expensas);
		this.setSession(session);
		
		responsables = cargarDNIResponsables();
		
		return SUCCESS;	
	}

	private List<Integer> cargarDNIResponsables() {
		Session s = HibernateUtil.getSession();
		try {
			return new ResponsableAppl(s).listarDNIs();
		} finally {
			s.close();
		}
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setElegido(String elegido) {
		this.elegido = elegido;
	}

	public String getElegido() {
		return elegido;
	}

	public void setSession(Map<String,Object> session) {
		this.session = session;
	}

	public Map<String,Object> getSession() {
		return session;
	}

	public List<Integer> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<Integer> responsables) {
		this.responsables = responsables;
	}
}
