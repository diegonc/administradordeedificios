<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Tipos de Propiedad</h3>
	</div>
	<div class="cuerpo">
		<s:actionerror />
		<s:form action="tiposPropiedadesListado" method="POST">
			<s:select label="Edificio" 
				headerKey="" headerValue="-- Seleccione un edificio --"
				list="edificios" 
				key="nombreEdificio">
				<s:param name="reloadValue" value="'Actualizar'"/>
				<s:param name="reloadMethod" value="'listar'"/>
			</s:select>
			<s:div>
				<table class="listado" align="center">
					<thead>
						<tr>
							<td class="listado_par">Nombre</td>
							<td class="listado_par">Monto Expensa</td>
							<td class="listado_par">Divisor</td>
							<td class="listado_par" colspan="2">&nbsp;</td>
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
			</s:div>
			<s:submit align="center" name="action:tiposPropiedadesFormulario!crear" value="Agregar"/>
		</s:form>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
