<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Tipos de Propiedad</h3>
	</div>
	<div class="cuerpo">
	<s:actionerror />
	<s:form action="tiposPropiedadesListado" method="GET">
		<s:select label="Edificio" 
			headerKey="" headerValue="-- Seleccione un edificio --"
			list="edificios" 
			key="nombreEdificio" />				
		<s:submit method="listar" value="Actualizar" />	
	</s:form>
	<table class="listado">
		<thead>
		<tr>
			<td class="listado_par">Nombre</td>
			<td class="listado_par">Monto Expensa</td>
			<td class="listado_par">Dividor</td>
		</tr>	
		</thead>
		<tbody>
			<s:iterator value="lista">
				<tr>
					<td><s:property value="nombreTipo" /></td>
					<td><s:property value="montoExp" /></td>
					<td><s:property value="divisor" /></td>
					<td class="accion">
						<s:url id="url" action="tiposPropiedadesFormulario!editar" escapeAmp="false">
							<s:param name="nombreEdificio" value="[1].nombreEdificio" />
							<s:param name="nombreTipo" value="nombreTipo" />
						</s:url>
						<a href="<s:property value='#url' />">Modificar</a>
					</td>
					<td class="accion">
						<s:url id="url" action="tiposPropiedadesFormulario!borrar" escapeAmp="false">
							<s:param name="nombreEdificio" value="[1].nombreEdificio" />
							<s:param name="nombreTipo" value="nombreTipo" />
						</s:url>
						<a href="<s:property value='#url' />">Eliminar</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<s:url id="url" action="tiposPropiedadesFormulario!crear">
		<s:param name="nombreEdificio" value="nombreEdificio" />
	</s:url>
	<a href="<s:property value='#url' />">Agregar</a>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
