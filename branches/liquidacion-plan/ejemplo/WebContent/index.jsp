<%@ taglib uri="/struts-tags" prefix="s" %>  
 <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Bienvenidos a Nicolino Propiedades</title>
    <link rel="stylesheet" href="menu_style.css" type="text/css" />
</head>
<body>
<div id="contenedor">
<div >
<ul class="menu"> 
<li class="top"><a href="#" class="top_link"><span>Autenticacion</span></a></li>
</ul>
</div>
<table id="tablaLogin" height ="200" cellpadding="0" cellspacing="0">
		<tr><td height="5" bgcolor="#383838" colspan="3"></td></tr>
		<tr>
			<td width="5"  bgcolor="#383838"></td>
			<td width="800" bgcolor="#F0F0F0" align="center">
				<s:form action="LoginAction!checkLoggin" cssClass="form">  
					<font>Usuario</font>
					<input class="elegante" type ="text" name="Username" size="24"/>
					<font>Clave</font>
					<input class="elegante" type ="password" name="Password" size="25" />					
					<s:submit value="Ingresar"/>
				</s:form>
			</td>	  
			<td width="5"  bgcolor="#383838"></td>
		</tr>
		<tr><td height="5" bgcolor="#383838" colspan="3"></td></tr>
	</table>
	<s:actionerror cssClass="error"/>

<jsp:include page="/WEB-INF/jspf/footer.jspf"></jsp:include>