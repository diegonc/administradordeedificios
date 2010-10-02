<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo"><h3>Propiedades</h3></div>
	<div class="cuerpo">
	<s:actionerror />
	<s:actionmessage />
	<s:form action="propiedadesFormulario!grabar" method="post" cssClass="elegante">
		<s:hidden name="nombreEdificio" value="%{nombreEdificio}"/>
		<s:select label="Tipo Propiedad" 
				headerKey="-1" headerValue="-- Seleccione un tipo de propiedad --"
				list="listaTiposPropiedades" 
				name="entidad.tipoPropiedad.nombreTipo"
				value="%{entidad.tipoPropiedad.nombreTipo}"
				required="true" />
	    <s:textfield required="true" name="entidad.nivel" value="%{entidad.nivel}" label="Nivel" />
	    <s:textfield required="true" name="entidad.orden" value="%{entidad.orden}" label="Orden" />
	    <s:textfield required="true" name="propietario" value="%{entidad.propietario.dni}" label="Propietario" />
	    <s:textfield name="entidad.inquilino" value="%{entidad.inquilino.dni}" label="Inquilino" />
   	    <s:textfield name="entidad.poderPropietario" value="%{entidad.poderPropietario.dni}" label="Poder Propietario" />
   	    <s:textfield name="entidad.poderInquilino" value="%{entidad.poderInquilino.dni}" label="Poder Inquilino" />
   	    <s:textfield name="entidad.ctaExtSaldoExp" value="%{entidad.ctaExtSaldoExp}" label="ctaExtSaldoExt" />
   	    <s:textfield name="entidad.ctaExtSaldoInt" value="%{entidad.ctaExtSaldoInt}" label="ctaExtSaldoInt" />
   	    <s:textfield name="entidad.ctaOrdSaldoExp" value="%{entidad.ctaOrdSaldoExp}" label="ctaOrdSaldoExp" />
   	    <s:textfield name="entidad.ctaOrdSaldoInt" value="%{entidad.ctaOrdSaldoInt}" label="ctaOrdSaldoInt" />
	    
	    <s:submit value="Aceptar" />
	    <s:submit value="Cancelar" name="redirectAction:propiedadesListado" />
	</s:form>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
