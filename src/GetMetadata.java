import java.util.Scanner;
import java.sql.*;

public class GetMetadata
{
	public static void getMeta(Connection connection) throws Exception{
		Statement select = connection.createStatement();
		ResultSet result = null;
		ResultSetMetaData metadata = null;
		
		getTable(select, result, metadata, "movies");
		getTable(select, result, metadata, "stars");
		getTable(select, result, metadata, "stars_in_movies");
		getTable(select, result, metadata, "genres");
		getTable(select, result, metadata, "genres_in_movies");
		getTable(select, result, metadata, "customers");
		getTable(select, result, metadata, "sales");
		getTable(select, result, metadata, "creditcards");
		System.out.println();
	}
	
	private static void getTable(Statement select, ResultSet result, ResultSetMetaData metadata, String tableName) throws Exception{
		result = select.executeQuery("select * from " + tableName);
		metadata = result.getMetaData();
		System.out.println("====================");
		System.out.println("TABLE_NAME : " + tableName);
		System.out.println("-----ATTRIBUTES-----");
		for (int i = 1; i <= metadata.getColumnCount(); ++i) {
			System.out.println(metadata.getColumnName(i) + " (" + metadata.getColumnTypeName(i) + ")");
		}
	}
}
