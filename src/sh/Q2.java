package sh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Q2 {

	/**
	 * I stored the data set at '/home/cloudera/Desktop/' during the
	 * execution of the project
	 */

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

		String load = "load data local inpath '/home/cloudera/Desktop/olympic_data.csv' overwrite into table olympic";
		try {
			statement.execute(load);
			System.out.println("Data had been added into olympic table successfully.");
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}

		statement.close();
		connection.close();
	}
}
