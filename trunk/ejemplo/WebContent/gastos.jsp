<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="edificio.*"%>
<%@ page language="java" contentType="text/html" import="usuarios.dto.*"%>
<jsp:useBean id="lista" scope="session" class="beans.EdificiosBean"/>

<%
	ArrayList<EdificioDTO> edificios = lista.getEdificios();
	AdministradorDePermisos administra = AdministradorDePermisos.getInstancia();
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
<table id="tablaEdificios" height="300" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td width="5" class="borde"></td>
		<td width="15" class="fondo"></td>
		<td width="770" class="fondo" align="left">
		<fieldset><legend>Listado de Edificios - Gastos</legend>
		<table width="500" border="1" class="listado" >
			<tr>
				<td>Nombre</td>
				<td></td>
				<td></td>
				<%if (administra.visibleTodosLosEdificios()){%><td></td><%} %>
			</tr>	
		<% for (EdificioDTO edificioDTO : edificios) {  %>		
			<tr>
				<td><%= edificioDTO.getNombre()%></td>
				<td><a href="gastosConsulta.jsp?id=<%= edificioDTO.getId()%>">Consultar</a></td>
				<%if (administra.visibleTodosLosEdificios()){%><td><a href="GastosAction!cargaTiposDeGastos?idEdificio=<%= edificioDTO.getId()%>">Agregar</a></td><%} %>	
				<td><a href="GastosListarModifElimAction?id=<%= edificioDTO.getId()%>">Eliminar/Modificar</a></td>		
			</tr>	
		<%} %>				
		
		</table> 
		</fieldset>				
		</td>
		<td width="15" class="fondo"></td>
		<td width="5" class="borde"></td>
	</tr>
</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
