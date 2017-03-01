<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<html>
<body>
	<%
		if (request.getParameter("browse").equals("title"))  {
	%>
	<jsp:include page="browse_title.jsp"/>
	<%
		} else { %>
		<jsp:include page="browse_genre.jsp"/>
		<%}
	%>

</body>
</html>