import java.util.Scanner;
import java.sql.*;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class StarSearch
{
	public static void search(Connection connection, Scanner in) throws Exception {
		String input;
		System.out.print("Enter an actor's name or ID: ");
		input = in.nextLine();
		String []inputs = input.split(" ");
		getMovies(connection, inputs);
	}
	
	private static void getMovies(Connection connection, String []name) throws Exception {
		Statement select = connection.createStatement();
		Statement selectLast = connection.createStatement();
		Statement selectID = connection.createStatement();
		
		String movieIDs = getMovieIDs(select, selectLast, selectID, name);
		if (movieIDs.length() == 1) {
			System.out.println("Actor not found");
			return;
		}
		
		if (name.length == 2) {
			System.out.println("Movies featuring " + name[0] + " " + name[1]);
		} else {
			System.out.println("Movies featuring " + name[0]);
		}
		
		ResultSet result = select.executeQuery("select * from movies where id in " + movieIDs);
		while (result.next()) {
			System.out.println("Id = " + result.getInt(1));
			System.out.println("Title = " + result.getString(2));
			System.out.println("Year = " + result.getInt(3));
			System.out.println("Director = " + result.getString(4));
			System.out.println("Banner URL = " + result.getString(5));
			System.out.println("Trailer URL = " + result.getString(6));
			System.out.println();
		}
	}
	
	
	
	private static String getMovieIDs(Statement select, Statement selectLast, Statement selectID, String []name) throws Exception {
		ArrayList<Integer> movieIDs = new ArrayList<Integer>();
		StringBuilder sb = new StringBuilder();
		sb.append("(");

		ResultSet result, resultLast = null, resultID = null;
		
		if (name.length == 2) {
			result = select.executeQuery("select m.movie_id from stars_in_movies m "
					+ "inner join stars s on s.id = m.star_id where s.first_name='"
					+ name[0] + "' and s.last_name='" + name[1] + "'");
		} else {
			result = select.executeQuery("select m.movie_id from stars_in_movies m "
					+ "inner join stars s on s.id = m.star_id where s.first_name='"
					+ name[0] + "'");
			resultLast = selectLast.executeQuery("select m.movie_id from stars_in_movies m "
					+ "inner join stars s on s.id = m.star_id where s.last_name='"
					+ name[0] + "'");
			resultID = selectID.executeQuery("select m.movie_id from stars_in_movies m "
					+ "inner join stars s on s.id = m.star_id where s.id='"
					+ name[0] + "'");
		}
			
		while (result.next()) {
			movieIDs.add(result.getInt(1));
		}
		
		if (resultLast != null) {
			while (resultLast.next()) {
				movieIDs.add(resultLast.getInt(1));
			}
		}
		
		if (resultID != null) {
			while (resultID.next()) {
				movieIDs.add(resultID.getInt(1));
			}
		}
		
		for (Integer i : movieIDs) {
			sb.append(i + ",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		
		return sb.toString();
	}
}
