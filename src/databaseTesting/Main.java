package databaseTesting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		int count = 0;

		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);

		System.out.println("Enter Username");

		String u = s.next();
		String unique = "select * from player_statistics where username = '" + u + "';";

		System.out.println("Enter Password");

		String p = s.next();

		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root", "password");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery(unique);

			while (rs.next()) {
				count++;
			}

			if (count != 0) {
				System.out.println("Duplicate user");
			} else {
				String create = "insert into player_statistics(username, password, kills, deaths, `K/D`) values('" + u
						+ "', '" + p + "', 0, 0, 0);";
				System.out.println("Unique user");
				st.execute(create);
				System.out.println("Created?");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
