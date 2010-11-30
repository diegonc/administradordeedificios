<%@page import="planes.*"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@page import="permisos.AdministradorDePermisos"%>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html"
	import="org.hibernate.*"%>

<%
	int idPlan = Integer.parseInt(request.getParameter("idPlan"));
	PlanesAppl planAppl = new PlanesAppl();
	PlanDTO plan = planAppl.getPlanById(idPlan);
	List<CuotaDTO> cuotas = plan.getCuotas();
	
	CuotaCobroAppl cuotaCobroAppl = new CuotaCobroAppl();
%>
<div class="contenido">
	<div class="titulo"><h3>Detalle de Plan</h3></div>
	<div class="cuerpo">
	<form class="elegante" id="listadoPlanes" name="listadoPlanes">
	<table>
		<tr>
		    <td>Responsable: <%=plan.getResponsable().getNombre()%></td>
		</tr>
		<tr>
			<td>Fecha: <%=plan.getFecha()%> - Tipo:  <%=plan.getTipo()%> - Cuotas: <%=plan.getCantidadCuotas()%></td>
		</tr>
		
		<tr>
			<td>Monto: <%=NumberFormat.redondeoDouble(plan.getMonto())%> - Intereses: <%=NumberFormat.redondeoDouble(plan.getSaldoIntereses())%> 
			-  Saldo: <%=NumberFormat.redondeoDouble(plan.getSaldoPlan())%></td>
		</tr>
	</table>
	<fieldset><legend>Cuotas</legend>	
	<table width="500" border="1" class="listado" align="center">
		<tr>
			<td class="listado_par">Nro</td>
			<td class="listado_par">Monto Amortizado</td>
			<td class="listado_par">Intereses</td>
			<td class="listado_par">Saldo</td>
			<td class="listado_par">Pagos</td>
			<td class="listado_par">Mora</td>
			<td class="listado_par">Consolidar</td>
		</tr>
		<%
			for (CuotaDTO cuotaDTO : cuotas) {
		%>
		<tr>
			<td><%=cuotaDTO.getNumeroCuota()%></td>
			<td><%=NumberFormat.redondeoDouble(cuotaDTO.getMonto())%></td>
			<td><%=NumberFormat.redondeoDouble(cuotaDTO.getIntereses())%></td>
			<td><%=NumberFormat.redondeoDouble(cuotaDTO.getMonto() + cuotaDTO.getIntereses()) %></td>
			<% if (!cuotaCobroAppl.existeCobro(cuotaDTO.getId()) && cuotaDTO.sePuedePagar() ) { %>
				<td><a href="cobroCuotaRegistrar.jsp?idCuota=<%=cuotaDTO.getId()%>">Saldar</a></td>
			<%} else if (cuotaCobroAppl.existeCobro(cuotaDTO.getId())) { %>
				<td>Saldado</td>
			<% } else { %> 
				<td>&nbsp;</td>
			<%} if (cuotaDTO.estaVencida() && !cuotaCobroAppl.existeCobro(cuotaDTO.getId())) {%>
				<td><a href="#">Calcular Mora</a></td>
			<%} else {%>
				<td>&nbsp;</td>
			<%} %>
			<% if (AdministradorDePermisos.getInstancia().isAdministrador() && cuotaCobroAppl.paraConsolidar(cuotaDTO.getId())) { %>
				<td><a href="ConsolidarCuota?cuota_id=<%=cuotaDTO.getId() %>">Consolidar</a></td>
			<%} else if (AdministradorDePermisos.getInstancia().isAdministrador() && cuotaCobroAppl.existeCobro(cuotaDTO.getId())) {%>
				<td>Consolidado</td>
			<%} else { %>
				<td>&nbsp;</td>
			<% } %>
		</tr>
		<%
		}
		%>
	
	</table>
	<a href="#">Imprimir</a>
	</fieldset>
	<a href="planesListado.jsp?<%=plan.getResponsable().getDni() %>">Volver</a>
	</form>
	</div>
	</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
