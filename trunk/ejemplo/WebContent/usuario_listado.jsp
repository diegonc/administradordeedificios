<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.List"%>
<%@ page language="java" contentType="text/html" import="usuarios.dto.*"%>
<jsp:useBean id="listado" scope="session" class="beans.UsuariosBean"/>
<%List<UsuarioDTO> usuarios = listado.getUsers();%>

<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"> <span id="header"><h>Usuarios </span></h></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaUsuariosListado" height ="300" cellpadding="0" cellspacing="0"  >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" align="center">
		<form class="elegante" id="usuarioAlta" name="usuarioAlta" action="UsuarioAction!grabar">
				<table width="600" border="1" class="listado" cellpadding="0" cellspacing="0" >
					<tr>
						<td class="listado_par">Usuario</td>
						<td class="listado_par">Password</td>
						<td class="listado_par">Nombre</td>
						<td class="listado_par">Apellido</td>
						<td class="listado_par">Dni</td>
						<td class="listado_par"></td>
						<td class="listado_par"></td>
					</tr>	
				<%	int i=0;
					for (UsuarioDTO usuarioDTO : usuarios) {						
				%>		
					<tr>
						<td><%= usuarioDTO.getUsuario()%></td>
						<td><%= usuarioDTO.getPassword()%></td>
						<td><%= usuarioDTO.getNombre()%></td>
						<td><%= usuarioDTO.getApellido()%></td>
						<td><%= usuarioDTO.getDni()%></td>
						<td><a href="GetListadoUsuariosAction!editar?id=<%=usuarioDTO.getId()%>" onclick="confirm('Esta seguro?')">Modificar</a></td>
						<td><a href="GetListadoUsuariosAction!eliminar?&id=<%=usuarioDTO.getId()%>" >Eliminar</a></td>		
					</tr>	
				<%} %>			
					
				</table>
			</form> 
		<a href="UsuarioAction">Agregar Usuario</a>
	</td>
	<td width="5" class="borde"></td>
</tr>


</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
