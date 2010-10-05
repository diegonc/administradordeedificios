<%@page import="gastos.appl.GastosAppl"%>
<%@page import="gastos.dto.GastoRealDTO"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="gastos.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html" import="org.hibernate.*"%>
<%
	GastosAppl edifAppl = new GastosAppl();
	SessionFactory factory = HibernateUtil.getSessionFactory();	
	int id = Integer.parseInt(request.getParameter("id"));
	GastoRealDTO gasto = edifAppl.getGastosRealesPendientesPorid(factory,id);
%>

<script type="text/javascript">
function validar(thisform) {
	var folio=document.getElementById("folio");
	var monto=document.getElementById("monto");
	var fechaPago=document.getElementById("fechaPago");
	var nroFac=document.getElementById("nroFac");
	
	valido=true;
	if (isNaN(folio.value)) {
		valido=false;
		alert("El folio debe ser un numero");
	} 
	if (isNaN(monto.value) && valido==true) {
		valido=false;
		alert("El monto debe ser un numero");
	}
	if (isNaN(nroFac.value) && valido==true) {
		valido=false;
		alert("El numero de factura debe ser un numero");
	}
	if(valido==true) {
		if(/^[0-9]{1,2}\/[0-9]{1,2}\/[0-9]{4}$/.test(fechaPago.value)==false ){ 
			alert("El formato de fecha de pago deber ser dd/mm/aaaa");
			valido=false;
		} 
	}
	if (valido==true) {
		document.modifGastoReal.submit();
	}
}
</script>
<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"><h3 id="header">Gastos Reales</h3></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaGastoGrales" height ="300" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
		<form class="elegante" name="modifGastoReal" id="modifGastoReal" action="GastoRealModifAction">
			<fieldset>
		  		<legend>Modificar Gasto Real Pendiente</legend>
			 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
			 			<tr><td colspan="8" height="10"></td></tr>
			 			<tr>
			 				<td align="right"><label for="folio">Folio:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="folio" name="folio" value="<%=gasto.getNumeroFolio() %>" size="15"/></td>
			 				<td align="right"><label for="detalle">Detalle:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="detalle" name="detalle" value="<%=gasto.getDetalle() %>" size="30"/></td>	
				 		</tr>
				 		<tr>
				 			<td align="right"><label for="monto">Monto:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="monto" name="monto" value="<%=gasto.getMonto() %>" size="15"/></td>					 			
				 			<td align="right"><label for="razon_social">Razon Social:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="razon_social" name="razon_social" value="<%=gasto.getRazonSocial() %>" size="30"/></td>		
				 		</tr>
				 		<tr>
				 			<td align="right"><label for="formaPago">Forma de Pago:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="formaPago" name="formaPago" value="<%=gasto.getFormaPago() %>"  size="15"/></td>
				 			<td align="right"><label for="nroFac">Nro. Factura:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="nroFac" name="nroFac" value="<%=gasto.getNumeroFacturaPago() %>"  size="15"/></td>
				 			<td align="right"><label for="fechaPago">Fecha Pago:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="fechaPago" name="fechaPago" value="<%=gasto.getFechaPago() %>"  size="15"/></td>
				 		</tr>
				 		<tr>
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="id" name="id" value="<%=gasto.getId() %>" readonly size="15"/></td>	
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="estado" name="estado" value="<%=gasto.getEstado() %>" readonly size="15"/></td>
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="id_tipo_gasto" name="id_tipo_gasto" value="<%=gasto.getTipoGasto().getId() %>" readonly size="15"/></td>
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="codigo_tipo_gasto" name="codigo_tipo_gasto" value="<%=gasto.getTipoGasto().getCodigo() %>" readonly size="15"/></td>
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="descripcion_tipo_gasto" name="descripcion_tipo_gasto" value="<%=gasto.getTipoGasto().getDescripcion() %>" readonly size="15"/></td>
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="edificio_id" name="edificio_id" value="<%=gasto.getEdificio().getId() %>" readonly size="15"/></td>
				 		</tr>
				  		<tr>
			  			<td colspan="8"><input class="btn" type="button" value="Modificar" onclick="validar()" /></td>
			  			<td> <a href="EdificioGastosListarAction">Volver</a> </td>
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
