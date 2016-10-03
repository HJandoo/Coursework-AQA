package mainProgram;

/** Computer Science AQA Coursework 2016/17
 * Made by Harnaam Jandoo
 * 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JFrame {

	private static final long serialVersionUID = 1L;

	ImageIcon img = new ImageIcon("Gun Mania Logo_RESIZED.png");

	JPanel p = new JPanel();

	JLabel l1 = new JLabel("GUN MANIA");
	JLabel l2 = new JLabel(img);

	JButton playGame = new JButton("Play Game");
	JButton stats = new JButton("Stats");
	JButton options = new JButton("Options");
	JButton quit = new JButton("Quit");

	Font f1 = new Font("Arial", Font.BOLD, 30);
	Font f2 = new Font("Arial", Font.BOLD, 14);

	Color bl = Color.BLACK;
	Color wh = Color.WHITE;

	public static Player p1, p2;
	static String player1Name;
	static boolean multiP;

	public MainMenu() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 505);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Gun Mania");
		setIconImage(img.getImage());

		p.setLayout(null);
		add(p);
		p.setBackground(wh);

		l1.setBounds(10, 10, 280, 40);
		l1.setHorizontalAlignment(JLabel.CENTER);
		l1.setFont(f1);
		l1.setForeground(bl);
		p.add(l1);

		l2.setBounds(10, 60, 280, 201);
		p.add(l2);

		playGame.setBounds(10, 275, 275, 40);
		playGame.setFont(f2);
		playGame.setForeground(bl);
		playGame.setBackground(new Color(225, 225, 225));
		p.add(playGame);

		stats.setBounds(10, 325, 275, 40);
		stats.setFont(f2);
		stats.setForeground(bl);
		stats.setBackground(new Color(225, 225, 225));
		p.add(stats);

		options.setBounds(10, 375, 275, 40);
		options.setFont(f2);
		options.setForeground(bl);
		options.setBackground(new Color(225, 225, 225));
		p.add(options);

		quit.setBounds(10, 425, 275, 40);
		quit.setFont(f2);
		quit.setForeground(bl);
		quit.setBackground(new Color(225, 225, 225));
		p.add(quit);

		playGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Weapon.createWeapons();
				
				for (int i = 0; i < 2; i++) {
					@SuppressWarnings("unused")
					LoginMain l = new LoginMain(i);
				}

			}

		});

		stats.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});

		options.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				OptionsMain m = new OptionsMain();
			}

		});

		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		});

	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainMenu m = new MainMenu();
	}

}
