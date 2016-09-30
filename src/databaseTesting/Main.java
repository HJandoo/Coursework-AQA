package databaseTesting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);

		System.out.println("Enter Username");

		String u = s.next();

		System.out.println("Enter Password");

		String p = s.next();

		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/coursework", "HJandooDB",
					"kierath123");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery(
					"select * from player_statistics where username = '" + u + "' and password = '" + p + "'");

			while (rs.next()) {
				System.out.println(rs.getInt("id") + " " + rs.getString("username") + " " + rs.getString("password")
						+ " " + rs.getInt("kills") + " " + rs.getInt("deaths"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
