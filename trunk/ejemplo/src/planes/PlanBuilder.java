package planes;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import propiedades.Responsable;
import expensas.dto.ExpensaCobroDTO;

public class PlanBuilder {

	private Map<String, Object> propiedades = new HashMap<String, Object>();
	private SistemaAmortizacion sistema;

	public void setFecha(Date fecha) {
		propiedades.put("fecha", fecha);
	}

	public void setCantidadCuotas(int cuotas) {
		propiedades.put("cantidadCuotas", new Integer(cuotas));
	}

	public void setTasa(double tasa) {
		propiedades.put("tasa", new Double(tasa));
	}

	public void setTipo(String tipo) {
		propiedades.put("tipo", tipo);
	}

	public void setResponsable(Responsable responsable) {
		propiedades.put("responsable", responsable);
	}

	public void addExpensaCobro(ExpensaCobroDTO cobro) {
		@SuppressWarnings("unchecked")
		Set<ExpensaCobroDTO> cobros = (Set<ExpensaCobroDTO>) propiedades.get("cobrosCancelados");
		if (cobros == null)
			cobros = new HashSet<ExpensaCobroDTO>();

		cobros.add(cobro);
		propiedades.put("cobrosCancelados", cobros);
	}

	public void setSistema(String sistema) {
		propiedades.put("sistema", sistema);
	}

	public PlanDTO calcularPlan() {
		procesarParametros();

		PlanDTO plan = crearPlan();

		propiedades.clear();
		sistema = null;

		return plan;
	}

	private PlanDTO crearPlan() {

		propiedades.remove("tasa");
		propiedades.remove("sistema");

		PlanDTO plan = new PlanDTO();
		try {
			PropertyUtils.copyProperties(plan, propiedades);
		} catch (Exception e) {
			throw new RuntimeException("No se pudo inicializar el plan.", e);
		}

		for (int i=1; i <= plan.getCantidadCuotas(); i++) {
			CuotaDTO cuota = new CuotaDTO();

			cuota.setNumeroCuota(i);
			cuota.setIntereses(sistema.getInteresCuota(i));
			cuota.setMonto(sistema.getCapitalCuota(i));
			plan.addCuota(cuota);
		}

		plan.setSaldoPlan(sistema.getMonto());
		plan.setSaldoIntereses(sistema.getInteresTotal());

		return plan;
	}

	private void procesarParametros() {
		procesarCobros();
		procesarSistema();

		if (propiedades.get("responsable") == null)
			throw new IllegalArgumentException("No se especificó responsable.");
	}

	@SuppressWarnings("unchecked")
	private void procesarSistema() {
		Double monto = (Double) propiedades.get("monto");
		Double tasa = (Double) propiedades.get("tasa");
		Integer cuotas = (Integer) propiedades.get("cantidadCuotas");

		if (cuotas == null)
		       throw new IllegalArgumentException("No se especificó la cantidad de cuotas.");

		if (tasa == null)
			tasa = ((ExpensaCobroDTO)((Set<ExpensaCobroDTO>) propiedades.get("cobrosCancelados"))
				.iterator().next())
				.getLiquidacion()
				.getPropiedad()
				.getTipoPropiedad()
				.getEdificio()
				.getTasa_anual() / 100. / 12.;
		
		sistema = SistemaAmortizacion.crear((String)propiedades.get("sistema"), monto, tasa, cuotas);
	}

	private void procesarCobros() {
		@SuppressWarnings("unchecked")
		Set<ExpensaCobroDTO> cobros = (Set<ExpensaCobroDTO>) propiedades.get("cobrosCancelados");

		if (cobros != null && !cobros.isEmpty()) {
			String tipo = (String) propiedades.get("tipo");
			double monto = 0;
			Iterator<ExpensaCobroDTO> it = cobros.iterator();
			if (tipo == null) {
				ExpensaCobroDTO expensa = it.next();
				tipo = expensa.getLiquidacion().getTipo();
				monto = expensa.getMontoPago();
				propiedades.put("tipo", tipo);
			}

			while (it.hasNext()) {
				ExpensaCobroDTO expensa = it.next();

				if (!tipo.equals(expensa.getLiquidacion().getTipo()))
					throw new IllegalArgumentException("Los cobros recibidos no son del mismo tipo.");

				monto += expensa.getMontoPago();
			}

			propiedades.put("monto", new Double(monto));
		} else throw new IllegalArgumentException("No hay cobros para cancelar.");
	}

}