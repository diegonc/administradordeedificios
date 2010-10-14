<%@page import="expensas.*"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="expensas.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html" import="org.hibernate.*"%>
<%
	int idEdificio = Integer.parseInt(request.getParameter("idEdificio"));
%>

<script type="text/javascript">
function validar(thisform) {
	var fechaPago=document.getElementById("fecha");
	valido=true;
	if(valido==true) {
		if(/^[0-9]{1,2}\/[0-9]{1,2}\/[0-9]{4}$/.test(fechaPago.value)==false ){ 
			alert("El formato de fecha de pago deber ser dd/mm/aaaa");
			valido=false;
		} 
	}
	if (valido==true) {
		//document.regisCobro.submit();
	}
}
</script>
<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"><h3 id="header">Registro de Cobros de Expensas</h3></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaRegisCobros" height ="300" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
		<form class="elegante" name="regisCobro" id="regisCobro" action="CobroRegistrarAction">
			<fieldset>
		  		<legend>Registrar Cobro</legend>
			 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
			 			<tr><td colspan="8" height="10"></td></tr>
			 			<tr>
			 				<td align="right"><label for="folio">Comprobante:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="comprobante" name="comprobante" size="15"/></td>
				 			<td align="right"><label for="fecha">Fecha Pago:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="fecha" name="fecha" size="15"/></td>
				 		</tr>
				  		<tr>
			  			<td colspan="8"><input class="btn" type="button" value="Registrar" onclick="validar()" /></td>
			  			<td> <a
					href="expensasPropiedadesListado.jsp?id=<%=idEdificio%>">Volver</a> </td>
			  			</tr>
			  		</table>			  	
			</fieldset>
		</form>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
