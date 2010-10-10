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
		<form class="elegante" name="consolidarGastoReal" id="consolidarGastoReal" action="GastoRealConsolidarAction">
			<fieldset>
		  		<legend>Consolidar Gasto Real</legend>
			 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
			 			<tr><td colspan="8" height="10"></td></tr>
			 			<tr>
			 				<td align="right"><label for="nombre">Folio:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="folio" name="folio" value="<%=gasto.getNumeroFolio() %>" readonly size="15"/></td>
			 				<td align="right"><label for="nombre">Detalle:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="detalle" name="detalle" value="<%=gasto.getDetalle() %>" readonly size="30"/></td>	
				 		</tr>
				 		<tr>
				 			<td align="right"><label for="nombre">Monto:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="monto" name="monto" value="<%=gasto.getMonto() %>" readonly size="15"/></td>					 			
				 			<td align="right"><label for="nombre">Razon Social:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="razon_social" name="razon_social" value="<%=gasto.getRazonSocial() %>" readonly size="30"/></td>		
				 		</tr>
				 		<tr>
				 			<td align="right"><label for="formaPago">Forma de Pago:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="formaPago" name="formaPago" value="<%=gasto.getFormaPago() %>" readonly size="15"/></td>
				 			<td align="right"><label for="nroFac">Nro. Factura:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="nroFac" name="nroFac" value="<%=gasto.getNumeroFacturaPago() %>" readonly  size="15"/></td>
				 			<td align="right"><label for="fechaPago">Fecha Pago:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="fechaPago" name="fechaPago" value="<%=gasto.getFechaPago() %>" readonly size="15"/></td>
				 		</tr>
				 		<tr>
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="id" name="id" value="<%=gasto.getId() %>" readonly size="15"/></td>
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="idEdificio" name="idEdificio" value="<%=gasto.getEdificio().getId() %>" readonly size="15"/></td>
				 		</tr>
				  		<tr>
			  			<td colspan="8"><input class="btn" type="button" value="Consolidar" onclick="submit()" /></td>
			  			<td> <a href="GastosListarModifElimAction?id=<%=id%>">Volver</a> </td>
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
