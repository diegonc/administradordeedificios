<%@page import="javax.lang.model.element.Element"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html" import="edificio.EdificioDTO"%>
<%@ page language="java" contentType="text/html" import="gastos.dto.TipoGastoDTO"%>
<%@ page language="java" contentType="text/html" import="expensas.dto.ExpensaDTO"%>
<%@ page language="java" contentType="text/html" import="propiedades.PropiedadDTO"%>
<%@ page language="java" contentType="text/html" import="expensas.calculo.ElementoPrevisionGasto"%>
<%@ page language="java" contentType="text/html" import="gastos.dto.GastoDTO"%>
<%@ page language="java" contentType="text/html" import="gastos.dto.GastoRealDTO"%>
<%@ page language="java" contentType="text/html" import="gastos.dto.GastoPrevisionDTO"%>
<%@ page language="java" contentType="text/html" import="java.util.List"%>
<%@ page language="java" contentType="text/html" import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html" import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html" import="utilidades.NumberFormat"%>
<jsp:useBean id="edificio" scope="session" class="edificio.EdificioDTO"/>
<jsp:useBean id="detalleExpensa" scope="session" class="beans.LiquidacionBean"/>
<script type="text/javascript">
function ocultarBotones(){
	var bt1 = document.getElementById("boton1");
	var bt2= document.getElementById("boton2");
	bt2.style.display ="none";
	bt1.style.display ="none";
}
function mostrarBotones(){
	var bt1 = document.getElementById("boton1");
	var bt2= document.getElementById("boton2");
	bt2.style.display ="";
	bt1.style.display ="";
}
</script>
<% 
EdificioDTO edificioDTO = edificio;
%>

<table cellpadding="0" cellspacing="0" width="700" align="center">
	<tr><td colspan="1" height="15"></td>
	</tr>
	<tr >
		<td align="center">Edificio:<%=edificioDTO.getNombre()%><br></br><%=edificioDTO.getCalle()%>&nbsp;<%=edificioDTO.getNumero()%>&nbsp;<%=edificioDTO.getLocalidad()%></td>						
	</tr>
	<tr><td colspan="1" height="15"></td>
</table>
<% if (edificio.getForma_liq_exp().equals("PRORRATEO")){ %>
		<table height="10" width="700" align="center" > <tr height="10"><td align="center"><div><h3>Gastos Extraordinarios del Per&iacute;odo</h3></div></td></tr></table>
		<table id="tabla3" width ="430" cellpadding="0" cellspacing="0" align="center" border="1">
			<tr>
				<td width="100">Nro Folio</td>
				<td width="250">Descripci&oacute;n</td>
				<td width="80">Monto</td>
				<td width="80">Parciales</td>
			</tr>
			
		  <%
		 	double montoTotal=0;
		    HashMap<TipoGastoDTO, List<GastoDTO>> tipoGastoGasto = detalleExpensa.getGastosExtraordinariosDelPeriodo();
			Iterator<TipoGastoDTO> it = tipoGastoGasto.keySet().iterator();
			double montoAcumulado=0.0;
		    while (it.hasNext()) {
		        TipoGastoDTO tipoGasto = (TipoGastoDTO) it.next();
		        List<GastoDTO> gastosEnTipo = ( List<GastoDTO>) tipoGastoGasto.get(tipoGasto);
		      
		  %>
			    <tr>
			       <td colspan="4" align="center"><%=tipoGasto.getDescripcion()%></td>					
				</tr>
		
				<tr>	
					<td>&nbsp;&nbsp;</td>
					<td colspan="1">Previsiones del mes Anterior</td>
					<td>&nbsp;&nbsp;</td>		
					<td>&nbsp;&nbsp;</td>			
				</tr>
		    <%  
		    	
		    	montoAcumulado =0.0;
		    	for (GastoDTO gastoActual : gastosEnTipo) {
		    		int h=0;
		    		String color ="bgcolor= '#E0E0E0'";
					if ((h%2)==0){
						color="";
					}
		     %>
		        	<tr>
				        <td><%=gastoActual.getNumeroFolio()%></td>
						<td><%=gastoActual.getDetalle()%></td>
						<td><%=gastoActual.getMonto()%></td>
						<td>&nbsp;</td>
					</tr>				
		   <%
		   			h++;
		   			montoAcumulado+=gastoActual.getMonto();
		         }
		    %>
		    		<tr>
		    			<td>&nbsp;&nbsp;</td>
				        <td colspan="2"> Total previsiones del mes </td>				       
						<td><%=montoAcumulado %></td>
					</tr>		  
					 <tr>
		<%} %>	
		
	
		</table>
<%} %>


	<table height="10" width="700" align="center"> <tr height="20"><td align="center"><div><h3>Detalle de la Liquidaci&oacute;n</h3></div></td></tr></table>
		<table id="tabla4" cellspacing="0" cellpadding="0" border="1" class="listado" align="center">
		<tr class="listado_par" >
			<td>Piso</td>
			<td>DTO</td>
			<td>Responsable</td>
			<td>Deuda<br/> Previa<br/> Saldo</td>
			<td>Deuda<br/> Previa<br/> Interes</td>
			<td>Exp.<br> Extraordinarias</td>
			<td>Int.PrimerVto</td>
			<td>Total.PrimerVto</td>
			<%if (edificio.getMora().equals("punitorio")){%>	
				<td>Int. SegundoVto</td>
				<td>Total. SegundoVto</td>
			<%} %>			
			</tr>
		<%
		List<ExpensaDTO> expensas = detalleExpensa.getExpensasExtraordinarias();
		
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
					<td ><%=propiedad.getNivel()%></td>
					<td><%=propiedad.getOrden()%></td>
					<td> <%=propiedad.getPropietario().getDni() %></td>
					<td><%=exp.getDeudaPrevia() %></td>
					<td><%=NumberFormat.redondeoDouble(-exp.getPropiedad().getCtaExtSaldoInt()) %></td>
					<td><%=exp.getMonto() %></td>
					<td><%=exp.getIntereses()%></td>
					<td><%=NumberFormat.redondeoDouble(exp.getIntereses()+exp.getDeudaPrevia()+exp.getMonto())%></td>
				<%if (edificio.getMora().equals("punitorio")){%>
					<td><%=exp.getInteresSegundoVencimiento()%></td>
					<td><%=NumberFormat.redondeoDouble(exp.getInteresSegundoVencimiento() + exp.getDeudaPrevia() + exp.getMonto())%></td>
				<%} %>				
			</tr>
		
		<%}%>
		</table>
	


		<table height="50"> <tr height="20"><td></td></tr></table>
		<input type="button"  id="boton1" onclick="javascript:print()" value="Imprimir" onblur="ocultarBotones()" onfocus="mostrarBotones()">
		<input type="button"  id="boton2" onclick="javascript:window.close();" value="Cerrar">
	
