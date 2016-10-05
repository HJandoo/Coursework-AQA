package mainProgram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLFunctions {
	
	public static void updateStats(Player[] players, int i) {
		
		int[] id = new int[2];
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"password");

			Statement st = c.createStatement();
			
			ResultSet rs = st.executeQuery("select * from player_statistics where username = '" + players[0].username + "' or username = '" + players[1].username + "';");
			
			while (rs.next()) {
				id[i] = rs.getInt(1);
				i++;
			}
			
			for (int j = 0; j < 2; j++) {
				st.execute("update `coursework`.`player_statistics`"
						 + "set `kills` = '" + players[j].kills + "', `deaths` = '" + players[j].deaths +  "', `K/D` = '" + players[j].killdiff + "' where `id` = '" + id[j] + "';");
			}			
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void getWeapons(Weapon[] weapons) {
		
		int i = 0;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"password");

			Statement st = c.createStatement();
			
			ResultSet rs = st.executeQuery("select * from weapons");
			
			while (rs.next()) {
				weapons[i] = new Weapon(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
