package mainProgram;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import engine.Hash;
import engine.Player;
import engine.SQLFunctions;
import engine.Weapon;

public class LoginMain extends JFrame {

	private static final long serialVersionUID = 1L;

	String username, duser, dpass;

	char[] password;

	int dk, dd, dkd, dgp, dgw;
	Double dwr;
	int[] counter = new int[2];

	public LoginMain(final int i, final Player[] players, final Weapon[] weapons, final int x, final int y,
			final int height, final boolean multiP) {

		JLabel uname = new JLabel("Username");
		JLabel pword = new JLabel("Password");

		final JTextField usernameField = new JTextField();
		final JPasswordField passwordField = new JPasswordField();

		JButton loginButton = new JButton("Log in");
		JButton register = new JButton("Register");

		JPanel panel = new JPanel();

		panel.setBackground(Color.WHITE);
		setLocationRelativeTo(null);

		if (i == 0) {
			setBounds(x - 250, y + (height / 3), 250, 160);
		} else {
			setBounds(x + 300, y + (height / 3), 250, 160);
		}

		setResizable(false);
		setTitle("Player " + (i + 1) + " log in");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel.setLayout(null);
		add(panel);

		uname.setBounds(10, 10, 100, 20);
		uname.setForeground(Color.BLACK);
		panel.add(uname);

		usernameField.setBounds(120, 10, 100, 20);
		panel.add(usernameField);

		pword.setBounds(10, 40, 100, 20);
		pword.setForeground(Color.BLACK);
		panel.add(pword);

		passwordField.setBounds(120, 40, 100, 20);
		panel.add(passwordField);

		loginButton.setBounds(120, 70, 100, 20);
		panel.add(loginButton);

		register.setBounds(120, 100, 100, 20);
		panel.add(register);

		loginButton.addActionListener(new ActionListener() {
			// The following is performed when the Log in button is pressed by
			// the user

			@Override
			public void actionPerformed(ActionEvent ae) {
				// This ActionListener executes a query to the database to see
				// if the acccount details entered here correspond to an
				// account stored in the player_statistics table
				username = usernameField.getText();
				password = passwordField.getPassword();
				int counter = 0;
				String passwordToBeHashed = new String(password);

				// All passwords must be at least 6 characters long for security
				// purposes
				if (password.length < 6) {
					JOptionPane.showMessageDialog(getParent(), "Invalid username or password", "Login doesn't exist",
							JOptionPane.ERROR_MESSAGE);
				} else {

					// This String variable will hold the hash value of the
					// password by
					// running the SHA-256 hashing algorithm
					String hashedPassword;
					try {

						// The subroutine runSHA performs the SHA-256 algoithm
						// on the player's entered password
						hashedPassword = Hash.runSHA(passwordToBeHashed);

						// This is the query to be executed to find the details
						// of the player from the player_statistics table
						String unique = "select * from player_statistics where username = '" + username
								+ "' and password = '" + hashedPassword + "';";

						Class.forName("com.mysql.jdbc.Driver");

						counter = 0;

						// This is where the program will look for the database
						// and it logs in
						// using a username and password
						Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/cscoursework",
								"root", "Ht3jkdtw7Hvx");

						// This generates a Statement object to query the
						// database with
						Statement st = c.createStatement();

						// This runs the query and stores the output given
						ResultSet rs = st.executeQuery(unique);

						while (rs.next()) {
							// This counts how many users are returned when
							// the query is run and it also assigns the
							// columns of the database to these variables
							// which then get assigned to the new player object
							// created
							counter++;
							duser = rs.getString("username");
							dpass = rs.getString("password");
							dk = rs.getInt("kills");
							dd = rs.getInt("deaths");
							dkd = rs.getInt("K/D");
							dgp = rs.getInt("games_played");
							dgw = rs.getInt("games_won");

						}

						if (counter == 1) {

							if (i == 1) {
								// This makes sure that a player doesn't log in
								// twice
								if (players[0].username.equals(username)) {
									JOptionPane.showMessageDialog(getParent(), "That profile is already logged in",
											"Profile already logged in", JOptionPane.ERROR_MESSAGE);
								} else {
									// Creates new player that has atttributes
									// collected
									// from the database for player 2
									players[i] = new Player(duser, 1000, weapons[0], weapons[0].ammo, dk, dd, dkd, dgp,
											dgw, dwr);
									players[i].gamesPlayed++;
									setVisible(false);

									// Once the player is logged in, the main
									// game window is launched
									MapMain m = new MapMain(players, weapons);

									m.addWindowListener(new WindowAdapter() {
										public void windowClosing(WindowEvent w) {

											MapPanel.ti.cancel();
										}
									});
								}

							} else if (i == 0) {
								// Creates new player that has atttributes
								// collected
								// from the database for player 1
								players[i] = new Player(duser, 1000, weapons[0], weapons[0].ammo, dk, dd, dkd, dgp, dgw,
										dwr);
								players[i].gamesPlayed++;
								setVisible(false);

								// Once the player is logged in, player 2 login
								// page is launched
								@SuppressWarnings("unused")
								LoginMain lm = new LoginMain(1, players, weapons, x, y, height, multiP);

							}

						} else {
							// If no login or multiple logins are found, this
							// error
							// is given to the user
							JOptionPane.showMessageDialog(getParent(), "Invalid username or password",
									"Login doesn't exist", JOptionPane.ERROR_MESSAGE);

						}

					} catch (Exception e) {
						// If the program fails to retrieve information from
						// the database or the syntax of the query is incorrect,
						// this error will show
						JOptionPane.showMessageDialog(getParent(), "Something went wrong... :(", "Error",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}

				}

			}

		});

		register.addActionListener(new ActionListener() {
			// This runs a query to add a new player into the database and then
			// sets up a new player object into the game
			@Override
			public void actionPerformed(ActionEvent e) {
				username = usernameField.getText();
				password = passwordField.getPassword();

				String passwordAsAString = new String(password);

				int count = 0;

				try {
					Class.forName("com.mysql.jdbc.Driver");

					// This is where the program will look for the database and
					// it logs in using a username and password
					Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/cscoursework", "root",
							"Ht3jkdtw7Hvx");
					Statement st = c.createStatement();

					// This runs a query to the database to see if there is
					// another account with the username entered
					ResultSet rs = st
							.executeQuery("select * from player_statistics where `username` = '" + username + "';");

					while (rs.next()) {
						// The variable count is incremented for each user that
						// has the username entered
						// (This should only reach the value 1 as all usernames
						// should be unique)
						count++;
					}

					if (count != 0) {
						// If count is incremented, then show this error to the
						// user
						JOptionPane.showMessageDialog(getParent(),
								"A profile already exists with that username. Please try another username",
								"Duplicate profile", JOptionPane.ERROR_MESSAGE);
					} else if (passwordAsAString.length() < 6) {
						// If count is 0 but the password length isn't shorter
						// than 6 characters long,
						// show this error to the user
						JOptionPane.showMessageDialog(getParent(),
								"Your password is too short. Please make sure it is at least 6 characters long",
								"Password too short", JOptionPane.ERROR_MESSAGE);
					} else {
						// If count is 0 and the password length is at least 6
						// characters long, then
						// allow the program to add this new user to the
						// database

						// This integer value holds the hash value of the
						// password by
						// running a self-made hashing algorithm
						String hashedPassword = Hash.runSHA(passwordAsAString);

						// This asks the user to re-enter their password to
						// confirm that they entered
						// it in correctly
						JPasswordField jpf = new JPasswordField();
						JLabel jl = new JLabel(
								"Please re-enter your password (remember your password is case-sensitive)");
						Box box = Box.createVerticalBox();

						box.add(jl);
						box.add(jpf);

						int x = JOptionPane.showConfirmDialog(getParent(), box, "Password verification",
								JOptionPane.DEFAULT_OPTION);

						if (x == JOptionPane.OK_OPTION) {
							@SuppressWarnings("deprecation")
							String verify = jpf.getText();

							if (verify.equals(passwordAsAString)) {
								// If the confirmed password is the same as the
								// original password,
								// then execute a statement to add this player
								// to the database
								String createPlayer = "insert into player_statistics(username, password, kills, deaths, `K/D`) values('"
										+ username + "', '" + hashedPassword + "', 0, 0, 0);";
								st.execute(createPlayer);

								

								// This initialises a new player object that has
								// attributes generated by the program
								players[i] = new Player(username, 1000, weapons[0], weapons[0].ammo, 0, 0, 0, 0, 0,
										0.0);
								players[i].gamesPlayed++;
								
								String setupWMD = "insert into weapon_kills(id, kills_with_pistol, kills_with_smg, kills_with_machine_gun, kills_with_sniper_rifle, kills_with_shotgun) values ("
										+ SQLFunctions.getPlayerId(players[i]) + ", 0, 0, 0, 0, 0);";
								
								st.execute(setupWMD);
								
								setVisible(false);

								if (i == 0) {
									// If player 1 just registered, then the
									// program runs the login for player 2
									@SuppressWarnings("unused")
									LoginMain lm = new LoginMain(1, players, weapons, x, y, height, multiP);

								} else {
									// If player 2 just registered, then a new
									// player object is initialised with
									// attributes generated by the program
									players[i] = new Player(username, 1000, weapons[0], weapons[0].ammo, 0, 0, 0, 0, 0,
											0.0);
									players[i].gamesPlayed++;
									setVisible(false);

									// This then launches the main game window
									// and starts the game
									@SuppressWarnings("unused")
									MapMain m = new MapMain(players, weapons);
								}
							} else {
								// If the confirmed password and original
								// password do not match, then this
								// error is showed to the user
								JOptionPane.showMessageDialog(getParent(), "Passwords do not match. Please try again",
										"Passwords do not match", JOptionPane.ERROR_MESSAGE);

							}
						}
					}
				} catch (Exception e1) {
					// If there is an SQL-related error, display where the error
					// happened
					e1.printStackTrace();
				}
			}
		});
	}
}