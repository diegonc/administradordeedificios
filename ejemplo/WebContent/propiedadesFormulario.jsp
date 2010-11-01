<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo"><h3>Propiedades</h3></div>
	<div class="cuerpo">
	<s:actionerror />
	<s:actionmessage />
	<fieldset>
		  		<legend>Alta en el servicio</legend>
	<s:form action="propiedadesFormulario!grabar" method="post" cssClass="elegante">
		<s:hidden name="nombreEdificio" value="%{nombreEdificio}"/>
		<s:select label="Tipo Propiedad" 
				headerKey="" headerValue="-- Seleccione un tipo de propiedad --"
				list="listaTiposPropiedades" 
				name="entidad.tipoPropiedad.nombreTipo"
				value="%{entidad.tipoPropiedad.nombreTipo}"
				required="true" />
	    <s:textfield required="true" name="entidad.nivel" value="%{entidad.nivel}" label="Nivel" />
	    <s:textfield required="true" name="entidad.orden" value="%{entidad.orden}" label="Orden" />
	    <s:select label="Propietario" 
				headerKey="" headerValue="-- Seleccione un responsable --"
				list="listaResponsables"
				name="entidad.propietario.dni"
				value="%{entidad.propietario.dni}"
				required="true" />
	    <s:select label="Inquilino" 
				headerKey="" headerValue="Ninguno"
				list="listaResponsables"
				name="entidad.inquilino.dni"
				value="%{entidad.inquilino.dni}"/>
   	    <s:select label="Poder Propietario" 
				headerKey="" headerValue="Ninguno"
				list="listaResponsables"
				name="entidad.poderPropietario.dni"
				value="%{entidad.poderPropietario.dni}"/>
   	    <s:select label="Poder Inquilino" 
				headerKey="" headerValue="Ninguno"
				list="listaResponsables"
				name="entidad.poderInquilino.dni"
				value="%{entidad.poderInquilino.dni}"/>
   	    <s:textfield name="entidad.dividendo" value="%{entidad.dividendo}" label="Dividendo" />
   	    </tbody></table>
	   </fieldset>
	   <table><tbody>
	    <tr>
	    <td colspan="2" ><s:submit theme="simple" value="Aceptar" />
	    <s:submit theme="simple" value="Cancelar" name="redirectAction:propiedadesListado" /></td>
	    </tr>
	</s:form>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
