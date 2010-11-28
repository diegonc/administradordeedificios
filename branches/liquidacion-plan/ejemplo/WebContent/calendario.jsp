<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
  <TITLE></TITLE>
</HEAD>

<SCRIPT LANGUAGE="JavaScript">

      calDocFrameset = 
        "<FRAMESET ROWS='30,*' FRAMEBORDER='no' border=0 framespacing=0>\n" +
        "  <FRAME NAME='topCalFrame' SRC='javascript:parent.opener.calDocTop' SCROLLING='no' frameborder='no' marginwidth=0 marginheight=0>\n" +
        "  <FRAME NAME='bottomCalFrame' SRC='javascript:parent.opener.calDocBottom' SCROLLING='no' frameborder='no' marginwidth=0 marginheight=0>\n" +
        "</FRAMESET>\n";

    document.write(calDocFrameset);

</SCRIPT>

</HTML>