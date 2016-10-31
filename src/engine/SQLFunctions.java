package engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

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

	public static void updateMatches(Player[] players, int i) {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root", "password");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery("select * from player_statistics where username = '" + players[0].username
					+ "' or username = '" + players[1].username + "';");

			while (rs.next()) {
				i++;
			}

			for (int j = 0; j < i; j++) {
				st.execute("update `coursework`.`player_statistics`" + "set `games_played` = '" + players[j].gamesPlayed
						+ "' where `username` = '" + players[j].username + "';");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateStats(Player[] players) {

		int i = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root", "password");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery("select * from player_statistics where username = '" + players[0].username
					+ "' or username = '" + players[1].username + "';");

			while (rs.next()) {
				i++;
			}

			for (int j = 0; j < i; j++) {
				st.execute("update `coursework`.`player_statistics`" + "set `kills` = '" + players[j].kills
						+ "', `deaths` = '" + players[j].deaths + "', `K/D` = '" + players[j].killdiff
						+ "', `games_played` = '" + players[j].gamesPlayed + "', `matches_won` = '"
						+ players[j].gamesWon + "', `win_rate` = '" + players[j].winRate + "' where `username` = '"
						+ players[j].username + "';");
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
	
	public static Weapon getRandomWeapon(Weapon weapon) {
		Random r = new Random();
		int i = r.nextInt(5) + 1;
				
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root", "password");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery("select * from weapons where idweapons = '" + i + "';");
			
			while (rs.next()) {
				weapon = new Weapon(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				
				return weapon;
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return weapon;
	}

	public static void getStats(Object[][] data) {
		int i = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root", "password");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery("select username, kills, deaths, `K/D`, win_rate from player_statistics;");

			while (rs.next() && i < getNumberOfPlayers()) {
				data[i][0] = rs.getString(1);
				data[i][1] = rs.getInt(2);
				data[i][2] = rs.getInt(3);
				data[i][3] = rs.getDouble(4);
				data[i][4] = rs.getDouble(5);
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void refillAmmo(Player[] players, Weapon[][] weapons, int i) {
		getWeapons(weapons);
		
		players[i].weapon.ammo = weapons[i][players[i].weapon.code - 1].ammo;
	}

	public static void getOfflineWeapons(Weapon[][] weapons) {
		
		for (int i = 0; i < 2; i++) {

			weapons[i][0] = new Weapon(0, "Pistol", 70, 100, 400);
			
		}
		
	}
}
