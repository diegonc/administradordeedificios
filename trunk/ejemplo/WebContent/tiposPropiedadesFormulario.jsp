<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<div class="titulo"><h3>Tipo de Propiedad</h3></div>
	<div class="cuerpo">
	<s:actionerror />
	<s:actionmessage />
	<s:form action="tiposPropiedadesFormulario" method="post">
		<s:hidden name="nombreEdificio" value="%{nombreEdificio}" />
	    <s:textfield required="true" name="entidad.nombreTipo" value="%{entidad.nombreTipo}" label="Nombre" />
	    <s:textfield name="entidad.montoExp" value="%{entidad.montoExp}" label="Monto Expensa" />
	    <s:textfield name="entidad.divisor" value="%{entidad.divisor}" label="Proporción" />
	    
	    <p>Tipo de gastos asociados</p>
	    <s:select multiple="true" name="codigosTipoGastoAAgregar" list="mapaTiposGastosDisponibles"/>
	    <s:submit value="Agregar Tipo" method="agregarTipos"/>
	    <table>
		    <thead>
		    	<tr>
		    	<th>Codigo</th>
		    	<th>Coeficiente de distribucion</th>
		    	<th><s:submit theme="simple" value="Borrar" method="borrarTipos"/></th>
		    	</tr>
		    </thead>
		    <tbody>
			    <s:iterator value="tiposGastos.values()">
			    	<tr>
			    		<td>
			    		<s:hidden
			    			name="%{'tiposGastos[\\''+tipoGasto.codigo+'\\'].tipoGasto.codigo'}"
			    			value="%{tipoGasto.codigo}"/>
			    		<s:hidden
			    			name="%{'tiposGastos[\\''+tipoGasto.codigo+'\\'].tipoGasto.descripcion'}"
			    			value="%{tipoGasto.descripcion}" /></td>
			    		<td>
			    		<s:textfield
			    			name="%{'tiposGastos[\\''+tipoGasto.codigo+'\\'].coeficienteDistribucion'}"
			    		 	value="%{coeficienteDistribucion}"
			    		 	label="%{tipoGasto.descripcion}" /></td>
			    		<td><s:checkbox fieldValue="%{tipoGasto.codigo}" name="codigosTipoGastoABorrar" value=""/></td>
			    	</tr>
			    </s:iterator>
		    </tbody>
	    </table>
	    
	    <s:submit value="Aceptar" method="grabar" />
	    <s:submit value="Cancelar" method="cancelar" />
	</s:form>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
