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

		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);

		System.out.println("Enter Username");

		String u = s.next();
		String unique = "select * from player_statistics where username = '" + u + "';";

		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root", "Ht3jkdtw7Hvx");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery(unique);

			while (rs.next()) {
				System.out.println("From database: " + rs.getString(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
