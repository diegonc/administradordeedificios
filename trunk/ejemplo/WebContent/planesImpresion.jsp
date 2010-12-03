<%@page import="planes.*"%>
<%@page import="permisos.AdministradorDePermisos"%>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html"
	import="org.hibernate.*"%>
<script type="text/javascript">
function ocultarBotones(){
	var bt1 = document.getElementById("boton1");
	var bt2= document.getElementById("boton2");
	bt2.style.display ="none";
	bt1.style.display ="none";
}
function mostrarBotones(){
	var bt1 = document.getElementById("boton1");
	var bt2= document.getElementById("boton2");
	bt2.style.display ="";
	bt1.style.display ="";
}
</script>


<%
	int idPlan = Integer.parseInt(request.getParameter("idPlan"));
	PlanesAppl planAppl = new PlanesAppl();
	PlanDTO plan = planAppl.getPlanById(idPlan);
	List<CuotaDTO> cuotas = plan.getCuotas();
	
	CuotaCobroAppl cuotaCobroAppl = new CuotaCobroAppl();
%>

	<div class="titulo"><h3>Detalle de Plan Numero <%=plan.getId() %></h3></div>
	
	
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
		<input type="button"  id="boton1" onclick="javascript:print()" value="Imprimir" onblur="ocultarBotones()" onfocus="mostrarBotones()">
		<input type="button"  id="boton2" onclick="javascript:window.close();" value="Cerrar">
	