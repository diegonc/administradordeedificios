<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>

<%@page import="edificio.EdificioAppl"%>
<%@page import="utilidades.HibernateUtil"%>
<%@page import="edificio.EdificioDTO"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script>
function submit(){
	
}
</script>
<% 	String id =request.getParameter("id");
	Calendar fechaActual = new GregorianCalendar();
	int anio = fechaActual.get(Calendar.YEAR);
	int mes = fechaActual.get(Calendar.MONTH)+1;
	int dia = fechaActual.get(Calendar.DATE);
	EdificioAppl edificioAppl = new EdificioAppl();
	EdificioDTO edificio = edificioAppl.getEdificio(HibernateUtil.getSessionFactory(),Integer.parseInt(id));
	if(dia>=edificio.getDia_primer_vto()){
		mes++;
		if(mes==13){ 
			mes=1;
			anio++;
		}
	}
	
%>
<div class="contenido">
	<div class="titulo"><h3>Liquidacion de Expensas</h3></div>
	<div class="cuerpo">
	<table>
		<tr>
			<td width="15"  class="fondo"></td>
			<td width="770" class="fondo" align="left">
				<form class="elegante" name="expensasLiquidacionResultante" action="expensasLiquidacionResultante">
					<fieldset>
				  		<legend>Periodo de Liquidación</legend>
					 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
					 			
					 			<tr>
					 				<td align="right">Mes:
					 					<input type="text" id="mes" name="mes" value="<%=mes%>"  size="5" readonly="readonly"  />	 				
						 						
					 				</td>
					 				<td align="right">Año:
					 					<input type="text" id="anio" name="anio" value="<%=anio%>"  size="5"  readonly="readonly" />
					 				</td>			 			
								
						 		</tr>
						 		
					  		</table>
					  		<input type="hidden" value=<%=id%> name="id" id="id"></input>			  	
					</fieldset>
					<input class="btn" type="button" value="Aceptar" onclick="submit()" />
					<a href="EdificioListarAction?redi=expensa">Volver</a> 
				</form>
			</td>
			<td width="15"  class="fondo"></td>			
		</tr>
	</table>
	<s:actionerror/>
	
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>