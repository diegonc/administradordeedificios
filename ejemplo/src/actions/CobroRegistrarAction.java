package actions;

import com.mysql.jdbc.EscapeTokenizer;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;

import permisos.AdministradorDePermisos;
import propiedades.PropiedadAppl;
import propiedades.PropiedadDTO;
import utilidades.HibernateUtil;
import expensas.ExpensaAppl;
import expensas.ExpensaCobroDTO;
import expensas.ExpensaDTO;
import expensas.ExpensasCobroAppl;
import gastos.exception.GastoExistenteException;

@SuppressWarnings("serial")
public class CobroRegistrarAction extends ActionSupport  {
	
	private int id;
	private Date fecha;
	private String comprobante;
	private int expensa_id;
	private boolean consolidado;
	private int responsablecobro_id;
	private int idPropiedad;
	
	public void setIdPropiedad(int idPropiedad) {
		this.idPropiedad = idPropiedad;
	}
	
	public int getIdPropiedad() {
		return this.idPropiedad;
	}
	
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
	public void setExpensa_id(int expensa_id) {
		this.expensa_id = expensa_id;
	}
	public int getExpensa_id() {
		return expensa_id;
	}
	public void setConsolidado(boolean consolidado) {
		this.consolidado = consolidado;
	}
	public boolean isConsolidado() {
		return consolidado;
	}
	public void setResponsablecobro_id(int responsablecobro_id) {
		this.responsablecobro_id = responsablecobro_id;
	}
	public int getResponsablecobro_id() {
		return responsablecobro_id;
	}
	
	public String execute() {
		ExpensaCobroDTO cobro = new ExpensaCobroDTO();
		cobro.setComprobante(comprobante);
		cobro.setConsolidado(false);
		cobro.setFecha(fecha);
		cobro.setResponsableCobro(AdministradorDePermisos.getInstancia().getUsuario());
		
		ExpensaAppl expAppl = new ExpensaAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();	
		List<ExpensaDTO> expensas = expAppl.getExpensaActivaPorIdProp(factory, this.idPropiedad );
		
		//TODO ver si esto va a quedar solo un registro de expensas...
		ExpensaDTO exp = expensas.get(0);

		cobro.setIntereses(exp.getIntereses());
		cobro.setMonto(exp.getMonto());
		cobro.setTipo(exp.getTipo());
		cobro.setPropiedad(exp.getPropiedad());
		
		ExpensasCobroAppl cobroAppl = new ExpensasCobroAppl();
		try {
			cobroAppl.addCobroExpensas(cobro);
		} catch (GastoExistenteException e) {
			System.out.println(e);
			return ERROR;
		}
		return SUCCESS;
	}

}
