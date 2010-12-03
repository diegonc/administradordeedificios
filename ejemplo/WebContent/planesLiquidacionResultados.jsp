<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Resultado de liquidacion de planes</h3>
	</div>
	<div class="cuerpo">
		<div>Fecha Liquidacion: <s:property value="fecha"/></div>
		<s:iterator value="planes" var="plan">
			<s:set var="tipoMora" value="edificio.mora"/>
			<div>
				<h4>Detalles del Plan</h4>
				<div style="float: left; margin-right: 2em;">
					<div>Fecha: <s:property value="#plan.fecha"/> </div>
					<div>Tipo:  <s:property value="#plan.tipo" /> </div>
					<div>Responsable: <s:property value="#plan.responsable.nombre"/></div>
					<div>Cantidad cuotas: <s:property value="#plan.cantidadCuotas"/></div>
				</div>
				<div>
					<div>Monto: <s:property value="#plan.monto"/></div>
					<div>Saldo Intereses: <s:property value="#plan.saldoIntereses"/></div>
					<div>Descuento: <s:property value="#plan.montoDescuento"/></div>
					<div>Saldo del Plan: <s:property value="#plan.saldoPlan"/></div>
				</div>	
				<div style="clear: both;"></div>
				<h4>Detalle de cuotas</h4>
				<table class="listado">
					<thead>
						<tr>
							<th>Cuota</th>
							<th>Monto Amortizado</th>
							<th>Interes</th>
							<s:if test="#tipoMora.equals('afecha')">
								<th>SubTotal</th>
								<th>Interes Mora</th>
								<th>Total</th>
							</s:if>
							<s:elseif test="#tipoMora.equals('punitorio')">
								<th>SubTotal</th>
								<th>Interes 1vto</th>
								<th>Interes 2vto</th>
								<th>Total 1vto</th>
								<th>Total 2vto</th>
							</s:elseif>
							<s:else>
								<th>Total</th>
							</s:else>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="#plan.cuotas">
							<tr>
								<td><s:property value="numeroCuota"/></td>
								<td><s:property value="monto"/></td>
								<td><s:property value="intereses"/></td>
								<td><s:property value="monto + intereses"/></td>
								<s:if test="#tipoMora.equals('afecha')">
									<td><s:property value="interesMora"/></td>
									<td><s:property value="monto + intereses + interesMora"/></td>									
								</s:if>
								<s:elseif test="#tipoMora.equals('punitorio')">
									<td><s:property value="interesMora"/></td>
									<td><s:property value="interesSegundoVencimiento"/></td>
									<td><s:property value="monto + intereses + interesMora"/></td>
									<td><s:property value="monto + intereses + interesSegundoVencimiento"/></td>
								</s:elseif>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</s:iterator>
		<div>
			<a href="EdificioListarAction?redi=planes">Volver</a>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
