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

<table cellpadding="0" cellspacing="0">
	<tr>
		<td width="5" class="borde"></td>
		<td width="800" class="borde" align="center">
		<h3 id="header">Planes - Listado Propiedades edifcio " <%=edificio.getNombre()%>
		"</h3>
		</td>
		<td width="5" class="borde"></td>
	</tr>
</table>
<tr>
	<td width="5" class="borde"></td>
	<td width="15" class="fondo"></td>
	<td width="770" class="fondo" align="center">
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
			<td align="center"><a href="#">Calcular</a></td>
		</tr>
		<%
			}
			}
		}
		%>
	</table>
	</fieldset>
	<s:actionerror cssClass="error"/>	
	<a href="EdificioListarAction?redi=planes">Volver</a>
	</form>
</tr>

<%
	hSession.close();
%>

<jsp:include page="/WEB-INF/jspf/footer.jspf" />
