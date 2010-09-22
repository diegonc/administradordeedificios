<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<script type="text/javascript">
function habilitarInputsMontos(){
	var montoVariable= document.getElementById("montoVariable");
	var montoFijo= document.getElementById("montoFijo");
	document.getElementById("tgMontoVariable.montoPrevision").disabled=(montoVariable.checked==true)?"":"disabled";
	document.getElementById("tgMontoVariable.proximoVencimiento").disabled=(montoVariable.checked==true)? "":"disabled";
	document.getElementById("tgMontoFijo.montoActual").disabled=(montoFijo.checked==true)?"":"disabled";
	document.getElementById("tgMontoFijo.diaLimite").disabled=(montoFijo.checked==true)? "":"disabled";

}
function habilitarInputsOrdinario(){
	var extraordinario= document.getElementById("tipoExtraOrdinario");
	document.getElementById("eventual").disabled=(extraordinario.checked==true)?"disabled":"";
	document.getElementById("periodico").disabled=(extraordinario.checked==true)? "disabled":"";
	if (extraordinario.checked==true){
		document.getElementById("tgMontoFijo.periodo").disabled="disabled";
		document.getElementById("montoFijo").disabled="disabled";
		document.getElementById("montoVariable").disabled="disabled";
		document.getElementById("tgMontoFijo.montoActual").disabled="disabled";
		document.getElementById("tgMontoVariable.montoPrevision").disabled="disabled";
		document.getElementById("tgMontoFijo.diaLimite").disabled="disabled";
		document.getElementById("tgMontoVariable.proximoVencimiento").disabled="disabled";
	}
}

function habilitarInputsPlazo(){
	var periodico = document.getElementById("periodico");
	document.getElementById("tgMontoFijo.periodo").disabled=(periodico.checked==true)?"":"disabled";
	document.getElementById("montoFijo").disabled=(periodico.checked==true)?"":"disabled";
	document.getElementById("montoVariable").disabled=(periodico.checked==true)?"":"disabled";
	if(periodico.checked==true){
		document.getElementById("tgMontoFijo.montoActual").disabled="disabled";
		document.getElementById("tgMontoVariable.montoPrevision").disabled="disabled";
		document.getElementById("tgMontoFijo.diaLimite").disabled="disabled";
		document.getElementById("tgMontoVariable.proximoVencimiento").disabled="disabled";
	}	
}
</script>




<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"> <span id="header"><h>Alta de Tipo de Gastos</h></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaTiposDeGastos" height ="300" cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
		<form class="elegante" id="tipoDeGastoAlta" action="TipoDeGastosAction">
			<fieldset>
		  		<legend>Alta en el servicio</legend>
			 		<table border="0" cellpadding="0" cellspacing="0">
			 			<tr><td colspan="6" height="10"></td></tr>
			 			<tr>
				 			<td width="120" align="right"><label for="codigo">Codigo:</label> </td>
				 			<td colspan="4" >&nbsp;&nbsp;<input type="text" id="codigo" name="codigo" /><font color="red">*&nbsp;&nbsp;</font></td>
				 		</tr>
				 		<tr>
				 			<td align="right"><label for="descripcion">Descripci&oacute;n:</label> </td>
				 			<td colspan="4">&nbsp;&nbsp;<input type="text" id="descripcion" name="descripcion" /><font color="red">*&nbsp;&nbsp;</font></td>
			 			</tr>
			 			<tr><td colspan="6" height="10"></td></tr>
				  		
			 			<tr>
				 			<td><label for="tipoOrdinario"></label><input type="radio" id="tipoOdinario" name="tipoGasto" value="ordinario" onclick="habilitarInputsOrdinario()" />Ordinario</td>
				 			<td colspan="4"> </td>
				 		</tr>	
				 		<tr>	
					  		<td><label for="tipoExtraOrdinario"></label><input type="radio" id="tipoExtraOrdinario" name="tipoGasto" value="extraordinario" onclick="habilitarInputsOrdinario()" checked="checked"/> Extraordinario</td>
					  		<td colspan="4"></td>
				  		</tr>
				  		<tr><td colspan="6" height="10"></td></tr>
				  		<tr>
				  			<td> <label for="eventual"></label><input type="radio" id="eventual" name="gastoPlazo" disabled="disabled" value="eventual" onclick="habilitarInputsPlazo()"/>Eventual</td>
				  			<td colspan="4"></td>
				  		</tr>
				  		<tr>	
				  			<td><label for="periodico"></label> <input type="radio" id="periodico" name="gastoPlazo" value="periodico" disabled="disabled" onclick="habilitarInputsPlazo()"/> Peri&oacute;dico</td>
				  			<td colspan="4">&nbsp;&nbsp;<label for="tgMontoFijo.periodo">Per&iacute;odo:</label> <input type="text" id="tgMontoFijo.periodo"  name="tgMontoFijo.periodo" disabled="disabled" /></td>
				  		</tr>
				  		<tr><td colspan="6" height="10"></td></tr>
				  		<tr>
				  		<td colspan="4"><span>Monto</span></td><td></td>
				  		</tr>
				  		<tr>
				  			<td><label for="montoFijo"></label> <input type="radio" id="montoFijo" name="tipoMonto" value="fijo" disabled="disabled" onclick="habilitarInputsMontos()"/> Fijo</td>
				  			<td><label for="tgMontoFijo.montoActual">Monto Actual:</label> </td>
				  			<td><input type="text" id="tgMontoFijo.montoActual" name="tgMontoFijo.montoActual" disabled="disabled"/></td>
				  			<td><label for="tgMontoFijo.diaLimite">&nbsp;D&iacute;a L&iacute;mite:&nbsp;</label></td> 
				  			<td><input type="text" id="tgMontoFijo.diaLimite" name="tgMontoFijo.diaLimite" disabled="disabled"/></td>
				  		</tr>
				  		<tr><td colspan="6" height="10"></td></tr>
				  		<tr>
				  			<td><label for="montoVariable"></label> <input type="radio" id="montoVariable" name="tipoMonto" value="variable" disabled="disabled" onclick="habilitarInputsMontos()"/> Variable</td>
				  			<td><label for="tgMontoVariable.montoPrevision">Monto Previsi&oacute;n:&nbsp;</label></td> 
				  			<td><input type="text" id="tgMontoVariable.montoPrevision" name="tgMontoVariable.montoPrevision" disabled="disabled"/></td>
				  			<td><label for="tgMontoVariable.proximoVencimiento">&nbsp;Prox. Vto:&nbsp;</label></td>
				  			<td> <input type="text" id="tgMontoVariable.proximoVencimiento" name="tgMontoVariable.proximoVencimiento" disabled="disabled"/></td>
				  		</tr>			  		
			  	  		<tr>
			  			<td colspan="4"><input class="btn" type="submit" value="Add"  /></td>
			  			</tr>
			  		</table>
			  		<input type="hidden" id="tgExtraordinario.codigo" name="tgExtraordinario.codigo" value="" >
			  		<input type="hidden" id="tgEventual.codigo" name="tgEventual.codigo" value="" >
			  		<input type="hidden" id="tgMontoFijo.codigo" name="tgMontoFijo.codigo" value="" >
			  		<input type="hidden" id="tgMontoVariable.codigo" name="tgMontoVariable.codigo" value="" >
			</fieldset>
		</form>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>


</table>

<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>