package actions;
import com.opensymphony.xwork2.ActionSupport;


public class TipoDeGastosAction extends ActionSupport {
	
	private String codigo;
	
	private String descripcion;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	public String execute() {
		System.out.println("codigo:"+this.getCodigo());
		return "success";
	}
	

}
