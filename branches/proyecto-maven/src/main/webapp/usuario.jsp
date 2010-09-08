<jsp:include page="header.jsp"></jsp:include>
<script type="text/javascript">

function habilitarEdificio(thisform){
	var opcion = document.getElementById("perfiles");
	var edificios = document.getElementById("edificios");
	edificios.disabled=(opcion.value == "presidente_consorcio")?"":"disabled";
}
function validar(thisform){
	validado=true;
	var nombre = document.getElementById("nombre");
	var apellido = document.getElementById("apellido");
	var dni = document.getElementById("dni");
	var contrasena = document.getElementById("contrasena");
	if(nombre.value==""){ alert("Debe completar el nombre"); validado=false;}
	if(validado==true)if(apellido.value==""){ alert("Debe completar el apellido"); validado=false;}
	if(validado==true)if(dni.value==""){ alert("Debe completar el dni"); validado=false;}
	if(validado==true)if(contrasena.value==""){ alert("Debe completar la contrasena");validado=false;}
	return false;
}
</script>
<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"> <span id="header"><h>Usuario</h></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaGastoGrales" height ="300" cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
		<form class="elegante" id="usuarioAlta" action="Usuario_Action">
			<fieldset>
		  		<legend>Alta en el servicio</legend>
			 		<table>
			 			
				  		<label for="nombre">Nombre:</label> <input type="text" id="nombre" name="nombre" /><font color="red">*&nbsp;&nbsp;</font>
				  		<label for="apellido">Apellidos:</label>  <input type="text" id="apellido" name="apellido" size="30" /><font color="red">*</font>
				  		</br></br>
			  	  		<label for="dni">DNI:</label>  <input type="text" id="dni" size="10" maxlength="9" name="dni"/><font color="red">*&nbsp;&nbsp;</font>
			 	  		<label for="contrasena">Contrase&ntilde;a:</label>  <input type="password" id="contrasena" name="contrasena" /><font color="red">*&nbsp;&nbsp;</font>
			 			<label for="perfiles">Perfil:</label> 
			 			<select name ="perfiles" id="perfiles" onclick="habilitarEdificio()"> 
						  <option value="resp_gastos">Responsable de Gastos</option>
						  <option value="resp_cobros">Responsable de Cobros</option>
						  <option value="resp_edificios">Responsable de Edificios</option>
						  <option value="presidente_consorcio">Presidente de Consorcio</option>
						  <option value="concejero"> Consejero de Administrai&oacute;n</option>			 
						</select>
						</br></br> 
						<label for="edificios">Edificio:</label>
						<select name ="edificios" id="edificios" disabled="disabled">
					  		<option value="ed0">Todos</option>
							<%for (int i=1 ;i<20;i++){ %> 
							<option value="ed"+<%=i %>>Edificio <%=i %></option>
						  	<%} %>			 
						</select>
						</br></br>
			  			<input class="btn" type="submit" value="Add"  onclick="validar()"/>
			  		</table>
			  	</legend>
			</fieldset>
		</form>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>


</table>
<jsp:include page="footer.jsp"></jsp:include>