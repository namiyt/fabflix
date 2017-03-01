<%@page contentType="text/html" pageEncoding="UTF-8" %>

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
</head>
<body>

	<form action="movie_list.jsp" method="get">
		<button name="browse" type="submit" value="genre">Browse Movies by Genre</button>
		<button name="browse" type="submit" value="title">Browse Movies by Title</button>
		<input type="hidden" name="page" value="1">
		<input type="hidden" name="n" value="10">
	</form>

	<script>
	$(function() {
		$('#title').keyup(function () {   
			var title = $("#title").val();
			dataString = "title="+title;
			
			$.ajax({
				url:"Auto",
				type:"post",
				data:dataString,
				success:function(data){
				 $( "#title" ).autocomplete({   
				     source: data   
				   });
				},error:  function(data, status, er){
				         console.log(data+"_"+status+"_"+er);
				         },
			});
		 });
	});
	</script>
	
	<div class="ui-widget">
		<form method="post" action="findSingleMovie">
			<label for="title"></label>
			<input id="title" type="text" name="title">
			<input type="submit" value="Search">
		</form>
	</div>

</body>
</html>

