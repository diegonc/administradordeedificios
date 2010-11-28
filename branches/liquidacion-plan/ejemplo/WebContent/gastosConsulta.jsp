<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Edificio: "<s:property value="nombreEdificio"/>" - Consulta de Gastos</h3>
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

		<s:set name="categoriaElegida" value="categoriaElegida == null ? '' : categoriaElegida"/>	
		<table class="listado">
			<thead>
				<tr>
					<td class="listado_par" >Folio</td>
					<td class="listado_par" >Tipo Gasto</td>
					<td class="listado_par" >Detalle</td>
					<td class="listado_par">Monto</td>
					<s:if test="#categoriaElegida.equals('PREVISION')">
					<td class="listado_par">Año</td>
					<td class="listado_par">Mes</td>
					</s:if>
					<s:else>
					<td class="listado_par">Razon Social</td>
					<td class="listado_par">Nro. Factura</td>
					<td class="listado_par">Fecha</td>
					<td class="listado_par">Estado</td>
					</s:else>
				</tr>
			</thead>
			<tbody>
			<s:if test="resultados != null && resultados.size() > 0">
			<s:iterator value="resultados">
			<tr>
				<td><s:property value="numeroFolio" /></td>
				<td><s:property value="tipoGasto.descripcion" /></td>
				<td><s:property value="detalle" /></td>
				<td><s:property value="monto" /></td>
				<s:if test="#categoriaElegida.equals('PREVISION')">
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
		<a href="EdificioListarAction?redi=gasto">Volver</a>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
