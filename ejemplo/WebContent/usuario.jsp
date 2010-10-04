<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html" import="usuarios.dto.UsuarioDTO"%>
<jsp:useBean id="user" scope="session" class="usuarios.dto.UsuarioDTO"/>

<script type="text/javascript">

function validar(){
	validado=true;
	var nombre = document.getElementById("user.nombre");
	var user = document.getElementById("user.usuario");
	var apellido = document.getElementById("user.apellido");
	var dni = document.getElementById("user.dni");
	var contrasena = document.getElementById("user.password");

	
	if(nombre.value==""){ alert("Debe completar el nombre"); validado=false;}
	if(validado==true)if(apellido.value==""){ alert("Debe completar el apellido"); validado=false;}
	if(validado==true)if(user.value==""){ alert("Debe completar el nombre de usuario"); validado=false;}
	if(validado==true)if(dni.value==""){ alert("Debe completar el dni"); validado=false;}
	if(validado==true)if(isNaN(dni.value)){ alert("El dni debe ser un valor numerico"); validado=false;}
	if(validado==true)if(contrasena.value==""){ alert("Debe completar la contrasena");validado=false;}
	
	if (validado==false)return false;
	document.usuarioAlta.submit();
	return true;
}
</script>
<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"> <span id="header"><h>Usuario</h></span></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaUsuarios" height ="300" cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
		<form class="elegante" id="usuarioAlta" name="usuarioAlta" action="UsuarioAction!grabar">
			<fieldset>
		  		<legend>Agregar Usuario</legend>
			 		<table>
			 			<tr>
					  		<td><label for="user.nombre">Nombre:</label></td>
					  		<td> <input type="text" id="user.nombre" name="user.nombre" /><font color="red">*&nbsp;&nbsp;</font></td>
					  		<td><label for="user.apellido">Apellidos:</label></td>
					  		<td> <input type="text" id="user.apellido" name="user.apellido" size="30" /><font color="red">*</font></td>
					  	</tr>
					  	<tr>
							<td height="14" colspan="4"></td>
						</tr>
				  		<tr>
			  	  			<td><label for="user.dni">DNI:</label></td> 
			  	  			<td> <input type="text" id="user.dni" name="user.dni" size="10" maxlength="9" /><font color="red">*&nbsp;&nbsp;</font></td>
			 	  			<td><label for="user.password">Contrase&ntilde;a:</label></td>
			 	  			<td> <input type="text" id="user.password" name="user.password"  /><font color="red">*&nbsp;&nbsp;</font></td>
			 	  		</tr>
			 	  		<tr>
							<td height="14" colspan="4"></td>
						</tr>
			 	  		<tr>
			  	  			<td></td> 
			  	  			<td> </td>
			 	  			<td><label for="user.usuario">Usuario:</label></td>
			 	  			<td> <input type="text" id="user.usuario" name="user.usuario" /><font color="red">*&nbsp;&nbsp;</font></td>
			 	  		</tr>
						<tr>
							<td height="14" colspan="4"></td>
						</tr>
												
			  		</table>
			  	</fieldset>
			  	<s:actionerror cssClass="error"/>
			<input type="button" value="Aceptar"  onclick="validar()" >
			<input type="submit" value="Cancelar" name="redirectAction:GetListadoUsuariosAction">
		</form>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>


</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>