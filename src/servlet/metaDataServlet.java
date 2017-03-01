package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class metaDataServlet
 */
@WebServlet("/metaDataServlet")
public class metaDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public metaDataServlet() {
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
		String metaData = "";

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
			metaData = "Failed to connect to database. Check username and password";
		}

		try {
			DatabaseMetaData metaDB = connection.getMetaData();
			List<String> tableNames = new ArrayList<String>();
            ResultSet result = metaDB.getTables(null, null, "%", null);
            while (result.next()) {
            	tableNames.add(result.getString(3));
            }
			
            for (String table : tableNames) {
            	result = metaDB.getColumns(null, null, table, null);
            	while(result.next()) {
            		metaData +=  table.toUpperCase() + " --- " + result.getString(4) + " --- " + result.getString(6) + "(" + result.getString(7) + ")" + ",";
            	}
            }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		request.setAttribute("metaData", metaData);
		request.getRequestDispatcher("/employeeDashboard.jsp").forward(request, response);
	}
}
