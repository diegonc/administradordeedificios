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
						<td class="listado_par">Perfiles</td>
						<td class="listado_par">Depto</td>
						<td class="listado_par"></td>
						<td class="listado_par"></td>
					</tr>	
				<%	int i=0;
					for (UsuarioDTO usuarioDTO : usuarios) {
						String estilo = "listado_par";
						if ((i%2)==0)estilo = "listado_impar";
						i++;
						String idEdificio =(usuarioDTO.getEdificio()==null)?"--":usuarioDTO.getEdificio().getNombre();
						List<PerfilDTO> perfiles = usuarioDTO.getPerfiles();	
				%>		
					<tr>
						<td class=<%=estilo%>><%= usuarioDTO.getUsuario()%></td>
						<td class=<%=estilo%>><%= usuarioDTO.getPassword()%></td>
						<td class=<%=estilo%>><%= usuarioDTO.getNombre()%></td>
						<td class=<%=estilo%>><%= usuarioDTO.getApellido()%></td>
						<td class=<%=estilo%>><%= usuarioDTO.getDni()%></td>
						<td class=<%=estilo%> align="left">
							<ul >
									<%for(PerfilDTO p: perfiles){%>
									<li ><%=p.getDescripcion()%> &nbsp;</li>
								<%} %>
							</ul>
						</td>
						<td class=<%=estilo%>><%= idEdificio%></td>	
						<td class=<%=estilo%>><a href="GetListadoUsuariosAction!editar?id=<%=usuarioDTO.getId()%>">Modificar</a></td>
						<td class=<%=estilo%>><a href="GetListadoUsuariosAction!eliminar?&id=<%=usuarioDTO.getId()%>" >Eliminar</a></td>		
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
