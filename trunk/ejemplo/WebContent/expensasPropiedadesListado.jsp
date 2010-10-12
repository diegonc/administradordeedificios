<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@page import="propiedades.PropiedadDTO"%>
<%@page import="propiedades.TipoPropiedadDTO"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="edificio.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html" import="org.hibernate.*"%>
<% 
	EdificioAppl edifAppl = new EdificioAppl();
	SessionFactory factory = HibernateUtil.getSessionFactory();
	int id = Integer.parseInt(request.getParameter("id"));
	EdificioDTO edificio = edifAppl.getEdificio(factory, id);
	Set<TipoPropiedadDTO> tipos = edificio.getTipoPropiedades();
	Iterator <TipoPropiedadDTO> iteradorTipos = tipos.iterator();
%>

<table cellpadding="0" cellspacing="0">
	<tr>
		<td width="5" class="borde"></td>
		<td width="800" class="borde" align="center">
		<h3 id="header">Expensas - Listado de Propiedades del edifcio <%= edificio.getNombre()%></h3>
		</td>
		<td width="5" class="borde"></td>
	</tr>
</table>
<tr>
	<td width="5" class="borde"></td>
	<td width="15" class="fondo"></td>
	<td width="770" class="fondo" align="left">
	<table width="500" border="1" class="listado">
		<tr>
			<td>Tipo Propieadad</td>
			<td></td>
			<td></td>
		</tr>
		<%
			while (iteradorTipos.hasNext()) {
				TipoPropiedadDTO tipoProp = iteradorTipos.next();
		%>
		<tr>
			<td><%=tipoProp.getNombreTipo()%></td>
			<td><a href="#">Eliminar</a></td>
			<td><a href="#">Modificar</a></td>
		</tr>
		<%
			}
		%>
	</table>
</tr>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />