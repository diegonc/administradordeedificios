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
	<s:actionerror />
	<s:actionmessage />
	<s:form action="responsablesFormulario!grabar" method="post">
	    <s:textfield name="entidad.dni" value="%{entidad.dni}" label="DNI" size="40" />
	    <s:textfield name="entidad.telefono" value="%{entidad.telefono}" label="Telefono" size="40" />
	    <s:textfield name="entidad.email" value="%{entidad.email}" label="Email" size="40" />
	    <s:textfield name="entidad.localidad" value="%{entidad.localidad}" label="Localidad" size="40" />
	    <s:textfield name="entidad.calle" value="%{entidad.calle}" label="Calle" size="40" />
	    <s:textfield name="entidad.ubicacion" value="%{entidad.ubicacion}" label="Ubicacion" size="40" />
	    <s:checkbox name="entidad.autoridad" value="%{entidad.autoridad}" label="Autoridad" />
	    <s:submit value="Aceptar" />
	    <s:submit value="Cancelar" name="redirectAction:responsablesListado" />
	</s:form>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
