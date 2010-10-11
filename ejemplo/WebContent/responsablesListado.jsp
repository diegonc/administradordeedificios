<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Responsables</h3>
	</div>
	<div class="cuerpo">
		<table class="listado">
			<thead>
				<th>DNI</th>
				<th>Telefono</th>
				<th>Email</th>
				<th>Localidad</th>
				<th>Calle</th>
				<th>Ubicacion</th>
				<th>Autoridad</th>
			</thead>
			<tbody>
				<s:iterator value="lista" var="responsable">
					<tr>
						<td><s:property value="dni" /></td>
						<td><s:property value="telefono" /></td>
						<td><s:property value="email" /></td>
						<td><s:property value="localidad" /></td>
						<td><s:property value="calle" /></td>
						<td><s:property value="ubicacion" /></td>
						<td><s:property value="autoridad" /></td>
						<td>
							<s:url id="url" action="responsablesFormulario!editar">
								<s:param name="dni" value="dni" />
							</s:url>
							<a href="<s:property value='#url' />">Editar</a>
						</td>
						<td>
							<s:url id="url" action="responsablesFormulario!borrar">
								<s:param name="dni" value="dni" />
							</s:url>
							<a href="<s:property value='#url' />">Borrar</a>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<s:url id="url" action="responsablesFormulario!crear" />
		<a href="<s:property value='#url' />">Agregar Responsable</a>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
