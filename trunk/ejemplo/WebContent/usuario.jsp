<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<script type="text/javascript">
<%@ page language="java" contentType="text/html" import="java.util.List"%>
<%@ page language="java" contentType="text/html" import="usuarios.dto.UsuarioDTO"%>
<%@ page language="java" contentType="text/html" import="usuarios.dto.PerfilDTO"%>
<jsp:useBean id="perfiles" scope="session" class="beans.PerfilesBean"/>

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
<table id="tablaUsuarios" height ="300" cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
		<form class="elegante" id="usuarioAlta" action="UsuarioAction">
			<fieldset>
		  		<legend>Alta en el servicio</legend>
			 		<table>
			 			<tr>
					  		<td><label for="nombre">Nombre:</label></td>
					  		<td> <input type="text" id="nombre" name="nombre" /><font color="red">*&nbsp;&nbsp;</font></td>
					  		<td><label for="apellido">Apellidos:</label></td>
					  		<td> <input type="text" id="apellido" name="apellido" size="30" /><font color="red">*</font></td>
					  	</tr>
				  		<tr>
			  	  			<td><label for="dni">DNI:</label></td> 
			  	  			<td> <input type="text" id="dni" size="10" maxlength="9" name="dni"/><font color="red">*&nbsp;&nbsp;</font></td>
			 	  			<td><label for="contrasena">Contrase&ntilde;a:</label></td>
			 	  			<td> <input type="password" id="contrasena" name="contrasena" /><font color="red">*&nbsp;&nbsp;</font></td>
			 	  		</tr>
			 	  		<tr>
			 				<td><label for="perfiles">Perfil:</label></td> 
			 				<td><select name ="perfiles" id="perfiles" onclick="habilitarEdificio()"> 
						
						<%--LinkedList<PerfilDTO> lista = perfiles.getPerfiles();	
							for (PerfilDTO it :lista) {
							}
						--%>			 
								</select>
							</td>
							<td><label for="edificios">Edificio:</label></td>
							<td><select name ="edificios" id="edificios" disabled="disabled">
					  			<option value="ed0">Todos</option>
								<%for (int i=1 ;i<20;i++){ %> 
								<option value="ed"+<%=i %>>Edificio <%=i %></option>
						  		<%} %>			 
								</select>
							</td>
						</tr>
						<tr>
							<td height="14" colspan="4"></td>
						</tr>
												
			  		</table>
			  	</legend>
			</fieldset>
			<a href="GetListadoUsuariosAction">Aceptar</a></br>
			<a href="GetListadoUsuariosAction">Cancelaar</a></br>
		</form>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>


</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>