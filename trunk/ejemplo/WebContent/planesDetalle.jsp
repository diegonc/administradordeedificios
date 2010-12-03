<%@page import="planes.*"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@page import="permisos.AdministradorDePermisos"%>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html"
	import="org.hibernate.*"%>
<script type="text/javascript">
function Abrir_ventana (pagina) {
	var opciones="toolbar=no,location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=1400, top=85, left=140";
	window.open(pagina,"",opciones);
}

</script>	

<%
	int idPlan = Integer.parseInt(request.getParameter("idPlan"));
	PlanesAppl planAppl = new PlanesAppl();
	PlanDTO plan = planAppl.getPlanById(idPlan);
	List<CuotaDTO> cuotas = plan.getCuotas();
	
	CuotaCobroAppl cuotaCobroAppl = new CuotaCobroAppl();
%>
<div class="contenido">
	<div class="titulo"><h3>Detalle de Plan Numero <%=plan.getId() %></h3></div>
	<div class="cuerpo">
	<form class="elegante" id="listadoPlanes" name="listadoPlanes">
	<table>
		<tr>
		    <td>Responsable: <%=plan.getResponsable().getNombre()%></td>
			<td>Plan: <%=plan.getId()%></td>
		</tr>
		<tr>
			<td>Fecha: <%=plan.getFecha()%> - Tipo:  <%=plan.getTipo()%> - Cuotas: <%=plan.getCantidadCuotas()%></td>
		</tr>
		
		<tr>
			<td>Monto: <%=NumberFormat.redondeoDouble(plan.getMonto())%> - Intereses: <%=NumberFormat.redondeoDouble(plan.getSaldoIntereses())%> 
			- Descuento: <%=NumberFormat.redondeoDouble(plan.getMontoDescuento()) %> Saldo: <%=NumberFormat.redondeoDouble(plan.getSaldoPlan())%></td>
		</tr>
	</table>
	<fieldset><legend>Cuotas</legend>	
	<table width="500" border="1" class="listado" align="center">
		<tr>
			<td class="listado_par">Nro</td>
			<td class="listado_par">Monto Amortizado</td>
			<td class="listado_par">Intereses</td>
			<td class="listado_par">Mora</td>			
			<td class="listado_par">Saldo</td>
			<td class="listado_par">Pagos</td>
			<td class="listado_par">Consolidar</td>
		</tr>
		<%
			for (CuotaDTO cuotaDTO : cuotas) {
		%>
		<tr>
			<td><%=cuotaDTO.getNumeroCuota()%></td>
			<td><%=NumberFormat.redondeoDouble(cuotaDTO.getMonto())%></td>
			<td><%=NumberFormat.redondeoDouble(cuotaDTO.getIntereses())%></td>
			<td><%=NumberFormat.redondeoDouble(cuotaDTO.getInteresMora())%></td>
			<td><%=NumberFormat.redondeoDouble(cuotaDTO.getMonto() + cuotaDTO.getIntereses() + cuotaDTO.getInteresMora()) %></td>
			<% if (!cuotaCobroAppl.existeCobro(cuotaDTO.getId()) && cuotaDTO.sePuedePagar() ) { %>
				<td><a href="cobroCuotaRegistrar.jsp?idCuota=<%=cuotaDTO.getId()%>">Saldar</a></td>
			<%} else if (cuotaCobroAppl.existeCobro(cuotaDTO.getId())) { %>
				<td><a href="cobroCuotaDetalle.jsp?idCuota=<%=cuotaDTO.getId()%>">Saldado</a></td>
			<% } else { %> 
				<td>&nbsp;</td>
			<% } if (AdministradorDePermisos.getInstancia().isAdministrador() && cuotaCobroAppl.paraConsolidar(cuotaDTO.getId())) { %>
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
	</fieldset>
	<table height="50"> <tr height="20"><td><a href="javascript:Abrir_ventana('planesImpresion.jsp?idPlan=<%=idPlan%>')">Imprimir</a></td></tr></table>
	<a href="planesListado.jsp?<%=plan.getResponsable().getDni() %>">Volver</a>
	</form>
	</div>
	</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
