<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>[Edificio] » Tipos de Propiedad</h3>
	</div>
	<div class="cuerpo">
	<s:if test="lista.size() > 0">
		<table>
		<s:iterator value="lista">
			<tr>
				<td><s:property value="nombreTipo" /></td>
				<td><s:property value="montoExp" /></td>
				<td><s:property value="divisor" /></td>
				<td>
					<s:url id="url" action="tiposPropiedadesFormulario!editar">
						<s:param name="nombreTipo" value="nombreTipo" />
					</s:url>
					<a href="<s:property value='#url' />">Editar</a>
				</td>
			</tr>
		</s:iterator>
		</table>
	</s:if>
	<s:url id="url" action="tiposPropiedadesFormulario!crear" />
	<a href="<s:property value='#url' />">Agregar Responsable</a>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
