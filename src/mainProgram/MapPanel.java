package mainProgram;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import engine.Player;
import engine.SQLFunctions;
import engine.Weapon;

public class MapPanel extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	// Initialise variables

	Random random = new Random();
	Timer timer = new Timer(5, this);
	java.util.Timer[] regeneratingTimer = new java.util.Timer[2];
	Timer[] respawnTimer = new Timer[2];

	Font font1;
	Font font2;
	Font font3;

	Rectangle[] wallRect = new Rectangle[250];
	Rectangle[] playerRect = new Rectangle[2];
	Rectangle[] weaponRect = new Rectangle[2];
	Rectangle[] gunfire = new Rectangle[2];
	Rectangle[] healthBar = new Rectangle[2];
	Rectangle hidePlayer = new Rectangle(2000, 2000, 40, 50);
	Rectangle ammoCrate;
	Rectangle wepCrate;

	Player[] players = new Player[2];
	Weapon[][] weapons = new Weapon[2][5];

	Color blue = new Color(0, 129, 222);
	static Color grey = new Color(61, 61, 61);

	String time;
	String message;

	int timeLimit = OptionsPanel.timeLim;
	int minutes = (timeLimit / 60) - 1;
	int seconds = OptionsPanel.timeLim / (OptionsPanel.timeLim / 60);
	int screenWidth = MapMain.getResWidth(), screenHeight = MapMain.getResHeight();
	
	
	double[][] velocity = new double[2][2];
	int[] count = { 0, 0, 0 };
	int[] orientation = { 0, 0 };
	int[] widths = new int[250];
	int[] count2 = new int[2];
	int[] scores = new int[2];
	int[] coordx = { screenWidth / 96, screenWidth - (screenWidth / (int) 38.4) };
	int[] coordy = { screenHeight / (int) 13.5, screenHeight - (screenHeight / 20) };

	JLabel[] usernames = new JLabel[2];
	JLabel[] weaponLabel = new JLabel[2];
	JLabel[] ammoLabel = new JLabel[2];
	JLabel[] scoreLabel = new JLabel[2];
	JLabel timeLabel = new JLabel(time);
	JLabel messageLabel = new JLabel();

	boolean paused = false;
	boolean[] startRegen = { false, false };
	boolean[] isFiring = { false, false };
	boolean[] playerKilled = { false, false };
	boolean[] respawning = { false, false };
	boolean[] unableToMove = { false, false };
	boolean[] ableToFire = { true, true };

	static java.util.Timer ti  = new java.util.Timer();
	Timer[] wait = new Timer[2];

	double newDamagePerShot = 0;

	public MapPanel(Player[] players, Weapon[][] weapons) {
		// Assign properties to this panel and initialise
		// some components

		setLayout(null);
		setFocusable(true);
		addKeyListener(this);
		setFocusTraversalKeysEnabled(false);
		timer.start();

		regeneratingTimer[0] = new java.util.Timer();
		regeneratingTimer[1] = new java.util.Timer();

		wepCrate = new Rectangle(2000, 2000, screenWidth / 96, screenHeight / 54);
		ammoCrate = new Rectangle(2000, 2000, screenWidth / 96, screenHeight / 54);
		
		// Makes sure that the score limit is never
		// zero or null
		if (OptionsPanel.scoreLim == 0) {
			OptionsPanel.scoreLim = 20;
		}

		// Methods used to start the game
		setupPlayers(players, weapons, playerRect, weaponRect, usernames, healthBar, font2, velocity);
		setupBlocks(wallRect);
		setupHud(players, font1, font3, weaponLabel, ammoLabel, scoreLabel, timeLabel, messageLabel);
		spawnWep(wepCrate);
		spawnAmmo(ammoCrate, random);
		countdown(ti);

	}
	
	public int fontSize() {

		int fontSize = 20;
		
		switch (screenWidth) {
		case 1920:
			fontSize = 20;
			return fontSize;
		case 1600:
			fontSize = 17;
			return fontSize;
		case 1366:
			fontSize = 14;
			return fontSize;
		case 1280:
			fontSize = 13;
			return fontSize;
		}
		
		return fontSize;
	}

	public void setupHud(Player[] players, Font font1, Font font3, JLabel[] wL, JLabel[] aL, JLabel[] sL, JLabel tL, JLabel mL) {
		// This creates the Heads-Up Display (HUD) for
		// both of the players
		
		int midpoint = screenWidth / 2;
		
		font1 = new Font("Arial", Font.PLAIN, fontSize());
		font3 = new Font("Arial", Font.PLAIN, (5 * fontSize()) / 4);

		wL[0] = new JLabel("Weapon: " + players[0].weapon.name);
		wL[0].setForeground(Color.RED);
		wL[0].setFont(new Font("Arial", Font.PLAIN, fontSize()));
		wL[0].setBounds(screenWidth / 192, screenHeight / 108, 200, 20);
		add(wL[0]);

		aL[0] = new JLabel("Ammo: " + Integer.toString(players[0].ammo));
		aL[0].setForeground(Color.RED);
		aL[0].setFont(font1);
		aL[0].setBounds(screenWidth / 192, screenHeight / 36, 200, 20);
		add(aL[0]);

		wL[1] = new JLabel("Weapon: " + players[1].weapon.name);
		wL[1].setForeground(blue);
		wL[1].setHorizontalAlignment(JLabel.RIGHT);
		wL[1].setFont(font1);
		wL[1].setBounds(0, screenHeight / 108, screenWidth - 10, 20);
		add(wL[1]);

		aL[1] = new JLabel("Ammo: " + Integer.toString(players[1].ammo));
		aL[1].setForeground(blue);
		aL[1].setHorizontalAlignment(JLabel.RIGHT);
		aL[1].setFont(font1);
		aL[1].setBounds(0, screenHeight / 36, screenWidth - 10, 20);
		add(aL[1]);

		sL[0] = new JLabel(Integer.toString(scores[0]));
		sL[0].setForeground(Color.WHITE);
		sL[0].setHorizontalAlignment(JLabel.CENTER);
		sL[0].setFont(font3);
		sL[0].setBounds(midpoint - (screenWidth / (int) 54.857), 2, screenWidth / (int) 54.857, screenHeight / 54);
		add(sL[0]);

		sL[1] = new JLabel(Integer.toString(scores[1]));
		sL[1].setForeground(Color.WHITE);
		sL[1].setHorizontalAlignment(JLabel.CENTER);
		sL[1].setFont(font3);
		sL[1].setBounds(midpoint, 2, screenWidth / (int) 54.857, screenHeight / 54);
		add(sL[1]);

		tL.setBounds(midpoint - (screenWidth / 96), screenHeight / (int) 43.2, screenWidth / 48, screenHeight / 54);
		tL.setForeground(Color.WHITE);
		tL.setHorizontalAlignment(JLabel.CENTER);
		tL.setOpaque(true);
		tL.setBackground(new Color(99, 0, 145));
		tL.setFont(font1);
		add(tL);

		mL.setBounds(0, screenHeight / (int) 21.6, screenWidth, 50);
		mL.setForeground(Color.WHITE);
		mL.setHorizontalAlignment(JLabel.CENTER);
		mL.setVerticalAlignment(JLabel.NORTH);
		mL.setFont(font1);
		add(mL);

	}

	public void setupPlayers(Player[] players, Weapon[][] weapons, Rectangle[] playerRect, Rectangle[] weaponRect, JLabel[] usernames, Rectangle[] hp, Font font2, double[][] vel) {
				
		font2 = new Font("Arial", Font.BOLD, (3 * fontSize()) / 5);
		
		// This allows for the global and local player objects are
		// one-in-the-same
		this.players[0] = players[0];
		this.players[1] = players[1];

		this.weapons[0] = weapons[0];
		this.weapons[1] = weapons[1];

		// Give each player an assigned rectangle
		playerRect[0] = new Rectangle(screenWidth / 96, screenHeight / (int) 13.5, screenWidth / (int) 76.8, screenHeight / (int) 43.2);
		weaponRect[0] = new Rectangle(playerRect[0].x + ((4 * playerRect[0].width) / 5), playerRect[0].y - ((2 * playerRect[0].width) / 5), playerRect[0].width / 5, (2 * playerRect[0].height) / 5);
		
		playerRect[1] = new Rectangle(screenWidth - (screenWidth / (int) 38.4), screenHeight - (screenHeight / 20), screenWidth / (int) 76.8, screenHeight / (int) 43.2);
		weaponRect[1] = new Rectangle(playerRect[1].x + ((4 * playerRect[1].width) / 5), playerRect[1].y - ((2 * playerRect[1].width) / 5), playerRect[1].width / 5, (2 * playerRect[1].height) / 5);

		// Display each player's username below their rectangle
		usernames[0] = new JLabel(players[0].username);
		usernames[0].setForeground(Color.RED);
		usernames[0].setFont(font2);
		usernames[0].setHorizontalAlignment(JLabel.CENTER);
		usernames[0].setBounds(playerRect[0].x - playerRect[0].width, playerRect[0].y + ((4 * playerRect[0].height) / 5), 3 * playerRect[0].width, (8 * playerRect[0].height) / 5);

		usernames[1] = new JLabel(players[1].username);
		usernames[1].setForeground(blue);
		usernames[1].setFont(font2);
		usernames[1].setHorizontalAlignment(JLabel.CENTER);
		usernames[1].setBounds(playerRect[1].x - playerRect[1].width, playerRect[1].y + ((4 * playerRect[1].height) / 5), 3 * playerRect[1].width, (8 * playerRect[1].height) / 5);

		add(usernames[0]);
		add(usernames[1]);

		// Display player's health bar above their rectangle
		hp[0] = new Rectangle(playerRect[0].x - (playerRect[0].width / 5), playerRect[0].y - ((4 * playerRect[0].height) / 5), (7 * playerRect[0].width) / 5, playerRect[0].height / 5);
		hp[1] = new Rectangle(playerRect[1].x - (playerRect[1].width / 5), playerRect[1].y - ((4 * playerRect[1].height) / 5), (7 * playerRect[1].width) / 5, playerRect[1].height / 5);

		// Give each player a starting velocity of 0
		for (int i = 0; i < 2; i++) {
			vel[0][i] = 0;
			vel[1][i] = 0;
		}

	}

	public void setupBlocks(Rectangle[] rects) {
		
		int fmw = screenWidth / 64, fmh = screenHeight / 36, dw = screenWidth / 10, dh = screenHeight / 10;
		
		// Draws up the map by randomly placing blocks across the screen
		for (int i = 0; i < 80; i += 4) {

			int x1 = fmw * random.nextInt(screenWidth / fmw), y1 = fmh * random.nextInt(screenHeight / fmh);
			int x2 = x1, y2 = y1 + fmh;
			int x3 = x1, y3 = y2 + fmh;
			int x4 = x1 + fmw, y4 = y3;

			// This creates in total an L shape object to add some variety of
			// walls
			rects[i] = new Rectangle(x1, y1, fmw, fmh);
			rects[i + 1] = new Rectangle(x2, y2, fmw, fmh);
			rects[i + 2] = new Rectangle(x3, y3, fmw, fmh);
			rects[i + 3] = new Rectangle(x4, y4, fmw, fmh);

			// This keeps the blocks within certain bounds and repositions them
			// if necessary
			while (rects[i].x < screenWidth / 48 || rects[i].x > screenWidth - (screenWidth / 16)
					|| rects[i].y < screenHeight / 18 || rects[i].y > screenHeight - (screenHeight / 12)) {
				x1 = fmw * random.nextInt(dw);
				y1 = fmh * random.nextInt(dh);
				x2 = x1;
				y2 = y1 + fmh;
				x3 = x1;
				y3 = y2 + fmh;
				x4 = x1 + fmw;
				y4 = y3;

				rects[i] = new Rectangle(x1, y1, fmw, fmh);
				rects[i + 1] = new Rectangle(x2, y2, fmw, fmh);
				rects[i + 2] = new Rectangle(x3, y3, fmw, fmh);
				rects[i + 3] = new Rectangle(x4, y4, fmw, fmh);

			}
		}

		// Positions blocks that don't intentionaly form a shape across the map
		// again within certain bounds and repositions them if necessary
		for (int i = 80; i < 250; i++) {

			int width = fmw * random.nextInt(2) + fmw;
			int height = fmh * random.nextInt(2) + fmh;

			rects[i] = new Rectangle(fmw * random.nextInt(dw), fmh * random.nextInt(dh), width, height);

			while (rects[i].x < screenWidth / 48 || rects[i].x > (screenWidth - (screenWidth / 16)) - fmw
					|| rects[i].y < fmh || rects[i].y > (screenHeight - (screenHeight / 12)) - (2 * fmh)) {
				rects[i] = new Rectangle(fmw * random.nextInt(dw), fmh * random.nextInt(dh), width, height);
			}
		}

		// Positions the gunfire rectangles off the screen so they are not
		// visible
		// to the users
		for (int i = 0; i < 2; i++) {
			gunfire[i] = new Rectangle(2000, 2000, 50, 50);
		}

	}

	public void countdown(java.util.Timer timer) {
		// This is what causes the timer to count down at secondly intervals
		// and updates the amount of time left to the HUD
		
		timer = new java.util.Timer();

		final DecimalFormat d = new DecimalFormat("00");

		// This timer is set to run every 1000 milliseconds (1 second)
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// Checks to see if the game is paused
				if (!paused) {
					timeLimit--;
					seconds--;
				} else {
					timeLimit -= 0;
					seconds -= 0;
				}

				// This allows the seconds counter to loop back to
				// 59 if the timer changes minute e.g. from 3:00 to 2:59
				if (seconds < 0) {
					seconds = 59;
					minutes--;
				}

				if (timeLimit < 0) {
					// If the timer reaches 0, then the program stops the game
					// and checks to see if there is a winner based on scores
					checkWinner(scores, players);

				}

				// Updates the time left to the HUD
				time = minutes + ":" + d.format(seconds);
				timeLabel.setText(time);

			}

		}, 0, 1000);
		
		if (timeLimit < 0) {
			timer.cancel();
		}

	}

	public void displayOnHud(Player[] players, int i, int j, int mess, final JLabel mL, String message) {
		
		if (i == 0) {
			mL.setForeground(Color.RED);
		} else {
			mL.setForeground(blue);
		}

		switch (mess) {
		case 0:
			message = players[i].username + " killed " + players[j].username;
			mL.setText(message);
			break;
		case 1:
			message = players[i].username + " picked up " + players[i].weapon.name;
			mL.setText(message);
			break;
		case 2:
			message = players[i].username + " ammo refilled";
			mL.setText(message);
			break;
		}

		java.util.Timer t = new java.util.Timer();

		t.schedule(new TimerTask() {

			@Override
			public void run() {
				mL.setText("");
				mL.setOpaque(false);
			}
		}, 5000);

	}
	
	public void displayPause(JLabel mL, String message) {
		if (paused) {
			mL.setForeground(new Color(99, 0, 145));
			message = "<HTML><center>Game Paused<br>Press Enter to resume or press Q to quit";
			mL.setText(message);
		} else {
			mL.setText("");
		}
		

	}

	public void checkWinner(int[] scores, Player[] players) {
		// This compares the scores of the players to see who has won if the
		// timer runs out before a player reaches the target score to get

		if (scores[0] > scores[1]) {
			// If player 1's score is greater than player 2's
			// show to user that player 1 has won
			timer.stop();
			displayWinner(0, 1, players);
		} else if (scores[0] < scores[1]) {
			// If player 2's score is greater than player 1's
			// show to user that player 2 has won
			timer.stop();
			displayWinner(1, 0, players);
		} else if (scores[0] == scores[1]) {
			// If the player's scores are equal, show
			// to user that it's a draw
			timer.stop();
			displayDraw();
		}
	}

	public void displayWinner(int i, int j, Player[] players) {
		// This updates necessary information and then displays that a
		// player has won the game

		// Increment gamesWon for the player that won the game
		players[i].gamesWon++;

		// Update the player's winRate
		players[i].winRate = (players[0].gamesWon / players[0].gamesPlayed) * 100;
		players[j].winRate = (players[1].gamesWon / players[1].gamesPlayed) * 100;

		// Execute a statement to change the values for these players in the
		// database
		SQLFunctions.updateStats(players);

		// Display message to the user that a player has won
		JOptionPane.showMessageDialog(null,
				players[i].username + " has won!" + "\nFinal score: " + scores[0] + " : " + scores[1], "Winner",
				JOptionPane.PLAIN_MESSAGE);
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(getParent());
		topFrame.dispose();
	}

	public void displayDraw() {
		// Display message to user that says that it's a draw
		JOptionPane.showMessageDialog(null, "It's a draw!" + "\nFinal score: " + scores[0] + " : " + scores[1], "Draw",
				JOptionPane.PLAIN_MESSAGE);
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(getParent());
		topFrame.dispose();
	}

	public void paintComponent(Graphics g) {
		// Draws everything onto the panel, such as the walls, the player
		// rectangles,
		// the player's weapon rectangles and the player's gunfire rectangles

		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setRenderingHints(rh);
		g2d.setRenderingHints(rh2);

		// Applies the background colour to the panel as chosen in the options
		// menu
		g.setColor(OptionsPanel.backgroundColour);
		g.fillRect(0, 0, screenWidth, screenHeight);

		for (int i = 0; i < 20; i++) {
			wallRect[i] = new Rectangle(wallRect[i].x, wallRect[i].y, wallRect[i].width, wallRect[i].height);
		}

		for (Rectangle r : wallRect) {
			// Draws the wall rectangles onto the panel
			g.setColor(OptionsPanel.wallColour);
			g.fillRect(r.x, r.y, r.width, r.height);
		}

		// Draws the players' rectangles onto the panel
		g.setColor(Color.RED);
		g.fillRect(playerRect[0].x, playerRect[0].y, playerRect[0].width, playerRect[0].height);
		g.fillRect((screenWidth / 2) - (screenWidth / (int) 54.857), 0, screenWidth / (int) 54.857, screenHeight / (int) 43.2);

		g.setColor(blue);
		g.fillRect(playerRect[1].x, playerRect[1].y, playerRect[1].width, playerRect[1].height);
		g.fillRect(screenWidth / 2, 0, screenWidth / (int) 54.857, screenHeight / (int) 43.2);

		// Draw the gunfire rectangles onto the panel
		g.setColor(Color.YELLOW);
		g.fillRect(gunfire[0].x, gunfire[0].y, gunfire[0].width, gunfire[0].height);
		g.fillRect(gunfire[1].x, gunfire[1].y, gunfire[1].width, gunfire[1].height);

		// Draw the players' weapon rectangles onto the panel
		g.setColor(grey);
		g.fillRect(weaponRect[0].x, weaponRect[0].y, weaponRect[0].width, weaponRect[0].height);
		g.fillRect(weaponRect[1].x, weaponRect[1].y, weaponRect[1].width, weaponRect[1].height);

		// Draw the players' health bars onto the panel
		g.setColor(Color.GREEN);
		g.fillRect(healthBar[0].x, healthBar[0].y, healthBar[0].width, healthBar[0].height);
		g.fillRect(healthBar[1].x, healthBar[1].y, healthBar[1].width, healthBar[1].height);

		// Prevents players from being able to move through the walls
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 250; k++) {
					intersections(i, j, k, velocity, playerRect, wallRect);
				}
			}
		}

		// Draws rectangle that is used to cover the killed player
		g.setColor(OptionsPanel.backgroundColour);
		g.fillRect(hidePlayer.x, hidePlayer.y, hidePlayer.width, hidePlayer.height);

		// Draws the ammo crate rectangle onto the panel
		g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
		g.fillRect(ammoCrate.x, ammoCrate.y, ammoCrate.width, ammoCrate.height);

		// Draws the weapon crate onto the panel
		g.setColor(new Color(128, 72, 0));
		g.fillRect(wepCrate.x, wepCrate.y, wepCrate.width, wepCrate.height);

		// Detects if a user picks up an ammo crate or a weapon crate
		for (int i = 0; i < 2; i++) {
			if (ammoCrate.intersects(playerRect[i])) {
				collectAmmo(i, players[i], ammoCrate, ammoLabel[i]);
			}
			if (wepCrate.intersects(playerRect[i])) {
				collectWep(players, i, wepCrate);
			}
		}

	}

	public void spawnWep(final Rectangle wepCrate) {
		// This makes the weapon crate appear on the screen
		// at a random time between 20 and 39 seconds

		java.util.Timer wc = new java.util.Timer();
		int i = (random.nextInt(20) + 20) * 1000;
		wc.schedule(new TimerTask() {

			@Override
			public void run() {

				// The weapon crate is positioned randomly on the panel
				wepCrate.x = random.nextInt(screenWidth) - wepCrate.width;
				wepCrate.y = random.nextInt(screenHeight) - wepCrate.height;

				for (int i = 0; i < 250; i++) {
					// This prevents the weapon crate from appearing in one
					// of the walls where it can't be reached
					while (wepCrate.intersects(wallRect[i])) {
						wepCrate.x = random.nextInt(screenWidth) - wepCrate.width;
						wepCrate.y = random.nextInt(screenHeight) - wepCrate.height;
					}
				}

			}
		}, i);
	}

	public void spawnAmmo(final Rectangle ammoCrate, final Random random) {
		// This makes the ammo crate appear on the screen at
		// a random time between 30 and 59 seconds

		java.util.Timer ac = new java.util.Timer();
		int i = (random.nextInt(30) + 30) * 1000;

		ac.schedule(new TimerTask() {

			@Override
			public void run() {

				// The ammo crate is positioned randomly on the panel
				ammoCrate.x = random.nextInt(screenWidth) - ammoCrate.width;
				ammoCrate.y = random.nextInt(screenHeight) - ammoCrate.height;

				for (int i = 0; i < 250; i++) {
					// This prevents the ammo crate from appearing in one
					// of the walsl where it can't be reached
					while (ammoCrate.intersects(wallRect[i])) {
						ammoCrate.x = random.nextInt(screenWidth) - ammoCrate.width;
						ammoCrate.y = random.nextInt(screenHeight) - ammoCrate.height;
					}
				}

			}
		}, i);

	}

	public void collectWep(Player[] players, int i, Rectangle wepCrate) {
		// This removes the weapon crate and changes the player's weapon
		// once they have collected the weapon crate

		// Weapon crate is positioned off the map
		wepCrate.x = 2000;
		wepCrate.y = 2000;

		// This gives the player a new random weapon that exists on the database
		players[i].weapon = getRandomWeapon(i, players[i]);

		// Update the HUD to display the new weapon and ammo that the player has
		weaponLabel[i].setText("Weapon: " + players[i].weapon.name);
		ammoLabel[i].setText("Ammo: " + players[i].ammo);

		int j;

		if (i == 0) {
			j = 1;
		} else {
			j = 0;
		}

		displayOnHud(players, i, j, 1, messageLabel, message);

		// Rerun the code to make the weapon crate appear
		spawnWep(wepCrate);
	}

	public void collectAmmo(int i, Player player, Rectangle ammoCrate, JLabel ammoLabel) {
		// This removes the ammo crate and gives the player
		// maximum ammo for their current weapon according to
		// the database

		// Ammo crate is positioned off the map
		ammoCrate.x = 2000;
		ammoCrate.y = 2000;

		// Retrieves the meximum ammo for the player's current weapon
		// and sets the player's ammo to this amount
		player.ammo = getFullAmmo(player);

		// Update the HUD to display the new amount of ammo the player has
		ammoLabel.setText("Ammo: " + player.ammo);

		int j;

		if (i == 0) {
			j = 1;
		} else {
			j = 0;
		}

		displayOnHud(players, i, j, 2, messageLabel, message);

		// Rerun the code to make the ammo crate appear
		spawnAmmo(ammoCrate, random);
	}

	public int getFullAmmo(Player player) {
		int ammo = player.weapon.ammo;
		return ammo;

	}

	public Weapon getRandomWeapon(int i, Player player) {
		Random r = new Random();
		int rWep = r.nextInt(5) + 1;
		player.weapon = weapons[i][rWep];
		player.ammo = weapons[i][rWep].ammo;

		return player.weapon;

	}

	public void hitDetection(final Player[] players) {
		// Checks to see if a player has been hit by the oppoenet's
		// gunfire and it decreases the player's health according to
		// the damagePerShot of the opponent's weapon

		if (gunfire[1].intersects(playerRect[0])) {
			// If player 2's gunfire rect touches player 1's rectangle,
			// then player 1 takes damage

			startRegen[0] = false;

			// This decreases the amount of damage player 2 does to player 1
			// depending on the distance between them and player 2's weapon
			rangeOrientation(1, 0);

			// Player 1's health is decreased and health bar is updated
			players[0].health -= newDamagePerShot;
			healthBar[0].width = (int) (((1.4 * playerRect[0].getWidth()) / 1000) * players[0].health);

			regeneratingTimer[0].schedule(new TimerTask() {
				// If player 1 has not been hit in more that 5 seconds, then
				// player 1's health begins to regenerate

				@Override
				public void run() {
					if (players[0].health >= 1000) {
						players[0].health += 0;
						players[0].health = 1000;
						healthBar[0].width = (int) (((1.4 * playerRect[0].getWidth()) / 1000) * players[0].health);
						cancel();
					} else if (gunfire[1].intersects(playerRect[0])) {
						players[0].health += 0;
						healthBar[0].width = (int) (((1.4 * playerRect[0].getWidth()) / 1000) * players[0].health);
						cancel();
					} else {
						players[0].health++;
						healthBar[0].width = (int) (((1.4 * playerRect[0].getWidth()) / 1000) * players[0].health);
					}
				}

			}, 5000, 10);
		}

		if (gunfire[0].intersects(playerRect[1])) {

			startRegen[1] = false;

			// Player 2 takes damage

			rangeOrientation(0, 1);
			players[1].health -= newDamagePerShot;
			healthBar[1].width = (int) (((1.4 * playerRect[1].getWidth()) / 1000) * players[1].health);

			regeneratingTimer[1].schedule(new TimerTask() {

				public void run() {

					if (players[1].health >= 1000) {
						players[1].health += 0;
						players[1].health = 1000;
						healthBar[1].width = (int) (((1.4 * playerRect[1].getWidth()) / 1000) * players[1].health);
						cancel();
					} else if (gunfire[0].intersects(playerRect[1])) {
						players[1].health += 0;
						healthBar[1].width = (int) (((1.4 * playerRect[1].getWidth()) / 1000) * players[1].health);
						cancel();
					} else {
						players[1].health++;
						healthBar[1].width = (int) (((1.4 * playerRect[1].getWidth()) / 1000) * players[1].health);

					}

				}

			}, 5000, 10);

		}
	}

	public void respawn(final int i, final Player[] players, final Weapon[][] weapons) {
		// This makes the player just killed wait 5 seconds before
		// they can play again

		unableToMove[i] = true;

		// Initialising a timer that is specific for the player killed
		respawnTimer[i] = new Timer(5000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Random r = new Random();
				// This randomly chooses the position of the player just killed
				// on the map
				// so that the player is less likely to be 'spawn-trapped'
				playerRect[i].x = coordx[r.nextInt(2)];
				playerRect[i].y = coordy[r.nextInt(2)];
				// Gives the player basic starting traits
				players[i].health = 1000;
				players[i].weapon = weapons[i][0];
				healthBar[i].width = (int) (((1.4 * playerRect[i].getWidth()) / 1000) * players[i].health);
				hidePlayer.x = 2000;
				hidePlayer.y = 2000;

				playerKilled[i] = false;
				respawning[i] = false;
				unableToMove[i] = false;
			}

		});
		respawnTimer[i].setRepeats(false);
		respawnTimer[i].start();

	}

	public void increment(int i, int j, boolean[] playerKilled, int[] scores, JLabel[] scoreLabel, Player[] players) {
		if (playerKilled[i]) {
			// If a player has been killed, increment the in-game score
			// and increment the killer's total kills stat and the killed's
			// total deaths stat
			
			// The int i represents the player who was killed
			// The int j represents the killer
			scores[j]++;
			players[j].kills++;
			players[i].deaths++;
			scoreLabel[j].setText(Integer.toString(scores[j]));

			// This if statement prevents the player 1 kill difference ratio
			// from being infinity as Java can't represent infinity as a number
			if (players[i].deaths != 0) {
				players[i].killdiff = players[i].kills / players[i].deaths;
			} else {
				players[i].killdiff = players[i].kills;
			}

			// This if statement does the exact same as above but it
			// is for player 2
			if (players[j].deaths != 0) {
				players[j].killdiff = players[j].kills / players[j].deaths;
			} else {
				players[j].killdiff = players[j].kills;
			}

			// Uploads latest changes in the stats to the database
			SQLFunctions.updateStats(players);

			// playerKilled is turned to false to prevent the score from
			// always incrementing until the 5 second respawn time has run out
			playerKilled[i] = false;
			respawning[i] = false;

		}

	}

	public void wait(final int i, int delay, final boolean[] ableToFire) {
		// This is used to prevent users from firing incredibly fast
		// so that means the game can have both automatic and
		// semi-automatic weapons by factoring in a unique rate of fire

		// Stops the user from being able to fire
		ableToFire[i] = false;
		
		// This timer is used to make ableToFire true again once
		// the the timer has waited the amount of time as the
		// player's weapon's rate of fire, e.g. if the player's
		// rate of fire was 200ms, then the timer waits 200ms before
		// turning ableToFire true again
		Timer t = new Timer(delay, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ableToFire[i] = true;

			}

		});
		t.setRepeats(false);
		t.start();

	}

	public void intersections(int i, int j, int k, double[][] velocity, Rectangle[] playerRect, Rectangle[] wallRect) {
		// This is used to stop players from being able
		// to travel through the walls
		
		if (playerRect[i].intersects(wallRect[k])) {
			whichSide(i, 0, k, velocity, playerRect, wallRect);		
		}

	}
	
	public void whichSide(int i, int j, int k, double[][] velocity, Rectangle[] playerRect, Rectangle[] wallRect) {
		//TODO Player shouldn't jump when it hits a block
		
		if (velocity[i][j] > 0 && playerRect[i].x > wallRect[k].x - playerRect[i].width) {
			velocity[i][j] = 0;
			//Has touched LHS moving RIGHT
			playerRect[i].x = wallRect[k].x - playerRect[i].width;			
		} else if (velocity[i][j] < 0 && playerRect[i].x < wallRect[k].x + wallRect[k].width){
			velocity[i][j] = 0;
			//Has touched RHS moving LEFT
			playerRect[i].x = wallRect[k].x + wallRect[k].width;
		} else if (velocity[i][j + 1] < 0 && playerRect[i].y < wallRect[k].y + wallRect[k].height) {
			velocity[i][j + 1] = 0;
			// Has touched LOWER moving UP
			playerRect[i].y = wallRect[k].y + wallRect[k].height;
		} else if (velocity[i][j + 1] > 0 && playerRect[i].y > wallRect[k].y - playerRect[i].height) {
			velocity[i][j + 1] = 0;
			// Has touched UPPER moving DOWN
			playerRect[i].y = wallRect[k].y - playerRect[i].height;
		}
	}

	public void fire(final int i, int[] count, boolean[] ableToFire, Rectangle[] gunfire, Rectangle[] weaponRect, Player[] players) {
		// This is used to determine what type of firing mode
		// should be used depending on the player's current weapon.
		// This is only called when the player fires their weapon

		int autoRate = players[i].weapon.rate / 10;
		
		switch (players[i].weapon.code) {
		case 1:
			semiauto(i, count, ableToFire, gunfire, weaponRect, wallRect, players, ammoLabel);
			wait(i, players[i].weapon.rate, ableToFire);
			break;
		case 2:
			auto(i, autoRate, count, gunfire, players);
			break;
		case 3:
			auto(i, autoRate, count, gunfire, players);
			break;
		case 4:
			semiauto(i, count, ableToFire, gunfire, weaponRect, wallRect, players, ammoLabel);
			wait(i, players[i].weapon.rate, ableToFire);
			break;
		case 5:
			semiauto(i, count, ableToFire, gunfire, weaponRect, wallRect, players, ammoLabel);
			wait(i, players[i].weapon.rate, ableToFire);
			break;
		case 6:
			semiauto(i, count, ableToFire, gunfire, weaponRect, wallRect, players, ammoLabel);
			wait(i, players[i].weapon.rate, ableToFire);
			break;
		}

	}

	public void auto(final int i, int mod, int[] count, final Rectangle[] gunfire, final Player[] players) {
		// This is used to give the players an automatic type of firing
		// for their current weapon

		count[i]++;
		
		// This timer causes the gunfire to quickly flash as it would
		// in real life
		java.util.Timer t = new java.util.Timer();

		t.schedule(new TimerTask() {

			@Override
			public void run() {
				// Decrease the player's ammo
				players[i].ammo -= 0;

				gunfire[i].x = 2000;
				gunfire[i].y = 2000;

			}

		}, 10);

		// Only fire if ableToFire is true
		if (count[i] % mod == 0 || count[i] == 1) {
			
			// Sort out the orientation of the player and then position
			// the guinfire rectangle on the screen accordingly
			switch (orientation[i]) {
			case 0:
				gunfire[i].x = weaponRect[i].x + 2;
				gunfire[i].y = weaponRect[i].y - 2000;
				gunfire[i].width = 2;
				gunfire[i].height = 2000;

				// Prevents the gunfire from going through walls
				for (int j = 0; j < 250; j++) {
					if (gunfire[i].intersects(wallRect[j])) {
						gunfire[i].y = wallRect[j].y + wallRect[j].height;
						gunfire[i].height = weaponRect[i].y - gunfire[i].y;
					}
				}
				break;
			case 1:
				gunfire[i].x = weaponRect[i].x;
				gunfire[i].y = weaponRect[i].y + 2;
				gunfire[i].width = 2000;
				gunfire[i].height = 2;

				// Prevents the gunfire from going through walls
				for (int j = 0; j < 250; j++) {
					if (gunfire[i].intersects(wallRect[j])) {
						gunfire[i].width = wallRect[j].x - weaponRect[i].x;
					}
				}
				break;
			case 2:
				gunfire[i].x = weaponRect[i].x + 2;
				gunfire[i].y = weaponRect[i].y;
				gunfire[i].width = 2;
				gunfire[i].height = 2000;

				// Prevents the gunfire from going through walls
				for (int j = 0; j < 250; j++) {
					if (gunfire[i].intersects(wallRect[j])) {
						gunfire[i].y = weaponRect[i].y;
						gunfire[i].height = wallRect[j].y - weaponRect[i].y;
					}
				}
				break;
			case 3:
				gunfire[i].x = weaponRect[i].x - 2000;
				gunfire[i].y = weaponRect[i].y + 2;
				gunfire[i].width = 2000;
				gunfire[i].height = 2;

				// Prevents the gunfire from going through walls
				for (int j = 0; j < 250; j++) {
					if (gunfire[i].intersects(wallRect[j])) {
						gunfire[i].x = wallRect[j].x + wallRect[j].width;
						gunfire[i].width = weaponRect[i].x - gunfire[i].x;
					}
				}
				break;
			}

			// Lower the amount of ammo the player has and update the HUD
			// to show this change
			players[i].ammo--;
			ammoLabel[i].setText("Ammo: " + Integer.toString(players[i].ammo));

			// Trigger sound effect
			sound(players[i]);

		} else {
			// Do not decrease the player's ammo
			players[i].ammo -= 0;

		}

	}

	public void semiauto(final int i, int[] count, boolean[] ableToFire, final Rectangle[] gunfire, final Rectangle[] weaponRect, Rectangle[] wallRect, final Player[] players, JLabel[] ammoLabel) {
		// This is used to give the players a semi-automatic type
		// of firing for their current weapon

		count[i]++;

		// This timer causes the gunfire to quickly flash as it would
		// in real life
		java.util.Timer t = new java.util.Timer();

		t.schedule(new TimerTask() {

			@Override
			public void run() {
				// Remove gunfire from screen
				players[i].ammo -= 0;

				gunfire[i].x = 2000;
				gunfire[i].y = 2000;

			}

		}, 10);

		// Only fire if the incrementing count variable is less than 2
		if (count[i] < 2 && ableToFire[i]) {

			// Sort out the orientation of the player and then position
			// the guinfire rectangle on the screen accordingly
			switch (orientation[i]) {
			case 0:
				gunfire[i].x = weaponRect[i].x + 2;
				gunfire[i].y = weaponRect[i].y - 2000;
				gunfire[i].width = 2;
				gunfire[i].height = 2000;

				// Prevents the gunfire from going through walls
				for (int j = 0; j < 250; j++) {
					if (gunfire[i].intersects(wallRect[j])) {
						gunfire[i].y = wallRect[j].y + wallRect[j].height;
						gunfire[i].height = weaponRect[i].y - gunfire[i].y;
					}
				}
				break;
			case 1:
				gunfire[i].x = weaponRect[i].x;
				gunfire[i].y = weaponRect[i].y + 2;
				gunfire[i].width = 2000;
				gunfire[i].height = 2;

				// Prevents the gunfire from going through walls
				for (int j = 0; j < 250; j++) {
					if (gunfire[i].intersects(wallRect[j])) {
						gunfire[i].width = wallRect[j].x - weaponRect[i].x;
					}
				}
				break;
			case 2:
				gunfire[i].x = weaponRect[i].x + 2;
				gunfire[i].y = weaponRect[i].y;
				gunfire[i].width = 2;
				gunfire[i].height = 2000;

				// Prevents the gunfire from going through walls
				for (int j = 0; j < 250; j++) {
					if (gunfire[i].intersects(wallRect[j])) {
						gunfire[i].y = weaponRect[i].y;
						gunfire[i].height = wallRect[j].y - weaponRect[i].y;
					}
				}
				break;
			case 3:
				gunfire[i].x = weaponRect[i].x - 2000;
				gunfire[i].y = weaponRect[i].y + 2;
				gunfire[i].width = 2000;
				gunfire[i].height = 2;

				// Prevents the gunfire from going through walls
				for (int j = 0; j < 250; j++) {
					if (gunfire[i].intersects(wallRect[j])) {
						gunfire[i].x = wallRect[j].x + wallRect[j].width;
						gunfire[i].width = weaponRect[i].x - gunfire[i].x;
					}
				}
				break;
			}

			// Lower the amount of ammo the player has and update the HUD
			// to show this change
			
			players[i].ammo--;
			ammoLabel[i].setText("Ammo: " + Integer.toString(players[i].ammo));
			
			// Trigger sound effect
			sound(players[i]);

		} else {
			// Do not decrease the player's ammo
			players[i].ammo -= 0;

		}
	}

	public void sound(final Player player) {
		// This is used to play the sound effect of the weapon being used
		// by the player when they fire

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(player.weapon.sound);
					Clip clip = AudioSystem.getClip();
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();

	}

	public void rangeOrientation(int i, int j) {
		// This is used to determine how far away the player being fired at is
		// from the player firing at them and it uses the orientation of the
		// firing player to get the necessary distance value needed to add
		// range effectiveness to the weapons. The main calculation is made
		// in the method 'range'

		double dist;

		if (orientation[i] == 0) {
			dist = Math.abs((playerRect[j].y + playerRect[j].height) - weaponRect[i].y);
			range(i, dist, newDamagePerShot);
		} else if (orientation[i] == 1) {
			dist = Math.abs(playerRect[j].x - (weaponRect[i].x + weaponRect[i].width));
			range(i, dist, newDamagePerShot);
		} else if (orientation[i] == 2) {
			dist = Math.abs((weaponRect[i].y + weaponRect[i].height) - playerRect[j].y);
			range(i, dist, newDamagePerShot);
		} else if (orientation[i] == 3) {
			dist = Math.abs((playerRect[j].x + playerRect[j].width) - weaponRect[i].x);
			range(i, dist, newDamagePerShot);
		}
	}

	public void range(int i, double distance, double dps) {
		// This method applies a multiplier to the player's weapon's
		// damagePerShot value based on what weapon the player is using
		// and also how far away the player is from their opponent player.
		// The new damagePerShot value is stored first in the parameter dps
		// and then in the variable newdps

		double multiplier = 1;

		switch (players[i].weapon.code) {
		case 1:
			if (distance >= 170 && distance <= 280) {
				multiplier = 0.9;
			} else if (distance > 280 && distance <= 420) {
				multiplier = 0.84;
			} else if (distance > 420 && distance <= 560) {
				multiplier = 0.8;
			} else if (distance > 560) {
				multiplier = 0.77;
			} else {
				multiplier = 1;
			}
			dps = (double) players[i].weapon.damagePerShot * multiplier;
			break;
		case 2:
			if (distance >= 140 && distance <= 280) {
				multiplier = 0.9;
			} else if (distance > 280 && distance <= 420) {
				multiplier = 0.7;
			} else if (distance > 420 && distance <= 560) {
				multiplier = 0.55;
			} else if (distance > 560) {
				multiplier = 0.35;
			} else {
				multiplier = 1;
			}
			dps = (double) players[i].weapon.damagePerShot * multiplier;
			break;
		case 3:
			if (distance >= 140 && distance <= 280) {
				multiplier = 0.94;
			} else if (distance > 280 && distance <= 420) {
				multiplier = 0.9;
			} else if (distance > 420 && distance <= 560) {
				multiplier = 0.87;
			} else if (distance > 560) {
				multiplier = 0.85;
			} else {
				multiplier = 1;
			}
			dps = (double) players[i].weapon.damagePerShot * multiplier;
			break;
		case 4:
			multiplier = 1;
			dps = (double) players[i].weapon.damagePerShot * multiplier;
			break;
		case 5:
			if (distance >= 100 && distance <= 280) {
				multiplier = 0.56;
			} else if (distance > 280 && distance <= 420) {
				multiplier = 0.33;
			} else if (distance > 420 && distance <= 560) {
				multiplier = 0.17;
			} else if (distance > 560) {
				multiplier = 0.08;
			} else {
				multiplier = 1;
			}
			dps = (double) players[i].weapon.damagePerShot * multiplier;

			break;
		case 6:
			multiplier = 1;
			dps = (double) players[i].weapon.damagePerShot * multiplier;
			break;
		}
		newDamagePerShot = dps;
	}

	public void sortOrientation(int i, int screenWidth, int[] orientation, Rectangle[] weaponRect, Rectangle[] playerRect) {
		// This determines the position of the weapon rectangle
		// depending on the orientation of the player so that it
		// looks like the player is facing a certain direction
		int fixX = 0, fixY = 0;
		
		if (screenWidth < 1920) {
			fixX = 1;
		} else {
			fixX = 0;
		}
		
		if (screenWidth < 1600) {
			fixY = 1;
		} else {
			fixY = 0;
		}

		switch (orientation[i]) {
		case 0:
			weaponRect[i].x = playerRect[i].x + ((4 * playerRect[i].width) / 5) + fixX;
			weaponRect[i].y = playerRect[i].y - ((2 * playerRect[i].width) / 5);
			weaponRect[i].width = playerRect[i].width / 5;
			weaponRect[i].height = (2 * playerRect[i].height) / 5;
			break;
		case 1:
			weaponRect[i].x = playerRect[i].x + playerRect[i].width;
			weaponRect[i].y = playerRect[i].y + ((4 * playerRect[i].height) / 5) + fixY;
			weaponRect[i].width = (2 * playerRect[i].width) / 5;
			weaponRect[i].height = playerRect[i].height / 5;
			break;
		case 2:
			weaponRect[i].x = playerRect[i].x;
			weaponRect[i].y = playerRect[i].y + playerRect[i].height;
			weaponRect[i].width = playerRect[i].width / 5;
			weaponRect[i].height = (2 * playerRect[i].height) / 5;
			break;
		case 3:
			weaponRect[i].x = playerRect[i].x - ((2 * playerRect[i].height) / 5);
			weaponRect[i].y = playerRect[i].y;
			weaponRect[i].width = (2 * playerRect[i].width) / 5;
			weaponRect[i].height = playerRect[i].height / 5;
			break;
		}
	}

	public boolean hasPlayerWon(Player[] players, int score) {
		// This is used to see if a player has reached the
		// intended score limit

		if (score == OptionsPanel.scoreLim) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// This is used mainly to control the movement and positioning
		// of rectangles on the screen as well as things like
		// constantly checking for if a player has been killed or not

		playerRect[0].x += velocity[0][0];
		playerRect[0].y += velocity[0][1];
		healthBar[0].x = playerRect[0].x - (playerRect[0].width / 5);
		healthBar[0].y = playerRect[0].y - ((4 * playerRect[0].height) / 5);
		usernames[0].setBounds(playerRect[0].x - playerRect[0].width, playerRect[0].y + ((4 * playerRect[0].height) / 5), 3 * playerRect[0].width, (8 * playerRect[0].height) / 5);

		playerRect[1].x += velocity[1][0];
		playerRect[1].y += velocity[1][1];
		healthBar[1].x = playerRect[1].x - (playerRect[1].width / 5);
		healthBar[1].y = playerRect[1].y - ((4 * playerRect[1].height) / 5);
		usernames[1].setBounds(playerRect[1].x - playerRect[1].width, playerRect[1].y + ((4 * playerRect[1].height) / 5), 3 * playerRect[1].width, (8 * playerRect[1].height) / 5);

		if (players[0].health <= 0) {
			// If player 1 has been killed then hide the player rectangle from
			// the screen
			playerKilled[0] = true;
			respawning[0] = true;
			unableToMove[0] = true;
			players[0].health = 1;

			playerRect[0].x = 2000;
			playerRect[0].y = 2000;

			hidePlayer.x = screenWidth - (screenWidth / 48);
			hidePlayer.y = screenHeight - (screenHeight / (int) 21.6);

			// Increment the score of player 2
			increment(0, 1, playerKilled, scores, scoreLabel, players);
			
			displayOnHud(players, 1, 0, 0, messageLabel, message);

			if (hasPlayerWon(players, scores[1])) {
				// If player 2 has reached the score limit, then end the game
				// and display that player 2 has won
				displayWinner(1, 0, players);

			} else {
				// Allow player 1 to respawn otherwise
				respawn(0, players, weapons);
			}
		}

		if (players[1].health <= 0) {
			// If player 2 has been killed then hide the player rectangle from
			// the screen
			playerKilled[1] = true;
			respawning[1] = true;
			unableToMove[1] = true;
			players[1].health = 1;

			playerRect[1].x = 2000;
			playerRect[1].y = 2000;

			hidePlayer.x = screenWidth - (screenWidth / 48);
			hidePlayer.y = screenHeight - (screenHeight / (int) 21.6);

			// Increment the score of player 1
			increment(1, 0, playerKilled, scores, scoreLabel, players);
			displayOnHud(players, 0, 1, 0, messageLabel, message);

			if (hasPlayerWon(players, scores[0])) {
				// If player 1 has reached the score limit, then end the game
				// and display that player 1 has won
				displayWinner(0, 1, players);
			} else {
				// Allow player 2 to respawn otherwise
				respawn(1, players, weapons);
			}
		}

		// This collection of if statements prevent the players from
		// being able to leave the boundaries of the screen
		
		for (int i = 0; i < 2; i++) {
			if (playerRect[i].x < 0) {
				playerRect[i].x = 0;
				velocity[i][0] = 0;
			}
			if (playerRect[i].x > screenWidth - playerRect[i].width) {
				playerRect[i].x = screenWidth - playerRect[i].width;
				velocity[i][0] = 0;
			}
			if (playerRect[i].y < 0) {
				playerRect[i].y = 0;
				velocity[i][1] = 0;
			}
			if (playerRect[i].y > screenHeight - playerRect[i].height) {
				playerRect[i].y = screenHeight - playerRect[i].height;
				velocity[i][1] = 0;
			}
			
			// Actively check for where to position the weapon
			// rectangle to give the player an orientation
			sortOrientation(i, screenWidth, orientation, weaponRect, playerRect);

			if (isFiring[i]) {
				// If player wants to fire, then display
				// the gunfire rectangle and wait until the rate
				// of fire timer ends
				fire(i, count, ableToFire, gunfire, weaponRect, players);
			}
		}

		// Checks to see if a player has been hit with gunfire
		hitDetection(players);

		// Update changes to the screen
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_1) {
			players[0].weapon = getRandomWeapon(0, players[0]);
			weaponLabel[0].setText("Weapon: " + players[0].weapon.name);
			ammoLabel[0].setText("Ammo: " + players[0].ammo);
		}

		if (!unableToMove[0]) {
			// If player 1 isn't dead and hence can move then
			// allow the player to input keys to control their rectangle
			switch (e.getKeyCode()) {
			// Player 1 key assignments for moving and firing
			case KeyEvent.VK_UP:
				velocity[0][1] = -(screenHeight / 540);
				orientation[0] = 0;
				break;
			case KeyEvent.VK_DOWN:
				velocity[0][1] = screenHeight / 540;
				orientation[0] = 2;
				break;
			case KeyEvent.VK_LEFT:
				velocity[0][0] = -(screenWidth / 960);
				orientation[0] = 3;
				break;
			case KeyEvent.VK_RIGHT:
				velocity[0][0] = screenWidth / 960;
				orientation[0] = 1;
				break;
			case KeyEvent.VK_SPACE:
				if (players[0].ammo > 0) {
					isFiring[0] = true;
				} else {
					ableToFire[0] = false;
				}
				break;

			}
		} else {
			// Don't assign and keys to player 1
		}

		if (!unableToMove[1]) {
			// If player 2 isn't dead and hence can move then
			// allow the player to input keys to control their rectangle
			switch (e.getKeyCode()) {
			// Player 2 key assignments for moving and firing
			case KeyEvent.VK_W:
				velocity[1][1] = -(screenHeight / 540);
				orientation[1] = 0;
				break;
			case KeyEvent.VK_S:
				velocity[1][1] = screenHeight / 540;
				orientation[1] = 2;
				break;
			case KeyEvent.VK_A:
				velocity[1][0] = -(screenWidth / 960);
				orientation[1] = 3;
				break;
			case KeyEvent.VK_D:
				velocity[1][0] = screenWidth / 960;
				orientation[1] = 1;
				break;
			case KeyEvent.VK_R:
				if (players[1].ammo > 0) {
					isFiring[1] = true;
				} else {
					ableToFire[1] = false;
				}
				break;
			}
		} else {
			// Don't assign and keys to player 2
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// If Escape is pressed, the game pauses

			timer.stop();
			paused = true;
			
			displayPause(messageLabel, message);
		}

		if (paused && e.getKeyCode() == KeyEvent.VK_ENTER) {
			// If Enter is pressed while the game is paused,
			// the game is resumed

			timer.start();
			paused = false;
			displayPause(messageLabel, message);
		}
		
		if (paused && e.getKeyCode() == KeyEvent.VK_Q) {
			JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(getParent());
			topFrame.dispose();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// These if statements regarding the int[] vel stop the player from
		// moving
		// if they release one of the keys used to move their rectangle
		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
			velocity[0][1] = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			velocity[0][0] = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_W) {
			velocity[1][1] = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
			velocity[1][0] = 0;
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			// If spacebar is released then stop player 1 from
			// firing their weapon
			gunfire[0].x = 2000;
			gunfire[0].y = 2000;
			isFiring[0] = false;
			count[0] = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			// If Q is released then stop player 2 from
			// firing their weapon
			gunfire[1].x = 2000;
			gunfire[1].y = 2000;
			isFiring[1] = false;
			count[1] = 0;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
