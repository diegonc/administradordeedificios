package actions;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.opensymphony.xwork2.ActionSupport;

import utilidades.HibernateUtil;
import edificio.EdificioAppl;
import edificio.EdificioDTO;

@SuppressWarnings("serial")
public class EdificioModifAction extends ActionSupport {
	private int id;
	private String nombre;
	private String tipoPropiedad;
	private String calle;
	private int numero;
	private String localidad;
	private String encargado_nombre;
	private String encargado_telefono;
	private String encargado_depto;
	private String encargado_piso;
	private Boolean apto_profesional;
	private Double fdoOdinario;
	private Double fdoextraordinario;
	private String formaliq_exp;
	private double tasa_anual;
	private String amortizacion;
	private int dia_primer_vto;
	private Integer dia_segundo_vto;
	private String mora;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoPropiedad() {
		return tipoPropiedad;
	}

	public void setTipoPropiedad(String tipoPropiedad) {
		this.tipoPropiedad = tipoPropiedad;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getEncargado_nombre() {
		return encargado_nombre;
	}

	public void setEncargado_nombre(String encargadoNombre) {
		encargado_nombre = encargadoNombre;
	}

	public String getEncargado_telefono() {
		return encargado_telefono;
	}

	public void setEncargado_telefono(String encargadoTelefono) {
		encargado_telefono = encargadoTelefono;
	}

	public String getEncargado_depto() {
		return encargado_depto;
	}

	public void setEncargado_depto(String encargadoDepto) {
		encargado_depto = encargadoDepto;
	}

	public String getEncargado_piso() {
		return encargado_piso;
	}

	public void setEncargado_piso(String encargadoPiso) {
		encargado_piso = encargadoPiso;
	}

	public Boolean getApto_profesional() {
		return apto_profesional;
	}

	public void setApto_profesional(Boolean aptoProfesional) {
		apto_profesional = aptoProfesional;
	}

	public Double getFdoOdinario() {
		return fdoOdinario;
	}

	public void setFdoOdinario(Double fdoOdinario) {
		this.fdoOdinario = fdoOdinario;
	}

	public Double getFdoextraordinario() {
		return fdoextraordinario;
	}

	public void setFdoextraordinario(Double fdoextraordinario) {
		this.fdoextraordinario = fdoextraordinario;
	}

	public String getFormaliq_exp() {
		return formaliq_exp;
	}

	public void setFormaliq_exp(String formaliqExp) {
		formaliq_exp = formaliqExp;
	}

	public double getTasa_anual() {
		return tasa_anual;
	}

	public void setTasa_anual(double tasaAnual) {
		tasa_anual = tasaAnual;
	}

	public String getAmortizacion() {
		return amortizacion;
	}

	public void setAmortizacion(String amortizacion) {
		this.amortizacion = amortizacion;
	}

	public int getDia_primer_vto() {
		return dia_primer_vto;
	}

	public void setDia_primer_vto(int diaPrimerVto) {
		dia_primer_vto = diaPrimerVto;
	}

	public int getDia_segundo_vto() {
		return dia_segundo_vto;
	}

	public void setDia_segundo_vto(Integer diaSegundoVto) {
		dia_segundo_vto = diaSegundoVto;
	}

	public EdificioDTO cargarEdificioDTO() {
		EdificioDTO edificioToHidratar = new EdificioDTO();
		edificioToHidratar.setId(id);
		edificioToHidratar.setAmortizacion(amortizacion);
		if (apto_profesional == null) {
			edificioToHidratar.setApto_profesional(false);
		} else {
			edificioToHidratar.setApto_profesional(true);
		}
		edificioToHidratar.setCalle(calle);
		edificioToHidratar.setDia_primer_vto(dia_primer_vto);
		if (dia_segundo_vto == null) {
			edificioToHidratar.setDia_segundo_vto(0);
		} else {
			edificioToHidratar.setDia_segundo_vto(dia_segundo_vto);
		}
		edificioToHidratar.setEncargado_depto(encargado_depto);
		edificioToHidratar.setEncargado_piso(encargado_piso);
		edificioToHidratar.setEncargado_telefono(encargado_telefono);
		edificioToHidratar.setEncargado_nombre(encargado_nombre);
		edificioToHidratar.setForma_liq_exp(formaliq_exp);
		edificioToHidratar.setLocalidad(localidad);
		edificioToHidratar.setNombre(nombre);
		edificioToHidratar.setNumero(numero);
		edificioToHidratar.setTasa_anual(tasa_anual);
		edificioToHidratar.setFondo_extraordinario(fdoextraordinario);
		edificioToHidratar.setFondo_ordinario(fdoOdinario);
		edificioToHidratar.setMora(mora);
		return edificioToHidratar;
	}

	public String execute() {
		EdificioAppl edificioAppl = new EdificioAppl();
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try {
			EdificioDTO edificio = cargarEdificioDTO();
			edificioAppl.updateEdifcio(factory.openSession(), edificio);
			return "success";
		} catch (HibernateException he) {
			LOG.error(he.getMessage(), he);
			addActionError("No se pudo actualizar el edificio.");
			return "error";
		}
	}

	public void setMora(String mora) {
		this.mora = mora;
	}

	public String getMora() {
		return mora;
	}
}
