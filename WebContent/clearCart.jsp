<%@page import="java.sql.*,
 javax.sql.*,
 java.util.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*,
 com.*"
%>
<jsp:useBean id="cart" class="com.ShoppingCart" scope="session" />
<html>
<body>

		<a href="home.jsp">Back to Search</a>


	<form action="movie_list.jsp" method="get">
	<button name="browse" type="submit" value="genre">Browse Movies by Genre</button>
	<button name="browse" type="submit" value="title">Browse Movies by Title</button>
	<input type="hidden" name="page" value="1">
	<input type="hidden" name="n" value="10">
	</form>

	<%
			cart.empty();
			cart.display(out);
	%>

</body>
</html>