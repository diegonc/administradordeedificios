<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
	
	
%>

<script type="text/javascript">
function redirecConsultaLiq() {
	elem=document.getElementsByName('propElegida');
	for(i=0;i<elem.length;i++) {
		if (elem[i].checked) {
			idProp = elem[i].value;
			location.href = "liquidaConsultaMonto.jsp?idEdificio="+<%=edificio.getId()%>+"&idProp="+idProp;
		}
	}
}
function redirecReLiq() {
	elem=document.getElementsByName('propElegida');
	for(i=0;i<elem.length;i++) {
		if (elem[i].checked) {
			idProp = elem[i].value;
			location.href = "expensasLiquidacionResultante!reliquidar?id="+<%=edificio.getId()%>+"&idProp="+idProp;
		}
	}
}
</script>

<div class="contenido">
<div class="titulo"><h3>Expensas - Listado Propiedades edifcio " <%=edificio.getNombre()%>
		"</h3></div>

	<div class="cuerpo">
	<form class="elegante" id="expensasProp" name="expensasProp" action="expensasPropiedadesListado">
	<fieldset><legend>Listado de Propiedades</legend>
	<table width="500" border="1" class="listado" align="center">
		<tr>
			<td class="listado_par">Tipo Propieadad</td>
			<td class="listado_par">Nivel</td>
			<td class="listado_par">Orden</td>
			<td class="listado_par">&nbsp;</td>
		</tr>
		<%if(tipos!=null){
			Iterator<TipoPropiedadDTO>	 iteradorTipos = tipos.iterator();
			
			while (iteradorTipos.hasNext()) {
				TipoPropiedadDTO tipoProp = iteradorTipos.next();
				List<PropiedadDTO> propiedades = tipoProp.getPropiedades();
				for (PropiedadDTO propiedadDTO : propiedades) {
		%>
		<tr>
			<td><%=propiedadDTO.getTipoPropiedad().getNombreTipo()%></td>
			<td><%=propiedadDTO.getNivel()%></td>
			<td><%=propiedadDTO.getOrden()%></td>
			<td><input type="radio" id="propElegida" name="propElegida" checked="checked" value="<%=propiedadDTO.getId()%>" /></td>
		</tr>
		<%
			}
			}
		}
		%>
	</table>
	</fieldset>
	<s:actionerror cssClass="error"/>	
	<input type="hidden" value="<%=id%>" name="idEdificio">
	<%if (edificio.getMora().equals(EdificioDTO.A_FECHA)){ %>
		<input type="button" value="Re-Liquidar" onclick="redirecReLiq()" > 
	<%} %>
	<input type="submit" value="Registrar Cobro Ord." name="method:mostrarFormularioOrd" >
	<input type="submit" value="Registrar Cobro Ext." name="method:mostrarFormularioExt" >
	<input type="submit" value="Consultar/Eliminar Cobros" name="method:consultarCobros">
	<input type="button" value="Consultar Deuda" onclick="redirecConsultaLiq()">
	<a href="EdificioListarAction?redi=expensa">Volver</a>
	</form>
</div>
</div>

<%
	hSession.close();
%>

<jsp:include page="/WEB-INF/jspf/footer.jspf" />
