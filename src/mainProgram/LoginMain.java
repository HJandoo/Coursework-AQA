package mainProgram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginMain extends JFrame {

	private static final long serialVersionUID = 1L;

	String user, duser, dpass;

	char[] pass;
	
	int dk, dd, dkd;
	int[] counter = new int[2];
	
	static Player[] players = new Player[2];

	JLabel uname = new JLabel("Username");
	JLabel pword = new JLabel("Password");

	JTextField ufield = new JTextField();
	JPasswordField pfield = new JPasswordField();

	JButton login = new JButton("Log in");

	JPanel p = new JPanel();

	public LoginMain(final int i) {

		setLocationRelativeTo(null);

		if (i == 0) {
			setBounds(650, 440, 300, 150);
		} else {
			setBounds(950, 440, 300, 150);
		}
		
		setResizable(false);
		setTitle("Player " + (i + 1) + " log in");
		setVisible(true);
		
		if (i == 1) {
			counter[0] = 1;
		}


		p.setLayout(null);
		add(p);

		uname.setBounds(10, 10, 100, 20);
		p.add(uname);

		ufield.setBounds(120, 10, 100, 20);
		p.add(ufield);

		pword.setBounds(10, 40, 100, 20);
		p.add(pword);

		pfield.setBounds(120, 40, 100, 20);
		pfield.setEchoChar('*');
		p.add(pfield);

		login.setBounds(120, 70, 100, 20);
		p.add(login);

		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				user = ufield.getText();
				pass = pfield.getPassword();
				
				String passw = new String(pass);
				int hPass = passw.hashCode();

				String unique = "select * from player_statistics where username = '" + user + "' and password = '" + hPass + "';";

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
						
						
					}

					if (count == 1) {
						
						while (players[i] == null) {
							if (duser.equals(user) && dpass.equals(Integer.toString(hPass))) {
								players[i] = new Player(duser, 1000, Weapon.weapons[0], dk, dd, dkd);
								System.out.println("Should have worked");
								counter[i]++;
								setVisible(false);
							} else {
								
							}
						}
						
						
						
						
					} else {
						String create = "insert into player_statistics(username, password, kills, deaths, `K/D`) values('"
								+ user + "', '" + hPass + "', 0, 0, 0);";
						st.execute(create);
						players[i] = new Player(duser, 1000, Weapon.weapons[0], 0, 0, 0);
						counter[i]++;
						setVisible(false);

					}
					
					

				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				System.out.println("Counter 0: " + counter[0]);
				System.out.println("Counter 1: " + counter[1]);
				
				if (counter[0] == 1 && counter[1] == 1) {
					MainMenu.multiP = true;
					
					@SuppressWarnings("unused")
					MapMain m = new MapMain();
				}
			}
			
		});	
		
		

	}

}
