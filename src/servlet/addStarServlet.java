package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class addStarServlet
 */
@WebServlet("/addStarServlet")
public class addStarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addStarServlet() {
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
		response.setContentType("text/html");
		String name = request.getParameter("name");
		String dob = request.getParameter("dob");
		String photo = request.getParameter("photo");
		String[] inputs = name.split(" ");
		String message = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		Connection connection = null;
		try {
			  connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "testuser", "testpass");
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			message = "Failed to connect to database. Check username and password";
		}
		Statement insert = null;
		try {
			insert = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (inputs.length == 2) {
			try {
				insert.executeUpdate("insert into stars (first_name, last_name, dob, photo_url) values (" 
				+ "'" + inputs[0] + "','" + inputs[1] + "','" + dob + "','" + photo + "')");
				message = inputs[0] + " " + inputs[1] + " added to database";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				message = "Failed inserting " + inputs[0] + " " + inputs[1] + " to database";
			}
		} else {
			try {
				insert.executeUpdate("insert into stars (first_name, last_name, dob, photo_url) values (" 
				+ "'','" + inputs[0] + "','" + dob + "','" + photo + "')");
				message = inputs[0] + " " + " added to database";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				message = "Failed inserting " + inputs[0] + " to database";
			}
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher("/employeeDashboard.jsp").forward(request, response);
     //   response.sendRedirect(request.getContextPath()+ "/employeeDashboard.jsp");
	}

}
