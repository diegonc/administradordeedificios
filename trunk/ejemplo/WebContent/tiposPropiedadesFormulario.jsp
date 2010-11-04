<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ss" uri="/fancy-struts2-tags" %>
<div class="contenido">
	<div class="titulo"><h3>Tipo de Propiedad</h3></div>
	<div class="cuerpo">
		<s:actionerror />
		<s:actionmessage />
		<s:form action="tiposPropiedadesFormulario" method="post">
			<ss:fieldset legend="Alta en el servicio">
				<s:hidden name="nombreEdificio" value="%{nombreEdificio}" />
				<s:textfield required="true" name="entidad.nombreTipo" value="%{entidad.nombreTipo}" label="Nombre" />
				<s:textfield name="entidad.montoExp" value="%{entidad.montoExp}" label="Expensa Ordinaria" />
				<s:textfield name="entidad.montoExpExt" value="%{entidad.montoExpExt}" label="Expensa Extraordinaria" />
				<s:textfield name="entidad.divisor" value="%{entidad.divisor}" label="Divisor" />
	
				<s:div>
					<h4>Tipo de gastos asociados</h4>
				</s:div>
	
				<s:select multiple="true" name="codigosTipoGastoAAgregar" list="mapaTiposGastosDisponibles"/>
				<s:submit value="Agregar Tipo" method="agregarTipos"/>
				<s:div>
					<table>
						<thead>
							<tr>
								<th>Codigo</th>
								<th>Coeficiente de distribuci&oacute;n</th>
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
											value="%{tipoGasto.descripcion}" />
									</td>
									<td>
										<s:textfield
											name="%{'tiposGastos[\\''+tipoGasto.codigo+'\\'].coeficienteDistribucion'}"
											value="%{coeficienteDistribucion}"
											label="%{tipoGasto.descripcion}"
											size="10"/>
									</td>
									<td><s:checkbox fieldValue="%{tipoGasto.codigo}" name="codigosTipoGastoABorrar" value=""/></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</s:div>
			</ss:fieldset>
			<tr>
	    	<td colspan="2" ><s:submit theme="simple" value="Aceptar" method="grabar" />
	    	<s:submit theme="simple" value="Cancelar" method="cancelar" /></td>
	    	</tr>
		</s:form>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
