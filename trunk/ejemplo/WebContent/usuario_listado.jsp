<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="usuarios.dto.*"%>
<jsp:useBean id="lista" scope="session" class="beans.UsuariosBean"/>

<%
	LinkedList<UsuarioDTO> usuarios = lista.getUsers();
%>
<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"> <span id="header"><h>Usuarios </span></h></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaUsuarioListado" height ="300" cellpadding="0" cellspacing="0"  >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" align="center">
		<table width="500" border="1" class="listado" >
			<tr>
				<td>Usuario</td>
				<td>Password</td>
				<td>Perfil</td>
				<td>Nombre</td>
				<td>Apellido</td>
				<td>DNI</td>
				<td></td>
			</tr>	
		<%for (UsuarioDTO usuarioDTO : usuarios) {  %>		
			<tr>
				<td><%= usuarioDTO.getUsuario()%></td>
				<td><%= usuarioDTO.getPassword()%></td>
				<td><%= usuarioDTO.getPerfil()%></td>
				<td><%= usuarioDTO.getNombre()%></td>
				<td><%= usuarioDTO.getApellido()%></td>
				<td><%= usuarioDTO.getDni()%></td>
				<td><a href="usuario.jsp">Modificar</a></td>		
			</tr>	
		<%} %>			
		
		</table> 
	</td>
	<td width="5" class="borde"></td>
</tr>


</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>