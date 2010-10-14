<%@page import="gastos.appl.GastosAppl"%>
<%@page import="gastos.dto.GastoRealDTO"%>
<%@page import="gastos.dto.GastoPrevisionDTO"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="gastos.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html" import="org.hibernate.*"%>
<%
	GastosAppl edifAppl = new GastosAppl();
	SessionFactory factory = HibernateUtil.getSessionFactory();	
	int id = Integer.parseInt(request.getParameter("id"));
	GastoPrevisionDTO gasto = edifAppl.getGastosPrevisionPorid(factory,id);
%>

<script type="text/javascript">
function validar(thisform) {
	valido=true;
	var folio = document.getElementById("folio");
	var monto = document.getElementById("monto");
	var mes = document.getElementById("mes");
	var anio = document.getElementById("anio");
	
	if (isNaN(folio.value)) {
		valido=false;
		alert("El folio debe ser un numero");
	}
	if (isNaN(monto.value) && monto.value) {
		valido=false;
		alert("El monto debe ser un numero");
	}
	if (isNaN(mes.value) && mes.value<1 && mes.value>12 && valido==true){
		valido=false;
		alert("El Mes debe ser un numero entre 1 y 12");
	}
	if (isNaN(anio.value) && valido==true){
		valido=false;
		alert("El Anio debe ser un numero");
	}
	if (valido==true) {
		document.modifGastoPrevi.submit();
	}
}
</script>
<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"><h3 id="header">Gastos Prevision</h3></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaGastoGrales" height ="300" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
		<form class="elegante" name="modifGastoPrevi" id="modifGastoPrevi" action="GastoPrevisionModifAction">
			<fieldset>
		  		<legend>Modificar Gasto Prevision Futuro</legend>
			 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
			 			<tr><td colspan="8" height="10"></td></tr>
			 			<tr>
			 				<td align="right"><label for="nombre">Folio:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="folio" name="folio" value="<%=gasto.getNumeroFolio() %>" size="15"/></td>
			 				<td align="right"><label for="nombre">Detalle:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="detalle" name="detalle" value="<%=gasto.getDetalle() %>" size="30"/></td>	
				 		</tr>
				 		<tr>
				 			<td align="right"><label for="nombre">Monto:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="monto" name="monto" value="<%=gasto.getMonto() %>" size="15"/></td>					 			
				 			<td align="right"><label for="nombre">Mes:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="mes" name="mes" value="<%=gasto.getMes() %>" size="2"/></td>		
				 			<td align="right"><label for="nombre">Anio:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="anio" name="anio" value="<%=gasto.getAnio() %>" size="4"/></td>
				 		
				 		
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="id" name="id" value="<%=gasto.getId() %>" readonly size="15"/></td>
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="id_tipo_gasto" name="id_tipo_gasto" value="<%=gasto.getTipoGasto().getId() %>" readonly size="15"/></td>
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="codigo_tipo_gasto" name="codigo_tipo_gasto" value="<%=gasto.getTipoGasto().getCodigo() %>" readonly size="15"/></td>
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="descripcion_tipo_gasto" name="descripcion_tipo_gasto" value="<%=gasto.getTipoGasto().getDescripcion() %>" readonly size="15"/></td>
				 			<td>&nbsp;&nbsp;<input type="text" style="display: none;" id="edificio_id" name="edificio_id" value="<%=gasto.getEdificio().getId() %>" readonly size="15"/></td>
				 		</tr>
				  		<tr>
			  			<td colspan="8"><input class="btn" type="button" value="Modificar" onclick="validar()" /></td>
			  			<td> <a href="GastosListarModifElimAction?id=<%=gasto.getEdificio().getId()%>">Volver</a> </td>
			  			</tr>
			  		</table>			  	
			</fieldset>
		</form>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
