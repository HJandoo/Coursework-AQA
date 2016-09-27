package databaseTesting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();	
		}
		try {

			Connection c = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/coursework", "HJandooDB",
					"kierath123");

			Statement s = c.createStatement();

			ResultSet rs = s.executeQuery("select * from player_statistics;");
			
			while (rs.next()) {
				System.out.println(rs.getInt("id") + " " + rs.getString("username") + " "
						+ rs.getString("password") + " " + rs.getInt("kills") + " " + rs.getInt("deaths"));
			}
			
			
			c.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
