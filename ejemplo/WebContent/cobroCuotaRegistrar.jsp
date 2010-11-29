<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="planes.*"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html" import="org.hibernate.*"%>
<%
	int idCuota = Integer.parseInt(request.getParameter("idCuota"));
	CuotaAppl cuotaAppl = new CuotaAppl();
	CuotaDTO cuota = cuotaAppl.getCuotaById(idCuota);
%>
<script src="calendario.js" type="text/javascript"></script>
<script type="text/javascript">
function armarFecha(elemento){
	var anio = document.getElementById("anio").value;
	var mes = document.getElementById("mes").value;
	var dia = document.getElementById("dia").value;
	elemento.value=dia+"/"+mes+"/"+anio;
}

function validar(thisform) {
	var fecha=document.getElementById("fecha");
	var comprobante=document.getElementById("comprobante");
	armarFecha(fecha);
	valido=true;
	if (valido==true) {
		if (comprobante.value=="") {
			alert("Debe colocar un comprabante");
			valido=false;
		}
	}
	if(valido==true) {
		if(/^[0-9]{1,2}\/[0-9]{1,2}\/[0-9]{4}$/.test(fecha.value)==false ){ 
			alert("El formato de fecha de pago deber ser dd/mm/aaaa");
			valido=false;
		} 
	}
	if (valido==true) {
		document.regisCobro.submit();
	}
}
</script>
<div class="contenido">
<div class="titulo">
	<h3 id="header" align="center">Registro de Cobros de Cuotas de Planes</h3>
</div>
<div class="cuerpo">
		<form class="elegante" name="regisCuotaCobro" id="regisCuotaCobro" action="cobroCuotaRegistrarAction">
			<fieldset>
		  		<legend>Registrar Cobro Cuota</legend>
			 		<table  border="0" cellpadding="3" cellspacing="3">
			 			<tr><td colspan="10" height="5"></td></tr>
			 			<tr>
			 				<td><label for="nroCuota">Cuota: <%=cuota.getNumeroCuota() %></label> </td>
				 			<td>&nbsp;<label for="monto">Monto Amortizado: <%=cuota.getMonto() %></label> </td>
				 			<td>&nbsp;<label for="interes">Interes: <%=cuota.getIntereses()%></label> </td>
				 			<td>&nbsp;<label for="saldo">Saldo a Pagar: <%=cuota.getMonto()+cuota.getIntereses() %></label> </td>
				 		</tr>
			 			<tr>
			 				<td><label for="compro">Comprobante:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="comprobante" name="comprobante" size="15"/></td>
				 			<td><label for="fechaPago">Fecha de Pago:&nbsp;</label>  </td>
					  		<td><input style="display: none;" type="text" id="fecha" name="fecha" size="15"/>
					  				&nbsp;&nbsp;<input type="text" name="dia" id="dia" maxlength="2" size="2" style="width:22px;" disabled >
									&nbsp;<input type="text" name="mes" id="mes" maxlength="2" size="2"  style="width:22px;" disabled  >
									&nbsp;<input type="text" name="anio" id="anio" maxlength="4" size="4" style="width:32px;" disabled >
									&nbsp;<a href="JavaScript:doNothing()" onclick="allowPrevious=true;setDateField(document.regisCobro.dia,document.regisCobro.mes,document.regisCobro.anio);top.newWin = window.open('calendario.jsp','cal','WIDTH=200,HEIGHT=160,TOP=200,LEFT=300')" onMouseOver="javascript: window.status = 'Abrir calendario'; return true;" onMouseOut="window.status=' '; return true;" ><img src="images/calendario.gif" ></img></a>
				 			</td>
				 		</tr>
			  		</table>		
			</fieldset>
			<input class="btn" type="button" value="Aceptar" onclick="validar()" />
			<a href="planesDetalle.jsp?idPlan=<%=cuota.getPlan().getId()%>">Volver</a>	
		</form>
</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
