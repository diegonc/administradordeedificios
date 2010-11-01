<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" import="expensas.dto.ExpensaDTO"%>
<%@ page language="java" import="edificio.EdificioDTO"%>

<jsp:useBean id="edificio" scope="session" class="edificio.EdificioDTO"/>
<jsp:useBean id="detalleExpensa" scope="session" class="beans.LiquidacionBean"/>
<%@page import="java.util.Date"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%ExpensaDTO expensaOrd = detalleExpensa.getExpensasOrdinarias().get(0); 
  ExpensaDTO expensaExt = detalleExpensa.getExpensasExtraordinarias().get(0);
%>
<div class="contenido">
	<div class="titulo"><h3>Reliquidación de Expensas</h3></div>
	<div class="cuerpo">
	<table>
		<tr>
			<td width="15"  class="fondo"></td>
			<td width="770" class="fondo" align="left">
				<form class="elegante" name="expensasLiquidacionResultante" action="expensasLiquidacionResultante">
					<fieldset>
				  		<legend>Reliquidación</legend>
					 		<table  border="0" cellpadding="0" cellspacing="0" class="listado">
					 			<%if(expensaOrd==null){ %>
					 			<tr>
					 				<td>La propiedad no tiene expensa vigente</td>
					 			</tr>
					 			<%} else{%>
						 			<td colspan="6"> Expensa Ordinaria</td>
						 			<tr>
							 			<td>Piso</td>
							 			<td>DTO</td>
							 			<td>Monto</td>
							 			<td>Deuda Previa</td>
							 			<td>Int primer vto</td>
							 			<td>Total</td>
							 		</tr>
						 			<tr>
						 				<td><%=expensaOrd.getPropiedad().getNivel() %>	</td>			 			
						 				<td><%=expensaOrd.getPropiedad().getOrden() %>	</td>			 			
						 				<td><%=expensaOrd.getMonto() %>	</td>			 			
						 				<td><%=expensaOrd.getDeudaPrevia() %>	</td>			 			
						 				<td><%=expensaOrd.getIntereses() %>	</td>		
						 				<td><%=expensaOrd.getMonto()+expensaOrd.getDeudaPrevia()+expensaOrd.getIntereses() %>	</td> 							 			
									</tr>
									<%if(expensaExt!=null){ %>
										<td colspan="6">Expensa Extraordinaria</td>
										<tr>
								 			<td>Piso</td>
								 			<td>DTO</td>
								 			<td>Monto</td>
								 			<td>Deuda Previa</td>
								 			<td>Int primer vto</td>
								 			<td>Total</td>
								 		</tr>
										<tr>
							 				<td><%=expensaExt.getPropiedad().getNivel() %>	</td>			 			
							 				<td><%=expensaExt.getPropiedad().getOrden() %>	</td>			 			
							 				<td><%=expensaExt.getMonto() %>	</td>			 			
							 				<td><%=expensaExt.getDeudaPrevia() %>	</td>			 			
							 				<td><%=expensaExt.getIntereses() %>	</td>		
							 				<td><%=expensaExt.getMonto()+expensaExt.getDeudaPrevia()+expensaExt.getIntereses() %>	</td> 							 			
										</tr>
									<%}%>
									
						 		<%} %>
					  		</table>
<!--					  		<input type="hidden" name="id" id="id"></input>			  	-->
					</fieldset>
					<input class="btn" type="button" value="Aceptar" onclick="submit()" />
					<a href="EdificioListarAction?redi=expensa">Volver</a> 
				</form>
			</td>
			<td width="15"  class="fondo"></td>			
		</tr>
	</table>
	<s:actionerror/>
	
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>