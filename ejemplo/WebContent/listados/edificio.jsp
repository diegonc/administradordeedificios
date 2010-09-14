<jsp:include page="/WEB-INF/jspf/header.jspf" />
<style type="text/css">
<!--
	.contenido div {
		padding: 1px; /* sin padding no se pinta correctamente el fondo. */ 
	}

	.titulo {
		color:white;
		background-color:black;
	}

	.titulo h3 {
		text-align: center;
		margin: 0;
	}

	.cuerpo {
		background-color: grey;
	}

-->
</style>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Edificios</h3>
	</div>
	<div class="cuerpo">
	<s:if test="listaEdificios.size() > 0">
		<table>
		<s:iterator value="listaEdificios" var="edificio">
			<tr>
				<td><s:property value="nombre" /></td>
				<td><s:property value="localidad" /></td>
				<td><s:property value="calle" /></td>
				<td><s:property value="numero" /></td>
			</tr>
		</s:iterator>
		</table>
	</s:if>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
