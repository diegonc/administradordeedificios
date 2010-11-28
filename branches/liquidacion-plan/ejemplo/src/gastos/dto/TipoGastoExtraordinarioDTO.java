package gastos.dto;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_GASTO_EXTRAORDINARIO")
@PrimaryKeyJoinColumn(name="TIPO_GASTO_ID")
public class TipoGastoExtraordinarioDTO extends TipoGastoDTO{

}
