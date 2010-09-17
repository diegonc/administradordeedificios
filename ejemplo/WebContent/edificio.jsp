<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="edificio.*"%>
<jsp:useBean id="lista" scope="session" class="beans.EdificiosBean"/>

<table cellpadding="0" cellspacing="0">
	<tr>
		<td width="5" class="borde"></td>
		<td width="800" class="borde" align="center">
		<h3 id="header">Edificios</h3>
		</td>
		<td width="5" class="borde"></td>
	</tr>
</table>
<table id="tablaGastoGrales" height="300" cellpadding="0"
	cellspacing="0" border="0">
	<tr>
		<td width="5" class="borde"></td>
		<td width="15" class="fondo"></td>
		<td width="770" class="fondo" align="left">
		<form class="elegante" name="altaEdificio" id="altaEdificio"
			action="EdificioAction">
		<fieldset><legend>Listado de Edificios</legend>

		<a href="altaEdificio.jsp">Agregar Edificio</a></fieldset>
		</form>
		</td>
		<td width="15" class="fondo"></td>
		<td width="5" class="borde"></td>
	</tr>
</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
