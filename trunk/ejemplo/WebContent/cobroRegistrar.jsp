<%@page import="expensas.appl.*"%>
<%@page import="expensas.dto.*"%>

<%@page import="com.opensymphony.xwork2.ActionContext"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="expensas.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html" import="org.hibernate.*"%>
<%
	int idEdificio = (Integer) ActionContext.getContext().getValueStack().findValue("idEdificio");
	int idProp = (Integer) ActionContext.getContext().getValueStack().findValue("idPropiedad");
	ExpensaAppl expAppl = new ExpensaAppl();
	SessionFactory factory = HibernateUtil.getSessionFactory();	
	List<ExpensaDTO> expensasProp = expAppl.getExpensaActivaPorIdProp(factory,idProp);
	ExpensaDTO expACobrar = expensasProp.get(0); 
%>

<script type="text/javascript">
function validar(thisform) {
	var fechaPago=document.getElementById("fecha");
	var montoPago=document.getElementById("montoPago");
	var comprobante=document.getElementById("comprobante");
	valido=true;
	if(valido==true) {
		if(/^[0-9]{1,2}\/[0-9]{1,2}\/[0-9]{4}$/.test(fechaPago.value)==false ){ 
			alert("El formato de fecha de pago deber ser dd/mm/aaaa");
			valido=false;
		} 
	}
	if (valido==true) {
		if (isNaN(montoPago.value)) {
			alert("El monto pago debe ser un numero");
			valido=false;
		}
	}
	if (valido==true) {
		if (comprobante.value=="") {
			alert("Debe colocar un comprabante");
			valido=false;
		}
	}
	if (valido==true) {
		document.regisCobro.submit();
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
		<form class="elegante" name="regisCobro" id="regisCobro" action="cobroRegistrarAction">
			<fieldset>
		  		<legend>Registrar Cobro</legend>
			 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
			 			<tr><td colspan="10" height="5"></td></tr>
			 			<tr>
			 				<tr><td colspan="5" height="10"></td></tr>
			 				<legend>Información de la Expensa a Cobrar</legend>
			 				<td align="right"><label for="operacion">Operacion: <%=expACobrar.getNumeroOperacion() %></label> </td>
				 			<td align="right">&nbsp;<label for="operacion">Monto: <%=expACobrar.getMonto() %></label> </td>
				 			<td align="right">&nbsp;<label for="edificio">Edificio: <%=expACobrar.getPropiedad().getTipoPropiedad().getEdificio().getNombre() %></label> </td>
				 			<td align="right">&nbsp;<label for="nivel">Nivel: <%=expACobrar.getPropiedad().getNivel() %></label> </td>
				 			<td align="right">&nbsp;<label for="orden">Orden: <%=expACobrar.getPropiedad().getOrden() %></label> </td>
				 		</tr>
				 		<tr><td colspan="25" height="10"></td></tr>
			 			<tr>
			 				<td align="right"><label for="folio">Comprobante:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="comprobante" name="comprobante" size="15"/></td>
				 			<td align="right"><label for="fecha">Fecha Pago:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="fecha" name="fecha" size="15"/></td>
				 			<td align="right"><label for="fecha">Monto Pago:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="montoPago" name="montoPago" size="15"/></td>
				 		</tr>
				  		<tr>
			  			<td colspan="8"><input class="btn" type="button" value="Registrar" onclick="validar()" /></td>
			  			<td> <a
					href="expensasPropiedadesListado.jsp?id=<%=idEdificio%>">Volver</a> </td>
			  			</tr>
			  		</table>		
			</fieldset>
			<input type="text" style="display: none;" id="idPropiedad" name="idPropiedad" value=<%=idProp %> size="15"/>	
			<input type="text" style="display: none;" id="idEdificio" name="idEdificio" value=<%=idEdificio %> size="15"/>		  	
		</form>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
