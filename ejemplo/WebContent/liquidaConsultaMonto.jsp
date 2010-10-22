<%@page import="expensas.*"%>

<%@page import="propiedades.PropiedadDTO"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="expensas.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html" import="org.hibernate.*"%>
<%
	int idEdificio = Integer.parseInt(request.getParameter("idEdificio"));
	int idProp = Integer.parseInt(request.getParameter("idProp"));	 
	ExpensaAppl expAppl = new ExpensaAppl();
	SessionFactory factory = HibernateUtil.getSessionFactory();	
	List<ExpensaDTO> expensas = expAppl.getAllExpensaPorIdProp(factory,idEdificio);
	//TODO ver si solo queda un solo registro
	ExpensaDTO exp = expensas.get(0);
%>

<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"><h3 id="header">Consulta Monto Expensas a Pagar</h3></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaRegisCobros" height ="300" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
			<fieldset>
		  		<legend>Monto de Expensas a Pagar</legend>
			 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
			 			<tr><td colspan="8" height="10"></td></tr>
			 			<tr>
			 				<td align="right"><label for="edificio">Edificio: <%=exp.getPropiedad().getTipoPropiedad().getEdificio().getNombre()%></label> </td>
			 			</tr>
			 			<tr>
			 				<td align="right"><label for="Nivel">Nivel: <%=exp.getPropiedad().getNivel()%></label> </td>
			 			</tr>
			 			<tr>
			 				<td align="right"><label for="Orden">Orden: <%=exp.getPropiedad().getOrden()%></label> </td>
			 			</tr>
			 			<tr>
			 				<td align="right"><label for="monto">Monto: <%=exp.getMonto()%></label> </td>
				 		</tr>
				  		<tr>
			  			<td> <a
					href="expensasPropiedadesListado.jsp?id=<%=idEdificio%>">Volver</a> </td>
			  			</tr>
			  		</table>			  	
			</fieldset>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
