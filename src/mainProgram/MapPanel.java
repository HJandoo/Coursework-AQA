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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MapPanel extends JPanel implements ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Random random = new Random();
	Timer t = new Timer(3, this);

	Rectangle[] rects = new Rectangle[250];
	Rectangle[] playerRect = new Rectangle[2];
	Rectangle[] weaponRect = new Rectangle[2];
	Rectangle[] gunfire = new Rectangle[2];
	Rectangle[] hp = new Rectangle[2];

	Player[] players = new Player[2];

	Color blue = new Color(0, 129, 222);
	Color grey = new Color(61, 61, 61);

	int[] vel = new int[4];
	int[] count = { 0, 0, 0 };
	int[] orientation = { 0, 0 };
	int[] widths = new int[250];
	int temp = 0;

	JLabel[] usernames = new JLabel[2];
	JLabel[] lWeapon = new JLabel[2];

	boolean[] isFiring = { false, false };

	public MapPanel() {
		
		

		setLayout(null);
		setFocusable(true);
		addKeyListener(this);
		setFocusTraversalKeysEnabled(false);
		t.start();

		setupPlayers();
		setupBlocks();
		
		lWeapon[0] = new JLabel(players[0].weapon.name);
		lWeapon[0].setForeground(Color.RED);
		lWeapon[0].setFont(new Font("Arial", Font.PLAIN, 20));
		lWeapon[0].setBounds(10, 10, 200, 20);
		add(lWeapon[0]);
		
		lWeapon[1] = new JLabel(players[1].weapon.name);
		lWeapon[1].setForeground(blue);
		lWeapon[1].setHorizontalAlignment(JLabel.RIGHT);
		lWeapon[1].setFont(new Font("Arial", Font.PLAIN, 20));
		lWeapon[1].setBounds(1720, 10, 190, 20);
		add(lWeapon[1]);
	}

	public void setupPlayers() {

		players[0] = MainMenu.p1;
		playerRect[0] = new Rectangle(20, 60, 25, 25);
		weaponRect[0] = new Rectangle(playerRect[0].x + 20,
				playerRect[0].y - 10, 5, 10);

		players[1] = MainMenu.p2;
		playerRect[1] = new Rectangle(1870, 970, 25, 25);
		weaponRect[1] = new Rectangle(playerRect[1].x + 20,
				playerRect[1].y - 10, 5, 10);

		usernames[0] = new JLabel(players[0].username);
		usernames[0].setForeground(Color.RED);
		usernames[0].setHorizontalAlignment(JLabel.CENTER);
		usernames[0].setBounds(playerRect[0].x - 25, playerRect[0].y + 20, 75,
				40);

		usernames[1] = new JLabel(players[1].username);
		usernames[1].setForeground(blue);
		usernames[1].setHorizontalAlignment(JLabel.CENTER);
		usernames[1].setBounds(playerRect[1].x - 25, playerRect[1].y + 20, 75,
				40);

		add(usernames[0]);
		add(usernames[1]);

		hp[0] = new Rectangle(playerRect[0].x - 5, playerRect[0].y - 20, 35, 5);
		hp[1] = new Rectangle(playerRect[1].x - 5, playerRect[1].y - 20, 35, 5);

		for (int i = 0; i < 4; i++) {
			vel[i] = 0;
		}
		
		
	}

	public void setupBlocks() {
		for (int i = 0; i < 80; i += 4) {

			int x1 = 30 * random.nextInt(64), y1 = 30 * random.nextInt(36);
			int x2 = x1, y2 = y1 + 30;
			int x3 = x1, y3 = y2 + 30;
			int x4 = x1 + 30, y4 = y3;

			rects[i] = new Rectangle(x1, y1, 30, 30);
			rects[i + 1] = new Rectangle(x2, y2, 30, 30);
			rects[i + 2] = new Rectangle(x3, y3, 30, 30);
			rects[i + 3] = new Rectangle(x4, y4, 30, 30);

			while (rects[i].x < 40 || rects[i].x > 1800 || rects[i].y < 60	|| rects[i].y > 990) {
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

			rects[i] = new Rectangle(30 * random.nextInt(192),
					30 * random.nextInt(108), width, height);

			while (rects[i].x < 60 || rects[i].x > 1770 || rects[i].y < 30
					|| rects[i].y > 930) {
				rects[i] = new Rectangle(30 * random.nextInt(192),
						30 * random.nextInt(108), width, height);
			}
		}

		for (int i = 0; i < 2; i++) {
			gunfire[i] = new Rectangle(2000, 2000, 50, 50);
		}

	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		RenderingHints rh2 = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setRenderingHints(rh);
		g2d.setRenderingHints(rh2);

		g.setColor(new Color(225, 225, 225));
		g.fillRect(0, 0, 1920, 1080);

		for (int i = 0; i < 20; i++) {
			rects[i] = new Rectangle(rects[i].x, rects[i].y, rects[i].width,
					rects[i].height);
		}

		for (Rectangle r : rects) {
			g.setColor(Color.BLACK);
			g.fillRect(r.x, r.y, r.width, r.height);
		}

		g.setColor(Color.RED);
		g.fillRect(playerRect[0].x, playerRect[0].y, playerRect[0].width,
				playerRect[0].height);

		g.setColor(blue);
		g.fillRect(playerRect[1].x, playerRect[1].y, playerRect[1].width,
				playerRect[1].height);

		g.setColor(Color.YELLOW);
		g.fillRect(gunfire[0].x, gunfire[0].y, gunfire[0].width,
				gunfire[0].height);
		g.fillRect(gunfire[1].x, gunfire[1].y, gunfire[1].width,
				gunfire[1].height);

		g.setColor(grey);
		g.fillRect(weaponRect[0].x, weaponRect[0].y, weaponRect[0].width,
				weaponRect[0].height);
		g.fillRect(weaponRect[1].x, weaponRect[1].y, weaponRect[1].width,
				weaponRect[1].height);

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
		
		hit();
	}

	public void hit() {
		
		

		if (gunfire[0].intersects(playerRect[1])) {

			players[1].health -= Weapon.weapons[0].damagePerShot;
			hp[1].width = (int) (0.035 * players[1].health);
			System.out.println(players[1].health);
			if (players[1].health < 0 && hp[1].width <= 0) {
				//Player 2 killed
			}

		}

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

	public void fire(int i) {

		count[i]++;

		if (count[i] < 3) {

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
		} else {
			gunfire[i].x = 2000;
			gunfire[i].y = 2000;
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

	@Override
	public void actionPerformed(ActionEvent e) {

		playerRect[0].x += vel[0];
		playerRect[0].y += vel[1];
		hp[0].x = playerRect[0].x - 5;
		hp[0].y = playerRect[0].y - 20;
		usernames[0].setBounds(playerRect[0].x - 25, playerRect[0].y + 17, 75, 40);
		

		playerRect[1].x += vel[2];
		playerRect[1].y += vel[3];
		hp[1].x = playerRect[1].x - 5;
		hp[1].y = playerRect[1].y - 20;
		usernames[1].setBounds(playerRect[1].x - 25, playerRect[1].y + 17, 75, 40);


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

		

		for (int i = 0; i < 2; i++) {
			sortOrientation(i);

			if (isFiring[i]) {
				fire(i);

			}
		}
		
		
		
		repaint();

	}

	@Override
	public void keyPressed(KeyEvent e) {

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
			isFiring[0] = true;
			break;

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
			isFiring[1] = true;
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN
				|| e.getKeyCode() == KeyEvent.VK_UP) {
			vel[1] = 0;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
			vel[0] = 0;
		}

		if (MainMenu.multiP) {
			if (e.getKeyCode() == KeyEvent.VK_S
					|| e.getKeyCode() == KeyEvent.VK_W) {
				vel[3] = 0;
			}

			if (e.getKeyCode() == KeyEvent.VK_A
					|| e.getKeyCode() == KeyEvent.VK_D) {
				vel[2] = 0;
			}
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
