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

    ResultSet result = select.executeQuery("Select * from movies where id="+request.getParameter("movie_id"));
%>

<table border="1"><tr><th>Genres</th><th>Stars</th></tr>
    <%
    Statement getGenres = connection.createStatement();
    ResultSet genres = getGenres.executeQuery("select * from genres");
    Statement getStars = connection.createStatement();
    ResultSet stars = getStars.executeQuery("Select * from stars");

    while (result.next()){

      String url = "http://";
      if (!result.getString(6).startsWith("http")) {
        url += result.getString(6);
      } else {
        url = result.getString(6);
      }

      // Get genres to populate genre column of table
      genres = getGenres.executeQuery("select * from genres where id in (select g.genre_id from genres_in_movies g inner join movies m on m.id = g.movie_id where m.id="+result.getString(1)+")");

      // Get stars to populate stars column of table
      stars = getStars.executeQuery("select * from stars where id in (select s.star_id from stars_in_movies s inner join movies m on m.id = s.movie_id where m.id="+result.getString(1)+")");

      out.print("<h2>"+result.getString(2)+" ("+result.getString(3)+")</h2>");
      out.print("<h3>(ID: "+result.getString(1)+")</h3>");
      out.print("<h3>Directed by "+result.getString(4)+"</h3>");
      out.print("<img src='"+result.getString(5)+"' alt='Movie poster'>");
      out.print("<p><a target='_blank' href='"+url+"'>View Trailer</a></p>");


      out.print("<tr>");
      out.print("<td>");
      while(genres.next()) {
        out.print("<a href='genre_page.jsp?genre="+genres.getString(2)+"&n=10&page=1'>"+genres.getString(2)+"</a><br>");
      }
      out.print("</td>");
      out.print("<td>");
      while(stars.next()) {
        out.print("<a href='star.jsp?star_id="+stars.getString(1)+"'>"+stars.getString(2)+" "+stars.getString(3)+"</a><br>");
      }
      out.print("</td>");
      out.println("<td><a href='shopping_cart.jsp?id="+result.getString(1)+"&title="+result.getString(2)+"'><input type='button' value='Add to Cart'/></a></td>");
      out.println("</tr>");

    } 
    out.print("</table>");


    getStars.close();
    stars.close();
    getGenres.close();
    genres.close();
    select.close();
    result.close();
    connection.close();
%> 

</body>
</html>