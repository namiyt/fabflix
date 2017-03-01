<%@page import="java.sql.*,
 javax.sql.*,
 java.util.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*,
 com.*"
%>



<%	

  Class.forName("com.mysql.jdbc.Driver").newInstance();
  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
  
  try{
    Statement statement = connection.createStatement();

    String email = request.getParameter("email");
    String password =  request.getParameter("password");

    String query = "select * from customers where email='"  + email + "'";


    // Perform the query
    ResultSet rs = statement.executeQuery(query);
  
    while(rs.next()){
      String r_password = rs.getString("password");
      

      if(r_password.equals(password)){
        //incorrect password
        //session.setAttribute("email",email);
        response.sendRedirect("home.jsp");
        return;

      }
      
      else{
        response.sendRedirect("index.jsp");
        return;
      }

    }

    response.sendRedirect("index.jsp");

    rs.close();
    statement.close();
    connection.close();
  }
  catch(SQLException ex){
    response.sendRedirect("index.jsp");
  }
  



%>