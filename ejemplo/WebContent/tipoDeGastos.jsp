<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" contentType="text/html" import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html" import="edificio.*"%>
<jsp:useBean id="edificios" scope="session" class="beans.EdificiosBean"/>

<%
	ArrayList<EdificioDTO> edificiosList = edificios.getEdificios();
%>
<script src="calendario.js" type="text/javascript"></script>
<script type="text/javascript">

function armarFecha(elemento){
	var anio = document.getElementById("anio").value;
	var mes = document.getElementById("mes").value;
	var dia = document.getElementById("dia").value;
	elemento.value=dia+"/"+mes+"/"+anio;
}
function validar(){
	validado=true;
	var codigo = document.getElementById("codigo");
	var descripcion = document.getElementById("descripcion");
	var periodico = document.getElementById("periodico");
	var periodo =	document.getElementById("tgMontoFijo.periodo");
	var montoVariable= document.getElementById("montoVariable");
	var montoFijo= document.getElementById("montoFijo");
	var montoActual =document.getElementById("tgMontoFijo.montoActual");
	var montoPrevision =	document.getElementById("tgMontoVariable.montoPrevision");
	var diaLimite = document.getElementById("tgMontoFijo.diaLimite");
	var proximoVencimiento=document.getElementById("tgMontoVariable.proximoVencimiento");
	var extraordinario= document.getElementById("tipoExtraOrdinario");
	var extraordinario= document.getElementById("tipoExtraOrdinario");
	armarFecha(proximoVencimiento);

	if(codigo.value==""){ alert("Debe completar el codigo"); validado=false;}
	if(validado==true)if(descripcion.value==""){ alert("Debe completar la descripcion"); validado=false;}
	if(validado==true)if (extraordinario.checked==false){
		if (periodico.checked==true){
			if(periodo.value==""){ alert("Debe completar el periodo"); validado=false;}
			if(validado==true) if(montoFijo.checked==true){
				if(montoActual.value==""){ alert("Debe completar el monto actual"); validado=false;}
				if(validado==true)if(isNaN(montoActual.value)){ alert("El monto actual debe ser un valor numerico."); validado=false;}
				if(diaLimite.value==""){ alert("Debe completar el dia limite"); validado=false;}
				if(validado==true)if(isNaN(diaLimite.value)||(diaLimite.value<1 && diaLimite.value>31)){ alert("El dia limite debe ser un valor de 1 a 31."); validado=false;}
			}
			if(validado==true) if(montoVariable.checked==true){
				if(montoPrevision.value==""){ alert("Debe completar el monto previsional"); validado=false;}
				if(validado==true)if(isNaN(montoPrevision.value)){ alert("El monto previsional debe ser un valor numerico"); validado=false;}
				if(validado==true)if(proximoVencimiento.value==""){ alert("Debe completar el proximo Vto"); validado=false;}
				if(validado==true)if(/^[0-9]{1,2}\/[0-9]{1,2}\/[0-9]{4}$/.test(proximoVencimiento.value)==false ){ alert("El formato de fecha del proximo vencimiento deber ser dd/mm/aaaa"); validado=false;}
			}
		}
	}
	if (validado==false)return false;
	document.tipoDeGastoAlta.submit();
	return true;
}

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
		document.getElementById("tgMontoFijo.edificio.id").disabled="disabled";
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
	document.getElementById("tgMontoFijo.edificio.id").disabled=(periodico.checked==true)?"":"disabled";
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
	<td width="800" class="borde" align="center"> <h3 id="header">Alta de Tipo de Gastos</h3></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaTiposDeGastos" height ="300" cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
		<form class="elegante" id="tipoDeGastoAlta" name="tipoDeGastoAlta" action="TipoDeGastosAction">
			<fieldset>
		  		<legend>Alta en el servicio</legend>
			 		<table border="0" cellpadding="0" cellspacing="0">
			 			<tr><td colspan="5" height="10"></td></tr>
			 			<tr>
				 			<td width="120" align="right"><label for="codigo">C&oacute;digo:</label> </td>
				 			<td colspan="4" >&nbsp;&nbsp;<input type="text" id="codigo" name="codigo" /><font color="red">*&nbsp;&nbsp;</font></td>
				 		</tr>
				 		<tr>
				 			<td align="right"><label for="descripcion">Descripci&oacute;n:</label> </td>
				 			<td colspan="4">&nbsp;&nbsp;<input type="text" id="descripcion" name="descripcion"/><font color="red">*&nbsp;&nbsp;</font></td>
			 			</tr>
			 			<tr><td colspan="5" height="10"></td></tr>
				  		
			 			<tr>
				 			<td><label for="tipoOrdinario"></label><input type="radio" id="tipoOdinario" name="tipoGasto" value="ordinario" onclick="habilitarInputsOrdinario()" />Ordinario</td>
				 			<td colspan="4"> </td>
				 		</tr>	
				 		<tr>	
					  		<td><label for="tipoExtraOrdinario"></label><input type="radio" id="tipoExtraOrdinario" name="tipoGasto" value="extraordinario" onclick="habilitarInputsOrdinario()" checked="checked"/> Extraordinario</td>
					  		<td colspan="4"></td>
				  		</tr>
				  		<tr><td colspan="5" height="10"></td></tr>
				  		<tr>
				  			<td> <label for="eventual"></label><input type="radio" id="eventual" name="gastoPlazo" disabled="disabled" value="eventual" onclick="habilitarInputsPlazo()" checked="checked"/>Eventual</td>
				  			<td colspan="4"></td>
				  		</tr>
				  	 	<tr>	
				  			<td><label for="periodico"></label> <input type="radio" id="periodico" name="gastoPlazo" value="periodico" disabled="disabled" onclick="habilitarInputsPlazo()"/> Peri&oacute;dico</td>
				  			<td>&nbsp;&nbsp;<label for="tgMontoFijo.periodo" >Per&iacute;odo:</label> </td>
				  			<td><select id="tgMontoFijo.periodo"  name="tgMontoFijo.periodo" disabled="disabled" >
				  			<%for (int i=1;i<=12;i++){ %>
				  			<option value="<%=i%>"><%=i%></option>
				  			<%} %>
				  			</select>
				  			</td>
				  			<td align="right"><label for="tgMontoFijo.edificio.id">Edificio:</label></td>
							<td>&nbsp;&nbsp;<select name ="tgMontoFijo.edificio.id" id="tgMontoFijo.edificio.id" disabled="disabled">
					  			<%for (EdificioDTO edif: edificiosList){ %> 
								<option value="<%=edif.getId() %>"><%=edif.getNombre() %></option>
						  		<%} %>			 
								</select>
							</td>
				  		</tr>
				  		<tr><td colspan="5" height="10"></td></tr>
				  		<tr>
				  			<td colspan="4"><span>Monto</span></td>
				  			<td></td>
				  		</tr>
				  		<tr>
				  			<td><label for="montoFijo"></label> <input type="radio" id="montoFijo" name="tipoMonto" value="fijo" disabled="disabled" onclick="habilitarInputsMontos()" checked="checked"/> Fijo</td>
				  			<td><label for="tgMontoFijo.montoActual">Importe:</label> </td>
				  			<td><input type="text" id="tgMontoFijo.montoActual" name="tgMontoFijo.montoActual" disabled="disabled"/></td>
				  			<td><label for="tgMontoFijo.diaLimite">&nbsp;D&iacute;a L&iacute;mite:&nbsp;</label></td> 
				  			<td><input type="text" id="tgMontoFijo.diaLimite" name="tgMontoFijo.diaLimite" disabled="disabled"/></td>
				  		</tr>
				  		<tr><td colspan="5" height="10"></td></tr>
				  		<tr>
				  			<td><label for="montoVariable"></label> <input type="radio" id="montoVariable" name="tipoMonto" value="variable" disabled="disabled" onclick="habilitarInputsMontos()"/> Variable</td>
				  			<td><label for="tgMontoVariable.montoPrevision">Importe:&nbsp;</label></td> 
				  			<td><input type="text" id="tgMontoVariable.montoPrevision" name="tgMontoVariable.montoPrevision" disabled="disabled"/></td>
				  			<td><label for="tgMontoVariable.proximoVencimiento">&nbsp;Prox. Vto:&nbsp;</label></td>
				  			<td><input type="hidden" id="tgMontoVariable.proximoVencimiento" name="tgMontoVariable.proximoVencimiento" disabled="disabled"/>
				  				&nbsp;&nbsp;<input type="text" name="dia" maxlength="2" size="2" style="width:22px;" >
								&nbsp;<input type="text" name="mes" maxlength="2" size="2"  style="width:22px;" >
								&nbsp;<input type="text" name="anio" maxlength="4" size="4" style="width:32px;" >
								&nbsp;&nbsp;<a href="JavaScript:doNothing()" onclick="allowPrevious=true;setDateField(tipoDeGastoAlta.dia,document.tipoDeGastoAlta.mes,document.tipoDeGastoAlta.anio);top.newWin = window.open('calendario.jsp','cal','WIDTH=200,HEIGHT=160,TOP=200,LEFT=300')" onMouseOver="javascript: window.status = 'Abrir calendario'; return true;" onMouseOut="window.status=' '; return true;" >Fecha</a>
				  			
				  			</td>
				  		</tr>			  		
			  	  		
			  		</table>
			  		<input type="hidden" id="tgExtraordinario.codigo" name="tgExtraordinario.codigo" value="" >
			  		<input type="hidden" id="tgEventual.codigo" name="tgEventual.codigo" value="" >
			  		<input type="hidden" id="tgMontoFijo.codigo" name="tgMontoFijo.codigo" value="" >
			  		<input type="hidden" id="tgMontoVariable.codigo" name="tgMontoVariable.codigo" value="" >
			</fieldset>
			<s:actionerror cssClass="error"/>
			<input type="button" value="Aceptar"  onclick="validar()" >
			<input type="submit" value="Cancelar" name="redirectAction:GetListadoTipoDeGastoAction">
		</form>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>


</table>

<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>