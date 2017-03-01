import java.util.Scanner;
import java.sql.*;

public class InsertStar
{
	public static void insert(Connection connection, Scanner in) throws Exception {
		String input;
		System.out.print("Name of actor to add: ");
		input = in.nextLine();
		String[] inputs = input.split(" ");
		insertStar(connection, inputs);
		System.out.println(input + " added to the database.");
	}
	
	private static void insertStar(Connection connection, String[] inputs) throws Exception{
		Statement insert = connection.createStatement();
		if (inputs.length == 2) {
			insert.executeUpdate("insert into stars (first_name, last_name) values ('" + inputs[0] + "','" + inputs[1] + "')");
		} else {
			insert.executeUpdate("insert into stars (first_name, last_name) values ('','" + inputs[0] + "')");
		}
	}
}
