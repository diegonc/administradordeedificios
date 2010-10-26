<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>[Edificio: <s:property value="idEdificio"/>] >> Consulta de Gastos</h3>
	</div>
	<div class="cuerpo">
		<s:form action="consultaGastos" method="GET">
			<s:hidden name="idEdificio" />
			<s:radio label="Categoria" name="categoriaElegida" list="categoriasGasto" value="%{categoriaElegida}" />
			<s:textfield label="Año" name="anioPrevision" value="%{anioPrevision}" />
			<s:textfield label="Mes" name="mesPrevision" value="%{mesPrevision}" />	
			<s:select multiple="true" label="Tipo Gasto" name="tipoGastoSeleccionados" list="tipoGastos" />
			<s:submit method="listar" value="Buscar" />
		</s:form>

		<table class="listado">
			<thead>
				<tr>
					<th>Tipo Gasto</th>
					<th>Monto</th>
					<s:if test="categoriaElegida.equals('PREVISION')">
					<th>Año</th>
					<th>Mes</th>
					</s:if>
					<s:else>
					<th>Razon Social</th>
					<th>Nro. Factura</th>
					<th>Fecha</th>
					<th>Estado</th>
					</s:else>
				</tr>
			</thead>
			<tbody>
			<s:if test="resultados != null && resultados.size() > 0">
			<s:iterator value="resultados">
			<tr>
				<td><s:property value="tipoGasto.descripcion" /></td>
				<td><s:property value="monto" /></td>
				<s:if test="categoriaElegida.equals('PREVISION')">
				<td><s:property value="anio" /></td>
				<td><s:property value="mes" /></td>						
				</s:if>
				<s:else>
				<td><s:property value="razonSocial" /></td>
				<td><s:property value="numeroFacturaPago" /></td>
				<td><s:property value="fechaPago" /></td>
				<td><s:property value="estado" /></td>
				</s:else>
			</tr>
			</s:iterator>
			</s:if>
			</tbody>
		</table>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
