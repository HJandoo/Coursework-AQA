package mainProgram;

/** 
 * Computer Science AQA Non-Exam Assessment Coursework 2016/17 - GUN MANIA
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

import engine.Player;
import engine.SQLFunctions;
import engine.Weapon;

public class MainMenu extends JFrame {

	private static final long serialVersionUID = 1L;

	ImageIcon gameLogo = new ImageIcon("Gun Mania Logo_RESIZED.png");

	Font font1 = new Font("Arial", Font.BOLD, 30);
	Font font2 = new Font("Arial", Font.BOLD, 14);

	Color black = Color.BLACK;
	Color white = Color.WHITE;
	
	Player[] players = new Player[2];

	static 	int[] optionsChoices = new int[3];
	int time;
	
	@SuppressWarnings("unused")
	public MainMenu() {
		
		// Sets default options for the options menu
		optionsChoices[0]  = 1;
		optionsChoices[1] = 2;
		optionsChoices[2] = 0;
		
		// Sets properties of the frame
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 505);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Gun Mania");
		setIconImage(gameLogo.getImage());
		
		// Initialising components
		JPanel panel = new JPanel();

		JLabel titleLabel = new JLabel("GUN MANIA");
		JLabel imageLabel = new JLabel(gameLogo);

		JButton playGame = new JButton("Play Game");
		JButton stats = new JButton("Statistics");
		JButton options = new JButton("Options");
		JButton quit = new JButton("Quit");
		
		final int xCoordinate;
		final int yCoordinate;
		final int heightOfFrame;
		
		final boolean multiP = true;
		
		// Sets properties of the components
		xCoordinate = getX();
		yCoordinate = getY();
		heightOfFrame = getHeight();

		panel.setLayout(null);
		panel.setBackground(white);
		add(panel);

		titleLabel.setBounds(10, 10, 280, 40);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(font1);
		titleLabel.setForeground(black);
		panel.add(titleLabel);

		imageLabel.setBounds(10, 60, 280, 201);
		panel.add(imageLabel);

		playGame.setBounds(10, 275, 275, 40);
		playGame.setFont(font2);
		playGame.setForeground(black);
		playGame.setBackground(new Color(225, 225, 225));
		panel.add(playGame);

		stats.setBounds(10, 325, 275, 40);
		stats.setFont(font2);
		stats.setForeground(black);
		stats.setBackground(new Color(225, 225, 225));
		panel.add(stats);

		options.setBounds(10, 375, 275, 40);
		options.setFont(font2);
		options.setForeground(black);
		options.setBackground(new Color(225, 225, 225));
		panel.add(options);

		quit.setBounds(10, 425, 275, 40);
		quit.setFont(font2);
		quit.setForeground(black);
		quit.setBackground(new Color(225, 225, 225));
		panel.add(quit);
		
		
		// Adding functionality to the JButtons created
		playGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Initialises the game's weapons and checks to make sure
				// that the time limit of the game doesn't equal null				
				Weapon[] weapons = SQLFunctions.getWeapons();
				
				getTimeFromOptions(time);
				// This launches the login screen for player 1 first and
				// then player 2. Both players can either log in to existing
				// accounts or create new ones here
				LoginMain m = new LoginMain(0, players, weapons, xCoordinate, yCoordinate, heightOfFrame, multiP);		
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
				OptionsMain m = new OptionsMain(xCoordinate, yCoordinate, optionsChoices);
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
	public void getTimeFromOptions(int time) {
		boolean timeFound = false;
		
		if (OptionsPanel.timeLim == 0) {
			// Sets a default value for the time limit for the game
			OptionsPanel.timeLim = 180;
		} else {
			// This checks to see if a game has already been played since
			// the game was first launched and sets the time limit accordingly
			
			// This array is used to store the differences between the current
			// time left and the time limits
			int[] differenceBetweenTimeLeftAndStartingTimes = new int[OptionsPanel.timelims.length];
			
			
			for (int i = 0; i < OptionsPanel.timelims.length; i++) {
				
				// If there is a difference between the current time and the
				// time limit in each position, store that difference in
				// the diff array
				if (OptionsPanel.timeLim != OptionsPanel.timelims[i]) {
					differenceBetweenTimeLeftAndStartingTimes[i] = OptionsPanel.timelims[i] - OptionsPanel.timeLim;
					timeFound = false;
				} else {
					// The time limit has been found
					timeFound = true;
				}
			}
			
			if (!timeFound) {
				// This bubble-sorts the differences in the differenceBetweenTimeLeftAndStartingTimes array into
				// ascending order and then sets the time to the value with
				// the smallest difference value
				int small = differenceBetweenTimeLeftAndStartingTimes[0];
				int index = 0;
				
				for (int i = 0; i < OptionsPanel.timelims.length; i++) {
					if (differenceBetweenTimeLeftAndStartingTimes[i] < small) {
						small = differenceBetweenTimeLeftAndStartingTimes[i];
						index = i;
					}
				}
				// The starting time limit becomes the time left + the first value in the
				// differenceBetweenTimeLeftAndStartingTimes array
				time = OptionsPanel.timeLim + differenceBetweenTimeLeftAndStartingTimes[0];
			}
	
		}
		
		
	}

	public static void main(String[] args) {
		// Opens the main menu window
		@SuppressWarnings("unused")
		MainMenu m = new MainMenu();
	}

}
