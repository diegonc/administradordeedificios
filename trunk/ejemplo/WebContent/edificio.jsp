<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
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
		<form class="elegante" id="gastoEdificio" action="EdificioAction">
			<fieldset>
		  		<legend>Alta en el servicio</legend>
			 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
			 			<tr><td colspan="8" height="10"></td></tr>
			 			<tr>
			 				<td align="right"><label for="nombre">Nombre:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="nombre" name="nombre" size="15"/></td>
				 			<td align="right" width="250"><label for="tipoPropiedad">Tipo Propiedad:</label> </td>
				 			<td colspan="4">&nbsp;&nbsp;<select id="tipoPropiedad" name="tipoProdiedad" ><option>tipo1</option><option>tipo1</option></select></td>
				 			
				 		</tr>
				 		<tr><td  colspan="8" height="3"></td></tr>
				 		<tr><td  colspan="8">Domicilio</td>	</tr>
				 		<tr>	
				 			<td align="right"><label for="calle">Calle:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="calle" name="calle" size="15"/></td>
				 			<td align="right"><label for="numero">Nro:</label> </td>
				 			<td >&nbsp;&nbsp;<input type="text" id="numero" name="numero" size="2"/></td>
				 			<td align="right"><label for="localidad">Localidad:</label> </td>
				 			<td colspan="3">&nbsp;&nbsp;<input type="text" id="localidad" name="localidad" size="15" /></td>
			 			</tr>
			 			<tr><td colspan="8" height="10"></td></tr>
				 		<tr><td colspan="8">Encargado</td></tr>
			 			<tr>	
				 			<td align="right"><label for="encargado_nombre">Nombre:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="encargado_nombre" name="encargado_nombre" size="15"/></td>
				 			<td align="right" ><label for="encargado_telefono">Tel:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="encargado_telefono" name="encargado_telefono" size="9"/></td>
				 			<td align="right" ><label for="encargado_piso">Piso:</label> &nbsp;&nbsp;<select id="encargado_piso" name="encargado_piso" ></select></td>
				 			<td align="right" colspan="3"><label for="encargado_depto">Depto:</label> &nbsp;&nbsp;<select id="encargado_depto" name="encargado_depto" ></select></td>
			 			</tr>			
			 			<tr><td colspan="8" height="10"></td></tr>	  		
			 			<tr>
				 			<td><label for="apto_profesional">Apto Profesional</label></td>
				 			 <td colspan="7">&nbsp;&nbsp;<input type="checkbox" id="apto_profesional" name="apto_profesional" /></td>
				 		</tr>				 		
				  		<tr><td colspan="8" height="10"></td></tr>  		
				  		<tr><td class="borderline" colspan="8" height="10"></td></tr>
				  		<tr>	
				  			<td align="right" ><label for="formaliq_exp">Forma Liq.:&nbsp;</label>  </td>
				  			<td colspan="7">&nbsp;&nbsp;<select  id="formaliq_exp" name="formaliq_exp"> <option>forma1</option><option>forma1</option></select></td>
				  		</tr>
				  		<tr><td colspan="8" height="10"></td></tr>
				  		<tr>	
				  			<td align="right" ><label for="plan" ></label>  Planes de Pago:&nbsp;</td>
				  			<td >&nbsp;&nbsp;<select id="plan" name="plan" ><option>Ninguno</option><option>Plan1</option></select></td>
				  			<td align="right" ><label for="tasa_anual" ></label>  Tasa Anual:&nbsp;</td>
				  			<td>&nbsp;&nbsp;<input type="text" id="tasa_anual" name="tasa_anual" size="2"/></input></td>
				  			<td align="right" ><label for="amortizacion" ></label>  Amortizacion:&nbsp;</td>
				  			<td>&nbsp;&nbsp;<input type="text" id="amortizacion" name="amortizacion" size="3"/></input></td>
				  			<td colspan="2"></td>
				  			
				  		</tr>
				  		<tr><td colspan="8" height="10"></td></tr>
				 		<tr><td colspan="8">Fondo</td></tr>
				  		<tr>	
				 			<td align="right"><label for="dia_primer_vto">Primer Vto:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="dia_primer_vto" name="dia_primer_vto" size="9"/></td>
				 			<td align="right" ><label for="dia_segundo_vto"> Seg Vta:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="dia_segundo_vto" name="dia_segundo_vto" size="9"/></td>
				 			
			 			</tr>
				  		<tr>
			  			<td colspan="8"><input class="btn" type="submit" value="Add"  /></td>
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
