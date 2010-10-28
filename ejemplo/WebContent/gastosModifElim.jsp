<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="gastos.dto.GastoDTO"%>
<%@page import="gastos.dto.GastoRealDTO"%>
<%@page import="gastos.dto.GastoPrevisionDTO"%>
<%@page import="permisos.AdministradorDePermisos"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="gastos.*"%>
<jsp:useBean id="lista" scope="session" class="beans.GastosBean"/>

<%
	List<GastoRealDTO> gastosReales = lista.getGastosReales();
	List<GastoPrevisionDTO> gastosPrevistos = lista.getGastosPrevistos();
%>

<div class="contenido">
	<div class="titulo"><h3>Gastos</h3></div>
	<div class="cuerpo" align="center">
	<table id="tablaGastosReales" height="300" cellpadding="0" cellspacing="0" border="0" align="center">
		<tr>
			<td width="15" class="fondo"></td>
			<td width="770" class="fondo" align="left">
			<fieldset><legend>Listado de Gastos Reales Pendientes</legend>
			<table width="500" border="1" class="listado" >
				<tr>
					<td class="listado_par">Folio</td>
					<td class="listado_par">Gasto</td>
					<td class="listado_par">Razon&nbsp;Social</td>
					<td class="listado_par">Detalle</td>
					<td class="listado_par">Monto</td>
					<td class="listado_par">&nbsp;</td>
					<td class="listado_par">&nbsp;</td>
					<%if (AdministradorDePermisos.getInstancia().isAdministrador()){ %>
					<td class="listado_par" >&nbsp;</td>
					<%} %>
				</tr>	
			<%for (GastoRealDTO gastoRealDTO : gastosReales) {  %>		
				<tr>
					<td><%= gastoRealDTO.getNumeroFolio()%></td>
					<td><%= gastoRealDTO.getTipoGasto().getDescripcion()%></td>
					<td><%= gastoRealDTO.getRazonSocial()%></td>
					<td><%= gastoRealDTO.getDetalle()%></td>
					<td><%= gastoRealDTO.getMonto()%></td>	
					<td><a href="gastosDeleteReales.jsp?id=<%= gastoRealDTO.getId()%>">Eliminar</a></td>		
					<td><a href="gastosModificarReales.jsp?id=<%= gastoRealDTO.getId()%>">Modificar</a></td>
					<%if (AdministradorDePermisos.getInstancia().isAdministrador()){ %>
					<td><a href="gastosConsolidarReales.jsp?id=<%= gastoRealDTO.getId()%>">Consolidar</a></td>
					<%} %>
				</tr>	
			<%} %>				
			</table> 
	
			</fieldset>	
			<fieldset>
			<legend>Listado de Gastos Previstos Futuros</legend>
			<table width="500" border="1" class="listado" >
				<tr>
					<td class="listado_par">Folio</td>
					<td class="listado_par">Gasto</td>
					<td class="listado_par">Detalle</td>
					<td class="listado_par">Año</td>
					<td class="listado_par">Mes</td>
					<td class="listado_par">Monto</td>
					<td class="listado_par">&nbsp;</td>
					<td class="listado_par">&nbsp;</td>
				</tr>	
			<%for (GastoPrevisionDTO gastoPrevistosDTO : gastosPrevistos) {  %>		
				<tr>
					<td><%= gastoPrevistosDTO.getNumeroFolio()%></td>
					<td><%= gastoPrevistosDTO.getTipoGasto().getDescripcion()%></td>
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
			<a href="EdificioListarAction?redi=gasto">Volver</a>	
			</td>
			<td width="15" class="fondo"></td>		
		</tr>
	</table>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
