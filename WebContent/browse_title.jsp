<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<html>
<head>
	<link href="jquery/jquery-ui.css" rel="stylesheet">
	<script src="jquery/jquery-1.11.2.js"></script>
	<script src="jquery/jquery-ui.js"></script>
	<style>
	.popbox {
	    display: none;
	    position: absolute;
	    z-index: 100;
	    width: 400px;
	    padding: 10px;
	    background: #EEEFEB;
	    color: #000000;
	    border: 1px solid #4D4F53;
	}
	</style>
</head>
<body>

<form action="movie_list.jsp" method="get">
<button name="browse" type="submit" value="genre">Browse Movies by Genre</button>
<button name="browse" type="submit" value="title">Browse Movies by Title</button>
<input type="hidden" name="page" value="1">
<input type="hidden" name="n" value="10">
</form>

<%
	Class.forName("com.mysql.jdbc.Driver").newInstance();
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
    

    Statement getCount = connection.createStatement();
    ResultSet count = getCount.executeQuery("Select count(*) from movies");

    int pages;
    int results = Integer.parseInt(request.getParameter("n"));

    count.next();
    pages = Integer.parseInt(count.getString(1))/results + 1;
    int currentPage = Integer.parseInt(request.getParameter("page"));

    Statement select = connection.createStatement();

    ResultSet result = select.executeQuery("Select * from movies order by title limit " + (currentPage-1)*results + "," + results);

    out.print("Results per page: <form style='display:inline-block'><select name='n'><option value='10'>10</option><option value='25'>25</option><option value='50'>50</option></select><input type='submit' value='Go'><input type='hidden' name='browse' value='title'><input type='hidden' name='page' value='1'></form><br>");

    out.print("Page " + currentPage + " of " + pages + "<br>");
    if (currentPage != 1) {
      out.print("<form style='display:inline-block'><button name='page' value='"+(currentPage-1)+"'>Prev</button><input type='hidden' name='browse' value='title'><input type='hidden' name='n' value='" + request.getParameter("n") + "'></form>");
    }
    if (currentPage != pages) {
      out.print("<form style='display: inline-block'><button name='page' value='"+(currentPage+1)+"'>Next</button><input type='hidden' name='browse' value='title'><input type='hidden' name='n' value='" + request.getParameter("n") + "'></form>");
    }
%>

<table border="1"><tr><th>ID</th><th>Title</th><th>Year</th>
      <th>Director</th><th>Genre</th><th>Stars</th></tr>
    <%
    Statement getGenres = connection.createStatement();
    ResultSet genres = getGenres.executeQuery("select * from genres");
    Statement getStars = connection.createStatement();
    ResultSet stars = getStars.executeQuery("Select * from stars");

    while (result.next()){
      // Get genres to populate genre column of table
      genres = getGenres.executeQuery("select * from genres where id in (select g.genre_id from genres_in_movies g inner join movies m on m.id = g.movie_id where m.id="+result.getString(1)+")");

      // Get stars to populate stars column of table
      stars = getStars.executeQuery("select * from stars where id in (select s.star_id from stars_in_movies s inner join movies m on m.id = s.movie_id where m.id="+result.getString(1)+")");

      out.print("<tr>");
      out.print("<td>"+result.getString(1)+"</td>");
      out.print("<div id='pop' class='popbox'></div>");
      out.print("<td><a class='popup' data-popbox='pop' href='single_movie.jsp?movie_id="+result.getString(1)+"'>"+result.getString(2)+"</a></td>");
      out.print("<td>"+result.getString(3)+"</td>");
      out.print("<td>"+result.getString(4)+"</td>");
      out.print("<td>");
      while(genres.next()) {
        out.print("<a href='genre_page.jsp?genre="+genres.getString(2)+"'>"+genres.getString(2)+"</a><br>");
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
%>

	<script>
		var moveLeft = 0;
		var moveDown = 0;
		$('a.popup').hover(function (e) {   
		    var target = "#" + ($(this).attr('data-popbox'));
		    $(target).show();
		    moveLeft = $(this).outerWidth();
		    moveDown = ($(target).outerHeight() / 2);
			var id = $(this).attr('href').substring(26);
			dataString = "id="+id;
    		var div = document.getElementById('pop');
    		div.innerHTML = "";
		    $.ajax({
		    	url:"popupMovie",
		    	type:"post",
		    	data:dataString,
		    	success:function(response) {
		    		div.innerHTML = div.innerHTML + "Banner Poster: <img src="+response[0]+" alt='Image not available'> <br>Year Release: "+response[1]+"<br> Star: "+response[2] + " " + response[3];
		    	}
		    });
		    
		    
		}, function () {
		    var target = "#" + ($(this).attr('data-popbox'));
		    if (!($("a.popup").hasClass("show"))) {
		        $(target).hide();
		    }
		 });
		
		$('a.popup').mousemove(function (e) {
		    var target = '#' + ($(this).attr('data-popbox'));

		    leftD = e.pageX + parseInt(moveLeft);
		    maxRight = leftD + $(target).outerWidth();
		    windowLeft = $(window).width() - 40;
		    windowRight = 0;
		    maxLeft = e.pageX - (parseInt(moveLeft) + $(target).outerWidth() + 20);

		    if (maxRight > windowLeft && maxLeft > windowRight) {
		        leftD = maxLeft;
		    }

		    topD = e.pageY - parseInt(moveDown);
		    maxBottom = parseInt(e.pageY + parseInt(moveDown) + 20);
		    windowBottom = parseInt(parseInt($(document).scrollTop()) + parseInt($(window).height()));
		    maxTop = topD;
		    windowTop = parseInt($(document).scrollTop());
		    if (maxBottom > windowBottom) {
		        topD = windowBottom - $(target).outerHeight() - 20;
		    } else if (maxTop < windowTop) {
		        topD = windowTop + 20;
		    }
		    $(target).css('top', topD).css('left', leftD);
		});
	</script>

<%
    getCount.close();
    count.close();
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