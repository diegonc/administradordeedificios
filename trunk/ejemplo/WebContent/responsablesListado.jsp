<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Responsables</h3>
	</div>
	<div class="cuerpo" align="center">
	<fieldset ><legend>Listado de Responsables</legend>
		<table class="listado" align="center">
			<thead>
			<tr>
				<td class="listado_par">DNI</td>
				<td class="listado_par">Tel&eacute;fono</td>
				<td class="listado_par">Email</td>
				<td class="listado_par">Localidad</td>
				<td class="listado_par">Calle</td>
				<td class="listado_par">Ubicaci&oacute;n</td>
				<td class="listado_par">Aut.</td>
				<td class="listado_par">&nbsp;</td>
				<td class="listado_par">&nbsp;</td>
			</tr>
			</thead>
			<tbody>
				<s:iterator value="lista">
					<tr>
						<td>&nbsp; <s:property value="dni" /></td>
						<td>&nbsp; <s:property value="telefono" /></td>
						<td>&nbsp; <s:property value="email" /></td>
						<td>&nbsp; <s:property value="localidad" /></td>
						<td>&nbsp; <s:property value="calle" /></td>
						<td>&nbsp; <s:property value="ubicacion" /></td>
						<td>&nbsp; <s:property value="autoridad" /></td>
						<td>&nbsp;
							<s:url id="url" action="responsablesFormulario!editar">
								<s:param name="dni" value="dni" />
							</s:url>
							<a href="<s:property value='#url' />">Modificar</a>
						</td>
						<td>&nbsp;
							<s:url id="url" action="responsablesFormulario!borrar">
								<s:param name="dni" value="dni" />
							</s:url>
							<a href="<s:property value='#url' />">Eliminar</a>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		</fieldset>
		<div align="center">
			<s:url id="url" action="responsablesFormulario!crear" />
			<a href="<s:property value='#url' />">Agregar Responsable</a>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
