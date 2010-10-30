<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="permisos.AdministradorDePermisos"%>
<div class="contenido">
	<div class="titulo">
		<h3></h3>
	</div>

	<div class="cuerpo">
		<table class="listado">
			<thead>
				<tr>
					<th>Fecha</th>
					<th>Comprobante</th>
					<th>Monto pago</th>
					<th>Monto a pagar</th>
					<th>Responsable</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="cobros">
					<tr>
						<td><s:property value="fecha"/></td>
						<td><s:property value="comprobante"/></td>
						<td><s:property value="montoPago"/></td>
						<td><s:property value="liquidacion.monto"/></td>
						<td><s:property value="responsableCobro.usuario"/></td>
						<td>
							<s:url id="url" action="eliminarCobro" escapeAmp="false">
								<s:param name="idCobro" value="id"/>
								<s:param name="idPropiedad" value="idPropiedad"/>
							</s:url>
							<a href="<s:property value='#url'/>">Eliminar</a>
						</td>
						<td>
							<s:url id="url" action="consolidarCobro" escapeAmp="false">
								<s:param name="idCobro" value="id"/>
								<s:param name="idPropiedad" value="idPropiedad"/>
							</s:url>
							<% if (AdministradorDePermisos.getInstancia().isAdministrador()) { %>
							<a href="<s:property value='#url'/>">Consolidar</a>
							<% } %>
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
