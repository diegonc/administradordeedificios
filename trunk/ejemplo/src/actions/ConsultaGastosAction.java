package actions;

import gastos.appl.TiposGastosAppl;
import gastos.dto.TipoGastoDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ConsultaGastosAction extends ActionSupport implements Preparable {

	/* Parametros de la accion */
	/*
	 * Categoria de gastos a mostrar.
	 */
	private String categoriaElegida = "";
	/*
	 * Año utilizado al filtrar gastos de prevision.
	 */
	private String anioPrevision;
	/*
	 * Mes utilizado al filtrar gastos de prevision.
	 */
	private String mesPrevision;
	/*
	 * Tipos de gastos permitidos.
	 */
	private String[] tipoGastoSeleccionados = {};
	/*
	 * Nombre del edificio con el que se trabaja.
	 */
	private String nombreEdificio;

	/* Atributos soporte */
	
	/* Lista de categorias de gastos. */
	private String[] categoriasGasto = { "PREVISION", "REAL" };

	/* codigo->descripcion de los tipos de gastos disponibles. */
	private Map<String,String> tipoGastos = cargarTiposGastosDisponibles();


	public void prepare() throws Exception {
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

	public String listar() {
		/* TODO: ejecutar busqueda. */
		return SUCCESS;
	}

	public String getCategoriaElegida() {
		return categoriaElegida;
	}

	public void setCategoriaElegida(String categoriaElegida) {
		this.categoriaElegida = categoriaElegida;
	}

	public String getAnioPrevision() {
		return anioPrevision;
	}

	public void setAnioPrevision(String anioPrevision) {
		this.anioPrevision = anioPrevision;
	}

	public String getMesPrevision() {
		return mesPrevision;
	}

	public void setMesPrevision(String mesPrevision) {
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
}
