<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Plan de pago</h3>
	</div>
	<div class="cuerpo">
		<div>
			<div><h4>Detalles del Plan</h4></div>
			<div>Fecha: <s:property value="plan.fecha"/> </div>
			<div>Tipo:  <s:property value="plan.tipo" /> </div>
			<div>Responsable: <s:property value="plan.responsable.nombre"/></div>
			<div>Monto: <s:property value="plan.monto"/></div>
			<div>Saldo Intereses: <s:property value="plan.saldoIntereses"/></div>
			<div>Descuento: <s:property value="plan.montoDescuento"/></div>
			<div>Saldo del Plan: <s:property value="plan.saldoPlan"/></div>	
			<div>Cantidad cuotas: <s:property value="plan.cantidadCuotas"/></div>
		</div>
		<div>
			<div><h4>Detalle de expensas a cancelar</h4></div>
			<table class="listado">
				<thead>
					<tr>
						<th>Tipo Propiedad</th>
						<th>Nivel</th>
						<th>Orden</th>
						<th>Fecha</th>
						<th>Monto Total</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="plan.cobrosCancelados">
						<tr>
						<s:push value="liquidacion.propiedad">
							<td><s:property value="tipoPropiedad.nombreTipo"/></td>
							<td><s:property value="nivel"/></td>
							<td><s:property value="orden"/></td>
						</s:push>
							<td><s:property value="liquidacion.fecha"/></td>
							<td><s:property value="montoPago"/></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		<div>
			<div><h4>Detalle de cuotas</h4></div>
			<table class="listado">
				<thead>
					<tr>
						<th>Cuota</th>
						<th>Monto Amortizado</th>
						<th>Interes</th>
						<th>Total</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="plan.cuotas">
						<tr>
							<td><s:property value="numeroCuota"/></td>
							<td><s:property value="monto"/></td>
							<td><s:property value="intereses"/></td>
							<td><s:property value="monto + intereses"/></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		<s:form action="CalculoCuotasAction">
			<s:hidden name="fecha" value="%{plan.fecha}" />
			<s:hidden name="responsableDNI" value="%{plan.responsable.dni}" />
			<s:hidden name="cantCuotas" value="%{plan.cantidadCuotas}" />
			<s:hidden name="descuento" value="%{plan.descuento}"/>
			<s:iterator value="plan.cobrosCancelados">
				<s:hidden name="expElegidas" value="%{liquidacion.id}"/>
			</s:iterator>
			<s:div>
				<s:submit theme="simple" value="Aceptar" name="method:confirmar" />
				<s:submit theme="simple" value="Cancelar" name="method:cancelar" />
			</s:div>
		</s:form>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
