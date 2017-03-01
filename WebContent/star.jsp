<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
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
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "testuser", "testpass");

    Statement select = connection.createStatement();

    ResultSet result = select.executeQuery("Select * from stars where id="+request.getParameter("star_id"));
%>

<table border="1"><tr><th>Movies</th></tr>
    <%
    Statement getMovies = connection.createStatement();
    ResultSet movies = getMovies.executeQuery("Select * from movies");

    while (result.next()){

      // Get movies to populate movies column of table
      movies = getMovies.executeQuery("select * from movies where id in (select m.movie_id from stars_in_movies m inner join stars s on s.id = m.star_id where s.id="+result.getString(1)+")");

      out.print("<h2>"+result.getString(2)+" "+result.getString(3)+"</h2>");
      out.print("<h3>(ID: "+result.getString(1)+")</h3>");
      out.print("<h4>Born "+result.getString(4)+"</h4>");
      out.print("<img src='"+result.getString(5)+"' alt='Actor picture'>");


      out.print("<tr>");
      out.print("<td>");
      while(movies.next()) {
        out.print("<a href='single_movie.jsp?movie_id="+movies.getString(1)+"'>"+movies.getString(2)+"</a><br>");
      }
      out.print("</td>");
      out.println("</tr>");
    } 
    out.print("</table>");


    getMovies.close();
    movies.close();
    select.close();
    result.close();
    connection.close();
%> 

</body>
</html>