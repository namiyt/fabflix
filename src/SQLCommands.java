import java.util.Scanner;
import java.sql.*;

public class SQLCommands {
	
	public static void sql(Connection connection) throws Exception {
		Scanner s = new Scanner(System.in);
		System.out.print("Enter SQL Command: ");
		String input = s.nextLine();
		Statement select = connection.createStatement();
		ResultSet result = null;
		int update;
		String query;
		
		if (input.charAt(0) == 'S' || input.charAt(0) == 's') {
			result = select.executeQuery(input);
			ResultSetMetaData rsmd = result.getMetaData();
			int numOfColumn = rsmd.getColumnCount();
			while (result.next()) {
				for (int i = 1; i < numOfColumn; i++) {
					if (i > 1) {
						System.out.print(", ");
					}
					String columnValue = result.getString(i);
		            System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}
		} else if (input.charAt(0) == 'U' || input.charAt(0) == 'u') {
			update = select.executeUpdate(input);
			System.out.println("Updated " + update + " of rows");
		} else if (input.charAt(0) == 'I' || input.charAt(0) == 'i') {
			update = select.executeUpdate(input);
			System.out.println("Inserted " + update + " of rows");
		} else if (input.charAt(0) == 'D' || input.charAt(0) == 'd') {
			update = select.executeUpdate(input);
			System.out.println("Deleted " + update + " of rows");
		} else {
			System.out.println("Incorrect SQL command!");
		}
	}
}
