<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<s:actionerror />
	<s:actionmessage />
	<s:form action="propiedadesFormulario!grabar" method="post">
		<s:hidden name="nombreEdificio" value="%{nombreEdificio}"/>
		<s:select label="Tipo Propiedad" 
				headerKey="-1" headerValue="-- Seleccione un tipo de propiedad --"
				list="listaTiposPropiedades" 
				name="entidad.tipoPropiedad.nombreTipo"
				value="%{entidad.tipoPropiedad.nombreTipo}" />
	    <s:textfield name="entidad.nivel" value="%{entidad.nivel}" label="Nivel" />
	    <s:textfield name="entidad.orden" value="%{entidad.orden}" label="Orden" />
	    <s:textfield name="entidad.propietario.dni" value="%{entidad.propietario.dni}" label="Propietario" />
	    <s:textfield name="entidad.inquilino.dni" value="%{entidad.inquilino.dni}" label="Inquilino" />
   	    <s:textfield name="entidad.poderPropietario.dni" value="%{entidad.poderPropietario.dni}" label="Poder Propietario" />
   	    <s:textfield name="entidad.poderInquilino.dni" value="%{entidad.poderInquilino.dni}" label="Poder Inquilino" />
   	    <s:textfield name="entidad.ctaExtSaldoExp" value="%{entidad.ctaExtSaldoExp}" label="ctaExtSaldoExt" />
   	    <s:textfield name="entidad.ctaExtSaldoInt" value="%{entidad.ctaExtSaldoInt}" label="ctaExtSaldoInt" />
   	    <s:textfield name="entidad.ctaOrdSaldoExp" value="%{entidad.ctaOrdSaldoExp}" label="ctaOrdSaldoExp" />
   	    <s:textfield name="entidad.ctaOrdSaldoInt" value="%{entidad.ctaOrdSaldoInt}" label="ctaOrdSaldoInt" />
	    
	    <s:submit value="Aceptar" />
	    <s:submit value="Cancelar" name="redirectAction:propiedadesListado" />
	</s:form>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
