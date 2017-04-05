package engine;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

public class SQLFunctions {

	public static int getNumberOfPlayers() {
		// This subroutine find out how many players there are stored on the database
		int i = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/cscoursework", "root",
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
		// This is used to find the id of a certain player defined by the
		// parameter 'player'
		int id = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/cscoursework", "root",
					"Ht3jkdtw7Hvx");
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery(
					"select * from cscoursework.player_statistics where username = '" + player.username + "';");

			while (rs.next()) {
				// The variable 'id' is assigned the value outputted by the
				// database query
				id = rs.getInt(1);
			}

			// Value 'id' is returned
			return id;

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Value 'id' is returned
		return id;

	}

	public static void findWMD() {
		// This subroutine is used to find a player's weapon of mass destruction
		// (WMD)
		int i = 0, numberOfPlayers = getNumberOfPlayers();
		// The kills array stores all of the kills with each weapon for each
		// player
		int[][] kills = new int[numberOfPlayers][6];

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/cscoursework", "root",
					"Ht3jkdtw7Hvx");
			Statement st = c.createStatement();
			// This query retrieves all the kills with each weapon for each
			// player on the database
			ResultSet rs = st.executeQuery(
					"select kills_with_pistol, kills_with_smg, kills_with_machine_gun, kills_with_sniper_rifle, kills_with_shotgun, wmd_weapon_id from weapon_kills;");

			while (rs.next() && i < numberOfPlayers) {
				// This stores the output from the datbase into the kills array
				kills[i][0] = rs.getInt(1);
				kills[i][1] = rs.getInt(2);
				kills[i][2] = rs.getInt(3);
				kills[i][3] = rs.getInt(4);
				kills[i][4] = rs.getInt(5);
				kills[i][5] = rs.getInt(6);

				i++;
			}

			for (int k = 0; k < numberOfPlayers; k++) {
				// These for loops are used to find out the weapon id of a
				// player's WMD
				int temp = kills[k][0];

				for (int j = 0; j < 6; j++) {
					// This loop looks at all 2nd dimension positions in the
					// array and compares it to the value in temp.
					// If the value in temp is less than the value in the array
					// position, then temp becomes the value
					// in that array position
					if (temp <= kills[k][j]) {
						temp = kills[k][j];
					}
				}

				// This next for loop determines which position the largest
				// value came from.
				// This is then used to find out the weapon id for the player's
				// WMD.
				int weaponid = 0;

				for (int j = 0; j < 6; j++) {
					if (temp == kills[k][j]) {
						weaponid = j + 1;
						j = 5;
					}
				}

				// This statement updates the table to set the player's WMD
				st.execute("UPDATE `cscoursework`.`weapon_kills` SET `wmd_weapon_id`='" + weaponid + "' WHERE `id`='"
						+ (k + 1) + "';");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addWeaponKills(Player player) {
		// This subroutine is used to increment the number of kills that a
		// player has got with each weapon, depending on what weapon the player
		// just used to kill their opponent

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/cscoursework", "root",
					"Ht3jkdtw7Hvx");

			Statement st = c.createStatement();

			String column = "";

			switch (player.weapon.code) {
			// This switch statement determines what weapon the player used to
			// kill their opponent and what column to add the increased number
			// of kills to
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
			}

			// This retrieves the current value in the specific column,
			// increments it and then changes the pld value with the new value
			// in the databaases
			ResultSet rs = st.executeQuery(
					"select " + column + " from cscoursework.weapon_kills where id = " + getPlayerId(player) + ";");
			rs.next();
			int kills = rs.getInt(1) + 1;
			st.execute("update cscoursework.weapon_kills set " + column + " = " + kills + " where id = "
					+ getPlayerId(player) + ";");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateMatches(Player[] players, int i) {
		// This subroutine is used to increment the number of games that the
		// players who have just logged in have played and updates the new value
		// on the database

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/cscoursework", "root",
					"Ht3jkdtw7Hvx");

			Statement st = c.createStatement();

			ResultSet rs = st.executeQuery("select * from player_statistics where username = '" + players[0].username
					+ "' or username = '" + players[1].username + "';");

			while (rs.next()) {
				i++;
			}

			for (int j = 0; j < i; j++) {
				st.execute("update `cscoursework`.`player_statistics`" + "set `games_played` = '"
						+ players[j].gamesPlayed + "' where `username` = '" + players[j].username + "';");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateStats(Player[] players) {
		// The follwing subroutine is used to update statistics to the database
		int i = 0;

		try {
			// A connection to the database is attempted and a query is sent to
			// the database to retrieve
			// the two players that are currently playing the game
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/cscoursework", "root",
					"Ht3jkdtw7Hvx");
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("select * from player_statistics where username = '" + players[0].username
					+ "' or username = '" + players[1].username + "';");

			while (rs.next()) {
				i++;
			}

			// This sets the win rate of a player to 0 if it is equal to null in
			// the database
			for (int j = 0; j < i; j++) {
				if (players[j].winRate == null) {
					players[j].winRate = 0.0;
				}

				// This statement updates the latest statistics of each player
				// to the database
				st.execute("update `cscoursework`.`player_statistics`" + "set `kills` = '" + players[j].kills
						+ "', `deaths` = '" + players[j].deaths + "', `K/D` = '" + players[j].killDifference
						+ "', `games_played` = '" + players[j].gamesPlayed + "', `games_won` = '" + players[j].gamesWon
						+ "', `win_rate` = '" + players[j].winRate + "' where `username` = '" + players[j].username
						+ "';");
			}

			// This subroutine is used to find the player's WMD
			findWMD();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getNumberOfWeapons() {
		// This subroutine is used to determine the number of weapons that are
		// currently available to the game
		int i = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/cscoursework", "root",
					"Ht3jkdtw7Hvx");
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("select * from weapons");

			while (rs.next()) {
				i++;
			}
			return i;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	public static Weapon[] getWeapons() {
		// This subroutine is used to retrieve all of the weapon details stored
		// in the 'weapons' database and this then stores each weapon and its
		// attributes to a weapon array

		int i = 0;
		Weapon[] weapons = new Weapon[getNumberOfWeapons()];

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/cscoursework", "root",
					"Ht3jkdtw7Hvx");
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("select * from weapons");

			while (rs.next()) {

				weapons[i] = new Weapon(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
						new File(rs.getString(6)));
				i++;

			}

			// Weapons array is returned
			return weapons;

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Weapons array is returned
		return weapons;
	}

	public static void getStats(Object[][] data) {
		// This subroutine is used to retrieve all of the stats to be displayed
		// in the statistics window of the game

		int i = 0, j = 0;

		DecimalFormat df1 = new DecimalFormat("0.000");
		DecimalFormat df2 = new DecimalFormat("0.00");

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/cscoursework", "root",
					"Ht3jkdtw7Hvx");
			Statement st = c.createStatement();
			Statement st2 = c.createStatement();
			ResultSet rs = st.executeQuery("select username, kills, deaths, `K/D`, win_rate from player_statistics;");
			ResultSet rs2 = st2.executeQuery("select wmd_weapon_id from weapon_kills;");

			while (rs.next() && i < getNumberOfPlayers()) {
				data[i][0] = rs.getString(1);
				data[i][1] = rs.getInt(2);
				data[i][2] = rs.getInt(3);
				data[i][3] = df1.format(rs.getDouble(4));
				data[i][4] = df2.format(rs.getDouble(5));

				i++;
			}

			while (rs2.next() && j < getNumberOfPlayers()) {
				// This converts the weapon id value from the weapon_kills
				// database to the name of the weapon for the WMD column in the
				// statistics table
				data[j][5] = rs2.getInt(1);

				switch ((int) data[j][5]) {
				case 0:
					data[j][5] = "N/A";
				case 1:
					data[j][5] = "Pistol";
					break;
				case 2:
					data[j][5] = "SMG";
					break;
				case 3:
					data[j][5] = "Machine Gun";
					break;
				case 4:
					data[j][5] = "Sniper Rifle";
					break;
				case 5:
					data[j][5] = "Shotgun";
					break;
				case 6:
					data[j][5] = "N/A";
				}

				j++;
			}

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
		}

	}
}
