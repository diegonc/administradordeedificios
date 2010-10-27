<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html" import="usuarios.dto.UsuarioDTO"%>
<%@ page language="java" contentType="text/html" import="usuarios.dto.PerfilDTO"%>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<jsp:useBean id="usuarioBean" scope="session" class="beans.UsuariosBean"/>


<%
	UsuarioDTO usuario = usuarioBean.getUsuarioUnico();
	List<PerfilDTO> perfiles=usuario.getPerfiles();
	String administrador="";
	String responsableEdificio="";
	String responsableGastos="";
	String responsableCobros="";
	
	for(PerfilDTO perfil:perfiles){
		if (perfil.getDescripcion().equals(PerfilDTO.ADMINISTRADOR)) administrador="checked='checked'";
		if (perfil.getDescripcion().equals(PerfilDTO.RESPONSABLE_EDIFICIO)) responsableEdificio="checked='checked'";
		if (perfil.getDescripcion().equals(PerfilDTO.RESPONSABLE_GASTOS)) responsableGastos="checked='checked'";
		if (perfil.getDescripcion().equals(PerfilDTO.RESPONSABLE_COBROS)) responsableCobros="checked='checked'";
	}
%>

<script type="text/javascript">

function deshabilitarPerfiles(){
	var administrador = document.getElementById("Administrador");
	document.getElementById("responsableEdificios").disabled = administrador.checked==true?"disabled":"";
	document.getElementById("responsableGastos").disabled = administrador.checked==true?"disabled":"";
	document.getElementById("responsableCobros").disabled = administrador.checked==true?"disabled":"";

	document.getElementById("edificiosResponsableEdificioResult").disabled = administrador.checked==true?"disabled":"";
	document.getElementById("edificiosResponsableGastosResult").disabled = administrador.checked==true?"disabled":"";
	document.getElementById("edificiosResponsableCobrosResult").disabled = administrador.checked==true?"disabled":"";
	
	document.getElementById("grupoEdif").disabled = administrador.checked==true?"disabled":"";
	document.getElementById("grupoGastos").disabled = administrador.checked==true?"disabled":"";
	document.getElementById("grupoCobros").disabled = administrador.checked==true?"disabled":"";
	
	
}
function validar(){
	
	document.vincularUsuario.submit();
	return true;
}
function cerrar(id){
	document.getElementById(id).style.display=document.getElementById(id).style.display=="none"?"block":"none";
	if (document.getElementById(id).style.display=="none"){
		document.getElementById("ima"+id).src="images/cerrar.jpg";
	}else{
		document.getElementById("ima"+id).src="images/abrir.jpg";
	}
}

function deshabilitarAdministrador(){
	var cobro = document.getElementById("responsableCobros").checked;
	var gasto = document.getElementById("responsableGastos").checked;
	var edif = document.getElementById("responsableGastos").checked;
	document.getElementById("administrador").disabled = (cobro||gasto||edif)?"":"disabled";
}
</script>

<div class="contenido">
	<div class="titulo"><h3>Asignaci&oacute;n de Perfiles</h3></div>
	<div class="cuerpo">
	<table id="tablaUsuarios" height ="300" cellpadding="0" cellspacing="0" >
	<tr>
		<td width="15"  class="fondo"></td>
		<td width="770" class="fondo" align="left">
			<form class="elegante" id="vincularUsuario" name="vincularUsuario" action="VinculacionUsuarioPerfilEdificio">
				<fieldset>
			  		<legend>Asignaci&oacute;n de Perfiles</legend>
				 		<table border="0" cellpadding="0" cellspacing="0">
				 			<tr>
								<td height="14" colspan="4"></td>
							</tr>
				 	  		<tr>
				  	  			<td></td> 
				  	  			<td> </td>
				 	  			<td><label for="user.usuario">Usuario:</label></td>
				 	  			<td> <input type="hidden" id="user.usuario" name="user.usuario"   value="<%=usuario.getUsuario()%>" />&nbsp;&nbsp;<%=usuario.getUsuario()%></td>
				 	  		</tr>
				 	  		<tr>
								<td height="14" colspan="4"></td>
							</tr>
						</table>
						
						<table border="0" cellpadding="0" cellspacing="0" width="100%">		
							<tr>
								<td align="right"><input type="checkbox" name="administrador" id="administrador" onclick="deshabilitarPerfiles()"  value="<%=PerfilDTO.ADMINISTRADOR%>"  <%=administrador%>/> </td>
					  			<td><label for="administrador"><%=PerfilDTO.ADMINISTRADOR%></label></td>			  	  	
					  		</tr>
						</table>
													
						<table border="0" cellpadding="0" cellspacing="0" width="100%">								
								<tr>
									<td bgcolor="#F0F0F0"><input type="checkbox" name="responsableEdificios" id="responsableEdificios" value="<%=PerfilDTO.RESPONSABLE_EDIFICIO%>" <%=responsableEdificio%> onclick="deshabilitarAdministrador()" /> </td>
				  	  				<td bgcolor="#F0F0F0"><label for="responsableEdificios"><%=PerfilDTO.RESPONSABLE_EDIFICIO%></label></td>
				  	  				<td bgcolor="#F0F0F0" align="right"><img id="imagrupo2" src="images/cerrar.jpg" alt="Agregar Edificios" onclick="cerrar('grupo2')"></img></td> 
				  	  			</tr>
				 	  	</table>
				 	  	
				 	  	<div id="grupo2"  style="display:none;">
							<table border="0" cellpadding="0" cellspacing="0" >
					  	  		<tr>		
					 	  			<td colspan="2"> 
					 	  			 	<s:optiontransferselect
					 	  						label="Seleccione"
					 	  						id="grupoEdif"
					 	  						headerValue="--- Please Select ---"
		      									name="listaEdificiosDefault"
		      									list="listaEdificiosDefault"
		      									rightTitle="Edificios seleccionados"      									
		      									doubleHeaderValue="--- Please Select ---"
		      									doubleId="edificiosResponsableEdificioResult"
		      									doubleName="edificiosResponsableEdificioResult"
		      									doubleList="edificiosResponsableEdificioResult">      										      									
		      							</s:optiontransferselect>      								
					 	  			</td>
					 	  		</tr>
								<tr>
									<td height="14" colspan="4"></td>
								</tr>
							</table>	
						</div>
						
						<table border="0" cellpadding="0" cellspacing="0" width="100%">	
								<tr>			  	  			 
				  	  				<td bgcolor="#F0F0F0"><input type="checkbox" name="responsableGastos" id="responsableGastos" value="<%=PerfilDTO.RESPONSABLE_GASTOS %>"   <%=responsableGastos%> onclick="deshabilitarAdministrador()"/> </td>
				  	  				<td bgcolor="#F0F0F0"><label for="responsableGastos"><%=PerfilDTO.RESPONSABLE_GASTOS %></label></td>
				  	  				<td bgcolor="#F0F0F0" align="right"><img src="images/cerrar.jpg" alt="Agregar Edificios"  id="imagrupo3" onclick="cerrar('grupo3')"></img></td> 				
				  	  			</tr>
				 	  	</table>
				 	  	<div id="grupo3" style="display:none;">
							<table border="0" cellpadding="0" cellspacing="0" >
					  	  		<tr>		
					 	  			<td colspan="2">  
					 	  				<s:optiontransferselect
					 	  					label="Seleccione"
					 	  					id="grupoGastos"
					 	  						headerValue="--- Please Select ---"
		      									name="listaEdificiosDefault1"
		      									list="listaEdificiosDefault1"
		      									rightTitle="Edificios seleccionados"      									
		      									doubleHeaderValue="--- Please Select ---"
		      									doubleId="edificiosResponsableGastosResult"
		      									doubleName="edificiosResponsableGastosResult"
		      									doubleList="edificiosResponsableGastosResult">  
		      							</s:optiontransferselect>	      								
					 	  			</td>
					 	  		</tr>
								<tr>
									<td height="14" colspan="4"></td>
								</tr>
							</table>	
						</div>
						
															
						<table border="0" cellpadding="0" cellspacing="0" width="100%">							
								<tr>
									<td bgcolor="#F0F0F0"><input type="checkbox" name="responsableCobros" id="responsableCobros" value="<%=PerfilDTO.RESPONSABLE_COBROS%>" <%=responsableCobros%> onclick="deshabilitarAdministrador()"/></td>			  	  			 
				  	  				<td bgcolor="#F0F0F0"><label for="responsableCobros"><%=PerfilDTO.RESPONSABLE_COBROS%></label></td>
				  	  				<td bgcolor="#F0F0F0" align="right"><img src="images/cerrar.jpg" alt="Agregar Edificios" id="imagrupo4" onclick="cerrar('grupo4')"></img></td>
				  	  				
				 	  			</tr>
				 	  	</table>								
						<div id="grupo4" style="display:none;">
							<table border="0" cellpadding="0" cellspacing="0" >
					  	  		<tr>		
					 	  			<td colspan="2">  
					 	  				<s:optiontransferselect
					 	  					label="Seleccione"
					 	  						label="Seleccione"
					 	  						id="grupoCobros"
					 	  						headerValue="--- Please Select ---"
		      									name="listaEdificiosDefault2"
		      									list="listaEdificiosDefault2"
		      									rightTitle="Edificios seleccionados"      									
		      									doubleHeaderValue="--- Please Select ---"
		      									doubleId="edificiosResponsableCobrosResult"
		      									doubleName="edificiosResponsableCobrosResult"
		      									doubleList="edificiosResponsableCobrosResult">
		      							</s:optiontransferselect>	   					
					 	  			</td>
					 	  		</tr>
								<tr>
									<td height="14" colspan="4"></td>
								</tr>
							</table>	
						</div>
																											
				  <s:actionerror />
				  <input type="hidden" value="<%=usuario.getId()%>" id="id" name="id">	
				
				</fieldset>
				<input type="button" value="Aceptar"  onclick="validar()" >
				<input type="submit" value="Cancelar" name="redirectAction:VinculacionUsuario">
			</form>
		</td>
		<td width="15"  class="fondo"></td>	
	</tr>
	
	</table>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>