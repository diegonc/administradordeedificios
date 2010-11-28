<%@ taglib prefix="s" uri="/struts-tags" %>
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
	int tip = (Integer) ActionContext.getContext().getValueStack().findValue("tipoACobrar");
	ExpensaAppl expAppl = new ExpensaAppl();
	SessionFactory factory = HibernateUtil.getSessionFactory();	
	List<ExpensaDTO> expensasProp = expAppl.getExpensaExtActivaPorIdProp(factory,idProp);
	ExpensaDTO expACobrar = expensasProp.get(0); 
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
	var montoPago=document.getElementById("montoPago");
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
		if (isNaN(montoPago.value) || (montoPago.value=="")) {
			alert("El monto pago debe ser un numero");
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
	<h3 id="header" align="center">Registro de Cobros de Expensas Extraordinarias</h3>
</div>
<div class="cuerpo">
		<form class="elegante" name="regisCobro" id="regisCobro" action="cobroExtRegistrarAction">
			<fieldset>
		  		<legend>Registrar Cobro</legend>
			 		<table  border="0" cellpadding="3" cellspacing="3">
			 			<tr><td colspan="10" height="5"></td></tr>
			 			<tr>
			 				<td><label for="operacion">Operacion: <%=expACobrar.getNumeroOperacion() %></label> </td>
				 			<td>&nbsp;<label for="monto">Monto: <%=(-1) * (expACobrar.getPropiedad().getCtaExtSaldoExp() + expACobrar.getPropiedad().getCtaExtSaldoInt()) %></label> </td>
				 			<td>&nbsp;<label for="edificio">Edificio: <%=expACobrar.getPropiedad().getTipoPropiedad().getEdificio().getNombre() %></label> </td>
				 			<td>&nbsp;<label for="nivel">Nivel: <%=expACobrar.getPropiedad().getNivel() %></label> </td>
				 			<td>&nbsp;<label for="orden">Orden: <%=expACobrar.getPropiedad().getOrden() %></label> </td>
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
				 		<tr>
				 			<td align="right"><label for="monto">Monto Pago:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="montoPago" name="montoPago" size="15"/></td>
				 		</tr>
			  		</table>		
			</fieldset>
			<input class="btn" type="button" value="Aceptar" onclick="validar()" />
			<a href="expensasPropiedadesListado.jsp?id=<%=idEdificio%>">Volver</a>
			<input type="text" style="display: none;" id="idPropiedad" name="idPropiedad" value=<%=idProp %> size="15"/>	
			<input type="text" style="display: none;" id="idEdificio" name="idEdificio" value=<%=idEdificio %> size="15"/>		  	
		</form>
</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
