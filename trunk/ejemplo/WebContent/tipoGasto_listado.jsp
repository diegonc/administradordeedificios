<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.List"%>
<%@ page language="java" contentType="text/html" import="gastos.dto.*"%>
<jsp:useBean id="listado" scope="session" class="beans.TiposGastosBean"/>
<%List<TipoGastoDTO> tiposGastos = listado.getTiposGastos();%>

<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"> <span id="header"><h>Tipo de Gasto</span></h></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaTipoGastoListado" height ="300" cellpadding="0" cellspacing="0"  >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" align="center">
		<form class="elegante" id="tipoGastoAlta" name="tipoGastoAlta" action="TipoDeGastosAction">
				<table width="500" border="1" class="listado" >
					<tr>
						<td>Codigo</td>
						<td>Descripcion</td>
						<td></td>
						<td></td>
					</tr>	
				<%for (TipoGastoDTO tipoGastoActual : tiposGastos) {  %>		
					<tr>
						<td><%= tipoGastoActual.getCodigo()%></td>
						<td><%= tipoGastoActual.getDescripcion()%></td>
						<td><a href="GetListadoTipoDeGastoAction!editar?id=<%=tipoGastoActual.getId()%>">Modificar</a></td>
						<td><a href="GetListadoTipoDeGastoAction!eliminar?&id=<%=tipoGastoActual.getId()%>" >Eliminar</a></td>		
					</tr>	
				<%} %>			
					
				</table>
			</form> 
		<a href="TipoDeGastosAction!cargaEdificios">Agregar Tipo de Gasto</a>
	</td>
	<td width="5" class="borde"></td>
</tr>


</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
