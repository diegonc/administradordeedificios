package gastos;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_GASTO")
@Inheritance(strategy=InheritanceType.JOINED)
public class TipoGastoDTO implements Serializable {

	private int id;
	private String codigo;
	private String descripcion;
		
	@Id 
	@GeneratedValue
	@Column(name="ID",unique=true,nullable=false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="CODIGO",nullable=false)
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="DESCRIPCION",nullable=false)
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
			
}
