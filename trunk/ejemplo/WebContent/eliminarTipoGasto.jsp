<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<script type="text/javascript">

function validar(){
	validado=true;
	document.eliminarTipoGasto.submit();
	return true;
}
</script>
<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"><h3 id="header">Tipo de Gasto</h3></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaEliminarTipoGasto" height ="300" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
		<form class="elegante" name="eliminarTipoGasto" id="eliminarTipoGasto" action="GetListadoTipoDeGastoAction!eliminar">
			<fieldset>
		  		<legend>Eliminar Tipo de Gasto</legend>
			 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
			 			<tr><td colspan="8" height="10"></td></tr>
			 			<tr>
			 				<td align="right"><label for="nombre">Quiere eliminar el Tipo de Gasto:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="codigo" name="codigo" value="<%= request.getParameter("codigo") %>"  disabled="disabled" size="15"/></td>
				 			<td>&nbsp;&nbsp;<input type="hidden" id="id" name="id" value="<%= request.getParameter("id") %>" /></td>
				 		</tr>
				  		</table>			  	
			</fieldset>
			<input type="button" value="Aceptar"  onclick="validar()" >
			<input type="submit" value="Cancelar" name="redirectAction:GetListadoTipoDeGastoAction">
		</form>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
