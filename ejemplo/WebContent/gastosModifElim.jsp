<%@page import="gastos.dto.GastoDTO"%>
<%@page import="gastos.dto.GastoRealDTO"%>
<%@page import="gastos.dto.GastoPrevisionDTO"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="gastos.*"%>
<jsp:useBean id="lista" scope="session" class="beans.GastosBean"/>

<%
	List<GastoRealDTO> gastosReales = lista.getGastosReales();
	List<GastoPrevisionDTO> gastosPrevistos = lista.getGastosPrevistos();
%>

<table cellpadding="0" cellspacing="0">
	<tr>
		<td width="5" class="borde"></td>
		<td width="800" class="borde" align="center">
		<h3 id="header">Gastos</h3>
		</td>
		<td width="5" class="borde"></td>
	</tr>
</table>
<table id="tablaGastosReales" height="300" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td width="5" class="borde"></td>
		<td width="15" class="fondo"></td>
		<td width="770" class="fondo" align="left">
		<fieldset><legend>Listado de Gastos Reales Pendientes</legend>
		<table width="500" border="1" class="listado" >
			<tr>
				<td>Gasto</td>
				<td>Razon Social</td>
				<td>Detalle</td>
				<td>Monto</td>
				<td></td>
				<td></td>
			</tr>	
		<%for (GastoRealDTO gastoRealDTO : gastosReales) {  %>		
			<tr>
				<td><%= gastoRealDTO.getDetalle()%></td>
				<td><%= gastoRealDTO.getRazonSocial()%></td>
				<td><%= gastoRealDTO.getDetalle()%></td>
				<td><%= gastoRealDTO.getMonto()%></td>	
				<td><a href="gastosDeleteReales.jsp?id=<%= gastoRealDTO.getId()%>">Eliminar</a></td>		
				<td><a href="gastosModificarReales.jsp?id=<%= gastoRealDTO.getId()%>">Modificar</a></td>
			</tr>	
		<%} %>				
		</table> 

		</fieldset>	
		<fieldset>
		<legend>Listado de Gastos Previstos Futuros</legend>
		<table width="500" border="1" class="listado" >
			<tr>
				<td>Gasto</td>
				<td>Detalle</td>
				<td>Anio</td>
				<td>Mes</td>
				<td>Monto</td>
				<td></td>
				<td></td>
			</tr>	
		<%for (GastoPrevisionDTO gastoPrevistosDTO : gastosPrevistos) {  %>		
			<tr>
				<td><%= gastoPrevistosDTO.getDetalle()%></td>
				<td><%= gastoPrevistosDTO.getDetalle()%></td>
				<td><%= gastoPrevistosDTO.getAnio()%></td>
				<td><%= gastoPrevistosDTO.getMes()%></td>
				<td><%= gastoPrevistosDTO.getMonto()%></td>	
				<td><a href="gastosDeletePrevision.jsp?id=<%= gastoPrevistosDTO.getId()%>">Eliminar</a></td>
				<td><a href="gastosModificarPrevision.jsp?id=<%= gastoPrevistosDTO.getId()%>">Modificar</a></td>		
			</tr>	
		<%} %>				
		</table>
		</fieldset>	
		<a href="EdificioGastosListarAction">Volver</a>	
		</td>
		<td width="15" class="fondo"></td>
		<td width="5" class="borde"></td>
	</tr>
</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
