<%@page import="propiedades.ResponsableAppl"%>
<%@page import="utilidades.HibernateUtil"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="propiedades.Responsable"%>
<script type="text/javascript">

function cargarDni(){
	var index = document.getElementById("respon_selected");
	var dniDirect =  document.getElementById("dni");
	dniDirect.value =index.value;
}
</script>
<%
	int idEdif = Integer.parseInt(request.getParameter("idEdif"));
	ResponsableAppl respAppl = new ResponsableAppl();
	respAppl.setSession(HibernateUtil.getSession());
	List<Responsable> listaResponsable = respAppl.listar(); 
%>
<div class="contenido">
	<div class="titulo"><h3>Elegir Responsable de Plan</h3></div>
		<div class="cuerpo">
		<table>
			<tr>
				<td width="15"  class="fondo"></td>
				<td width="770" class="fondo" align="left">
					<form class="elegante" name="planesPropListarAction" action="planesPropListarAction">
						<fieldset>
					  		<legend>Elija Responsable para Plan</legend>
						 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
							 		<tr>
								 		<td>								 		
									 		<select id="respon_selected">
									 		<%for (Responsable resp:listaResponsable) {%>
									 				<option value="<%=resp.getDni()%>"><%=resp.getDni()%></option>
									 			<%} %>
									 		</select>								
								 		</td>
							 		</tr>
							 		<tr>
							 			<td> Expensas Ordinarias <input type="radio" id="tipo" name="tipo" checked="checked" value="ord" /></td>
										<td> Expensas Extraordinarias <input type="radio" id="tipo" name="tipo" value="ext" /></td>
									</tr>
								</table>
						</fieldset>
						<s:actionerror cssClass="error"/>
						<input type="hidden" name="dni"></input>
						<input type="hidden" name="id" value="<%=idEdif%>"></input>
						<input type="submit" value="Ver Expensas" onclick="cargarDni()">
					</form>
				</td>
				<td width="15"  class="fondo"></td>
			</tr>		
		</table>	
		</div>
	</div>




<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>