<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="planes.*"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html" import="org.hibernate.*"%>
<%
	int idCuota = Integer.parseInt(request.getParameter("idCuota"));
	CuotaCobroAppl cuotaCobroAppl = new CuotaCobroAppl();
	CuotaCobroDTO cuotaCobro = cuotaCobroAppl.getCuotaCobroByIdCuota(idCuota);
%>
<div class="contenido">
<div class="titulo">
	<h3 id="header" align="center">Cobros de Cuotas de Planes</h3>
</div>
<div class="cuerpo">
		<form class="elegante" name="regisCuotaCobro" id="regisCuotaCobro">
			<fieldset>
		  		<legend>Cobro Cuota</legend>
			 		<table  border="0" cellpadding="3" cellspacing="3">
			 			<tr><td colspan="10" height="5"></td></tr>
			 			<tr>	
			 				<td><label for="nroCuota">Plan: <%=cuotaCobro.getCuota().getPlan().getId() %></label> </td>
			 				<td><label for="nroCuota">Responsable: <%=cuotaCobro.getCuota().getPlan().getResponsable().getNombre() %></label> </td>
			 				<td><label for="nroCuota">Cuota: <%=cuotaCobro.getCuota().getNumeroCuota() %></label> </td>
				 		</tr>
				 		<tr>
				 			<td><label for="monto">Monto Amortizado: <%=NumberFormat.redondeoDouble(cuotaCobro.getCuota().getMonto()) %></label> </td>
				 			<td><label for="interes">Interes: <%=NumberFormat.redondeoDouble(cuotaCobro.getCuota().getIntereses())%></label> </td>
				 			<td><label for="saldo">Saldo a Pagar: <%=NumberFormat.redondeoDouble(cuotaCobro.getCuota().getMonto()+cuotaCobro.getCuota().getIntereses()) %></label> </td>
				 		</tr>
			 			<tr>
			 				<td><label for="compro">Comprobante: <%=cuotaCobro.getComprobante() %></label> </td>
				 			<td><label for="fechaPago">Fecha de Pago: <%=cuotaCobro.getFecha() %></label>  </td>
				 		</tr>
			  		</table>		
			</fieldset>
			<a href="planesDetalle.jsp?idPlan=<%=cuotaCobro.getCuota().getPlan().getId()%>">Volver</a>	
		</form>
</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
