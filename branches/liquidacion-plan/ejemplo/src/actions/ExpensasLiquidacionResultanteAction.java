package actions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import propiedades.PropiedadDTO;
import propiedades.TipoPropiedadDTO;
import utilidades.HibernateUtil;
import utilidades.Periodo;
import beans.LiquidacionBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edificio.EdificioAppl;
import edificio.EdificioDTO;
import expensas.appl.ExpensaAppl;
import expensas.appl.ExpensaInteresesAppl;
import expensas.appl.liquidacion.ExpensaFijaAppl;
import expensas.appl.liquidacion.ExpensaProrrateoPrevisionAppl;
import expensas.dto.ExpensaDTO;
import gastos.dto.GastoDTO;
import gastos.dto.TipoGastoDTO;

@SuppressWarnings("serial")
public class ExpensasLiquidacionResultanteAction extends ActionSupport {
	

	private int id;
	private int idProp;
	private int mes;
	private int anio;
	@SuppressWarnings("unused")
	private Map<String, Object> session;
	private EdificioAppl edifAppl = new EdificioAppl();
	private ExpensaFijaAppl expensasFijasAppl = new ExpensaFijaAppl();
	private ExpensaProrrateoPrevisionAppl expensasPrevisionAppl = new ExpensaProrrateoPrevisionAppl();
	private String ordinaria;
	private String extraordinaria;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}



	public int getIdProp() {
		return idProp;
	}

	public void setIdProp(int idProp) {
		this.idProp = idProp;
	}
	
	


	public String getOrdinaria() {
		return ordinaria;
	}

	public void setOrdinaria(String ordinaria) {
		this.ordinaria = ordinaria;
	}

	public String registrarReliquidacion(){
		ExpensaAppl expensaAppl = new ExpensaAppl();
		Map<String, Object> session = ActionContext.getContext().getSession();
		LiquidacionBean liquidacion = (LiquidacionBean)session.get("detalleExpensa");
		ExpensaDTO nuevaExpensaOrdinaria = new ExpensaDTO();
		ExpensaDTO nuevaExpensaExtraordinaria = new ExpensaDTO();
		
		ExpensaDTO expensaOrdinaria = null;
		ExpensaDTO expensaExtraOrdinaria = null;
		if (liquidacion.getExpensasOrdinarias()!=null && !liquidacion.getExpensasOrdinarias().isEmpty()){
			expensaOrdinaria = liquidacion.getExpensasOrdinarias().get(0);
			expensaOrdinaria.setNumeroOperacion(expensaAppl.obtenerNumeroDeOperacion(idProp, ExpensaDTO.tipoOrdinario));
			nuevaExpensaOrdinaria=copiarDatosExpensa(expensaOrdinaria);
		}
		if (liquidacion.getExpensasExtraordinarias()!=null && !liquidacion.getExpensasExtraordinarias().isEmpty()){
			expensaExtraOrdinaria = liquidacion.getExpensasExtraordinarias().get(0);
			expensaExtraOrdinaria.setNumeroOperacion(expensaAppl.obtenerNumeroDeOperacion(idProp, ExpensaDTO.tipoExtraordinario));
			nuevaExpensaExtraordinaria = copiarDatosExpensa(expensaExtraOrdinaria);
		}
			
		Session hSession = HibernateUtil.getSession();
		
		if (expensaOrdinaria!=null &&ordinaria!=null && ordinaria.equalsIgnoreCase(ExpensaDTO.tipoOrdinario)){
			hSession.beginTransaction();
			hSession.save(nuevaExpensaOrdinaria);
			hSession.getTransaction().commit();
		}
		
		if (expensaExtraOrdinaria!=null&& extraordinaria!=null && extraordinaria.equalsIgnoreCase(ExpensaDTO.tipoExtraordinario)){
			hSession.beginTransaction();
			hSession.save(nuevaExpensaExtraordinaria);
			hSession.getTransaction().commit();
		}
		hSession.close();
		
		return "registrado";
	}
	
	private ExpensaDTO copiarDatosExpensa(ExpensaDTO expensa) {
		ExpensaDTO nuevaExpensa = new ExpensaDTO();
		nuevaExpensa.setDeudaPrevia(expensa.getDeudaPrevia());
		nuevaExpensa.setFecha(expensa.getFecha());
		nuevaExpensa.setIntereses(expensa.getIntereses());
		nuevaExpensa.setInteresSegundoVencimiento(expensa.getInteresSegundoVencimiento());
		nuevaExpensa.setMonto(expensa.getMonto());
		nuevaExpensa.setNumeroOperacion(expensa.getNumeroOperacion());
		nuevaExpensa.setPropiedad(expensa.getPropiedad());
		nuevaExpensa.setTipo(expensa.getTipo());
		return nuevaExpensa;
	}

	public String getExtraordinaria() {
		return extraordinaria;
	}

	public void setExtraordinaria(String extraordinaria) {
		this.extraordinaria = extraordinaria;
	}

	public String reliquidar(){
		LiquidacionBean liquidacion = new LiquidacionBean();
		Map<String, Object> session = ActionContext.getContext().getSession();
		Session hSession = HibernateUtil.getSession();
		EdificioDTO edificio = (EdificioDTO) hSession.load(	EdificioDTO.class, id);
		Set<TipoPropiedadDTO> tipos = edificio.getTipoPropiedades();
		Iterator<TipoPropiedadDTO> iteradorTipos = tipos.iterator();
		PropiedadDTO prop = new PropiedadDTO();
		
		while (iteradorTipos.hasNext()) {
			List<PropiedadDTO> propiedades = iteradorTipos.next().getPropiedades();
			for (PropiedadDTO propiedadDTO : propiedades) {
				if (propiedadDTO.getId() == idProp) {
					prop = propiedadDTO;		
				}
			}
		}
		
		ExpensaAppl expensaAppl = new ExpensaAppl();
		ExpensaInteresesAppl expensaInteresesAppl = new ExpensaInteresesAppl();

		session.put("edificio",edificio);
		if (prop.getCtaOrdSaldoExp()<0){
			List<ExpensaDTO> expensasOrdinarias = new ArrayList<ExpensaDTO>();
			ExpensaDTO expensaOrdinaria = expensaAppl.obtenerExpensaUltimaLiquidacion(idProp,ExpensaDTO.tipoOrdinario); 
			expensaInteresesAppl .reliquidarConInteresAFechaDePago(edificio, expensaOrdinaria);
			expensasOrdinarias.add(expensaOrdinaria);
			liquidacion.setExpensasOrdinarias(expensasOrdinarias);
		}
		if (prop.getCtaExtSaldoExp()<0){
				ExpensaDTO expensaExtraordinaria = expensaAppl.obtenerExpensaUltimaLiquidacion(idProp,ExpensaDTO.tipoExtraordinario);
				List<ExpensaDTO> expensasExtraordinarias = new ArrayList<ExpensaDTO>();
				expensaInteresesAppl .reliquidarConInteresAFechaDePago(edificio, expensaExtraordinaria);
				expensasExtraordinarias.add(expensaExtraordinaria);
				liquidacion.setExpensasExtraordinarias(expensasExtraordinarias);
		}
	
		session.put("detalleExpensa",liquidacion);	
        this.setSession(session);
		
		return "success1";
	}
	
	public String execute() {
		
		Periodo periodo = new Periodo(mes,anio); 
		ExpensaAppl expensaAppl = new ExpensaAppl();
		
		if (expensaAppl.existeExpensaUltimaLiquidacion(id, mes, anio)){
			addActionError("El periodo ya se encuentra liquidado.");
			return "error";
		}else{
			HashMap<TipoGastoDTO, List<GastoDTO>> gastosLiquidacion = null;
			SessionFactory factory = HibernateUtil.getSessionFactory();
			EdificioDTO edificio = edifAppl.getEdificio(factory, id);
			LiquidacionBean expensaDetalle = new LiquidacionBean();
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("edificio",edificio);
			//Si es fijo no paso los gastos
			if (edificio.getForma_liq_exp().equals("PRORRATEO")){
				gastosLiquidacion = expensasPrevisionAppl.obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(id, periodo, ExpensaDTO.tipoOrdinario);
				expensaDetalle.setGastosOrdinariosDelPeriodo(gastosLiquidacion);		
				expensaDetalle.setExpensasOrdinarias(expensasPrevisionAppl.obtenerExpensasPorTipoPorEdificioYPeriodo(gastosLiquidacion,id, ExpensaDTO.tipoOrdinario));
				
				gastosLiquidacion = expensasPrevisionAppl.obtenerGastosPorEdificioYPeriodoAgrupadoPorTipo(id, periodo, ExpensaDTO.tipoExtraordinario);
				expensaDetalle.setGastosExtraordinariosDelPeriodo(gastosLiquidacion);		
				expensaDetalle.setExpensasExtraordinarias(expensasPrevisionAppl.obtenerExpensasPorTipoPorEdificioYPeriodo(gastosLiquidacion,id, ExpensaDTO.tipoExtraordinario));
			}else{
				expensaDetalle.setExpensasOrdinarias(expensasFijasAppl.obtenerExpensasFijas(id,ExpensaDTO.tipoOrdinario));
				expensaDetalle.setExpensasExtraordinarias(expensasFijasAppl.obtenerExpensasFijas(id,ExpensaDTO.tipoExtraordinario));
				
			}
			
			session.put("detalleExpensa",expensaDetalle);		
	        this.setSession(session);
		}
		return "success";		
		
	}
	public void setSession(Map<String, Object> arg0) {
		this.session=arg0;
	}
}
