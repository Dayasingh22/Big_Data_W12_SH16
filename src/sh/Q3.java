package sh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Q3 {

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

		// Create partition by year and load data from olympic table in the partition
		String createPartition = "create table medalsByYear ("
				+ "athlete_name string, age int, country string, closing_date string, sport string, gold int, silver int, bronze int, total int"
				+ ") partitioned by (year string)" + " row format delimited"
				+ " fields terminated by ','";
		try {
			statement.execute(createPartition);
			System.out.println("Partition named medalsByYear created.");
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}

		try {
			statement.execute("set hive.exec.dynamic.partition.mode=nonstrict");
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}

		// Load the data into the partition dynamically year wise
		String loadPartition = "insert overwrite table medalsByYear partition(year)"
				+ " select athlete_name, age, country, cast(year as string), closing_date, sport, gold, silver, bronze, total from olympic";
		try {
			statement.execute(loadPartition);
			System.out.println("Data loaded.");
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}

		statement.close();
		connection.close();
	}
}
