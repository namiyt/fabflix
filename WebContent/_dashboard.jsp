<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Login Page</title>
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
	<h3>Employee Login</h3>
	<form method="post" action="loginServlet">
		<table border="0">
			<tr>
				<td>Email</td>
				<td><input type="text" name="email"></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password"></td>
			</tr>
		</table>
		<input type="submit" value="Login">
	</form>
</body>
</html>