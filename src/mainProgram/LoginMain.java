package mainProgram;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginMain extends JFrame {

	private static final long serialVersionUID = 1L;

	String user, duser, dpass;

	char[] pass;

	int dk, dd, dkd, dgp;
	int[] counter = new int[2];

	public LoginMain(final int i, final Player[] players, final Weapon[][] weapons, final int x, final int y, final int height, final boolean multiP) {
		
		JLabel uname = new JLabel("Username");
		JLabel pword = new JLabel("Password");

		final JTextField ufield = new JTextField();
		final JPasswordField pfield = new JPasswordField();

		JButton login = new JButton("Log in");
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

		panel.setLayout(null);
		add(panel);

		uname.setBounds(10, 10, 100, 20);
		uname.setForeground(Color.BLACK);
		panel.add(uname);

		ufield.setBounds(120, 10, 100, 20);
		panel.add(ufield);

		pword.setBounds(10, 40, 100, 20);
		pword.setForeground(Color.BLACK);
		panel.add(pword);

		pfield.setBounds(120, 40, 100, 20);
		panel.add(pfield);

		login.setBounds(120, 70, 100, 20);
		panel.add(login);

		register.setBounds(120, 100, 100, 20);
		panel.add(register);

		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				user = ufield.getText();
				pass = pfield.getPassword();

				String passw = new String(pass);
				int hPass = passw.hashCode();

				String unique = "select * from player_statistics where username = '" + user + "' and password = '"
						+ hPass + "';";

				try {
					Class.forName("com.mysql.jdbc.Driver");

					int count = 0;

					Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
							"password");

					Statement st = c.createStatement();

					ResultSet rs = st.executeQuery(unique);

					while (rs.next()) {
						count++;
						duser = rs.getString("username");
						dpass = rs.getString("password");
						dk = rs.getInt("kills");
						dd = rs.getInt("deaths");
						dkd = rs.getInt("K/D");
						dgp = rs.getInt("games_played");

					}

					if (count == 1) {
												
						if (i == 1) {
							
							if (players[0].username.equals(user)){
								JOptionPane.showMessageDialog(getParent(),
										"That profile is already logged in",
										"Profile already logged in", JOptionPane.ERROR_MESSAGE);
							} else {
								
								players[i] = new Player(duser, 1000, weapons[i][0], dk, dd, dkd, dgp);
								players[i].gamesPlayed++;
								setVisible(false);
								
								@SuppressWarnings("unused")
								MapMain m = new MapMain(players, weapons);
							}
							
						} else if (i == 0){

						players[i] = new Player(duser, 1000, weapons[i][0], dk, dd, dkd, dgp);
						players[i].gamesPlayed++;
						setVisible(false);
						
						@SuppressWarnings("unused")
						LoginMain lm = new LoginMain(1, players, weapons, x, y, height, multiP);
						
						}

					} else {
						JOptionPane.showMessageDialog(getParent(),
								"Invalid username or password",
								"Login doesn't exist", JOptionPane.ERROR_MESSAGE);

					}

				} catch (SQLException | ClassNotFoundException e) {
					JOptionPane.showMessageDialog(getParent(), "Something went wrong... :(", "Error",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

				
			}

		});

		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				user = ufield.getText();
				pass = pfield.getPassword();

				String passw = new String(pass);
				int hPass = passw.hashCode();

				int count = 0;

				try {
					Class.forName("com.mysql.jdbc.Driver");

					Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.18:3306/coursework", "root",
							"password");
					Statement st = c.createStatement();

					ResultSet rs = st.executeQuery("select * from player_statistics where `username` = '" + user + "';");

					while (rs.next()) {
						count++;

					}

					if (count != 0) {
						JOptionPane.showMessageDialog(getParent(),
								"A profile already exists with that username. Please try another username",
								"Duplicate profile", JOptionPane.ERROR_MESSAGE);
					} else {
						
						JPasswordField jpf = new JPasswordField();
						JLabel jl = new JLabel("Please confirm your password");
						Box box = Box.createVerticalBox();

						
						box.add(jl);
						box.add(jpf);
						
						int x = JOptionPane.showConfirmDialog(null, box, "Password verification", JOptionPane.DEFAULT_OPTION);
						
						if (x == JOptionPane.OK_OPTION) {
							@SuppressWarnings("deprecation")
							String verify = jpf.getText();			
						
						if (verify.equals(passw)) {
							String create = "insert into player_statistics(username, password, kills, deaths, `K/D`) values('"
									+ user + "', '" + hPass + "', 0, 0, 0);";
							st.execute(create);
							players[i] = new Player(user, 1000, weapons[i][0], 0, 0, 0, 0);
							players[i].gamesPlayed++;
							setVisible(false);
						
							if (i == 0) {
								@SuppressWarnings("unused")
								LoginMain lm = new LoginMain(1, players, weapons, x, y, height, multiP);
								
							} else {

								players[i] = new Player(user, 1000, weapons[i][0], 0, 0, 0, 0);
								players[i].gamesPlayed++;
								setVisible(false);
								
								@SuppressWarnings("unused")
								MapMain m = new MapMain(players, weapons);
							}	
						} else {
						JOptionPane.showMessageDialog(getParent(),
									"Passwords do not match. Please try again",
									"Passwords do not match", JOptionPane.ERROR_MESSAGE);
							
						}
						
						}				
						
					}

				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}

			}

		});

		
		
	}

}
