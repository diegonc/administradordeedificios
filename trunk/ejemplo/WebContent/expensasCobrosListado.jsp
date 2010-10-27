<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3></h3>
	</div>

	<div class="cuerpo">
		<table class="listado">
			<thead>
				<th>Nº Op</th>
				<th>Tipo</th>
				<th>Monto</th>
				<th>Intereses</th>
				<th>Fecha</th>
				<th>Comprobante</th>
			</thead>
			<tbody>
				<s:iterator value="cobros">
					<tr>
						<td><s:property value="numeroOperacion"/></td>
						<td><s:property value="tipo"/></td>
						<td><s:property value="monto"/></td>
						<td><s:property value="intereses"/></td>
						<td><s:property value="fecha"/></td>
						<td><s:property value="comprobante"/></td>
						<td>
							<s:url id="url" action="eliminarCobro">
								<s:param name="idCobro" value="id"/>
								<s:param name="idPropiedad" value="idPropiedad"/>
							</s:url>
							<a href="<s:property value='#url'/>">Eliminar</a>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<s:url id="url" action="consultaCobros!back">
			<s:param name="idEdificio" value="idEdificio"/>
		</s:url>
		<p><a href="<s:property value="#url"/>">Volver</a></p>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
