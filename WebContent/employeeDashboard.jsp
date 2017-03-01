<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Dashboard</title>
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
	<h1>Welcome</h1>
	<h2> Add New Star </h2>
	<form method="post" action="addStarServlet">
		<table border="0">
			<tr>
				<td>Star's name</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>Star's Date of Birth</td>
				<td><input type="date" name="dob"></td>
			</tr>
			<tr>
				<td>Star's Photo_URL</td>
				<td><input type="text" name="photo"></td>
			</tr>
		</table>
		<input type="submit" value="Add">
	</form>
	<c:if test="${not empty message}">
	    <p>${message}</p><br><br>
    </c:if>
    <h2> Add Movie </h2>
    <form method="post" action="addMovieServlet">
		<table border="0">
			<tr>
				<td>Title</td>
				<td><input type="text" name="title"></td>
			</tr>
			<tr>
				<td>Year</td>
				<td><input type="text" name="year"></td>
			</tr>
			<tr>
				<td>Director</td>
				<td><input type="text" name="dir"></td>
			</tr>
			<tr>
				<td>Star</td>
				<td><input type="text" name="star"></td>
			</tr>
			<tr>
				<td>Genre</td>
				<td><input type="text" name="genre"></td>
			</tr>
		</table>
		<input type="submit" value="Add Movie">
	</form>
    <c:if test="${not empty addMovie}">
		<c:forTokens items="${addMovie}" delims="," var="movie">
		   <c:out value="${movie}"/><p>
		</c:forTokens>
    </c:if>
    <h2> Metadata of Database </h2>
    <form method="post" action="metaDataServlet">
		<input type="submit" value="Click for MetaData">
	</form><br>
	<c:if test="${not empty metaData}">
		<c:forTokens items="${metaData}" delims="," var="name">
		   <c:out value="${name}"/><p>
		</c:forTokens>
    </c:if>
</body>
</html>