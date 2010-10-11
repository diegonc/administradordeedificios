<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>[Edificio] » Propiedad</h3>
	</div>
	<div class="cuerpo">
		<div>
			<s:actionmessage  cssClass="error" />
			<s:actionerror cssClass="error"/>
		</div>
		<s:url id="url" action="propiedadesListado">
			<s:param name="nombreEdificio" value="nombreEdificio" />
		</s:url>
		<a href="<s:property value='#url' />">Volver al listado</a>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
