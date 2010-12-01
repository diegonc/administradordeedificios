<%@page import="propiedades.ResponsableAppl"%>
<%@page import="utilidades.HibernateUtil"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="propiedades.Responsable"%>

<%
	ResponsableAppl respAppl = new ResponsableAppl();
	respAppl.setSession(HibernateUtil.getSession());
	List<Responsable> listaResponsable = respAppl.listar(); 
%>
<div class="contenido">
	<div class="titulo"><h3>Consulta de Planes</h3></div>
		<div class="cuerpo">
		<table>
			<tr>
				<td width="15"  class="fondo"></td>
				<td width="770" class="fondo" align="left">
					<form class="elegante" name="PlanesConsultaAction" action="PlanesConsultaAction">
						<fieldset>
					  		<legend>Elija Responsable</legend>
						 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
							 		<tr>
								 		<td>								 		
									 		<select name="dni">
									 		<%for (Responsable resp:listaResponsable) {%>
									 				<option value="<%=resp.getDni()%>"><%=resp.getDni()%></option>
									 			<%} %>
									 		</select>								
								 		</td>
							 		</tr>
								</table>
						</fieldset>
						<s:actionerror cssClass="error"/>
						<input type="submit" value="Ver Planes"/>
					</form>
				</td>
				<td width="15"  class="fondo"></td>
			</tr>		
		</table>	
		</div>
	</div>




<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>