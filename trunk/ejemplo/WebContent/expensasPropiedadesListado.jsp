<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@page import="propiedades.PropiedadDTO"%>
<%@page import="propiedades.TipoPropiedadDTO"%>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="edificio.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html"
	import="org.hibernate.*"%>
<%
	int id = Integer.parseInt(request.getParameter("id"));
	Session hSession = HibernateUtil.getSession();
	EdificioDTO edificio = (EdificioDTO) hSession.load(
			EdificioDTO.class, id);
	Set<TipoPropiedadDTO> tipos = edificio.getTipoPropiedades();
	Iterator<TipoPropiedadDTO> iteradorTipos = tipos.iterator();
%>

<script type="text/javascript">
function redirecRegisCobros() {
	elem=document.getElementsByName('propElegida');
	for(i=0;i<elem.length;i++)
	if (elem[i].checked) {
		idProp = elem[i].value;
		location.href = "cobroRegistrar.jsp?idEdificio="+<%=edificio.getId()%>+"&idProp="+idProp;
	}
}
</script>

<table cellpadding="0" cellspacing="0">
	<tr>
		<td width="5" class="borde"></td>
		<td width="800" class="borde" align="center">
		<h3 id="header">Expensas - Listado Propiedades edifcio " <%=edificio.getNombre()%>
		"</h3>
		</td>
		<td width="5" class="borde"></td>
	</tr>
</table>
<tr>
	<td width="5" class="borde"></td>
	<td width="15" class="fondo"></td>
	<td width="770" class="fondo" align="left">
	<form class="elegante" id="expensasProp" name="expensasProp">
	<fieldset><legend>Listado de Propiedades</legend>
	<table width="500" border="1" class="listado">
		<tr>
			<td>Tipo Propieadad</td>
			<td>Nivel</td>
			<td>Orden</td>
			<td></td>
		</tr>
		<%
			while (iteradorTipos.hasNext()) {
				TipoPropiedadDTO tipoProp = iteradorTipos.next();
				List<PropiedadDTO> propiedades = tipoProp.getPropiedades();
				for (PropiedadDTO propiedadDTO : propiedades) {
		%>
		<tr>
			<td><%=propiedadDTO.getTipoPropiedad().getNombreTipo()%></td>
			<td><%=propiedadDTO.getNivel()%></td>
			<td><%=propiedadDTO.getOrden()%></td>
			<td><input type="radio" id="propElegida" name="propElegida" value="<%=propiedadDTO.getId()%>" /></td>
		</tr>
		<%
			}
			}
		%>
	</table>
	<input type="button" value="Re-Liquidar" onclick=""> <input
		type="button" value="Registrar Cobro" 
		onClick="redirecRegisCobros()" >
	<input type="button" value="Consultar/Eliminar Cobros" onclick="">
	<input type="button" value="Consultar Liquidaciones" onclick="">
	<a href="EdificioListarAction?redi=expensa">Volver</a></fieldset>
	</form>
</tr>

<%
	hSession.close();
%>

<jsp:include page="/WEB-INF/jspf/footer.jspf" />
