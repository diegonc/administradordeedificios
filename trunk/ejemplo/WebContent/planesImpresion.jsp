<%@page import="planes.*"%>
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

	<div class="titulo"><h3>Detalle de Plan</h3></div>
	
	<form  id="listadoPlanes" name="listadoPlanes">
	<table>
		<tr>
		    <td>Responsable: <%=plan.getResponsable().getNombre()%></td>
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
			<td class="listado_par">Saldo</td>
		</tr>
		<%
			for (CuotaDTO cuotaDTO : cuotas) {
		%>
		<tr>
			<td><%=cuotaDTO.getNumeroCuota()%></td>
			<td><%=NumberFormat.redondeoDouble(cuotaDTO.getMonto())%></td>
			<td><%=NumberFormat.redondeoDouble(cuotaDTO.getIntereses())%></td>
			<td><%=NumberFormat.redondeoDouble(cuotaDTO.getMonto() + cuotaDTO.getIntereses()) %></td>
		</tr>
		<%
		}
		%>	
	</table>
	</fieldset>
		<input type="button" onclick="javascript:print()" value="Imprimir">
		<input type="button" onclick="javascript:window.close();" value="Cerrar">
	