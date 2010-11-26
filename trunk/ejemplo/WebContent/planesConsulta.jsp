<%@page import="propiedades.ResponsableAppl"%>
<%@page import="utilidades.HibernateUtil"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
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
									 		<select id="respon_selected">
									 		<%for (Responsable resp:listaResponsable) {%>
									 				<option value="<%=resp.getDni()%>"><%=resp.getDni()%></option>
									 			<%} %>
									 		</select>								
								 		</td>
							 		</tr>
								</table>
						</fieldset>
						<input type="hidden" name="dni"  ></input>
						<input type="submit" value="Ver Planes" onclick="cargarDni()">
					</form>
				</td>
				<td width="15"  class="fondo"></td>
			</tr>		
		</table>	
		</div>
	</div>




<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>