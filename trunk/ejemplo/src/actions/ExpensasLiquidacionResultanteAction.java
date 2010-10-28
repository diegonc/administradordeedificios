package actions;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;
import utilidades.HibernateUtil;
import beans.LiquidacionBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import edificio.EdificioAppl;
import edificio.EdificioDTO;
import expensas.appl.ExpensaAppl;
import expensas.appl.ExpensaCalculoAppl;
import expensas.appl.ExpensaFijaAppl;
import expensas.dto.ExpensaDTO;
import gastos.dto.GastoDTO;
import gastos.dto.TipoGastoDTO;

public class ExpensasLiquidacionResultanteAction extends ActionSupport {
	
	private int id;
	private Map<String, Object> session;
	private EdificioAppl edifAppl = new EdificioAppl();
	private ExpensaFijaAppl expensasFijasAppl = new ExpensaFijaAppl();
	private ExpensaCalculoAppl expensasCalculoAppl = new ExpensaCalculoAppl();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String execute() {
			
		HashMap<TipoGastoDTO, List<GastoDTO>> gastosLiquidacion = null;
		SessionFactory factory = HibernateUtil.getSessionFactory();
		EdificioDTO edificio = edifAppl.getEdificio(factory, id);
		LiquidacionBean expensaDetalle = new LiquidacionBean();
		Map session = ActionContext.getContext().getSession();
		session.put("edificio",edificio);
		//Si es fijo no paso los gastos
		if (edificio.getForma_liq_exp().equals("PRORRATEO")){
			gastosLiquidacion= expensasCalculoAppl.obtenerGastosPorEdificioAgrupadoPorTipo(id);
			expensaDetalle.setGastosDelPeriodo(gastosLiquidacion);		
			
		}else{
			List<ExpensaDTO> expensasFijas = expensasFijasAppl.obtenerExpensasFijas(id);
			expensaDetalle.setExpensas(expensasFijas);
			
		}
		
		session.put("detalleExpensa",expensaDetalle);		
        this.setSession(session);
		return "success";		
		
	}
	public void setSession(Map<String, Object> arg0) {
		this.session=arg0;
	}
}
