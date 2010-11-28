<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.List"%>
<%@ page language="java" contentType="text/html" import="usuarios.dto.*"%>
<jsp:useBean id="listado" scope="session" class="beans.UsuariosBean"/>
<%List<UsuarioDTO> usuarios = listado.getUsers();%>
<div class="contenido">
	<div class="titulo"><h3>Asignaci&oacute;n de Perfiles</h3></div>
	<div class="cuerpo">
		<table id="tablaVinculacion" height ="300" cellpadding="0" cellspacing="0"  >
		<tr>
			<td width="800" align="center">
			<fieldset><legend> Listado de Usuarios</legend>
				<form class="elegante" id="usuarioAlta" name="usuarioAlta" >
						<table width="600" border="1" class="listado" cellpadding="0" cellspacing="0" >
							<tr>
								<td class="listado_par">Usuario</td>
								<td class="listado_par">Nombre</td>
								<td class="listado_par">Apellido</td>
								<td class="listado_par">Dni</td>
								<td class="listado_par"></td>
								
							</tr>	
						<%	int i=0;
							for (UsuarioDTO usuarioDTO : usuarios) {						
						%>		
							<tr>
								<td><%= usuarioDTO.getUsuario()%></td>
								<td><%= usuarioDTO.getNombre()%></td>
								<td><%= usuarioDTO.getApellido()%></td>
								<td><%= usuarioDTO.getDni()%></td>								
								<td><%if (usuarioDTO.getDni()!=1){%> <a href="VinculacionUsuarioPerfilEdificio!vincular?&id=<%=usuarioDTO.getId()%>" >Asignar</a><%} %></td>		
							</tr>	
						<%} %>			
							
						</table>
					</form>
			</fieldset>	
			</td>
		</tr>
		</table>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
