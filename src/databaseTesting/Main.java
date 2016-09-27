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
					"jdbc:mysql://localhost:3306/sql8137762", "sql8137762",
					"7kENtw5WXj");

			Statement s = c.createStatement();

			ResultSet rs = s.executeQuery("select * from Player Statistics");
			
			while (rs.next()) {
				System.out.println(rs.getInt("ID") + " " + rs.getString("Username") + " "
						+ rs.getString("Password"));
			}
			
			
			c.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
