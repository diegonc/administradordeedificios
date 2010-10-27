<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="edificio.*"%>
<%@ page language="java" contentType="text/html" import="usuarios.dto.*"%>
<jsp:useBean id="lista" scope="session" class="beans.EdificiosBean"/>

<%	ArrayList<EdificioDTO> edificios = lista.getEdificios();	%>

<table cellpadding="0" cellspacing="0">
	<tr>
		<td width="5" class="borde"></td>
		<td width="800" class="borde" align="center">
		<h3 id="header">Expensas</h3>
		</td>
		<td width="5" class="borde"></td>
	</tr>
</table>
<table id="tablaEdificios" height="300" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td width="5" class="borde"></td>
		<td width="15" class="fondo"></td>
		<td width="770" class="fondo" align="left">
		<fieldset><legend>Listado de Edificios - Expensas</legend>
		<table width="500" border="1" class="listado" >
			<tr>
				<td class="listado_par">Nombre</td>
				<td class="listado_par"></td>
				<td class="listado_par"></td>
			</tr>	
		<% for (EdificioDTO edificioDTO : edificios) {  %>		
			<tr>
				<td><%= edificioDTO.getNombre()%></td>
				<td><a href="expensasPropiedadesListado.jsp?id=<%=edificioDTO.getId()%>">Ver Propiedades</a></td>	
				<td><a href="#">Liquidar</a></td>
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
