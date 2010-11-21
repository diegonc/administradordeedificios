
<%@page import="expensas.dto.ExpensaDTO"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="propiedades.PropiedadDTO"%>
<%@page import="propiedades.TipoPropiedadDTO"%>
<jsp:useBean id="lista" scope="session" class="beans.LiquidacionBean"/>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html"
	import="org.hibernate.*"%>
<%
	List<ExpensaDTO> expensas = new ArrayList<ExpensaDTO>();
	String tipo = "";
	String redi = "";
	if (lista.getExpensasOrdinarias() != null ) {
		expensas = lista.getExpensasOrdinarias();
		tipo = "Ordinarias";
		redi = "ord";
	} else  if (lista.getExpensasExtraordinarias() != null) {
		expensas = lista.getExpensasExtraordinarias();
		tipo = "Extraordinarias";
		redi = "ext";
	}
	int idEdificio = expensas.get(0).getPropiedad().getTipoPropiedad().getEdificio().getId();
%>

<table cellpadding="0" cellspacing="0">
	<tr>
		<td width="5" class="borde"></td>
		<td width="800" class="borde" align="center">
		<h3 id="header">Seleccion de expensas <%=tipo%></h3>
		</td>
		<td width="5" class="borde"></td>
	</tr>
</table>
<tr>
	<td width="5" class="borde"></td>
	<td width="15" class="fondo"></td>
	<td width="770" class="fondo" align="center">
	<form class="elegante" id="CalculoCuotasAction" name="CalculoCuotasAction" action="CalculoCuotasAction">
	<fieldset><legend>Listado de Expensas</legend>	
	<table width="500" border="1" class="listado" align="center">
		<tr>
			<td class="listado_par">Tipo Propieadad</td>
			<td class="listado_par">Nivel</td>
			<td class="listado_par">Orden</td>
			<td class="listado_par">Numero Op.</td>
			<td class="listado_par">Monto</td>
			<td class="listado_par">&nbsp;</td>
		</tr>
		<%for (ExpensaDTO expensasDTO : expensas) {
		%>
		<tr>
			<td><%=expensasDTO.getPropiedad().getTipoPropiedad().getNombreTipo()%></td>
			<td><%=expensasDTO.getPropiedad().getNivel()%></td>
			<td><%=expensasDTO.getPropiedad().getOrden()%></td>
			<td><%=expensasDTO.getNumeroOperacion()%></td>
			<td><%=expensasDTO.getMonto()%></td>
			<td bgcolor="#F0F0F0">
				<input type="checkbox" name="elegido" id="elegido" value="<%=expensasDTO.getId()%>"/> 
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<input class="btn" type="button" value="Calcular Cuotas" onclick="submit()"/>
	</fieldset>
	<s:actionerror cssClass="error"/>	
	<a href="planesPropListarAction?id=<%=idEdificio %>&tipo=<%=redi%>">Volver</a>
	</form>
</tr>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
