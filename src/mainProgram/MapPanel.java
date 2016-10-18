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
import java.util.Random;
import java.util.TimerTask;

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

	Random random = new Random();
	Timer t = new Timer(5, this);
	java.util.Timer[] reg = new java.util.Timer[2];
	Timer[] resp = new Timer[2];

	Font font = new Font("Arial", Font.PLAIN, 20);
	Font font2 = new Font("Arial", Font.BOLD, 12);
	Font font3 = new Font("Arial", Font.PLAIN, 25);

	Rectangle[] rects = new Rectangle[250];
	Rectangle[] playerRect = new Rectangle[2];
	Rectangle[] weaponRect = new Rectangle[2];
	Rectangle[] gunfire = new Rectangle[2];
	Rectangle[] hp = new Rectangle[2];
	Rectangle hidePlayer = new Rectangle(2000, 2000, 40, 50);
	Rectangle ammoCrate = new Rectangle(2000, 2000, 20, 20);

	Player[] players = new Player[2];
	Weapon[][] weapons = new Weapon[2][5];

	Color blue = new Color(0, 129, 222);
	static Color grey = new Color(61, 61, 61);

	int[] vel = new int[4];
	int[] count = { 0, 0, 0 };
	int[] orientation = { 0, 0 };
	int[] widths = new int[250];
	int[] count2 = new int[2];
	int[] scores = new int[2];
	int[] coordx = { 20, 1870 };
	int[] coordy = { 80, 970 };

	JLabel[] usernames = new JLabel[2];
	JLabel[] weaponLabel = new JLabel[2];
	JLabel[] ammoLabel = new JLabel[2];
	JLabel scoreLabel = new JLabel(Integer.toString(scores[0]) + "   " + Integer.toString(scores[1]));

	boolean[] isFiring = { false, false };
	boolean[] takingDamage = { false, false };
	boolean[] playerKilled = { false, false };
	boolean[] regenning = { false, false };
	boolean[] respawning = { false, false };
	boolean[] unableToMove = { false, false };
	boolean[] ableToFire = { true, true };
	
	static java.util.Timer ti;

	public MapPanel(Player[] players, Weapon[][] weapons) {
		setLayout(null);
		setFocusable(true);
		addKeyListener(this);
		setFocusTraversalKeysEnabled(false);
		t.start();

		//OptionsPanel.scoreLim = 1;
		System.out.println("SCORE: " + OptionsPanel.scoreLim);
		
		setupPlayers(players, weapons);
		setupBlocks(rects);
		setupHud(players);
		spawnAmmo(ammoCrate);
		countdown();

	}

	public void setupHud(Player[] players) {
		weaponLabel[0] = new JLabel("Weapon: " + players[0].weapon.name);
		weaponLabel[0].setForeground(Color.RED);
		weaponLabel[0].setFont(font);
		weaponLabel[0].setBounds(10, 10, 200, 20);
		add(weaponLabel[0]);

		ammoLabel[0] = new JLabel("Ammo: " + Integer.toString(players[1].weapon.ammo));
		ammoLabel[0].setForeground(Color.RED);
		ammoLabel[0].setFont(font);
		ammoLabel[0].setBounds(10, 30, 200, 20);
		add(ammoLabel[0]);

		weaponLabel[1] = new JLabel("Weapon: " + players[1].weapon.name);
		weaponLabel[1].setForeground(blue);
		weaponLabel[1].setHorizontalAlignment(JLabel.RIGHT);
		weaponLabel[1].setFont(font);
		weaponLabel[1].setBounds(1720, 10, 190, 20);
		add(weaponLabel[1]);

		ammoLabel[1] = new JLabel("Ammo: " + Integer.toString(players[1].weapon.ammo));
		ammoLabel[1].setForeground(blue);
		ammoLabel[1].setHorizontalAlignment(JLabel.RIGHT);
		ammoLabel[1].setFont(font);
		ammoLabel[1].setBounds(1720, 30, 190, 20);
		add(ammoLabel[1]);

		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(font3);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setBounds(0, 2, 1920, 20);
		add(scoreLabel);

	}

	public void setupPlayers(Player[] players, Weapon[][] weapons) {

		this.players[0] = players[0];
		this.players[1] = players[1];

		this.weapons[0] = weapons[0];
		this.weapons[1] = weapons[1];

		playerRect[0] = new Rectangle(20, 80, 25, 25);
		weaponRect[0] = new Rectangle(playerRect[0].x + 20, playerRect[0].y - 10, 5, 10);

		playerRect[1] = new Rectangle(1870, 970, 25, 25);
		weaponRect[1] = new Rectangle(playerRect[1].x + 20, playerRect[1].y - 10, 5, 10);

		usernames[0] = new JLabel(players[0].username);
		usernames[0].setForeground(Color.RED);
		usernames[0].setFont(font2);
		usernames[0].setHorizontalAlignment(JLabel.CENTER);
		usernames[0].setBounds(playerRect[0].x - 25, playerRect[0].y + 20, 75, 40);

		usernames[1] = new JLabel(players[1].username);
		usernames[1].setForeground(blue);
		usernames[1].setFont(font2);
		usernames[1].setHorizontalAlignment(JLabel.CENTER);
		usernames[1].setBounds(playerRect[1].x - 25, playerRect[1].y + 20, 75, 40);

		add(usernames[0]);
		add(usernames[1]);

		hp[0] = new Rectangle(playerRect[0].x - 5, playerRect[0].y - 20, 35, 5);
		hp[1] = new Rectangle(playerRect[1].x - 5, playerRect[1].y - 20, 35, 5);

		for (int i = 0; i < 4; i++) {
			vel[i] = 0;
		}

	}

	public void setupBlocks(Rectangle[] rects) {
		// Draws up the map by randomly placing blocks across the screen
		for (int i = 0; i < 80; i += 4) {

			int x1 = 30 * random.nextInt(64), y1 = 30 * random.nextInt(36);
			int x2 = x1, y2 = y1 + 30;
			int x3 = x1, y3 = y2 + 30;
			int x4 = x1 + 30, y4 = y3;

			rects[i] = new Rectangle(x1, y1, 30, 30);
			rects[i + 1] = new Rectangle(x2, y2, 30, 30);
			rects[i + 2] = new Rectangle(x3, y3, 30, 30);
			rects[i + 3] = new Rectangle(x4, y4, 30, 30);

			while (rects[i].x < 40 || rects[i].x > 1800 || rects[i].y < 60 || rects[i].y > 990) {
				x1 = 30 * random.nextInt(64);
				y1 = 30 * random.nextInt(36);
				x2 = x1;
				y2 = y1 + 30;
				x3 = x1;
				y3 = y2 + 30;
				x4 = x1 + 30;
				y4 = y3;

				rects[i] = new Rectangle(x1, y1, 30, 30);
				rects[i + 1] = new Rectangle(x2, y2, 30, 30);
				rects[i + 2] = new Rectangle(x3, y3, 30, 30);
				rects[i + 3] = new Rectangle(x4, y4, 30, 30);

			}
		}

		for (int i = 80; i < 250; i++) {

			int width = 30 * random.nextInt(2) + 30;
			int height = 30 * random.nextInt(2) + 30;

			rects[i] = new Rectangle(30 * random.nextInt(192), 30 * random.nextInt(108), width, height);

			while (rects[i].x < 60 || rects[i].x > 1770 || rects[i].y < 30 || rects[i].y > 930) {
				rects[i] = new Rectangle(30 * random.nextInt(192), 30 * random.nextInt(108), width, height);
			}
		}

		for (int i = 0; i < 2; i++) {
			gunfire[i] = new Rectangle(2000, 2000, 50, 50);
		}

	}

	public void countdown() {
		
		ti = new java.util.Timer();
		
		ti.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println(MainMenu.timeLim);
				MainMenu.timeLim--;
				
			}
			
		}, 0, 1000);
		
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setRenderingHints(rh);
		g2d.setRenderingHints(rh2);

		g.setColor(OptionsPanel.back);
		g.fillRect(0, 0, 1920, 1080);

		for (int i = 0; i < 20; i++) {
			rects[i] = new Rectangle(rects[i].x, rects[i].y, rects[i].width, rects[i].height);
		}

		for (Rectangle r : rects) {
			g.setColor(OptionsPanel.wall);
			g.fillRect(r.x, r.y, r.width, r.height);
		}

		g.setColor(Color.RED);
		g.fillRect(playerRect[0].x, playerRect[0].y, playerRect[0].width, playerRect[0].height);
		g.fillRect(925, 0, 35, 25);

		g.setColor(blue);
		g.fillRect(playerRect[1].x, playerRect[1].y, playerRect[1].width, playerRect[1].height);
		g.fillRect(960, 0, 35, 25);

		g.setColor(Color.YELLOW);
		g.fillRect(gunfire[0].x, gunfire[0].y, gunfire[0].width, gunfire[0].height);
		g.fillRect(gunfire[1].x, gunfire[1].y, gunfire[1].width, gunfire[1].height);

		g.setColor(grey);
		g.fillRect(weaponRect[0].x, weaponRect[0].y, weaponRect[0].width, weaponRect[0].height);
		g.fillRect(weaponRect[1].x, weaponRect[1].y, weaponRect[1].width, weaponRect[1].height);

		g.setColor(Color.GREEN);
		g.fillRect(hp[0].x, hp[0].y, hp[0].width, hp[0].height);
		g.fillRect(hp[1].x, hp[1].y, hp[1].width, hp[1].height);

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j += 2) {
				for (int k = 0; k < 250; k++) {
					intersections(i, j, k);
				}
			}
		}

		g.setColor(OptionsPanel.back);
		g.fillRect(hidePlayer.x, hidePlayer.y, hidePlayer.width, hidePlayer.height);

		hitDetection(players);
		
		
		g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
		g.fillRect(ammoCrate.x, ammoCrate.y, ammoCrate.width, ammoCrate.height);
		
		
		for (int i = 0; i < 2; i++) {
			if (ammoCrate.intersects(playerRect[i])) {
				collectAmmo(i, ammoCrate);
			}
		}


	}

	public void spawnAmmo(final Rectangle ammoCrate) {
		java.util.Timer ac = new java.util.Timer();
		int i = (random.nextInt(30) + 30) * 1000;
		System.out.println(i / 1000);

		ac.schedule(new TimerTask() {

			@Override
			public void run() {
				
				ammoCrate.x = random.nextInt(1900);
				ammoCrate.y = random.nextInt(1000);
				
				for (int i = 0; i < 250; i++) {
					while (ammoCrate.intersects(rects[i])) {
						ammoCrate.x = random.nextInt(1900);
						ammoCrate.y = random.nextInt(1000);
					}
				}
								
				System.out.println("x: " + ammoCrate.x);
				System.out.println("y: " + ammoCrate.y);				
				System.out.println("Should be spawned");
				
			}
		}, i);
		
		
	}
	
	public void collectAmmo(int i, Rectangle ammoCrate) {
		ammoCrate.x = 2000;
		ammoCrate.y = 2000;
		
		SQLFunctions.refillAmmo(players, weapons, i);
		
		ammoLabel[i].setText("Ammo: " + players[i].weapon.ammo);
		
		System.out.println("Ammo should be refilled");
		
		spawnAmmo(ammoCrate);
	}

	public void hitDetection(Player[] players) {

		if (gunfire[1].intersects(playerRect[0])) {

			// Player 1 takes damage
			takingDamage[0] = true;
			players[0].health -= players[1].weapon.damagePerShot;
			hp[0].width = (int) (0.035 * players[0].health);
			regenning[0] = false;

		}

		if (gunfire[0].intersects(playerRect[1])) {

			// Player 2 takes damage
			takingDamage[1] = true;
			players[1].health -= players[0].weapon.damagePerShot;
			hp[1].width = (int) (0.035 * players[1].health);
			regenning[1] = false;

		}
	}

	public void regen(final int i, Player[] players) {

		regenning[i] = true;
		players[i].health += 2;
		hp[i].width = (int) (0.035 * players[i].health);

	}

	public void respawn(final int i, final Player[] players, final Weapon[][] weapons) {
		unableToMove[i] = true;

		resp[i] = new Timer(5000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Random r = new Random();

				playerRect[i].x = coordx[r.nextInt(2)];
				playerRect[i].y = coordy[r.nextInt(2)];
				players[i].health = 1000;
				players[i].weapon = weapons[i][0];
				hp[i].width = (int) (0.035 * players[i].health);
				hidePlayer.x = 2000;
				hidePlayer.y = 2000;

				playerKilled[i] = false;
				respawning[i] = false;
				unableToMove[i] = false;
			}

		});
		resp[i].setRepeats(false);
		resp[i].start();

	}

	public void increment(int i, int j, boolean[] playerKilled, int[] scores, JLabel scoreLabel, Player[] players) {
		if (playerKilled[i]) {
			scores[j]++;
			players[j].kills++;
			players[i].deaths++;
			scoreLabel.setText(Integer.toString(scores[0]) + "   " + Integer.toString(scores[1]));

			if (players[i].deaths != 0) {
				players[i].killdiff = players[i].kills / players[i].deaths;

			} else {
				players[i].killdiff = players[i].kills;

			}

			if (players[j].deaths != 0) {
				players[j].killdiff = players[j].kills / players[j].deaths;

			} else {
				players[j].killdiff = players[j].kills;
			}

			SQLFunctions.updateStats(players);

			playerKilled[i] = false;
			respawning[i] = false;

		}

	}

	public void wait(Player[] players, final int i, int delay) {
		ableToFire[i] = false;

		Timer t = new Timer(delay, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ableToFire[i] = true;
			}

		});

		t.setRepeats(false);
		t.start();

	}

	public void intersections(int i, int j, int k) {
		if (playerRect[i].intersects(rects[k]) && vel[j] != 0) {
			vel[j] = 0;

			if (orientation[i] == 1) {
				playerRect[i].x = rects[k].x - playerRect[i].width;
			} else if (orientation[i] == 3) {
				playerRect[i].x = rects[k].x + rects[k].width;
			}

		} else if (playerRect[i].intersects(rects[k]) && vel[j + 1] != 0) {
			vel[j + 1] = 0;

			if (orientation[i] == 0) {
				playerRect[i].y = rects[k].y + rects[k].height;
			} else if (orientation[i] == 2) {
				playerRect[i].y = rects[k].y - playerRect[i].height;
			}
		}
	}

	public void fire(final int i) {

		count[i]++;

		java.util.Timer t = new java.util.Timer();

		t.schedule(new TimerTask() {

			@Override
			public void run() {
				players[i].weapon.ammo -= 0;

				gunfire[i].x = 2000;
				gunfire[i].y = 2000;

			}

		}, 10);

		if (count[i] < 2) {

			switch (orientation[i]) {
			case 0:
				gunfire[i].x = weaponRect[i].x + 2;
				gunfire[i].y = weaponRect[i].y - 2000;
				gunfire[i].width = 2;
				gunfire[i].height = 2000;

				for (int j = 0; j < 250; j++) {
					if (gunfire[i].intersects(rects[j])) {
						gunfire[i].y = rects[j].y + rects[j].height;
						gunfire[i].height = weaponRect[i].y - gunfire[i].y;
					}
				}
				break;
			case 1:
				gunfire[i].x = weaponRect[i].x;
				gunfire[i].y = weaponRect[i].y + 2;
				gunfire[i].width = 2000;
				gunfire[i].height = 2;

				for (int j = 0; j < 250; j++) {
					if (gunfire[i].intersects(rects[j])) {
						gunfire[i].width = rects[j].x - weaponRect[i].x;
					}
				}
				break;
			case 2:
				gunfire[i].x = weaponRect[i].x + 2;
				gunfire[i].y = weaponRect[i].y;
				gunfire[i].width = 2;
				gunfire[i].height = 2000;

				for (int j = 0; j < 250; j++) {
					if (gunfire[i].intersects(rects[j])) {
						gunfire[i].y = weaponRect[i].y;
						gunfire[i].height = rects[j].y - weaponRect[i].y;
					}
				}
				break;
			case 3:
				gunfire[i].x = weaponRect[i].x - 2000;
				gunfire[i].y = weaponRect[i].y + 2;
				gunfire[i].width = 2000;
				gunfire[i].height = 2;

				for (int j = 0; j < 250; j++) {
					if (gunfire[i].intersects(rects[j])) {
						gunfire[i].x = rects[j].x + rects[j].width;
						gunfire[i].width = weaponRect[i].x - gunfire[i].x;
					}
				}
				break;
			}

			players[i].weapon.ammo--;

			ammoLabel[i].setText("Ammo: " + Integer.toString(players[i].weapon.ammo));

		} else {
			players[i].weapon.ammo -= 0;

		}
	}

	public void time(final Player[] players) {

		reg[0] = new java.util.Timer();

		reg[0].schedule(new TimerTask() {

			@Override
			public void run() {
				if (players[0].health >= 1000) {
					players[0].health = 1000;
					reg[0].cancel();
				} else {

					takingDamage[0] = false;
					regen(0, players);

				}

			}

		}, 5000);

		if (players[0].health >= 1000) {
			players[0].health = 1000;
			reg[0].cancel();
		} else {

		}

		reg[1] = new java.util.Timer();

		reg[1].schedule(new TimerTask() {

			@Override
			public void run() {
				takingDamage[1] = false;
				regen(1, players);

				if (players[1].health >= 1000) {

					players[1].health = 1000;
					reg[1].cancel();
				}

			}

		}, 5000);

		if (players[1].health >= 1000) {

			players[1].health = 1000;
			reg[1].cancel();
		}

	}

	public void sortOrientation(int i) {
		switch (orientation[i]) {
		case 0:
			weaponRect[i].x = playerRect[i].x + 20;
			weaponRect[i].y = playerRect[i].y - 10;
			weaponRect[i].width = 5;
			weaponRect[i].height = 10;
			break;
		case 1:
			weaponRect[i].x = playerRect[i].x + 25;
			weaponRect[i].y = playerRect[i].y + 20;
			weaponRect[i].width = 10;
			weaponRect[i].height = 5;
			break;
		case 2:
			weaponRect[i].x = playerRect[i].x;
			weaponRect[i].y = playerRect[i].y + 25;
			weaponRect[i].width = 5;
			weaponRect[i].height = 10;
			break;
		case 3:
			weaponRect[i].x = playerRect[i].x - 10;
			weaponRect[i].y = playerRect[i].y;
			weaponRect[i].width = 10;
			weaponRect[i].height = 5;
			break;
		}
	}

	public boolean hasPlayerWon(Player[] players, int score) {
		if (score == OptionsPanel.scoreLim) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		playerRect[0].x += vel[0];
		playerRect[0].y += vel[1];
		hp[0].x = playerRect[0].x - 5;
		hp[0].y = playerRect[0].y - 20;
		usernames[0].setBounds(playerRect[0].x - 25, playerRect[0].y + 20, 75, 40);

		playerRect[1].x += vel[2];
		playerRect[1].y += vel[3];
		hp[1].x = playerRect[1].x - 5;
		hp[1].y = playerRect[1].y - 20;
		usernames[1].setBounds(playerRect[1].x - 25, playerRect[1].y + 20, 75, 40);

		if (players[0].health <= 0) {
			playerKilled[0] = true;
			respawning[0] = true;
			unableToMove[0] = true;
			players[0].health = 1;

			playerRect[0].x = 2000;
			playerRect[0].y = 2000;

			hidePlayer.x = 1880;
			hidePlayer.y = 970;

			increment(0, 1, playerKilled, scores, scoreLabel, players);

			if (hasPlayerWon(players, scores[1])) {

				players[1].gamesWon++;

				players[0].winRate = (players[0].gamesWon / players[0].gamesPlayed) * 100;
				players[1].winRate = (players[1].gamesWon / players[1].gamesPlayed) * 100;

				SQLFunctions.updateStats(players);

				JOptionPane.showMessageDialog(null,
						players[0].username + " has won!" + "\nFinal score: " + scores[0] + " - " + scores[1], "Winner",
						JOptionPane.PLAIN_MESSAGE);
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(getParent());
				topFrame.dispose();

			} else {
				respawn(0, players, weapons);
			}
		}

		if (players[1].health <= 0) {
			playerKilled[1] = true;
			respawning[1] = true;
			unableToMove[1] = true;
			players[1].health = 1;

			playerRect[1].x = 2000;
			playerRect[1].y = 2000;

			hidePlayer.x = 1880;
			hidePlayer.y = 970;

			increment(1, 0, playerKilled, scores, scoreLabel, players);

			if (hasPlayerWon(players, scores[0])) {

				players[0].gamesWon++;

				players[0].winRate = (players[0].gamesWon / players[0].gamesPlayed) * 100;
				players[1].winRate = (players[1].gamesWon / players[1].gamesPlayed) * 100;

				SQLFunctions.updateStats(players);

				JOptionPane.showMessageDialog(null,
						players[0].username + " has won!" + "\nFinal score: " + scores[0] + " - " + scores[1], "Winner",
						JOptionPane.PLAIN_MESSAGE);
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(getParent());
				topFrame.dispose();
			} else {
				respawn(1, players, weapons);

			}
		}

		if (playerRect[0].x < 0) {
			playerRect[0].x = 0;
			vel[0] = 0;
		}

		if (playerRect[0].x > 1895) {
			playerRect[0].x = 1895;
			vel[0] = 0;
		}

		if (playerRect[0].y < 0) {
			playerRect[0].y = 0;
			vel[1] = 0;
		}

		if (playerRect[0].y > 995) {
			playerRect[0].y = 995;
			vel[1] = 0;
		}

		if (playerRect[1].x < 0) {
			playerRect[1].x = 0;
			vel[2] = 0;
		}

		if (playerRect[1].x > 1895) {
			playerRect[1].x = 1895;
			vel[2] = 0;
		}

		if (playerRect[1].y < 0) {
			playerRect[1].y = 0;
			vel[3] = 0;
		}

		if (playerRect[1].y > 995) {
			playerRect[1].y = 995;
			vel[3] = 0;
		}

		sortOrientation(0);
		sortOrientation(1);

		if (isFiring[0]) {
			fire(0);
			wait(players, 0, players[0].weapon.rate);
		}

		if (isFiring[1]) {
			fire(1);
			wait(players, 1, players[1].weapon.rate);
		}

		if (takingDamage[0] || takingDamage[1]) {
			time(players);
		} else {

		}

		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (!unableToMove[0]) {
			switch (e.getKeyCode()) {
			// PLAYER ONE KEYS
			case KeyEvent.VK_UP:
				vel[1] = -2;
				orientation[0] = 0;
				break;
			case KeyEvent.VK_DOWN:
				vel[1] = 2;
				orientation[0] = 2;
				break;
			case KeyEvent.VK_LEFT:
				vel[0] = -2;
				orientation[0] = 3;
				break;
			case KeyEvent.VK_RIGHT:
				vel[0] = 2;
				orientation[0] = 1;
				break;
			case KeyEvent.VK_SPACE:
				if (ableToFire[0] && players[0].weapon.ammo != 0) {
					isFiring[0] = true;
				} else {
					//ableToFire[0]  = false;
				}
				break;

			}
		} else {

		}

		if (!unableToMove[1]) {
			switch (e.getKeyCode()) {

			// PLAYER TWO KEYS
			case KeyEvent.VK_W:
				vel[3] = -2;
				orientation[1] = 0;
				break;
			case KeyEvent.VK_S:
				vel[3] = 2;
				orientation[1] = 2;
				break;
			case KeyEvent.VK_A:
				vel[2] = -2;
				orientation[1] = 3;
				break;
			case KeyEvent.VK_D:
				vel[2] = 2;
				orientation[1] = 1;
				break;
			case KeyEvent.VK_Q:
				if (ableToFire[1] && players[1].weapon.ammo != 0) {
					isFiring[1] = true;
				} else {
					
				}
				break;
			}
		} else {

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
			vel[1] = 0;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			vel[0] = 0;
		}

		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_W) {
			vel[3] = 0;
		}

		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
			vel[2] = 0;
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			gunfire[0].x = 2000;
			gunfire[0].y = 2000;
			isFiring[0] = false;
			count[0] = 0;
		}

		if (e.getKeyCode() == KeyEvent.VK_Q) {
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
