<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<html>
<body>


<form action="movie_list.jsp" method="get">
<button name="browse" type="submit" value="genre">Browse Movies by Genre</button>
<button name="browse" type="submit" value="title">Browse Movies by Title</button>
<input type="hidden" name="n" value="10">
<input type="hidden" name="page" value="1">
</form>

<%
  Class.forName("com.mysql.jdbc.Driver").newInstance();
    Connection connection =
      DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "testuser", "testpass");
    Statement select = connection.createStatement();

    ResultSet result = select.executeQuery("Select * from genres order by name");
%>

<%

    while (result.next()){
      // Group movies by genre
      String genreID = result.getString(1);
      out.print("<a href='genre_page.jsp?genre="+result.getString(2)+"&n=10&page=1'>"+result.getString(2)+"</a><br>");
    } 
    select.close();
    result.close();
    connection.close();
%> 

</body>
</html>