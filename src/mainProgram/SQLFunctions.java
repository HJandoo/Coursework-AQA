package mainProgram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLFunctions {
	
	public static int getNumberOfPlayers() {
		int i = 0;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root", "password");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery("select * from player_statistics");

			while (rs.next()) {
				i++;
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;

	}

	public static void updateStats(Player[] players, int i) {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root", "password");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery("select * from player_statistics where username = '" + players[0].username
					+ "' or username = '" + players[1].username + "';");

			while (rs.next()) {
				i++;
			}

			for (int j = 0; j < 2; j++) {
				st.execute("update `coursework`.`player_statistics`" + "set `kills` = '" + players[j].kills
						+ "', `deaths` = '" + players[j].deaths + "', `K/D` = '" + players[j].killdiff
						+ "', `games_played` = '" + players[j].gamesPlayed + "' where `username` = '" + players[j].username + "';");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void getWeapons(Weapon[][] weapons) {

		int i = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root", "password");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery("select * from weapons");

			while (rs.next()) {

				weapons[0][i] = new Weapon(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				weapons[1][i] = new Weapon(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				i++;
				
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void getStats(Object[][] data) {
		int i = 0;
		
		try {
			
			
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root", "password");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery("select username, kills, deaths, `K/D` from player_statistics;");

			while (rs.next() && i < getNumberOfPlayers()) {
				data[i][0] = rs.getString(1);
				data[i][1] = rs.getInt(2);
				data[i][2] = rs.getInt(3);
				data[i][3] = rs.getInt(4);
				i++;
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}



}
