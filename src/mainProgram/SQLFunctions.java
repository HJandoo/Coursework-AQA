package mainProgram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLFunctions {
	
	public void updateStats(Player[] players, int id) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"password");

			Statement st = c.createStatement();
			
			@SuppressWarnings("unused")
			ResultSet rs = st.executeQuery("select * from player_statistics where username = '" + players[0].username + "' or username = '" + players[1].username + "';");

			st.execute("update `coursework`.`player_statistics`");
			
			
		} catch (Exception e){
			
		}
		
	}
	

}
