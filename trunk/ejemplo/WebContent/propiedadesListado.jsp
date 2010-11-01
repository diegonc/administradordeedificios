<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Propiedades</h3>
	</div>
	<div class="cuerpo" >
		<s:actionerror />
		<s:form action="propiedadesListado" method="POST">
			<s:select label="Edificio"
				headerKey="" headerValue="-- Seleccione un edificio --"
				list="listaEdificios"
				key="nombreEdificio">
				<s:param name="reloadValue" value="'Actualizar'"/>
				<s:param name="reloadMethod" value="'listar'"/>
			</s:select>
			<s:if test="listaTiposPropiedades != null && listaTiposPropiedades.size() == 0">
				<span>No se han definido tipos de propiedades para este edificio.</span>
			</s:if>
			<s:div>
				<table  class="listado" align="center">
					<thead>
						<tr>
							<td class="listado_par">Nivel</td>
							<td class="listado_par" >Orden</td>
							<td class="listado_par">Nombre</td>
							<td class="listado_par">Propietario</td>
							<td class="listado_par">Inquilino</td>
							<td class="listado_par">Poder Propietario</td>
							<td class="listado_par">Poder Inquilino</td>
						</tr>
					</thead>
					<tbody>
					<s:iterator value="listaPropiedades" >
						<tr>
							<td><s:property value="nivel" /></td>
							<td><s:property value="orden" /></td>
							<td><s:property value="tipoPropiedad.nombreTipo" /></td>
							<td><s:property value="propietario.dni" /></td>
							<td><s:property value="inquilino.dni" /></td>
							<td><s:property value="poderPropietario.dni" /></td>
							<td><s:property value="poderInquilino.dni" /></td>
							<td class="accion">
								<s:url id="url" action="propiedadesFormulario!editar" escapeAmp="false">
									<s:param name="nombreEdificio" value="[1].nombreEdificio" />
									<s:param name="nombreTipo" value="tipoPropiedad.nombreTipo" />
									<s:param name="nivel" value="nivel" />
									<s:param name="orden" value="orden" />
								</s:url>
								<a href="<s:property value='#url' />">Modificar</a>
							</td>
							<td class="accion">
								<s:url id="url" action="propiedadesFormulario!borrar" escapeAmp="false">
									<s:param name="nombreEdificio" value="[1].nombreEdificio" />
									<s:param name="nombreTipo" value="tipoPropiedad.nombreTipo" />
									<s:param name="nivel" value="nivel" />
									<s:param name="orden" value="orden" />
								</s:url>
								<a href="<s:property value='#url' />">Eliminar</a>
							</td>
						</tr>
					</s:iterator>
					</tbody>
				</table>
			</s:div>
			<s:submit align="center" name="action:propiedadesFormulario!crear" value="Agregar"/>
		</s:form>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
