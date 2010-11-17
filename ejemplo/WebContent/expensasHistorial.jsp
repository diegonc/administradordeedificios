<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="propiedades.Responsable"%>
<jsp:useBean id="responsables" scope="session" class="beans.ResponsablesBean"/>
<script type="text/javascript">
function cargarDni(){
	var index = document.getElementById("respon_selected");
	var dniDirect =  document.getElementById("dni");
	dniDirect.value =index.value;
}
</script>
<%List<Responsable> listaResponsable = responsables.getResponsables(); %>
<div class="contenido">
	<div class="titulo"><h3>Historial de Liquidaciones</h3></div>
		<div class="cuerpo">
		<table>
			<tr>
				<td width="15"  class="fondo"></td>
				<td width="770" class="fondo" align="left">
					<form class="elegante" name="expensasHistorialAction" action="expensasHistorialAction!mostrar">
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
						<input type="submit" value="ver historial" onclick="cargarDni()">
					</form>
				</td>
				<td width="15"  class="fondo"></td>
			</tr>		
		</table>	
		</div>
	</div>




<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>