package actions;

import com.opensymphony.xwork2.ActionSupport;

public class EdificioAction extends ActionSupport {
	private int id;
	private String nombre;
	private String tipoPropiedad;
	
	private String calle;
	private String numero;
	private String localidad;
	
	private String encargado_nombre;
	private String encargado_telefono;
	private String encargado_depto;
	private String encargado_piso;
	
	
	private String apto_profesional;
	
	private String fdoOdinario;
	private String fdoextraordinario;
	
	
	private String formaliq_exp;
	
	private String tasa_anual;
	private String amortizacion;
	
	
	
	private String dia_primer_vto;
	private String dia_segundo_vto;
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
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
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
	public String getApto_profesional() {
		return apto_profesional;
	}
	public void setApto_profesional(String aptoProfesional) {
		apto_profesional = aptoProfesional;
	}
	public String getFdoOdinario() {
		return fdoOdinario;
	}
	public void setFdoOdinario(String fdoOdinario) {
		this.fdoOdinario = fdoOdinario;
	}
	public String getFdoextraordinario() {
		return fdoextraordinario;
	}
	public void setFdoextraordinario(String fdoextraordinario) {
		this.fdoextraordinario = fdoextraordinario;
	}
	public String getFormaliq_exp() {
		return formaliq_exp;
	}
	public void setFormaliq_exp(String formaliqExp) {
		formaliq_exp = formaliqExp;
	}
	public String getTasa_anual() {
		return tasa_anual;
	}
	public void setTasa_anual(String tasaAnual) {
		tasa_anual = tasaAnual;
	}
	public String getAmortizacion() {
		return amortizacion;
	}
	public void setAmortizacion(String amortizacion) {
		this.amortizacion = amortizacion;
	}
	public String getDia_primer_vto() {
		return dia_primer_vto;
	}
	public void setDia_primer_vto(String diaPrimerVto) {
		dia_primer_vto = diaPrimerVto;
	}
	public String getDia_segundo_vto() {
		return dia_segundo_vto;
	}
	public void setDia_segundo_vto(String diaSegundoVto) {
		dia_segundo_vto = diaSegundoVto;
	}
	public String excecute(){
		System.out.println(this.getCalle() +""+ this.getNumero());
		return SUCCESS;
		
	}
	

}
