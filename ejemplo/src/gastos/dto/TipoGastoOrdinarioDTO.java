package gastos.dto;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_GASTO_ORDINARIO")
@PrimaryKeyJoinColumn(name="TIPO_GASTO_ID")
public class TipoGastoOrdinarioDTO extends TipoGastoDTO {
	
	
	

}
