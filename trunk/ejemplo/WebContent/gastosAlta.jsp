<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"> <span id="header"><h>Gastos</h></span></td>
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
							<td></td>			 			
				 			<td width="120" align="right"><label for="codigo">Edificio:</label> </td>
				 			<td  >&nbsp;&nbsp;<select id="edificio" name="edificio" /></select></td>				 			
				 			<td align="right"><label for="gasto">Tipo de Gasto:</label> </td>
				 			<td colspan="2">&nbsp;&nbsp;<select id="gasto" name="gasto" /></select></td>
			 			</tr>
			 			<tr>
			 				<td width="120"><label for="nroFolio" >Nro Folio :</label> </td>
				 			<td><input type="text" id="nroFolio" name="nroFolio" size="9"></input></td>				 			
				 			<td width="120" align="right"><label for="monto" >Monto:</label> </td>
				 			<td >&nbsp;&nbsp;<input type="text" id="monto" name="monto" size="9" /></td>
				 			<td width="120" align="right"><label for="detalle">Detalle:</label> </td>
				 			<td  >&nbsp;&nbsp;<input type="text" id="detalle" name="detalle" /></td>
			 			</tr>
			 			
			 			<tr><td colspan="6" height="10"></td></tr>
				  		
			 			<tr>
			 				<td></td>		
				 			<td><label for="previsional"></label><input type="radio" id="previsional" name="tipo_gasto" />Previsional</td>
				 			<td align="right"><label for="anio">Anio:&nbsp;</label> </td>
				  			<td><input type="text" id="anio" name="anio" disabled="disabled"/></td>
				  			<td align="right" width="150"><label for="mes">&nbsp;Mes:&nbsp;</label></td> 
				  			<td><input type="text" id="mes" name="mes" disabled="disabled"/></td>
				 		</tr>
				 		<tr>
				 			<td></td>		
				 			<td><label for="real"></label><input type="radio" id="real" name="tipo_gasto"/>Real &nbsp;</td>
				 			<td align="right"><label for="razon_social">Razon Social:&nbsp;</label> </td>
				  			<td><input type="text" id="razon_social" name="razon_social" disabled="disabled"/></td>
				  			<td colspan="2"></td>
				 		</tr>	
				 		
				  		<tr><td colspan="6" height="10"></td></tr>
				  					  		
			  	  		<tr>
			  			<td colspan="5"><input class="btn" type="submit" value="Add"  /></td>
			  			<td> <a href="gastos.jsp">Volver</a> </td>
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