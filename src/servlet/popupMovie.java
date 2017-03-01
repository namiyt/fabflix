package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;


/**
 * Servlet implementation class popupMovie
 */
@WebServlet("/popupMovie")
public class popupMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public popupMovie() {
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
		String id = request.getParameter("id");
		Statement stmt = null;
		String sql = "";
		String star = "";
		ArrayList<String> al=new ArrayList<String>();

		try {			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "testuser", "testpass");
			stmt = connection.createStatement();
			sql= "select * from movies where id='"+id+"'";
			star = "select * from stars_in_movies where movie_id='"+id+"'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
		        al.add(rs.getString("banner_url"));
		        al.add(rs.getString("year"));
			} else {
		        al.add("error");
			}
			ResultSet st = stmt.executeQuery(star);
			st.next();
			String star_id = st.getString("star_id");
			String getStar = "select * from stars where id='"+star_id+"'";
			ResultSet gst = stmt.executeQuery(getStar);
			
			if (gst.next()) {
		        al.add(gst.getString("first_name"));
		        al.add(gst.getString("last_name"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray json = new JSONArray(al);
		response.setContentType("application/json");
		response.getWriter().print(json);
	}

}
