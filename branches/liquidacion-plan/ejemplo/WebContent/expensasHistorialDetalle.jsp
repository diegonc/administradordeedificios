
<%@page import="beans.LiquidacionBean"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="expensas.dto.*"%>
<jsp:useBean id="liquidaciones" scope="session" class="beans.LiquidacionBean"/>
<%List<ExpensaDTO> expensas = liquidaciones.getExpensasOrdinarias(); %>
<div class="contenido">
	<div class="titulo"><h3>Historial de Liquidaciones</h3></div>
		<div class="cuerpo">
		<table>
			<tr>
				<td width="15"  class="fondo"></td>
				<td width="770" class="fondo" align="left">
					<form class="elegante" name="expensasHistorialAction" action="expensasHistorialAction!mostrar">
						<fieldset>
					  		<legend>Detalle</legend>
						 		<table  border="1" cellpadding="0" cellspacing="0" border="2">
						 			<tr><td>Orden</td><td>Nivel</td><td>Numero Op</td><td>Monto</td><td>Deuda</td><td>Int 1 vto</td><td>Int 2 vto</td></tr>
								 	<%for (ExpensaDTO exp : expensas){ %>
								 	<tr>
						 				<td><%=exp.getPropiedad().getOrden() %></td>
						 				<td><%=exp.getPropiedad().getNivel() %></td>
						 				<td><%=exp.getNumeroOperacion() %></td>
						 				<td><%=exp.getMonto() %></td>
						 				<td><%=exp.getDeudaPrevia() %></td>
						 				<td><%=exp.getIntereses() %></td>
						 				<td><%=exp.getInteresSegundoVencimiento() %></td>
						 			</tr>	
						 			<%} %>
								 	
								</table>
						</fieldset>
						<input type="hidden" name="dni"  ></input>
						<a href="expensasHistorialAction">Volver</a>					
						
					</form>
				</td>
				<td width="15"  class="fondo"></td>
			</tr>		
		</table>	
		</div>
	</div>




<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>