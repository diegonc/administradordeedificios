<%@page import="expensas.*"%>
<%@page import="edificio.*"%>
<%@page import="propiedades.*"%>
<jsp:include page="/WEB-INF/jspf/header.jspf"></jsp:include>
<%@ page language="java" contentType="text/html" import="java.util.*"%>
<%@ page language="java" contentType="text/html" import="expensas.appl.*"%>
<%@ page language="java" contentType="text/html" import="expensas.dto.*"%>
<%@ page language="java" contentType="text/html" import="utilidades.*"%>
<%@ page language="java" contentType="text/html" import="org.hibernate.*"%>
<%
	int idEdificio = Integer.parseInt(request.getParameter("idEdificio"));
	int idProp = Integer.parseInt(request.getParameter("idProp"));	 
	
	Session hSession = HibernateUtil.getSession();
	EdificioDTO edificio = (EdificioDTO) hSession.load(
			EdificioDTO.class, idEdificio);
	Set<TipoPropiedadDTO> tipos = edificio.getTipoPropiedades();
	Iterator<TipoPropiedadDTO> iteradorTipos = tipos.iterator();
	PropiedadDTO prop = new PropiedadDTO();
	
	while (iteradorTipos.hasNext()) {
		List<PropiedadDTO> propiedades = iteradorTipos.next().getPropiedades();
		for (PropiedadDTO propiedadDTO : propiedades) {
			if (propiedadDTO.getId() == idProp) {
				prop = propiedadDTO;		
			}
		}
	}
	
	boolean mostrarDeudaOrd = false;
	
	ExpensaAppl expAppl = new ExpensaAppl();
	SessionFactory factory = HibernateUtil.getSessionFactory();	
	List<ExpensaDTO> expensas = expAppl.getExpensaActivaPorIdProp(factory,idProp);
	
	ExpensaDTO exp = new ExpensaDTO();
	if (!expensas.isEmpty()) {
		Session sessionCobro = HibernateUtil.getSession();
		ExpensasCobroAppl cobroAppl = new ExpensasCobroAppl(sessionCobro);
		exp = expensas.get(0);
		List<ExpensaCobroDTO> cobros = cobroAppl.getCobroPorIdExpensas(exp.getId());
		sessionCobro.close();
		if (cobros.isEmpty()) {
			mostrarDeudaOrd = true;
		} else {
			ExpensaCobroDTO cobro = cobros.get(0);
			if (cobro.getMontoPago() < exp.getMonto()) {
				exp.setMonto(exp.getMonto() - cobro.getMontoPago());
				mostrarDeudaOrd = true;
			}
		}
	}
	
	boolean mostrarDeudaExt = false;
	List<ExpensaDTO> expensasExtra = expAppl.getExpensaExtActivaPorIdProp(factory,idProp);
	ExpensaDTO expExt = new ExpensaDTO();
	if (!expensasExtra.isEmpty()) {
		Session sessionCobro = HibernateUtil.getSession();
		ExpensasCobroAppl cobroAppl = new ExpensasCobroAppl(sessionCobro);
		expExt = expensasExtra.get(0);
		List<ExpensaCobroDTO> cobrosExtra = cobroAppl.getCobroPorIdExpensas(exp.getId());
		sessionCobro.close();
		if (cobrosExtra.isEmpty()) {
			mostrarDeudaExt = true;
		} else {
			ExpensaCobroDTO cobro = cobrosExtra.get(0);
			if (cobro.getMontoPago() < expExt.getMonto()) {
				expExt.setMonto(expExt.getMonto() - cobro.getMontoPago());
				mostrarDeudaExt = true;
			}
		}
	}
	
%>

<table  cellpadding="0" cellspacing="0" >
<tr>
	<td width="5"  class="borde"></td>
	<td width="800" class="borde" align="center"><h3 id="header">Consulta Monto Expensas a Pagar</h3></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<table id="tablaRegisCobros" height ="300" cellpadding="0" cellspacing="0" border="0">
<tr>
<td width="5"  class="borde"></td>
	<td width="15"  class="fondo"></td>
	<td width="770" class="fondo" align="left">
			<fieldset>
		  		<legend>Monto de Expensas a Pagar</legend>
			 		<table  border="0" cellpadding="0" cellspacing="0" border="2">
			 			<tr>
			 				<td align="right"><label for="edificio">Edificio: <%=prop.getTipoPropiedad().getEdificio().getNombre()%></label> </td>
			 				<td align="right">&nbsp;&nbsp;<label for="Nivel">Nivel: <%=prop.getNivel()%></label> </td>
			 				<td align="right">&nbsp;&nbsp;<label for="Orden">Orden: <%=prop.getOrden()%></label> </td>
			 			</tr>
			 			<tr><td colspan="5" height="10"></td></tr>
			 			<%if (mostrarDeudaOrd) { %>
			 			<tr>
			 				<td><label for="monto">Monto Adeudado por Expensas Ordinarias Liquidada: <%=exp.getMonto()%></label> </td>
				 		</tr>
				 		<% } else { %>
				 		<tr>
			 				<td><label for="monto">No existe liquidación ordinaria adeudada</label> </td>
				 		</tr>
				 		<% } %>
				 		<%if (mostrarDeudaExt) { %>
			 			<tr>
			 				<td><label for="monto">Monto Adeudado por Expensas Extraordinaria Liquidada: <%=expExt.getMonto()%></label> </td>
				 		</tr>
				 		<% } else { %>
				 		<tr>
			 				<td><label for="monto">No existe liquidación extraordinaria adeudada</label> </td>
				 		</tr>
				 		<% } %>
				 		<tr><td colspan="5" height="10"></td></tr>
				 		<tr>
			 				<td><label for="saldoOrd">Saldo Oridinario de la Propiedad: <%=prop.getCtaOrdSaldoExp()%></label> </td>
				 		</tr>
				 		<tr>
			 				<td><label for="saldoExt">Saldo Extraordinario de la Propiedad: <%=prop.getCtaExtSaldoExp()%></label> </td>
				 		</tr>
				 		<tr><td colspan="5" height="10"></td></tr>
				  		<tr>
			  			<td> <a href="expensasPropiedadesListado.jsp?id=<%=idEdificio%>">Volver</a> </td>
			  			</tr>
			  		</table>			  	
			</fieldset>
	</td>
	<td width="15"  class="fondo"></td>
	<td width="5" class="borde"></td>
</tr>
</table>
<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>
