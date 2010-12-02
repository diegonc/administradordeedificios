
<%@page import="permisos.AdministradorDePermisos"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="propiedades.Responsable"%>
<%@ page language="java" contentType="text/html" import="edificio.EdificioDTO"%>
<jsp:useBean id="responsables" scope="session" class="beans.ResponsablesBean"/>
<script type="text/javascript">
function cargarDni(){
	var index = document.getElementById("respon_selected");
	var dniDirect =  document.getElementById("dni");
	dniDirect.value =index.value;
}
</script>
<%
	List<Integer> listaResponsable = responsables.getListaDNIs();
	List<EdificioDTO> listaEdificios = AdministradorDePermisos.getInstancia().getEdificiosAdministrador();
%>
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
						 		<table  border="0" cellpadding="0" cellspacing="5" >
							 		<tr>
								 		<td>								 		
									 		<select id="dni" name="dniElegido">
									 		<%for (Integer resp:listaResponsable) {%>
									 				<option value="<%=resp%>"><%=resp%></option>
									 			<%} %>
									 		</select>								
								 		</td>
							 		</tr>
							 		<tr>
								 		<td>	
								 			<input type="checkbox" name="consultarEdificio" value="true">
								 			<select name="idEdificio">
								 				<%for (EdificioDTO edificio:listaEdificios){ %>
								 				<option value="<%=edificio.getId()%>"><%=edificio.getNombre()%> </option>
								 				<%} %>
								 			</select>
								 		</td>								 		
							 		</tr>
							 		<tr>
							 			<td>
							 				<input type="radio" name="tipoExpensaElegida" value="O">Ordinaria
							 				<input type="radio" name="tipoExpensaElegida" value="E">Extraordinaria
							 			</td>
							 		</tr>
							 		<tr>
							 			<td>Nivel:&nbsp;<input type="text" name="nivel" size="10"></td>
							 		</tr>
							 		<tr>
							 			<td>Orden: &nbsp;<input type="text" name="orden" size="10" ></td>
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