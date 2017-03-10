package engine;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLFunctions {

	public static int getNumberOfPlayers() {
		int i = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"Ht3jkdtw7Hvx");

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

	public static int getPlayerId(Player player) {
		int id = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"Ht3jkdtw7Hvx");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery(
					"select * from coursework.player_statistics where username = '" + player.username + "';");

			while (rs.next()) {
				id = rs.getInt(1);
			}

			return id;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;

	}

	public static void findWMD() {

		int i = 0, numberOfPlayers = getNumberOfPlayers();
		int[][] kills = new int[numberOfPlayers][6];

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"Ht3jkdtw7Hvx");
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery(
					"select kills_with_pistol, kills_with_smg, kills_with_machine_gun, kills_with_sniper_rifle, kills_with_shotgun, kills_with_bazooka, most_used_weapon_id from weapon_kills;");

			while (rs.next() && i < numberOfPlayers) {
				kills[i][0] = rs.getInt(1);
				kills[i][1] = rs.getInt(2);
				kills[i][2] = rs.getInt(3);
				kills[i][3] = rs.getInt(4);
				kills[i][4] = rs.getInt(5);
				kills[i][5] = rs.getInt(6);

				i++;
			}

			for (int k = 0; k < numberOfPlayers; k++) {
				int temp = kills[k][0];

				for (int j = 0; j < 6; j++) {
					if (temp <= kills[k][j]) {
						temp = kills[k][j];
					}

				}

				int weaponid = 0;

				for (int j = 0; j < 6; j++) {
					if (temp == kills[k][j]) {
						weaponid = j + 1;
						j = 5;
					}

				}

				st.execute("UPDATE `coursework`.`weapon_kills` SET `most_used_weapon_id`='" + weaponid
						+ "' WHERE `player_id`='" + (k + 1) + "';");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void addKills(Player player) {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"Ht3jkdtw7Hvx");

			Statement st = c.createStatement();

			String column = "";

			switch (player.weapon.code) {
			case 1:
				column = "kills_with_pistol";
				break;
			case 2:
				column = "kills_with_smg";
				break;
			case 3:
				column = "kills_with_machine_gun";
				break;
			case 4:
				column = "kills_with_sniper_rifle";
				break;
			case 5:
				column = "kills_with_shotgun";
				break;
			case 6:
				column = "kills_with_bazooka";
				break;
			}

			ResultSet rs = st.executeQuery("select " + column + " from coursework.weapon_kills where player_id = "
					+ getPlayerId(player) + ";");
			rs.next();
			int kills = rs.getInt(1) + 1;
			st.execute("update coursework.weapon_kills set " + column + " = " + kills + " where player_id = "
					+ getPlayerId(player) + ";");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateMatches(Player[] players, int i) {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"Ht3jkdtw7Hvx");

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

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"Ht3jkdtw7Hvx");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery("select * from player_statistics where username = '" + players[0].username
					+ "' or username = '" + players[1].username + "';");

			while (rs.next()) {
				i++;
			}

			for (int j = 0; j < i; j++) {

				if (players[j].winRate == null) {
					players[j].winRate = 0.0;
				}

				st.execute("update `coursework`.`player_statistics`" + "set `kills` = '" + players[j].kills
						+ "', `deaths` = '" + players[j].deaths + "', `K/D` = '" + players[j].killDifference
						+ "', `games_played` = '" + players[j].gamesPlayed + "', `matches_won` = '"
						+ players[j].gamesWon + "', `win_rate` = '" + players[j].winRate + "' where `username` = '"
						+ players[j].username + "';");
			}

			findWMD();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Weapon[] getWeapons() {

		int i = 0;
		Weapon[] weapons = new Weapon[6];

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"Ht3jkdtw7Hvx");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery("select * from weapons");

			while (rs.next()) {

				weapons[i] = new Weapon(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
						new File(rs.getString(6)));
				i++;

			}

			return weapons;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return weapons;
	}

	public static void getStats(Object[][] data) {
		int i = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"Ht3jkdtw7Hvx");
			Statement st = c.createStatement();
			Statement st2 = c.createStatement();
			ResultSet rs = st.executeQuery("select username, kills, deaths, `K/D`, win_rate from player_statistics;");
			ResultSet rs2 = st2.executeQuery("select most_used_weapon_id from weapon_kills;");

			while (rs.next() && rs2.next() && i < getNumberOfPlayers()) {
				data[i][0] = rs.getString(1);
				data[i][1] = rs.getInt(2);
				data[i][2] = rs.getInt(3);
				data[i][3] = rs.getDouble(4);
				data[i][4] = rs.getDouble(5);
				data[i][5] = rs2.getInt(1);

				switch ((int) data[i][5]) {

				case 1:
					data[i][5] = "Pistol";
					break;
				case 2:
					data[i][5] = "SMG";
					break;
				case 3:
					data[i][5] = "Machine Gun";
					break;
				case 4:
					data[i][5] = "Sniper Rifle";
					break;
				case 5:
					data[i][5] = "Shotgun";
					break;
				case 6:
					data[i][5] = "Bazooka";
					break;
				}

				i++;
			}

			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void getOfflineWeapons(Weapon[][] weapons) {

		for (int i = 0; i < 2; i++) {
			weapons[i][1] = new Weapon(1, "Pistol", 120, 400, 70, new File("Pistol.wav"));
			weapons[i][2] = new Weapon(2, "SMG", 40, 90, 250, new File("SMG.wav"));
			weapons[i][3] = new Weapon(3, "Machine Gun", 90, 170, 200, new File("Machine Gun.wav"));
			weapons[i][4] = new Weapon(4, "Sniper Rifle", 700, 700, 16, new File("Sniper Rifle.wav"));
			weapons[i][5] = new Weapon(5, "Shotgun", 1000, 1500, 12, new File("Shotgun.wav"));
			weapons[i][6] = new Weapon(6, "Bazooka", 1200, 2000, 4, new File("Pistol.wav"));
		}

	}
}
