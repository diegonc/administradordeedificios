package actions;


import java.sql.Date;

import org.hibernate.SessionFactory;

import utilidades.HibernateUtil;

import edificio.EdificioDTO;
import gastos.appl.GastosAppl;
import gastos.dto.GastoRealDTO;
import gastos.dto.TipoGastoDTO;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GastoRealModifAction extends ActionSupport {
	
	private int id_tipo_gasto;
	private String codigo_tipo_gasto;
	private String descripcion_tipo_gasto;
	
	private int edificio_id;
	
	private String formaPago;
	private Integer nroFac;
	private Date fechaPago;
	
	private int id;
	private int folio;
	private double monto;
	private String detalle;
	private String razon_social;
	private String estado;
	
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

	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razonSocial) {
		razon_social = razonSocial;
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

	private GastoRealDTO hidratar() {
		GastoRealDTO gastoReal = new GastoRealDTO();
		TipoGastoDTO tipoGasto = new TipoGastoDTO();
		EdificioDTO edificio = new EdificioDTO();
		edificio.setId(edificio_id);
		
		tipoGasto.setCodigo(codigo_tipo_gasto);
		tipoGasto.setDescripcion(descripcion_tipo_gasto);
		tipoGasto.setId(id_tipo_gasto);
		
		gastoReal.setDetalle(detalle);
		
		
		gastoReal.setEdificio(edificio);
		gastoReal.setFechaPago(fechaPago);
		gastoReal.setFormaPago(formaPago);
		gastoReal.setId(id);
		gastoReal.setMonto(monto);
		gastoReal.setNumeroFacturaPago(nroFac);
		gastoReal.setNumeroFolio(folio);
		gastoReal.setRazonSocial(razon_social);
		gastoReal.setTipoGasto(tipoGasto);
		gastoReal.setEstado(estado);
		return gastoReal;
	}
	
	public String execute(){
		GastoRealDTO gastoReal = hidratar();
		GastosAppl gastoAppl = new GastosAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try {
			gastoAppl.updateGastoReal(factory, gastoReal);
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

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setNroFac(Integer nroFac) {
		this.nroFac = nroFac;
	}

	public Integer getNroFac() {
		return nroFac;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Date getFechaPago() {
		return fechaPago;
	}
	
}
