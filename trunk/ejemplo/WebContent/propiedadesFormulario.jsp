<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<s:actionerror />
	<s:actionmessage />
	<s:form action="tiposPropiedadesFormulario!grabar" method="post">
	    <s:textfield name="entidad.nombreTipo" value="%{entidad.nombreTipo}" label="Nombre" size="40" />
	    <s:textfield name="entidad.montoExp" value="%{entidad.montoExp}" label="Monto Expensa" size="40" />
	    <s:textfield name="entidad.divisor" value="%{entidad.divisor}" label="Proporción" size="40" />
	    <s:submit value="Aceptar" />
	    <s:submit value="Cancelar" name="redirectAction:tiposPropiedadesListado" />
	</s:form>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
