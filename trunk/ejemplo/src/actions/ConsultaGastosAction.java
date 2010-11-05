package actions;

import edificio.EdificioDTO;
import gastos.appl.TiposGastosAppl;
import gastos.dto.GastoDTO;
import gastos.dto.GastoPrevisionDTO;
import gastos.dto.GastoRealDTO;
import gastos.dto.TipoGastoDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import utilidades.SessionAwareAction;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class ConsultaGastosAction extends SessionAwareAction implements Preparable {

	private static final Logger LOG = LoggerFactory.getLogger(ConsultaGastosAction.class);

	/* Parametros de la accion */
	/*
	 * Categoria de gastos a mostrar.
	 */
	private String categoriaElegida;
	/*
	 * Año utilizado al filtrar gastos de prevision.
	 */
	private Integer anioPrevision;
	/*
	 * Mes utilizado al filtrar gastos de prevision.
	 */
	private Integer mesPrevision;
	/*
	 * Tipos de gastos permitidos.
	 */
	private String[] tipoGastoSeleccionados = {};
	/*
	 * Nombre del edificio con el que se trabaja.
	 */
	private Integer idEdificio;
	private String nombreEdificio;

	/* Atributos soporte */
	
	/* Lista de categorias de gastos. */
	private String[] categoriasGasto = { "PREVISION", "REAL" };

	/* codigo->descripcion de los tipos de gastos disponibles. */
	private Map<String,String> tipoGastos = cargarTiposGastosDisponibles();

	/* Resultados de la busqueda */
	private List<GastoDTO> resultados;

	public void prepare() throws Exception {
		try {
			EdificioDTO edificio = (EdificioDTO) getSession()
				.get(EdificioDTO.class, idEdificio);
			nombreEdificio = edificio.getNombre();
		} catch(Exception e) {
			LOG.warn("Error al obtener el nombre del edificio.", e);
			nombreEdificio = "<<desconocido>>";
			if (e instanceof HibernateException) {
				renewSession();
			}
		}
	}

	private Map<String,String> cargarTiposGastosDisponibles() {
		Map<String,String> mapa = new HashMap<String, String>();
		for (TipoGastoDTO tg : listarTodosTiposGastos()) {
			mapa.put(tg.getCodigo(), tg.getDescripcion());
		}
		return mapa;
	}

	private Collection<TipoGastoDTO> listarTodosTiposGastos() {
		return new TiposGastosAppl().getAllTipoGasto();
	}

	public String input() {
		return SUCCESS;
	}

	private Criteria getCriteria() {
		Session session = getSession();
		@SuppressWarnings("rawtypes")
		Class categoriaGasto = null;

		if (categoriaElegida.equals("PREVISION"))
			categoriaGasto = GastoPrevisionDTO.class;
		else if (categoriaElegida.equals("REAL"))
			categoriaGasto = GastoRealDTO.class;
		else
			throw new IllegalArgumentException("Categoria invalida: " + categoriaElegida);

		Criteria criteria = session.createCriteria(categoriaGasto)
			.createAlias("edificio", "edificio")
			.createAlias("tipoGasto", "tipoGasto")
			.add(Restrictions.eq("edificio.id", idEdificio));

		if (tipoGastoSeleccionados.length > 0)
			criteria.add(Restrictions.in("tipoGasto.codigo", tipoGastoSeleccionados));

		if (categoriaElegida.equals("PREVISION")) {
			if (mesPrevision != null) {
				criteria.add(Restrictions.eq("mes", mesPrevision));
			}
			if (anioPrevision != null) {
				criteria.add(Restrictions.eq("anio", anioPrevision));
			}

		}

		return criteria;
	}

	@SuppressWarnings("unchecked")
	public String listar() {
		try {
			resultados = getCriteria().list();
		} catch (IllegalArgumentException e) {
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	public String getCategoriaElegida() {
		return categoriaElegida;
	}

	@RequiredFieldValidator(message="Seleccione categoria.")
	public void setCategoriaElegida(String categoriaElegida) {
		this.categoriaElegida = categoriaElegida;
	}

	public Integer getAnioPrevision() {
		return anioPrevision;
	}

	@ConversionErrorFieldValidator(message="El valor debe ser numerico.")
	public void setAnioPrevision(Integer anioPrevision) {
		this.anioPrevision = anioPrevision;
	}

	public Integer getMesPrevision() {
		return mesPrevision;
	}

	@ConversionErrorFieldValidator(message="El valor debe ser numerico.")
	public void setMesPrevision(Integer mesPrevision) {
		this.mesPrevision = mesPrevision;
	}

	public String[] getTipoGastoSeleccionados() {
		return tipoGastoSeleccionados;
	}

	public void setTipoGastoSeleccionados(String[] tipoGastoSeleccionados) {
		this.tipoGastoSeleccionados = tipoGastoSeleccionados;
	}

	public String getNombreEdificio() {
		return nombreEdificio;
	}

	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
	}

	public Integer getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Integer idEdificio) {
		this.idEdificio = idEdificio;
	}

	public String[] getCategoriasGasto() {
		return categoriasGasto;
	}

	public void setCategoriasGasto(String[] categoriasGasto) {
		this.categoriasGasto = categoriasGasto;
	}

	public Map<String, String> getTipoGastos() {
		return tipoGastos;
	}

	public void setTipoGastos(Map<String, String> tipoGastos) {
		this.tipoGastos = tipoGastos;
	}

	public List<GastoDTO> getResultados() {
		return resultados;
	}
	public void setResultados(List<GastoDTO> resultados) {
		this.resultados = resultados;
	}
}
