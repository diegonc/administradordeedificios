<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html" import="usuarios.dto.UsuarioDTO"%>
<jsp:useBean id="usuarioBean" scope="session" class="beans.UsuariosBean"/>

<%
	UsuarioDTO usuario = usuarioBean.getUsuarioUnico();

%>

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
<div class="contenido">
	<div class="titulo"><h3>Usuarios</h3></div>
	<div class="cuerpo" align="center">
	<table id="tablaUsuarios" height ="300" cellpadding="0" cellspacing="0" >
		<tr>
			<td width="15"  class="fondo"></td>
			<td width="770" class="fondo" align="left">
				<form class="elegante" id="usuarioAlta" name="usuarioAlta" action="UsuarioAction!actualizar">
					<fieldset>
				  		<legend>Modificar Usuario</legend>
					 		<table border="0" cellpadding="0" cellspacing="0">
					 			<tr>
							  		<td><label for="user.nombre">Nombre:</label></td>
							  		<td> <input type="text" id="user.nombre" name="user.nombre" value="<%=usuario.getNombre()%>" /><font color="red">*&nbsp;&nbsp;</font></td>
							  		<td><label for="user.apellido">Apellidos:</label></td>
							  		<td> <input type="text" id="user.apellido" name="user.apellido" size="30" value="<%=usuario.getApellido()%>"/><font color="red">*</font></td>
							  	</tr>
							  	<tr>
									<td height="14" colspan="4"></td>
								</tr>
						  		<tr>
					  	  			<td><label for="user.dni">DNI:</label></td> 
					  	  			<td> <input type="text" id="user.dni" name="user.dni" size="10" maxlength="9" value="<%=usuario.getDni()%>" disabled="disabled" /><font color="red">*&nbsp;&nbsp;</font></td>
					 	  			<td><label for="user.password">Contrase&ntilde;a:</label></td>
					 	  			<td> <input type="text" id="user.password" name="user.password"  value="<%=usuario.getPassword()%>" /><font color="red">*&nbsp;&nbsp;</font></td>
					 	  		</tr>
					 	  		<tr>
									<td height="14" colspan="4"></td>
								</tr>
					 	  		<tr>
					  	  			<td></td> 
					  	  			<td> </td>
					 	  			<td><label for="user.usuario">Usuario:</label></td>
					 	  			<td> <input type="text" id="user.usuario" name="user.usuario" disabled="disabled" value="<%=usuario.getUsuario()%>"/><font color="red">*&nbsp;&nbsp;</font></td>
					 	  		</tr>
								<tr>
									<td height="14" colspan="4"></td>
								</tr>												
					  		</table>
					  	</fieldset>
					  	<s:actionerror />
					  <input type="hidden" value="<%=usuario.getId()%>" id="id" name="id">	
					<input type="button" value="Aceptar"  onclick="validar()" >
					<input type="submit" value="Cancelar" name="redirectAction:GetListadoUsuariosAction">
				</form>
			</td>
			<td width="15"  class="fondo"></td>	
		</tr>	
	</table>
	</div>
</div>	
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>