<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script>
function submit(){
	
}
</script>
<% String id =request.getParameter("id");
	Calendar fechaActual = new GregorianCalendar();
	int anio = fechaActual.get(Calendar.YEAR);
	int anioDesde=anio-5;
	int anioHasta=anio+1;
%>
<div class="contenido">
	<div class="titulo"><h3>Liquidacion de Expensas</h3></div>
	<div class="cuerpo">
	<table>
		<tr>
			<td width="15"  class="fondo"></td>
			<td width="770" class="fondo" align="left">
				<form class="elegante" name="expensasLiquidacionResultante" action="expensasLiquidacionResultante">
					<fieldset>
				  		<legend>Seleccione un período para la Liquidación</legend>
					 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
					 			
					 			<tr>
					 				<td align="right">Mes:
					 					<select  id="mes" name="mes" >
						 				<%for (int i=1;i<=12;i++){ %>
						 					<option value="<%=i%>"> <%=i%></option>
						 				<%} %>
						 				</select>		
					 				</td>
					 				<td align="right">Año:
					 					<select  id="anio" name="anio" >
						 				<%for (int j=anioDesde;j<=anioHasta;j++){ %>
						 					<option value="<%=j%>"> <%=j%></option>
						 				<%} %>
						 				</select>		
					 				</td>			 			
								
						 		</tr>
						 		
					  		</table>
					  		<input type="hidden" value=<%=id%> name="id" id="id"></input>			  	
					</fieldset>
					<input class="btn" type="button" value="Aceptar" onclick="submit()" />
					<a href="EdificioListarAction?redi=expensa">Volver</a> 
				</form>
			</td>
			<td width="15"  class="fondo"></td>
			<td width="5" class="borde"></td>
		</tr>
	</table>
	<s:actionerror/>
	
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>