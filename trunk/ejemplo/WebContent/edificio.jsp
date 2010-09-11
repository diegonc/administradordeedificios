<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"> <span id="header"><h>Edificios</h></span></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaGastoGrales" height ="300" cellpadding="0" cellspacing="0" border="2">
<tr>
<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
		<form class="elegante" id="gastoAlta" action="GastosAction">
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
				 			<td align="right"><label for="nroCalle">Nro:</label> </td>
				 			<td >&nbsp;&nbsp;<input type="text" id="nroCalle" name="nroCalle" size="2"/></td>
				 			<td align="right"><label for="localidad">Localidad:</label> </td>
				 			<td colspan="3">&nbsp;&nbsp;<input type="text" id="localidad" name="localidad" size="15" /></td>
			 			</tr>
			 			<tr><td colspan="8" height="10"></td></tr>
				 		<tr><td colspan="8">Encargado</td></tr>
			 			<tr>	
				 			<td align="right"><label for="calle">Nombre:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="nombreEncargado" name="nombreEncargado" size="15"/></td>
				 			<td align="right" ><label for="nroTel">Tel:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="nroTel" name="nroTel" size="9"/></td>
				 			<td align="right" ><label for="piso">Piso:</label> &nbsp;&nbsp;<select id="piso" name="piso" ></select></td>
				 			<td align="right" colspan="3"><label for="depto">Depto:</label> &nbsp;&nbsp;<select id="depto" name="depto" ></select></td>
			 			</tr>			
			 			<tr><td colspan="8" height="10"></td></tr>	  		
			 			<tr>
				 			<td><label for="aptoProfesional">Apto Profesional</label></td>
				 			 <td colspan="7">&nbsp;&nbsp;<input type="checkbox" id="aptoProfesional" name="aptoProfesional" /></td>
				 		</tr>				 		
				  		<tr><td colspan="8" height="10"></td></tr>  		
				  		<tr><td class="borderline" colspan="8" height="10"></td></tr>
				  		<tr>	
				  			<td align="right" ><label for="formaLiquidacion">Forma Liq.:&nbsp;</label>  </td>
				  			<td colspan="7">&nbsp;&nbsp;<select  id="formaLiquidacion" name="formaLiquidacion"> <option>forma1</option><option>forma1</option></select></td>
				  		</tr>
				  		<tr><td colspan="8" height="10"></td></tr>
				  		<tr>	
				  			<td align="right" ><label for="plan" ></label>  Planes de Pago:&nbsp;</td>
				  			<td >&nbsp;&nbsp;<select id="plan" name="plan" ><option>Ninguno</option><option>Plan1</option></select></td>
				  			<td align="right" ><label for="tasaAnual" ></label>  Tasa Anual:&nbsp;</td>
				  			<td>&nbsp;&nbsp;<input type="text" id="tasaAnual" name="tasaAnual" size="2"/></input></td>
				  			<td align="right" ><label for="amortizacion" ></label>  Amortizacion:&nbsp;</td>
				  			<td>&nbsp;&nbsp;<input type="text" id="amortizacion" name="amortizacion" size="3"/></input></td>
				  			<td colspan="2"></td>
				  			
				  		</tr>
				  		<tr><td colspan="8" height="10"></td></tr>
				 		<tr><td colspan="8">Fondo</td></tr>
				  		<tr>	
				 			<td align="right"><label for="fdoOrd">Ordinario:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="fdOrd" name="fdoOrd" size="9"/></td>
				 			<td align="right" ><label for="fdoExt"> Extraordinario:</label> </td>
				 			<td>&nbsp;&nbsp;<input type="text" id="fdoExt" name="fdoExt" size="9"/></td>
				 			
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
