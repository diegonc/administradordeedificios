<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="propiedades.PropiedadDTO"%>
<%@page import="propiedades.TipoPropiedadDTO"%>
<jsp:useBean id="lista" scope="session" class="beans.PropiedadesBean"/>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html"
	import="org.hibernate.*"%>
	
<script type="text/javascript">
function validar(thisform) {
	var elegido = document.getElementById("elegido");
	if (elegido.value == null) {
		alert("Debe elegir una propiedad");
	} else {
		alert(elegido.value);
		document.CalculoPlanesAction.submit();
	}
}

</script>

<%
	ArrayList<PropiedadDTO> propiedades = lista.getPropiedades();
	String tipo = "";
	if (lista.getTipo().equals("ord")) {
		tipo = "ordinarios";
	} else if (lista.getTipo().equals("ext")) {
		tipo = "extraordinarios";
	}
%>

<table cellpadding="0" cellspacing="0">
	<tr>
		<td width="5" class="borde"></td>
		<td width="800" class="borde" align="center">
		<h3 id="header">Planes <%=tipo%> - Propiedades edificio " <%=propiedades.get(0).getTipoPropiedad().getEdificio().getNombre()%>"</h3>
		</td>
		<td width="5" class="borde"></td>
	</tr>
</table>
<tr>
	<td width="5" class="borde"></td>
	<td width="15" class="fondo"></td>
	<td width="770" class="fondo" align="center">
	<form class="elegante" id="CalculoPlanesAction" name="CalculoPlanesAction" action="CalculoPlanesAction">
	<fieldset><legend>Listado de Propiedades</legend>
	<table width="500">
	<tr> Amortizacion: <%=propiedades.get(0).getTipoPropiedad().getEdificio().getAmortizacion()%>  </tr>
	<tr> Interes: <%=propiedades.get(0).getTipoPropiedad().getEdificio().getTasa_anual()%> </tr>
	</table>
	<table width="500" border="1" class="listado" align="center">
		<tr>
			<td class="listado_par">Tipo Propieadad</td>
			<td class="listado_par">Nivel</td>
			<td class="listado_par">Orden</td>
			<td class="listado_par">&nbsp;</td>
		</tr>
		<tr>
			<td><input type="text" style="display: none;" id="tipo" name="tipo" value="<%=lista.getTipo()%>" size="15"/></td>
		</tr>
		<%for (PropiedadDTO propiedadDTO : propiedades) {
		%>
		<tr>
			<td><%=propiedadDTO.getTipoPropiedad().getNombreTipo()%></td>
			<td><%=propiedadDTO.getNivel()%></td>
			<td><%=propiedadDTO.getOrden()%></td>
			<td bgcolor="#F0F0F0">
				<input type="checkbox" name="elegido" id="elegido" value="<%=propiedadDTO.getId()%>"/> 
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<input class="btn" type="button" value="Seleccionar Expensas" onclick="submit()"/>
	</fieldset>
	<s:actionerror cssClass="error"/>	
	<a href="EdificioListarAction?redi=planes">Volver</a>
	</form>
</tr>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
