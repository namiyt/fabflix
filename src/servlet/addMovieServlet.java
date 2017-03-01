package servlet;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class addMovieServlet
 */
@WebServlet("/addMovieServlet")
public class addMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private static boolean created = false;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addMovieServlet() {
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
		String title = request.getParameter("title");
		int year = Integer.parseInt(request.getParameter("year"));
		String dir = request.getParameter("dir");
		String star = request.getParameter("star");
		String genre = request.getParameter("genre");
		String[] inputs = star.split(" ");

		String addMovie = "";

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
			addMovie += "Failed to connect to database. Check username and password" + ",";
		}
		Statement insert = null;
		String query = null;
		ResultSet result;
		
		try {
			Statement stmt = null;	
			stmt = connection.createStatement();
			stmt.execute("DROP PROCEDURE IF EXISTS add_movie");

			createProcedure();
			
						
			insert = connection.createStatement();
			
			result = insert.executeQuery("select * from movies "
					+ "where title = '" +title + "' and director = '" + dir+ "' and year = " + year +"");
			if (!result.next()) {
				addMovie += title + "(" + year + ") director by " + dir + " featuring " + star +" does not exist. Inserting movie." + ",";
			}
			
			CallableStatement ps = connection.prepareCall("{call add_movie(?,?,?,?,?,?)}");
			
			if (inputs.length == 2) {
				ps.setString(1,title);
				ps.setInt(2, year);
				ps.setString(3, dir);
				ps.setString(4, inputs[0]);
				ps.setString(5, inputs[1]);
				ps.setString(6, genre);
				ps.execute();

			} else {
				ps.setString(1,title);
				ps.setInt(2, year);
				ps.setString(3, dir);
				ps.setString(4, "");
				ps.setString(5, inputs[0]);
				ps.setString(6, genre);
				ps.execute();
			}
			addMovie = "Database updated with the following parameters ("+title+"/"+year+","+"/"+dir+"/"+"/"+star+"/"+genre+")";
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		request.setAttribute("addMovie", addMovie);
		request.getRequestDispatcher("/employeeDashboard.jsp").forward(request, response);
	}
	
	private static void createProcedure() throws SQLException {
		Statement stmt = null;	
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "testuser", "testpass");
;
		stmt = connection.createStatement();
		
		stmt.execute("CREATE PROCEDURE add_movie("
		+"IN mTitle VARCHAR(50),"
		+"IN mYear INT,"
		+"IN mDirector VARCHAR(50),"
		+"IN starFirst VARCHAR(50),"
		+"IN starLast VARCHAR(50),"
		+"IN genre VARCHAR(50))"

		+"process:BEGIN "
		+"declare movieID INT DEFAULT NULL;"
		+"declare starID INT DEFAULT NULL;"
		+"declare genreID INT DEFAULT NULL;"
		
		+"select id into movieID from movies "
		+"where title = mTitle and "
		+"director = mDirector and "
		+"year = mYear;"
		
		+"if movieID is null then "
		+"insert into movies(title,year,director) values (mTitle,mYear,mDirector);"
		
		+"select id into movieID from movies "
		+"where title = mTitle and "
		+"director = mDirector and "
		+"year = mYear;"	
		+"select 'Movie added to Movies table' as 'm';"
		+"end if;"
		
		+"if movieID is null then "
		+"LEAVE process;"
		+"end if;"
		
		+"select id into starID from stars "
		+"where first_name = starFirst and last_name = starLast;"
		
		+"select id into genreID from genres "
		+"where name = genre;"
		
		+"if starID is null then "
		+"insert into stars(first_name, last_name) values (starFirst,starLast);"
		+"select id into starID from stars "
		+"where first_name = starFirst and "
		+"last_name = starLast;"
		+"select 'Star added to Stars table' as 's';"
		+"end if;"
		
		+"if genreID is null then "
		+"insert into genres(name) values (genre);"
		+"select id into genreID from genres "
		+"where name = genre;"
		+"select 'Genre added to Genres table' as 'g';"
		+"end if;"
		
		+"if starID is not null then "
		+"insert into stars_in_movies(star_id,movie_id) values (starID,movieID);"
		+"select 'Movie and Star added to stars_in_movies table' as 's_m';"
		+"end if;"
		
		+"if genreID is not null then "
		+"insert into genres_in_movies(genre_id,movie_id) values (genreID,movieID);"
		+"select 'Movie and Genre added to genres_in_movies table' as 'g_m';"
		+"end if;"
		+"END");
		created = true;
	}
}
