<%@page import="expensas.dto.ExpensaDTO"%>
<%@page import="propiedades.ResponsableAppl"%>
<%@page import="propiedades.Responsable"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="propiedades.PropiedadDTO"%>
<%@page import="propiedades.TipoPropiedadDTO"%>
<jsp:useBean id="lista" scope="session" class="beans.LiquidacionBean"/>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html"
	import="org.hibernate.*"%>

<script src="calendario.js" type="text/javascript"></script>	
<script type="text/javascript">
function armarFecha(elemento){
	var anio = document.getElementById("anio").value;
	var mes = document.getElementById("mes").value;
	var dia = document.getElementById("dia").value;
	elemento.value=dia+"/"+mes+"/"+anio;
}

function validar(thisform) {
	var fecha=document.getElementById("fecha");
	var cantCuotas = document.getElementById("cantCuotas");
	var valido=true;
	armarFecha(fecha);
	if (cantCuotas.value=="" || isNaN(cantCuotas.value)) {
		alert("La cantidad de cuotas debe ser numerico");
		valido=false;
	} 
	if (fecha.value=="//" && valido==true) {
		alert("Debe elgir una fecha para el plan");
		valido=false;
	}
	if (valido==true) {
		document.CalculoCuotasAction.submit();
	}
}

</script>

<%
	List<ExpensaDTO> expensas = new ArrayList<ExpensaDTO>();
	String tipo = "";
	String redi = "";
	if (lista.getExpensasOrdinarias() != null ) {
		expensas = lista.getExpensasOrdinarias();
		tipo = "Ordinarias";
		redi = "ord";
	} else  if (lista.getExpensasExtraordinarias() != null) {
		expensas = lista.getExpensasExtraordinarias();
		tipo = "Extraordinarias";
		redi = "ext";
	}
	int idEdificio = expensas.get(0).getPropiedad().getTipoPropiedad().getEdificio().getId();
%>
<div class="contenido">
<div class="titulo"><h3>Seleccion de Expensas</h3></div>
<div class="cuerpo">
	<form class="elegante" id="CalculoCuotasAction" name="CalculoCuotasAction" action="CalculoCuotasAction">
	<fieldset><legend>Listado de Expensas</legend>	
	<table width="500" border="1" class="listado" align="center">
		<tr>
			<td class="listado_par">Tipo Propieadad</td>
			<td class="listado_par">Nivel</td>
			<td class="listado_par">Orden</td>
			<td class="listado_par">Numero Op.</td>
			<td class="listado_par">Monto</td>
			<td class="listado_par">Intereses</td>
			<td class="listado_par">&nbsp;</td>
		</tr>
		<%for (ExpensaDTO expensasDTO : expensas) {
		%>
		<tr>
			<td><%=expensasDTO.getPropiedad().getTipoPropiedad().getNombreTipo()%></td>
			<td><%=expensasDTO.getPropiedad().getNivel()%></td>
			<td><%=expensasDTO.getPropiedad().getOrden()%></td>
			<td><%=expensasDTO.getNumeroOperacion()%></td>
			<td><%=expensasDTO.getMonto()%></td>
			<td><%=expensasDTO.getIntereses()%></td>
			<td bgcolor="#F0F0F0">
				<input type="checkbox" name="expElegidas" id="expElegidas" value="<%=expensasDTO.getId()%>"/> 
			</td>
		</tr>
		<%
		}
		//TODO: listar los responsables para elegir el que se hara cargo del plan
		%>
	
	</table>
	<table>
	<tr>
		<td>Cantidad de Cuotas del Plan: <input type="text" id="cantCuotas" name="cantCuotas" size="3"/></td>
		<td><label for="fecha">Fecha:&nbsp;</label>  </td>
			<td><input style="display: none;" type="text" id="fecha" name="fecha" size="15"/>
				&nbsp;&nbsp;<input type="text" name="dia" id="dia" maxlength="2" size="2" style="width:22px;" disabled >
				&nbsp;<input type="text" name="mes" id="mes" maxlength="2" size="2"  style="width:22px;" disabled  >
				&nbsp;<input type="text" name="anio" id="anio" maxlength="4" size="4" style="width:32px;" disabled >
				&nbsp;<a href="JavaScript:doNothing()" onclick="allowPrevious=true;setDateField(document.CalculoCuotasAction.dia,document.CalculoCuotasAction.mes,document.CalculoCuotasAction.anio);top.newWin = window.open('calendario.jsp','cal','WIDTH=200,HEIGHT=160,TOP=200,LEFT=300')" onMouseOver="javascript: window.status = 'Abrir calendario'; return true;" onMouseOut="window.status=' '; return true;" ><img src="images/calendario.gif" ></img></a>
			</td>
	</tr>
	<tr>
	<td>Responsable: <input readonly size="7" id="responsableDNI" name="responsableDNI" value="<%=lista.getResponsable()%>"></input></td>
	<td>
	</table>
	<input class="btn" type="button" value="Calcular Cuotas" onclick="validar()"/>
	</fieldset>
	<s:actionerror cssClass="error"/>	
	<a href="planesPropListarAction?id=<%=idEdificio%>&dni=<%=lista.getResponsable()%>&tipo=<%=redi%>">Volver</a>
	</form>
	</div>
	</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
