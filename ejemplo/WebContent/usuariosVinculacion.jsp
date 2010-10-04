<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html" import="usuarios.dto.UsuarioDTO"%>
<jsp:useBean id="usuarioBean" scope="session" class="beans.UsuariosBean"/>


<%
	UsuarioDTO usuario = usuarioBean.getUsuarioUnico();
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
	document.getElementById(id).innerHtml=document.getElementById(id).style.display=="none"?"src='images/cerrar.jpg'":"src='images/abrir.jpg'";
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
		<form class="elegante" id="vincularUsuario" name="vincularUsuario" action="VinculacionUsuarioPerfilEdificio">
			<fieldset>
		  		<legend>Vinculacion-Usuario</legend>
			 		<table border="0" cellpadding="0" cellspacing="0">
			 			<tr>
							<td height="14" colspan="4"></td>
						</tr>
			 	  		<tr>
			  	  			<td></td> 
			  	  			<td> </td>
			 	  			<td><label for="user.usuario">Usuario:</label></td>
			 	  			<td> <input type="hidden" id="user.usuario" name="user.usuario"   value="<%=usuario.getUsuario()%>"/>&nbsp;&nbsp;<%=usuario.getUsuario()%></td>
			 	  		</tr>
			 	  		<tr>
							<td height="14" colspan="4"></td>
						</tr>
					</table>
					
					<table border="0" cellpadding="0" cellspacing="0" width="100%">		
						<tr>
							<td align="right"><s:checkbox name="administrador" id="administrador" onclick="deshabilitarPerfiles()"  /> </td>
				  			<td><label for="administrador">Administrador</label></td>			  	  	
				  		</tr>
					</table>
												
					<table border="0" cellpadding="0" cellspacing="0" width="100%">	
							
							<tr>
								<td bgcolor="#F0F0F0"><s:checkbox name="responsableEdificios" id="responsableEdificios" /> </td>
			  	  				<td bgcolor="#F0F0F0"><label for="responsableEdificios">Responsables de Edificios</label></td>
			  	  				<td bgcolor="#F0F0F0" align="right"><img src="images/cerrar.jpg" alt="Agregar Edificios" onclick="cerrar('grupo2')"></img></td> 
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
			  	  				<td bgcolor="#F0F0F0"><s:checkbox name="responsableGastos" id="responsableGastos"/> </td>
			  	  				<td bgcolor="#F0F0F0"><label for="responsableGastos">Responsables de Gastos</label></td>
			  	  				<td bgcolor="#F0F0F0" align="right"><img src="images/cerrar.jpg" alt="Agregar Edificios" onclick="cerrar('grupo3')"></img></td> 				
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
								<td bgcolor="#F0F0F0"><s:checkbox name="responsableCobros" id="responsableCobros"/></td>			  	  			 
			  	  				<td bgcolor="#F0F0F0"><label for="responsableCobros">Responsables de Cobros</label></td>
			  	  				<td bgcolor="#F0F0F0" align="right"><img src="images/cerrar.jpg" alt="Agregar Edificios" onclick="cerrar('grupo4')"></img></td>
			  	  				
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
			<input type="submit" value="Aceptar"  onclick="validar()" >
			<input type="submit" value="Cancelar" name="redirectAction:VinculacionUsuario">
			</fieldset>
		</form>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>

</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>