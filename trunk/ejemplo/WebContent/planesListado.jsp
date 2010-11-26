<%@page import="planes.*"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<jsp:useBean id="lista" scope="session" class="beans.PlanesBeans"/>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html"
	import="org.hibernate.*"%>

<%
	List<PlanDTO> planes = lista.getPlanes();
%>
<div class="contenido">
	<div class="titulo"><h3>Seleccion de Plan</h3></div>
	<div class="cuerpo">
	<form class="elegante" id="listadoPlanes" name="listadoPlanes">
	<fieldset><legend>Listado de Planes de <%=planes.get(0).getResponsable().getNombre() %></legend>	
	<table width="500" border="1" class="listado" align="center">
		<tr>
			<td class="listado_par">Id</td>
			<td class="listado_par">Fecha</td>
			<td class="listado_par">Cuotas</td>
			<td class="listado_par">Monto</td>
			<td class="listado_par">Intereses</td>
			<td class="listado_par">Saldo</td>
			<td class="listado_par">&nbsp;</td>
		</tr>
		<%
			for (PlanDTO planDTO : planes) {
		%>
		<tr>
			<td><%=planDTO.getId()%></td>
			<td><%=planDTO.getFecha()%></td>
			<td><%=planDTO.getCantidadCuotas()%></td>
			<td><%=planDTO.getMonto()%></td>
			<td><%=planDTO.getSaldoIntereses()%></td>
			<td><%=planDTO.getSaldoPlan()%></td>
			<td>
				<a href="planesDetalle.jsp?idPlan=<%=planDTO.getId()%>">Detalle</a>
			</td>
		</tr>
		<%
		}
		%>
	
	</table>
	</fieldset>
	<a href="planesConsulta.jsp">Volver</a>
	</form>
	</div>
	</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
