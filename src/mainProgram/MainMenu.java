package mainProgram;

/** 
 * Computer Science AQA Coursework 2016/17 - GUN MANIA
 * Made by Harnaam Jandoo
 * Candidate number: 5039
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

import engine.Player;
import engine.SQLFunctions;
import engine.Weapon;

public class MainMenu extends JFrame {

	private static final long serialVersionUID = 1L;

	ImageIcon img = new ImageIcon("Gun Mania Logo_RESIZED.png");

	Font f1 = new Font("Arial", Font.BOLD, 30);
	Font f2 = new Font("Arial", Font.BOLD, 14);

	Color bl = Color.BLACK;
	Color wh = Color.WHITE;
	
	Player[] players = new Player[2];
	Weapon[][] weapons = new Weapon[2][6];
	
	static 	int[] choice = new int[3];
	int time;
	
	@SuppressWarnings("unused")
	public MainMenu() {
		
		// Sets default options for the options menu
		choice[0]  = 1;
		choice[1] = 2;
		choice[2] = 0;
		
		// Sets properties of the frame
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 505);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Gun Mania");
		setIconImage(img.getImage());
		
		// Initialising components
		JPanel p = new JPanel();

		JLabel l1 = new JLabel("GUN MANIA");
		JLabel l2 = new JLabel(img);

		JButton playGame = new JButton("Play Game");
		JButton stats = new JButton("Stats");
		JButton options = new JButton("Options");
		JButton quit = new JButton("Quit");
		
		final int x;
		final int y;
		final int height;
		
		final boolean multiP = true;
		
		// Sets properties of the components
		x = getX();
		y = getY();
		height = getHeight();

		p.setLayout(null);
		p.setBackground(wh);
		add(p);

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
		
		
		// Adding functionality to the JButtons created
		playGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Initialises the game's weapons and checks to make sure
				// that the time limit of the game doesn't equal null				
				SQLFunctions.getWeapons(weapons);
				
				getTime(time);
				// This launches the login screen for player 1 first and
				// then player 2. Both players can either log in to existing
				// accounts or create new ones here
				LoginMain m = new LoginMain(0, players, weapons, x, y, height, multiP);		
			}
		});

		stats.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// This launches the statistics window which displays all of
				// the players that have created accounts and shows some
				// important statistics like kills, deaths, win rate etx.
				StatsMain m = new StatsMain();
			}
		});

		options.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// This launches the options menu for the game where the
				// user can change certain mechanics of the game like
				// what score to get to in order to win
				OptionsMain m = new OptionsMain(x, y, choice);
			}
		});

		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Quits the game
				System.exit(0);
			}
		});
	}
	
	@SuppressWarnings("unused")
	public void getTime(int time) {
		boolean timeFound = false;
		
		if (OptionsPanel.timeLim == 0) {
			// Sets a default value for the time limit for the game
			OptionsPanel.timeLim = 180;
		} else {
			// This checks to see if a game has already been played since
			// the game was first launched and sets the time limit accordingly
			
			// This array is used to store the differences between the current
			// time left and the time limits
			int[] diff = new int[OptionsPanel.timelims.length];
			
			
			for (int i = 0; i < OptionsPanel.timelims.length; i++) {
				
				// If there is a difference between the current time and the
				// time limit in each position, store that difference in
				// the diff array
				if (OptionsPanel.timeLim != OptionsPanel.timelims[i]) {
					diff[i] = OptionsPanel.timelims[i] - OptionsPanel.timeLim;
					timeFound = false;
				} else {
					// The time limit has been found
					timeFound = true;
				}
			}
			
			if (!timeFound) {
				// This bubble-sorts the differences in the diff array into
				// ascending order and then sets the time to the value with
				// the smallest difference value
				int small = diff[0];
				int index = 0;
				
				for (int i = 0; i < OptionsPanel.timelims.length; i++) {
					if (diff[i] < small) {
						small = diff[i];
						index = i;
					}
				}
				time = OptionsPanel.timeLim + diff[0];
			}
	
		}
		
		
	}

	public static void main(String[] args) {
		// Run the game
		@SuppressWarnings("unused")
		MainMenu m = new MainMenu();
	}

}
