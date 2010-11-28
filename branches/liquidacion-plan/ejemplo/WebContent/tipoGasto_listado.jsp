<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.List"%>
<%@ page language="java" contentType="text/html" import="gastos.dto.*"%>
<jsp:useBean id="listado" scope="session" class="beans.TiposGastosBean"/>
<%List<TipoGastoDTO> tiposGastos = listado.getTiposGastos();%>
<div class="contenido">
	<div class="titulo"><h3>Tipo de Gastos</h3></div>
	<div class="cuerpo">

	<table id="tablaTipoGastoListado" height ="300" cellpadding="0" cellspacing="0"  >
		<tr>
			<td width="800" align="center">
				<form class="elegante" id="tipoGastoAlta" name="tipoGastoAlta" action="TipoDeGastosAction">
					<fieldset><legend>Listado de Tipos de Gastos</legend>
						<table width="500" border="1" class="listado" >
							<tr>
								<td class="listado_par">Codigo</td>
								<td class="listado_par">Descripcion</td>
								<td class="listado_par">&nbsp;</td>
								<td class="listado_par">&nbsp;</td>
							</tr>	
						<%for (TipoGastoDTO tipoGastoActual : tiposGastos) {  %>		
							<tr>
								<td><%= tipoGastoActual.getCodigo()%></td>
								<td><%= tipoGastoActual.getDescripcion()%></td>
								<td><a href="GetListadoTipoDeGastoAction!editar?id=<%=tipoGastoActual.getId()%>">Modificar</a></td>
								<td><a href="eliminarTipoGasto.jsp?&id=<%=tipoGastoActual.getId()%>&codigo=<%=tipoGastoActual.getCodigo()%>">Eliminar</a></td>	
								
							</tr>	
						<%} %>			
							
						</table>
					</form> 
				</fieldset>	
				<a href="TipoDeGastosAction!cargaEdificios">Agregar Tipo de Gasto</a>
			</td>
		</tr>	
	</table>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
