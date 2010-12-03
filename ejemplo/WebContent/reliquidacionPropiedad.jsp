<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" import="expensas.dto.ExpensaDTO"%>
<%@ page language="java" import="edificio.EdificioDTO"%>
<jsp:useBean id="edificio" scope="session" class="edificio.EdificioDTO"/>
<jsp:useBean id="detalleExpensa" scope="session" class="beans.LiquidacionBean"/>

<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>

<%	
	ExpensaDTO expensaExt =null;
	ExpensaDTO expensaOrd = null;	
	if(detalleExpensa!=null){ 
		if(detalleExpensa.getExpensasExtraordinarias()!=null)
				expensaExt = detalleExpensa.getExpensasExtraordinarias().get(0);
	
		if(detalleExpensa.getExpensasOrdinarias()!=null)
			expensaOrd = detalleExpensa.getExpensasOrdinarias().get(0);
	}
	
%>
<div class="contenido">
	<div class="titulo"><h3>Reliquidación de Expensas</h3></div>
	<div class="cuerpo">
	<table>
		<tr>
			<td width="15"  class="fondo"></td>
			<td width="770" class="fondo" align="left">
				<form class="elegante" name="expensasLiquidacionResultante" action="expensasLiquidacionResultante!registrarReliquidacion">
					<fieldset>
				  		<legend>Reliquidación	 </legend>
					 		<table  border="0" cellpadding="0" cellspacing="0" class="listado" wi>
					 			<%if(expensaOrd==null){ %>
					 			<tr>
					 				<td>La propiedad no tiene expensa vigente</td>
					 			</tr>
					 			<%} else{%>
					 			<tr>&nbsp;&nbsp; Nivel:&nbsp; <%=expensaOrd.getPropiedad().getNivel() %> &nbsp;&nbsp;&nbsp;Orden: &nbsp;<%=expensaOrd.getPropiedad().getOrden() %></tr>
					 			<%if (expensaOrd!=null){ %>
					 				<tr>
						 				<td class="listado_par" colspan="6"> Expensa Ordinaria</td>
						 			</tr>
						 		
						 			<tr>
						 				<td>
							 				Monto ultima Liquidaci&oacute;n: &nbsp;<%=expensaOrd.getMonto() %>	<br>			 			
							 				Deuda a la fecha: &nbsp;<%=expensaOrd.getDeudaPrevia() %>	<br>		 			
							 				Intereses en la reliquidaci&oacute;n: &nbsp;<%=expensaOrd.getIntereses() %>	<br>						 						
							 				Total Adeudado:&nbsp;<%=expensaOrd.getDeudaPrevia()+expensaOrd.getIntereses() %>	
						 				</td> 							 			
									</tr>
								<%} %>
								<%if (expensaExt!=null){ %>
										<tr>
											<td class="listado_par" colspan="1">Expensa Extraordinaria</td>
										</tr>	
										<tr>
							 				<td>
									 				Monto &uacute;ltima Liquidaci&oacute;n:&nbsp;<%=expensaExt.getMonto() %><br>			 			
									 				Deuda a la fecha:&nbsp;<%=expensaExt.getDeudaPrevia() %>				 	<br>		
									 				Intereses en la reliquidaci&oacute;n:&nbsp;<%=expensaExt.getIntereses() %>		<br>	
									 				Total Adeuda:&nbsp;<%=expensaExt.getDeudaPrevia()+expensaExt.getIntereses() %><br>
							 				</td> 							 			
										</tr>
									<%}%>
						<%}%>
						 		
					  		</table>
					  		<table>
					  		<%if (expensaOrd!=null){ %>
						  		<tr>
						  			<td>
						  				<input type="checkbox" name="ordinaria" value="O">							  		
							  		</td>
							  		<td>
							  			Expensas Ordinarias
							  		</td>
						  		</tr>
						  	<%} %>	
						  	<%if (expensaExt!=null){ %>
						  		<tr>
						  			<td>
						  				<input type="checkbox" name="extraordinaria" value="E">							  		
							  		</td>
							  		<td>
							  			Expensas Extraordinarias
							  		</td>
						  		</tr>
						  <%} %>		
					  		</table>
					  		<input type="hidden" name="id" id="id" value="<%=edificio.getId() %>"></input>
					  	<%if (expensaOrd!=null){ %>
					  		<input type="hidden" name="idProp" id="idProp" value="<%=(expensaOrd.getPropiedad().getId()) %>"></input>
					  	<%}%>
					  	<%if (expensaExt!=null && expensaOrd==null){ %>
					  		<input type="hidden" name="idProp" id="idProp" value="<%=(expensaExt.getPropiedad().getId()) %>"></input>
					  	<%}%>				  	
					</fieldset>
					<input class="btn" type="button" value="Aceptar" onclick="submit()" />
					<a href="EdificioListarAction?redi=expensa">Volver</a> 
				</form>
			</td>
			<td width="15"  class="fondo"></td>			
		</tr>
	</table>	
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>