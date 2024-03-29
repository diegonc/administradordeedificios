
<%@page import="gastos.appl.GastosAppl"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="gastos.dto.GastoRealDTO"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="gastos.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html" import="org.hibernate.*"%>
<%
	GastosAppl gastoAppl = new GastosAppl();
	SessionFactory factory = HibernateUtil.getSessionFactory();
	int id = Integer.parseInt(request.getParameter("id"));
	GastoRealDTO gasto = gastoAppl.getGastosRealesPendientesPorid(
			factory, id);
%>

<script type="text/javascript">


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
		<form class="elegante" name="deleGastoReal" id="deleGastoReal" action="GastoRealDeleteAction">
			<fieldset>
		  		<legend>Eliminar Gasto Real Pendiente</legend>
			 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
			 			<tr><td colspan="8" height="10"></td></tr>
			 			<tr>
			 				<td align="right"><label for="nombre">Quiere eliminar el Gasto:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="nombre" name="nombre" value="<%=gasto.getDetalle()%>" readonly size="30"/></td>
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="id" name="id" value="<%=gasto.getId()%>" readonly size="15"/></td>	
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="edificio_id" name="edificio_id" value="<%=gasto.getEdificio().getId()%>" readonly size="15"/></td>
				 		</tr>				  		
			  		</table>			  	
			</fieldset>
			<s:actionerror cssClass="error"/>
			<input class="btn" type="button" value="Aceptar" onclick="submit()" />&nbsp;<a href="GastosListarModifElimAction?id=<%=gasto.getEdificio().getId()%>">Volver</a>
		</form>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
