<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Propiedades</h3>
	</div>
	<div class="cuerpo">
		<s:actionerror />
		<s:form action="propiedadesListado" method="GET">
			<s:select label="Edificio" 
				headerKey="" headerValue="-- Seleccione un edificio --"
				list="listaEdificios" 
				key="nombreEdificio" />
			<s:if test="listaTiposPropiedades != null && listaTiposPropiedades.size() == 0">
				<span>No se han definido tipos de propiedades para este edificio.</span>
			</s:if>
			<s:submit method="listar" value="Actualizar" />
		</s:form>
		<s:if test="(listaPropiedades != null) && (listaPropiedades.size() > 0)">
			<table  class="listado">
			<s:iterator value="listaPropiedades" >
				<tr>
					<td><s:property value="nivel" /></td>
					<td><s:property value="orden" /></td>
					<td><s:property value="tipoPropiedad.nombreTipo" /></td>
					<td><s:property value="propietario.dni" /></td>
					<td>
						<s:property value="inquilino.dni" />
					</td>
					<td>
						<s:property value="poderPropietario.dni" />
					</td>
					<td><s:property value="poderInquilino.dni" /></td>
					<td>
						<s:url id="url" action="propiedadesFormulario!editar" escapeAmp="false">
							<s:param name="nombreEdificio" value="[1].nombreEdificio" />
							<s:param name="nombreTipo" value="tipoPropiedad.nombreTipo" />
							<s:param name="nivel" value="nivel" />
							<s:param name="orden" value="orden" />
						</s:url>
						<a href="<s:property value='#url' />">Editar</a>
					</td>
					<td>
						<s:url id="url" action="propiedadesFormulario!borrar" escapeAmp="false">
							<s:param name="nombreEdificio" value="[1].nombreEdificio" />
							<s:param name="nombreTipo" value="tipoPropiedad.nombreTipo" />
							<s:param name="nivel" value="nivel" />
							<s:param name="orden" value="orden" />
						</s:url>
						<a href="<s:property value='#url' />">Borrar</a>
					</td>
				</tr>
			</s:iterator>
			</table>
		</s:if>
		<s:url id="url" action="propiedadesFormulario!crear">
			<s:param name="nombreEdificio" value="nombreEdificio" />
		</s:url>
		<a href="<s:property value='#url' />">Agregar</a>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
