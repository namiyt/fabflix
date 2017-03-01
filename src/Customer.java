import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class Customer {
	// Insert a customer into the database
	public static void insert(Connection connection, Scanner in) throws Exception {
		String input;
		Statement select = connection.createStatement();
		ResultSet result = null;
		
		System.out.print("Name of customer to add: ");
		input = in.nextLine();
		String[] inputs = input.split(" ");
		// Query through credit card table to find a match for first_name and last_name or 
		// last_name
		if (inputs.length == 2) {
			result = select.executeQuery("select id, first_name, last_name from creditcards "
					+ "where first_name + ' ' + last_name = '" + inputs[0] + " " + inputs[1] + "'");
		} else {
			result = select.executeQuery("select id, last_name from creditcards "
					+ "where last_name = '" + inputs[0]+ "'");
		}
		// Check if the result is empty
		if (!result.isBeforeFirst()) {
			System.out.println("Customer does not exist in the credit card table");
		} else {
			Statement insert = connection.createStatement();
			// Ask for customer information (address, email, and password)
			ArrayList<String> info = requestCustomerInfo();
			result.next();
			if (inputs.length == 2) {
				insert.executeUpdate("insert into customers (cc_id, first_name, last_name, address, email, password) values (" 
				+ result.getString("id") + ",'" + inputs[0] + "','" + inputs[1] + "','" + info.get(0) + "','" + info.get(1) + "','" + info.get(2) + "')");
			} else {
				insert.executeUpdate("insert into customers (cc_id, first_name, last_name, address, email, password) values (" 
			    + result.getString("id") + ",'','" + inputs[0] + "','" + info.get(0) + "','" + info.get(1) + "','" + info.get(2) + "')");
			}	
			System.out.println(input + " added to the database");
		}
	}
	
	public static ArrayList<String> requestCustomerInfo() {
		ArrayList<String> info = new ArrayList<String>();
		Scanner in = new Scanner(System.in);
		
		System.out.print("Enter an address: ");
		info.add(in.nextLine());
		System.out.print("Enter an email: ");
		info.add(in.nextLine());
		System.out.print("Enter a password: ");
		info.add(in.nextLine());
		
		return info;
	}
	
	// Delete a customer from the database (queried by credit card id)
	public static void delete(Connection connection, Scanner in) throws Exception {
		// Delete a customer from the database. ( The delete operation should be queried by Credit Card id)
		Statement select = connection.createStatement();
		
		Scanner name = new Scanner(System.in);
		System.out.print("(Delete) Enter Customer name: ");
		String input = name.nextLine();
		String[] inputs = input.split(" ");
		
		ResultSet result;
		if (inputs.length == 2) {
			result = select.executeQuery("select id, first_name, last_name from creditcards "
					+ "where first_name + ' ' + last_name = '" + inputs[0] + " " + inputs[1] + "'");
		} else {
			result = select.executeQuery("select id, last_name from creditcards "
					+ "where last_name = '" + inputs[0]+ "'");
		}
		// Check if result is empty
		if (!result.isBeforeFirst()) {
			System.out.println("Customer does not exist in the credit card table");
		} else {
			Statement delete = connection.createStatement();
			// Ask for customer information (address, email, and password)
			result.next();
			delete.executeUpdate("delete from customers where cc_id = '" + result.getString("id") + "'");
			
			System.out.println(input + " deleted from customer table");
		}
	}
}
