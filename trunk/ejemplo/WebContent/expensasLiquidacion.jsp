
<%@page import="javax.lang.model.element.Element"%><jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html" import="edificio.EdificioDTO"%>
<%@ page language="java" contentType="text/html" import="gastos.dto.TipoGastoDTO"%>
<%@ page language="java" contentType="text/html" import="expensas.dto.ExpensaDTO"%>
<%@ page language="java" contentType="text/html" import="propiedades.PropiedadDTO"%>
<%@ page language="java" contentType="text/html" import="expensas.calculo.ElementoPrevisionGasto"%>
<%@ page language="java" contentType="text/html" import="gastos.dto.GastoDTO"%>
<%@ page language="java" contentType="text/html" import="java.util.List"%>
<%@ page language="java" contentType="text/html" import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html" import="java.util.Iterator"%>
<jsp:useBean id="edificio" scope="session" class="edificio.EdificioDTO"/>
<jsp:useBean id="detalleExpensa" scope="session" class="beans.LiquidacionBean"/>
<script type="text/javascript">
function ocularYMostrar(id){
	document.getElementById(id).style.display=document.getElementById(id).style.display=="none"?"block":"none";
	if (id=="tabla1"){
		document.getElementById("detalle1").innerHTML= document.getElementById(id).style.display=="none"?"Ver":"Ocultar";
	}
	if (id=="tabla2"){
		document.getElementById("detalle2").innerHTML= document.getElementById(id).style.display=="none"?"Ver":"Ocultar";
	}
}
</script>
<% EdificioDTO edificioDTO = edificio;
%>
<div class="contenido">
	<div class="titulo"><h3>Liquidaci&oacute;n de Expensas</h3></div>
	<div class="cuerpo">
		<table cellpadding="0" cellspacing="0" width="700" align="center">
			<tr><td colspan="1" height="15"></td>
			</tr>
			<tr >
				<td align="center">Edificio:<%=edificioDTO.getNombre()%></br><%=edificioDTO.getCalle()%>&nbsp;<%=edificioDTO.getNumero()%>&nbsp;<%=edificioDTO.getLocalidad()%></td>						
			</tr>
			<tr><td colspan="1" height="15"></td>
		</table>
		
<% if (edificio.getForma_liq_exp().equals("PRORRATEO")){ %>
		<table height="10" width="700"> <tr height="10"><td align="center"><div><h3>Gastos del Per&iacute;odo</h3></div></td><td><a href="#" onclick="ocularYMostrar('tabla1')" ><span id="detalle1">Ver</span></a></td></tr></table>
		<table id="tabla1" width ="430" cellpadding="0" cellspacing="0" align="center" class="listado" style="display: none;">
			<tr class="listado_par">
				<td width="100">Nro Folio</td>
				<td width="250">Descripci&oacute;n</td>
				<td width="80">Monto</td>
				<td width="80">Parciales</td>
			</tr>
			
		  <%
		  	double montoTotal=0;
		    HashMap<TipoGastoDTO, ElementoPrevisionGasto> tipoGastoGasto = detalleExpensa.getGastosOrdinariosDelPeriodo();
			Iterator<TipoGastoDTO> it = tipoGastoGasto.keySet().iterator();
			double montoAcumulado=0.0;
		    while (it.hasNext()) {
		        TipoGastoDTO tipoGasto = (TipoGastoDTO) it.next();
		        ElementoPrevisionGasto gastosEnTipo = (ElementoPrevisionGasto) tipoGastoGasto.get(tipoGasto);
		  %>
			    <tr bgcolor="#E0E0E0">
			       
					<td colspan="4" align="center"><%=tipoGasto.getDescripcion()%></td>
					
				</tr>
				<tr>	
					<td>&nbsp;&nbsp;</td>
					<td colspan="1">Previsiones del mes Anterior</td>
					<td>&nbsp;&nbsp;</td>		
					<td>&nbsp;&nbsp;</td>			
				</tr>
		    <%  
		    	montoAcumulado =gastosEnTipo.obtenerMontoPrevisionMesAnterior();
		    	for (GastoDTO gastoActual : gastosEnTipo.getPrevisionesMesAnterior()) {
		     %>
		        	<tr>
				        <td><%=gastoActual.getNumeroFolio()%></td>
						<td><%=gastoActual.getDetalle()%></td>
						<td><%=gastoActual.getMonto()%></td>
						<td>&nbsp;</td>
					</tr>				
		   <%
		         }
		    %>
		    		<tr class="listado_par">
		    			<td>&nbsp;&nbsp;</td>
				        <td colspan="2"> Total previsiones del mes anterior</td>				       
						<td><%=montoAcumulado %></td>
					</tr>		  
					 <tr>
					 	<td>&nbsp;&nbsp;</td>
						<td colspan="1">Gastos Reales del Mes</td>
						<td>&nbsp;&nbsp;</td>		
						<td>&nbsp;&nbsp;</td>
					</tr>
			<%  montoAcumulado =gastosEnTipo.obtenerMontoGastosRealesMesAnterior();
		    	for (GastoDTO gastoActual : gastosEnTipo.getGastosRealesMesAnterior()) {
		     %>
		        	<tr>
				        <td><%=gastoActual.getNumeroFolio()%></td>
						<td><%=gastoActual.getDetalle()%></td>
						<td><%=gastoActual.getMonto()%></td>
						<td>&nbsp;</td>
					</tr>				
		   <%
		         }
		    %>
		   			<tr class="listado_par">
		    			<td>&nbsp;&nbsp;</td>
				        <td colspan="2"> Total Gastos Reales del mes anterior</td>				       
						<td><%=montoAcumulado %></td>
					</tr>	
					<tr class="listado_par">
		    			<td>&nbsp;&nbsp;</td>
				        <td colspan="2"> Total del mes anterior</td>				       
						<td><%=gastosEnTipo.obtenerMontoDiferenciaPrevisionGastoMesAnterior()%></td>
					</tr>
		    		<tr>
		    			<td>&nbsp;&nbsp;</td>
				        <td colspan="1"> Previsiones del mes Actual</td>						
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						
					</tr>
		   <%  montoAcumulado =gastosEnTipo.obtenerMontoPrevisionMesActual();
		    	for (GastoDTO gastoActual : gastosEnTipo.getPrevisiones()) {
		     %>
		        	<tr>
				        <td><%=gastoActual.getNumeroFolio()%></td>
						<td><%=gastoActual.getDetalle()%></td>
						<td><%=gastoActual.getMonto()%></td>
						<td>&nbsp;</td>
					</tr>				
		   <%
		         }
		    %>
		    		<tr class="listado_par">
		    			<td>&nbsp;&nbsp;</td>
				        <td colspan="2"> Total previsiones del mes Actual</td>				     
						<td><%=montoAcumulado %></td>
					</tr>
		   
				
		 <%
		     }			
		 %> 
		</table>
<%} %>
				
		<table height="10" width="700"> <tr height="20"><td align="center"><div><h3>Detalle de la Liquidaci&oacute;n</h3></div></td><td><a href="#" onclick="ocularYMostrar('tabla2')"><span id="detalle2">Ver</span></a></td></tr></table>
		<table id="tabla2" cellspacing="0" cellpadding="0" border="1" class="listado" align="center" style="display: none;">
		<tr class="listado_par" >
			<td>Piso-DTO</td>
			<td>Responsable</td>
			<td>Deuda</br> Previa</td>
			<td>Exp.</br> Ordinarias</td>
			<td>Int.PrimerVto</td>
			<%if (edificio.getMora().equals("punitorio")){%>	
				<td>Int. SegundoVto</td>
			<%} %>			
			</tr>
		<%
		List<ExpensaDTO> expensas = detalleExpensa.getExpensasOrdinarias();
		
		int h =0;
		for (ExpensaDTO exp : expensas){
			PropiedadDTO propiedad = exp.getPropiedad();
			String color ="bgcolor= '#E0E0E0'";
			if ((h%2)==0){
				color="";
			}
			h++;
		%>
		
				<tr <%=color%>>
					<td ><%=propiedad.getNivel() +" " + propiedad.getNivel() %></td>
					<td> <%=propiedad.getPropietario().getDni() %></td>
					<td><%=exp.getDeudaPrevia() %></td>
					<td><%=exp.getMonto() %></td>
					<td><%=exp.getIntereses()%></td>
				<%if (edificio.getMora().equals("punitorio")){%>
					<td><%=exp.getInteresSegundoVencimiento()%></td>
				<%} %>				
			</tr>
		
		<%}%>
		</table>
		<table height="50"> <tr height="20"><td></td></tr></table>
		
		<a href="EdificioListarAction?redi=expensa">Volver</a>
	</div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>