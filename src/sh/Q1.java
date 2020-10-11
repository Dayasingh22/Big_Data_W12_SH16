package sh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Q1 {

	private static String driverClass = "org.apache.hive.jdbc.HiveDriver";

	public static void main(String[] args) throws SQLException {
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException exception) {
			System.out.println(exception.toString());
			System.exit(1);
		}

		Connection connection = DriverManager.getConnection(
				"jdbc:hive2://localhost:10000/default", "hive", "");

		Statement statement = connection.createStatement();

		String Olympic_table = "create table olympic ("
				+ "athlete_name string, age int, country string, year int, closing_date string, sport string, gold int, silver int, bronze int, total int"
				+ ")" + " row format delimited" + " fields terminated by ','";

		try {
			statement.execute(Olympic_table);
			System.out.println("Oympic Table Created successfully");
		} catch (SQLException ex) {
			System.out.println(ex);
		}

		statement.close();
		connection.close();
	}
}
