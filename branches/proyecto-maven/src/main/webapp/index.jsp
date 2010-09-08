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
<li class="top"><a href="#" class="top_link"><span>Login</span></a></li>
</ul>
</div>


	<table id="tablaLogin" height ="200" cellpadding="0" cellspacing="0">
		<tr><td height="5" bgcolor="#383838" colspan="3"></td></tr>
		<tr>
			<td width="5"  bgcolor="#383838"></td>
			<td width="800" bgcolor="#F0F0F0" align="center">
				<s:form action="Login_Action" class="form">  
					<font>Usuario</font><input class="elegante" type ="text" name="Username" />
					<font>Password</font><input class="elegante" type ="password" name="Password" />					
					<s:submit/>
				</s:form>
			</td>	  
			<td width="5"  bgcolor="#383838"></td>
		</tr>
		<tr><td height="5" bgcolor="#383838" colspan="3"></td></tr>
	</table>
</div>
  
</body>  
<jsp:include page="footer.jsp"></jsp:include>