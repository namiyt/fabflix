<%@page import="java.sql.*,
 javax.sql.*,
 java.util.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*,
 com.*"
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
  <TITLE>Tomcat Test Form</TITLE>
</HEAD>

<BODY BGCOLOR="#FDF5E6">
<H1 ALIGN="CENTER">Login</H1> 

<FORM ACTION="login.jsp"
      METHOD="POST">
      <div ALIGN="CENTER">
  		Email <INPUT TYPE="TEXT" NAME="email"><BR>
  		Password <INPUT TYPE="PASSWORD" NAME="password"><BR>
		<INPUT TYPE="SUBMIT" VALUE="Submit">   	
      </div>
  
</FORM>

</BODY>
</HTML>
