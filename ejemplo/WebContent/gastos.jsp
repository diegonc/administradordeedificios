<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"> <span id="header"><h>Gastos</h></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaGasto" height ="300" cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
		<form class="elegante" id="gastoAlta" action="GastosAction">
			<fieldset>
		  		<legend>Alta en el servicio</legend>
			 		<table border="0" cellpadding="0" cellspacing="0">
			 			<tr><td colspan="6" height="10"></td></tr>
			 			<tr>
				 			<td width="120" align="right"><label for="codigo">Edificio:</label> </td>
				 			<td colspan="4" >&nbsp;&nbsp;<select id="edificio" name="edificio" /></td>
				 		</tr>
				 		<tr>	
				 			<td align="right"><label for="gasto">Tipo de Gasto:</label> </td>
				 			<td colspan="4">&nbsp;&nbsp;<select id="gasto" name="gasto" /></td>
			 			</tr>
			 			<tr><td colspan="6" height="10"></td></tr>
				  		
			 			<tr>
				 			<td><label for="previsional"></label><input type="radio" id="previsional" name="previsional" onclick="habilitarPrevisional()"/>Previsional</td>
				 			<td align="right"><label for="anio">Anio:&nbsp;</label> </td>
				  			<td><input type="text" id="anio" name="anio" disabled="disabled"/></td>
				  			<td align="right" widht="150"><label for="mes">&nbsp;Mes:&nbsp;</label></td> 
				  			<td><input type="text" id="mes" name="mes" disabled="disabled"/></td>
				 		</tr>
				 		<tr>
				 			<td><label for="real"></label><input type="radio" id="real" name="real" onclick="habilitarReal()"/>Real &nbsp;</td>
				 			<td width="120" align="right"><label for="razon_social">Razon Social:&nbsp;</label> </td>
				  			<td><input type="text" id="razon_social" name="razon_social" disabled="disabled"/></td>
				  			 <td colspan="2"></td>
				 		</tr>	
				 		
				  		<tr><td colspan="6" height="10"></td></tr>
				  		<tr>
				  			<td> <span>Pago</span></td>	<td colspan="4"></td>
				  		</tr>
				  		<tr>	
				  			<td align="right" ><label for="fecha">Fecha:&nbsp;</label>  </td>
				  			<td colspan="4"><input type="text" id="fecha" name="fecha" /></td>
				  		</tr>
				  		<tr>	
				  			<td align="right" ><label for="nrofactura" ></label>  Nro Factura:&nbsp;</td>
				  			<td colspan="4"><input type="text" id="nroFactura" name="nroFactura" /></td>
				  		</tr>
				  		<tr>	
				  			<td align="right"><label for="forma"></label>  Forma:&nbsp;</td>
				  			<td colspan="4"><input type="text" id="forma" name="forma" /></td>
				  		</tr>			  		
			  	  		<tr>
			  			<td colspan="4"><input class="btn" type="submit" value="Add"  /></td>
			  			</tr>
			  		</table>
			  	</legend>
			</fieldset>
		</form>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>



</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>