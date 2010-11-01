package actions;


import org.hibernate.SessionFactory;

import utilidades.HibernateUtil;

import edificio.EdificioDTO;
import gastos.appl.GastosAppl;
import gastos.dto.GastoPrevisionDTO;
import gastos.dto.TipoGastoDTO;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GastoPrevisionModifAction extends ActionSupport {
	
	private int id_tipo_gasto;
	private String codigo_tipo_gasto;
	private String descripcion_tipo_gasto;
	
	private int edificio_id;
	private int folio;
	private int id;
	private double monto;
	private String detalle;
	private int mes;
	private int anio;
	
	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public void setId_tipo_gasto(int id_tipo_gasto) {
		this.id_tipo_gasto = id_tipo_gasto;
	}

	public int getId_tipo_gasto() {
		return id_tipo_gasto;
	}

	public void setCodigo_tipo_gasto(String codigo_tipo_gasto) {
		this.codigo_tipo_gasto = codigo_tipo_gasto;
	}

	public String getCodigo_tipo_gasto() {
		return codigo_tipo_gasto;
	}

	public void setDescripcion_tipo_gasto(String descripcion_tipo_gasto) {
		this.descripcion_tipo_gasto = descripcion_tipo_gasto;
	}

	public String getDescripcion_tipo_gasto() {
		return descripcion_tipo_gasto;
	}

	private GastoPrevisionDTO hidratar() {
		GastoPrevisionDTO gastoProvi = new GastoPrevisionDTO();
		TipoGastoDTO tipoGasto = new TipoGastoDTO();
		EdificioDTO edificio = new EdificioDTO();
		edificio.setId(edificio_id);
		tipoGasto.setCodigo(codigo_tipo_gasto);
		tipoGasto.setDescripcion(descripcion_tipo_gasto);
		tipoGasto.setId(id_tipo_gasto);
		
		gastoProvi.setDetalle(detalle);
		gastoProvi.setEdificio(edificio);
		gastoProvi.setMonto(monto);
		gastoProvi.setNumeroFolio(folio);
		gastoProvi.setTipoGasto(tipoGasto);
		
		gastoProvi.setId(id);
		gastoProvi.setMes(mes);
		gastoProvi.setAnio(anio);
		
		return gastoProvi;
	}
	
	public String execute(){
		GastoPrevisionDTO gastoProvi = hidratar();
		GastosAppl gastoAppl = new GastosAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try {
			gastoAppl.updateGastoPrevision(factory, gastoProvi);
			return SUCCESS;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			addActionError("No se pudo actualizar el gasto.");
			return "error";
		}

	}

	public void setEdificio_id(int edificio_id) {
		this.edificio_id = edificio_id;
	}

	public int getEdificio_id() {
		return edificio_id;
	}
	
}
