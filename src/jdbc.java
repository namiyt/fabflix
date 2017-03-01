import java.util.Scanner;

import java.sql.*;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class jdbc
{
	private static boolean loggedIn = false;
	private static Connection connection = null;
	
	public static void login() throws Exception{
		if (loggedIn == false) {
			Scanner login = new Scanner(System.in);
			String input = null;
			
			do {
				System.out.println("Logging in");
				System.out.print("Please enter database name: ");
				String db = login.nextLine();
				System.out.print("Please enter username: ");
				String username = login.nextLine();
				System.out.print("Please enter password: ");
				String password = login.nextLine();
				
				try {
					// Incorporate mySQL driver
					Class.forName("com.mysql.jdbc.Driver").newInstance();	
					// Connect to the database	
					connection = DriverManager.getConnection("jdbc:mysql:///"+db,username,password);
					
					loggedIn = true;
					break;
				} catch (SQLException err) {
					System.out.println("Incorrect username or password");
					System.out.print("Would you like to exit (Y/N): ");
					input = login.nextLine();
					if (input.equalsIgnoreCase("Y")) {
						System.exit(0);
					}
				}
			} while (input.equalsIgnoreCase("N"));
		}
	}
	
	public static void main (String[] args) throws Exception {
		login();
		
		if (loggedIn == true) {
			String input = "";
			Scanner in = new Scanner(System.in);
			
			while (!input.equalsIgnoreCase("QUIT")) {
				printMenu();
				input = in.nextLine();
				if (input.equalsIgnoreCase("S"))
					StarSearch.search(connection, in);
				if (input.equalsIgnoreCase("I")) 
					InsertStar.insert(connection, in);
				if (input.equalsIgnoreCase("M"))
					GetMetadata.getMeta(connection);
				if (input.equalsIgnoreCase("C"))
					Customer.insert(connection, in);
				if (input.equalsIgnoreCase("D"))
					Customer.delete(connection, in);
				if (input.equalsIgnoreCase("SQL"))
					SQLCommands.sql(connection);
			}
			in.close();
		}
	}
	
	private static void printMenu() {
		System.out.println("========WELCOME TO FABFLIX========");
		System.out.println("[S]earch for a star");
		System.out.println("[I]nsert a new star");
		System.out.println("Insert a [C]ustomer");
		System.out.println("[D]elete a customer");
		System.out.println("Get [M]etadata");
		System.out.println("Enter [SQL] command");
		System.out.println("[QUIT] the program");
		System.out.println("----------------------------------");
		System.out.print("Enter a command: ");
	}
}
