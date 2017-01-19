package databaseTesting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import engine.SQLFunctions;

public class WMDMain {

	public WMDMain() {
		findTotalKills();
		findWMD();
	}

	public void findWMD() {

		int i = 0;
		int[][] kills = new int[SQLFunctions.getNumberOfPlayers()][6];

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"Ht3jkdtw7Hvx");
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery(
					"select kills_with_pistol, kills_with_smg, kills_with_machine_gun, kills_with_sniper_rifle, kills_with_shotgun, kills_with_bazooka, most_used_weapon_id from weapon_kills;");

			while (rs.next() && i < SQLFunctions.getNumberOfPlayers()) {
				kills[i][0] = rs.getInt(1);
				kills[i][1] = rs.getInt(2);
				kills[i][2] = rs.getInt(3);
				kills[i][3] = rs.getInt(4);
				kills[i][4] = rs.getInt(5);
				kills[i][5] = rs.getInt(6);

				i++;
			}

			for (int k = 0; k < SQLFunctions.getNumberOfPlayers(); k++) {
				int temp = kills[k][0];

				for (int j = 0; j < 6; j++) {
					if (temp <= kills[k][j]) {
						temp = kills[k][j];
					}

				}

				System.out.println("Most kills with one weapon: " + temp);

				int whatisj = 0;

				for (int j = 0; j < 6; j++) {
					if (temp == kills[k][j]) {
						whatisj = j + 1;
						j = 5;
					}

				}

				System.out.println("Column number/weapon id: " + whatisj);
				System.out.println();

				st.execute("UPDATE `coursework`.`weapon_kills` SET `most_used_weapon_id`='" + whatisj
						+ "' WHERE `player_id`='" + (k + 1) + "';");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void findTotalKills() {
		int i = 0, pnum = SQLFunctions.getNumberOfPlayers();
		int[][] kills = new int[pnum][6];
		int[] tots = new int[pnum];
		
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
					"Ht3jkdtw7Hvx");
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("select kills_with_pistol, kills_with_smg, kills_with_machine_gun, kills_with_sniper_rifle, kills_with_shotgun, kills_with_bazooka, most_used_weapon_id from weapon_kills;");

			while (rs.next() && i < pnum) {
				kills[i][0] = rs.getInt(1);
				kills[i][1] = rs.getInt(2);
				kills[i][2] = rs.getInt(3);
				kills[i][3] = rs.getInt(4);
				kills[i][4] = rs.getInt(5);
				kills[i][5] = rs.getInt(6);				
				
				i++;
			}
			
			for (int k = 0; k < pnum; k++) {
				
				tots[k] = kills[k][0] + kills[k][1] + kills[k][2] + kills[k][3] + kills[k][4] + kills[k][5];
				
				st.execute("UPDATE `coursework`.`player_statistics` SET `kills` = '" + tots[k] + "' WHERE `id` = '" + (k + 1) + "';");
			}
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new WMDMain();
	}

}
