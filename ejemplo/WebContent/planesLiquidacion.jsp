<jsp:include page="/WEB-INF/jspf/header.jspf" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ss" uri="/fancy-struts2-tags" %>
<div class="contenido">
	<div class="titulo">
		<h3>Liquidacion de planes</h3>
	</div>
	<div class="cuerpo">
		<script src='<s:url value="/calendario.js"/>' type="text/javascript"></script>
		<script type="text/javascript">
			function show_picker(basename) {
				var dia = document.getElementById(basename + "_day");
				var mes = document.getElementById(basename + "_month");
				var anio = document.getElementById(basename + "_year");
				allowPrevious=true;
				setDateField(dia,mes,anio);
				top.newWin = window.open('calendario.jsp','cal','WIDTH=200,HEIGHT=160,TOP=200,LEFT=300');
			}
			
			function update_date(basename, component) {
				var dia = document.getElementById(basename + "_day").value;
				var mes = document.getElementById(basename + "_month").value;
				var anio = document.getElementById(basename + "_year").value;
				var full = document.getElementById(basename + "_text");
				full.value = dia + "/" + mes + "/" + anio;
			}
		</script>
		<s:actionerror/>
		<s:form action="liquidarPlanesAction" cssClass="elegante">
			<ss:datetimepicker label="Fecha liquidacion" key="fecha" pickerfunc="show_picker" changefunc="update_date" />
			<s:if test="fieldErrors != null && fieldErrors['idPlanes'] != null">
				<s:iterator value="fieldErrors['idPlanes']" var="error">
					<s:div>
						<span class="errorMessage"><s:property value="#error"/></span>
					</s:div>
				</s:iterator>
			</s:if>
			<s:div>
				<table class="listado">
					<thead>
						<tr>
							<th>Plan</th>
							<th>Responsable</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="planes">
							<s:set var="checked" value="idPlanes != null && idPlanes.contains(id)"/>
							<tr>
								<td><s:property value="id"/></td>
								<td><s:property value="responsable.nombre"/></td>
								<td><s:checkbox theme="simple" name="idPlanes" value="%{#checked}" fieldValue="%{id}"/></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</s:div>
			<s:div>
				<s:submit theme="simple" value="Liquidar" name="method:liquidar" />
				<a href="EdificioListarAction?redi=planes">Volver</a>	
			</s:div>
		</s:form>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
