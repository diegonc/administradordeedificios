<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Responsables</h3>
	</div>
	<div class="cuerpo">
		<s:actionerror />
		<s:actionmessage />

        <s:form action="responsablesFormulario!grabar" method="post"  cssClass="elegante">
            <s:textfield requiredposition="right" required="true" name="dni" value="%{dni}" label="DNI" />
            <s:textfield name="telefono" value="%{telefono}" label="Telefono" />
            <s:textfield name="email" value="%{email}" label="Email" />
            <s:textfield name="localidad" value="%{localidad}" label="Localidad" />
            <s:textfield name="calle" value="%{calle}" label="Calle" />
            <s:textfield name="ubicacion" value="%{ubicacion}" label="Ubicacion" />
            <s:checkbox name="autoridad" value="%{autoridad}" label="Autoridad" />
            <s:submit value="Aceptar" />
            <s:submit value="Cancelar" name="redirectAction:responsablesListado" />
        </s:form>
    </div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
