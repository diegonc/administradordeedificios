<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ss" uri="/fancy-struts2-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Responsables</h3>
	</div>
	<div class="cuerpo">
		<s:actionerror />
		<s:actionmessage />
		<s:form action="responsablesFormulario!grabar" method="post"  cssClass="elegante">
			<ss:fieldset legend="Alta en el servicio">
				<s:textfield requiredposition="right" required="true" name="dni" value="%{dni}" label="DNI" />
				<s:textfield name="nombre" value="%{nombre" label="Nombre" />
				<s:textfield name="telefono" value="%{telefono}" label="Telefono" />
				<s:textfield name="email" value="%{email}" label="Email" />
				<s:textfield name="localidad" value="%{localidad}" label="Localidad" />
				<s:textfield name="calle" value="%{calle}" label="Calle" />
				<s:textfield name="ubicacion" value="%{ubicacion}" label="Ubicacion" />
				<s:checkbox name="autoridad" value="%{autoridad}" label="Autoridad" />
			</ss:fieldset>
			<tr> <td colspan="2" >
				<s:submit theme="simple" value="Aceptar" />
				<s:submit theme="simple" value="Cancelar" name="redirectAction:responsablesListado" />
			</td></tr>
		</s:form>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
