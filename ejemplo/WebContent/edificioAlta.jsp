<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<script type="text/javascript">

function habilita(){
	var punitorio= document.getElementById("punitorio");
	var afecha= document.getElementById("afecha");
	
	document.getElementById("dia_segundo_vto").disabled=(afecha.checked==true)?"":"disabled";
	document.getElementById("dia_segundo_vto").disabled=(punitorio.checked==true)?"":"disabled";
}

function validar(thisform) {
	validado=true;
	var nombre = document.getElementById("nombre");
	var calle = document.getElementById("calle");
	var numero = document.getElementById("numero");
	var localidad = document.getElementById("localidad");
	var encargado_nombre = document.getElementById("encargado_nombre");
	var encargado_telefono = document.getElementById("encargado_telefono");
	var encargado_piso = document.getElementById("encargado_piso");
	var encargado_depto = document.getElementById("encargado_depto");
	var tasa_anual = document.getElementById("tasa_anual");
	var dia_primer_vto = document.getElementById("dia_primer_vto");
	var dia_segundo_vto = document.getElementById("dia_segundo_vto");
	
	var punitorio= document.getElementById("punitorio");
	var afecha= document.getElementById("afecha");
	var diferido= document.getElementById("diferido");
	
	if(nombre.value=="") { 
		alert("Debe completar el nombre"); 
		validado=false;
	} 
	if(calle.value=="" && validado == true) { 
		alert("Debe completar la calle"); 
		validado=false;
	}
	if( (numero.value=="" || isNaN(numero.value)) && validado == true) { 
		alert("Debe completar el numero"); 
		validado=false;
	}
	if(localidad.value=="" && validado == true) { 
		alert("Debe completar la localidad"); 
		validado=false;
	}
	if(encargado_nombre.value=="" && validado == true) { 
		alert("Debe completar el nombre del encargado"); 
		validado=false;
	}
	if(encargado_telefono.value=="" && validado == true) { 
		alert("Debe completar el telefono del encargado"); 
		validado=false;
	}
	if( (encargado_piso.value=="" || isNaN(encargado_piso.value)) && validado == true ) { 
		alert("El piso debe ser un numero"); 
		validado=false;
	}
	if(encargado_depto.value=="" && validado == true) { 
		alert("Debe completar el depto del encargado"); 
		validado=false;
	}
	if(isNaN(tasa_anual.value) && validado == true ) { 
		alert("Debe completar el monto de la tasa anual y debe ser un numero"); 
		validado=false;
	}
	if((isNaN(dia_primer_vto.value) || (dia_primer_vto.value < 1) || (dia_primer_vto.value > 15)) && validado == true ) { 
		alert("Debe completar el dia del primer vto"); 
		validado=false;
	} 
	if((dia_segundo_vto.value!="") && (isNaN(dia_segundo_vto.value)|| (dia_segundo_vto.value < 0) || (dia_segundo_vto.value > 15)) && validado == true ) { 
		alert("Debe completar el dia del segundo vto"); 
		validado=false;
	} 
	if ((punitorio.checked==true) && (dia_segundo_vto.value=="") && validado == true ) {
		alert("Si es punitorio debe completar el dia del segundo vto"); 
		validado=false;
	}
	if ((dia_primer_vto.value > dia_segundo_vto.value) && (validado == true) && (punitorio.checked==true)){
		alert("El segundo vto debe ser posterior al primero"); 
		validado=false;
	}
	if (validado==true) {
		document.altaEdificio.submit();
	}
	return validado;
}
</script>
<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"><h3 id="header">Edificios</h3></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaGastoGrales" height ="300" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
		<form class="elegante" name="altaEdificio" id="altaEdificio" action="EdificioAction">
			<fieldset>
		  		<legend>Alta de Edificios</legend>
			 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
			 			<tr><td colspan="8" height="10"></td></tr>
			 			<tr>
			 				<td align="right"><label for="nombre">Nombre:</label></td>
				 			<td>&nbsp;&nbsp;<input type="text" id="nombre" name="nombre" size="15"/></input><font color="red">*&nbsp;&nbsp;</font> </td>
				 		</tr>
				 		<tr><td  colspan="8" height="3"></td></tr>
				 		<tr><td  colspan="8">Domicilio<font color="red"> *&nbsp;&nbsp;</font></td>	</tr>
				 		<tr>	
				 			<td align="right"><label for="calle">Calle:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="calle" name="calle" size="15"/></td>
				 			<td align="right"><label for="numero">Nro:</label> </td>
				 			<td >&nbsp;&nbsp;<input type="text" id="numero" name="numero" size="2"/></td>
				 			<td align="right"><label for="localidad">Localidad:</label> </td>
				 			<td colspan="3">&nbsp;&nbsp;<input type="text" id="localidad" name="localidad" size="15" /></td>
			 			</tr>
			 			<tr><td colspan="8" height="10"></td></tr>
				 		<tr><td colspan="8">Encargado<font color="red"> *&nbsp;&nbsp;</font> </td></tr>
			 			<tr>	
				 			<td align="right"><label for="encargado_nombre">Nombre:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="encargado_nombre" name="encargado_nombre" size="15"/></td>
				 			<td align="right" ><label for="encargado_telefono">Tel:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="encargado_telefono" name="encargado_telefono" size="9"/></td>
				 			<td align="right" ><label for="encargado_piso">Piso</label> &nbsp;&nbsp;
				 			<input type="text" id="encargado_piso" name="encargado_piso" size="2" />
				 			<label for="encargado_depto">Dpto</label> &nbsp;&nbsp;
				 			<input type="text" id="encargado_depto" name="encargado_depto" size="2" />
				 		
			 			</tr>			
			 			<tr><td colspan="8" height="10"></td></tr>	  		
			 			<tr>
				 			<td><label for="apto_profesional">Apto Profesional</label></td>
				 			 <td colspan="7">&nbsp;&nbsp;<input type="checkbox" value="true" id="apto_profesional" name="apto_profesional" /></td>
				 		</tr>				 		
				  		<tr><td colspan="8" height="10"></td></tr>  		
				  		<tr><td class="borderline" colspan="8" height="10"></td></tr>
				  		<tr>	
				  			<td align="right" ><label for="formaliq_exp">Forma Liq.:&nbsp;</label>  </td>
				  			<td colspan="7">&nbsp;&nbsp;<select  id="formaliq_exp" name="formaliq_exp"> <option>FIJO</option><option>PRORRATEO</option></select></td>
				  		</tr>
				  		<tr><td colspan="8" height="10"></td></tr>
				  		<tr>	
				  			<td align="right" ><label for="tasa_anual" ></label>  Tasa Anual:&nbsp;</td>
				  			<td>&nbsp;&nbsp;<input type="text" id="tasa_anual" name="tasa_anual" size="2"/></input><font color="red">*&nbsp;&nbsp;</font></td>
				  			<td align="right" ><label for="amortizacion" ></label>  Amortizacion:&nbsp;</td>
				  			<td colspan="7">&nbsp;&nbsp;<select  id="amortizacion" name="amortizacion"> <option>ALEMAN</option><option>FRANCÉS</option></select></td>
				  			<td colspan="2"></td>
				  			
				  		</tr>
				  		<tr><td colspan="8">Calculo de Interes</td></tr>
				  		<tr>	
				 			<td>&nbsp;&nbsp;<label for="punitorio"></label><input type="radio" id="punitorio" name="mora" value="punitorio"  checked="checked" onclick="habilita()"/>Punitorio</td>
				 			<td>&nbsp;&nbsp;<label for="afecha"></label><input type="radio" id="afecha" name="mora" value="afecha" onclick="habilita()" />A fecha de pago &nbsp;</td>
			 				<td>&nbsp;&nbsp;<label for="diferido"></label><input type="radio" id="diferido" name="mora" value="diferido" onclick="habilita()" />Diferido &nbsp;</td>
			 			</tr>
			 			<tr><td colspan="8" height="10"></td></tr>
				 		<tr><td colspan="8">Fondo</td></tr>
				  		<tr>	
				 			<td align="right"><label for="dia_primer_vto">Primer Vto:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="dia_primer_vto" name="dia_primer_vto" size="9"/><font color="red">*&nbsp;&nbsp;</font></td>
				 			<td align="right" ><label for="dia_segundo_vto"> Seg Vta:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="dia_segundo_vto" name="dia_segundo_vto" size="9"/></td>
				 			
			 			</tr>
				  		<tr>
			  			<td colspan="6"><input class="btn" type="button" value="Add" onclick="validar()" /></td>
			  			<td> <a href="EdificioListarAction">Volver</a> </td>
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
