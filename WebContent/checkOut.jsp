<%@page import="java.sql.*,
 javax.sql.*,
 java.util.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*,
 com.*"
%>
<jsp:useBean id="movie" class="com.Movie" scope="request" />
<jsp:useBean id="cart" class="com.ShoppingCart" scope="session" />
<jsp:setProperty name="movie" property="*" />

<html>
<body>

	<a href="home.jsp">Back to Search</a>


	<FORM ACTION="/jspTest/servlet/Checkout" METHOD="GET">

		Last Name: <INPUT TYPE="TEXT" NAME="lastname"><BR>

		Credit Card Number: <INPUT TYPE="PASSWORD" NAME="cardnum"><BR>
		<CENTER>
			<INPUT TYPE="SUBMIT" VALUE="Submit Order">
		</CENTER>

	</FORM>

	<%
		request.setAttribute("cart",cart);
		
	%>
	
</body>
</html>