<%@page import="propiedades.ResponsableAppl"%>
<%@page import="utilidades.HibernateUtil"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="propiedades.Responsable"%>
<div class="contenido">
	<div class="titulo"><h3>Error en Plan</h3></div>
		<div class="cuerpo">
		<fieldset><legend>Error</legend>	
		<table>
			<tr>
				<td width="15"  class="fondo"></td>
				<td width="770" class="fondo" align="left">
					<s:actionerror cssClass="error"/>
					<a href="javascript:history.back()">volver </a>
				</td>
				<td width="15"  class="fondo"></td>
			</tr>		
		</table>	
		</fieldset>
		</div>
	</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>