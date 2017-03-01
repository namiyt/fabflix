package servlet;

import java.io.IOException;
import java.sql.*;
import javax.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		Connection connection = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "testuser", "testpass");

			stmt = connection.createStatement();
			result = stmt.executeQuery("select * from employees where email='"+email+"'");
			if (result.next()) {
				if (result.getString(2).equals(password)) { 
			        response.sendRedirect(request.getContextPath()+ "/employeeDashboard.jsp");
				} else {
			        response.sendRedirect(request.getContextPath()+ "/_dashboard.jsp");
				}
			} else {
			    response.sendRedirect(request.getContextPath()+ "/_dashboard.jsp");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}