<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.List"%>
<%@ page language="java" contentType="text/html" import="usuarios.dto.*"%>
<jsp:useBean id="listado" scope="session" class="beans.UsuariosBean"/>
<%List<UsuarioDTO> usuarios = listado.getUsers();%>
<div class="contenido">
	<div class="titulo"><h3>Usuarios</h3></div>
	<div class="cuerpo" align="center">
		<table id="tablaUsuariosListado" height ="300" cellpadding="0" cellspacing="0"  >
		<tr>
			<td width="800" align="center">
			<fieldset> <legend>Listado de Usuarios</legend>
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
								<td><a href="GetListadoUsuariosAction!editar?id=<%=usuarioDTO.getId()%>" >Modificar</a></td>
								<%if(usuarioDTO.getDni()!=1){ %>
									<td><a href="usuarioEliminar.jsp?&id=<%=usuarioDTO.getId()%>&usuario=<%=usuarioDTO.getUsuario()%>" >Eliminar</a></td>
								<%}else{ %>
									<td>&nbsp;</td>
								<%} %>		
							</tr>	
						<%} %>			
							
						</table>
					</form> 
				</fieldset>
				<a href="UsuarioAction">Agregar Usuario</a>
			</td>
		</tr>
		</table>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
