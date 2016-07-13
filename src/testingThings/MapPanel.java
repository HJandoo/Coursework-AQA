package testingThings;

import java.awt.Color;
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

	Random r = new Random();
	Timer t = new Timer(5, this);

	Rectangle[] rects = new Rectangle[250];
	Rectangle[] playerRect = new Rectangle[2];

	Player[] players = new Player[2];

	Color blue = new Color(0, 129, 222);
	
	int[] vel = new int[4];
	boolean touchingX = false, touchingY = false;
	
	JLabel[] usernames = new JLabel[2];
	
	public MapPanel() {

		players[0] = MainMenu.p1;
		//players[1] = MainMenu.p2;
				
		String u1 = players[0].username;
		
		System.out.println(u1);

		setFocusable(true);
		addKeyListener(this);
		setFocusTraversalKeysEnabled(false);
		t.start();

		playerRect[0] = new Rectangle(20, 20, 25, 25);

		playerRect[1] = new Rectangle(1880, 980, 25, 25);
		

		for (int i = 0; i < 80; i += 4) {

			int x1 = 30 * r.nextInt(64), y1 = 30 * r.nextInt(36);
			int x2 = x1, y2 = y1 + 30;
			int x3 = x1, y3 = y2 + 30;
			int x4 = x1 + 30, y4 = y3;

			rects[i] = new Rectangle(x1, y1, 30, 30);
			rects[i + 1] = new Rectangle(x2, y2, 30, 30);
			rects[i + 2] = new Rectangle(x3, y3, 30, 30);
			rects[i + 3] = new Rectangle(x4, y4, 30, 30);

			while (rects[i].x < 40 || rects[i].x > 1860 || rects[i].y < 60
					|| rects[i].y > 1020) {
				x1 = 30 * r.nextInt(64);
				y1 = 30 * r.nextInt(36);
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

			int width = 30 * r.nextInt(2) + 30;
			int height = 30 * r.nextInt(2) + 30;

			rects[i] = new Rectangle(30 * r.nextInt(192), 30 * r.nextInt(108),
					width, height);

			while (rects[i].x < 40 || rects[i].x > 1860 || rects[i].y < 60
					|| rects[i].y > 1020) {
				rects[i] = new Rectangle(30 * r.nextInt(192),
						30 * r.nextInt(108), width, height);
			}
		}
		
		for (int i = 0; i < 4; i++) {
			vel[i] = 0;
		}
				
		
		usernames[0] = new JLabel(u1);
		usernames[0].setBounds(playerRect[0].x, playerRect[0].y + 25, 70, 40);
		
		add(usernames[0]);
		
		//usernames[1].setText(players[1].username);

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

		g.setColor(new Color(255, 255, 255));
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
		g.fillRect(playerRect[0].x, playerRect[0].y, playerRect[0].width, playerRect[0].height);

		if (MainMenu.multiP) {

			g.setColor(blue);
			g.fillRect(playerRect[1].x, playerRect[1].y, playerRect[1].width, playerRect[1].height);
		}
		
		for (int i = 0; i < 250; i++) {
			if (playerRect[0].intersects(rects[i]) && vel[0] != 0) {
				vel[0] = 0;
				touchingX = true;
			} else {
				touchingX = false;
			}
			
			if (playerRect[0].intersects(rects[i]) && vel[1] != 0) {
				vel[1] = 0;
				touchingY = true;
			} else {
				touchingY = false;
			}

		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (!touchingX) {
			playerRect[0].x += vel[0];
		}
		
		if (!touchingY) {
			playerRect[0].y += vel[1];
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
		
		if (playerRect[0].y > 1055) {
			playerRect[0].y = 1055;
			vel[1] = 0;
		}
		
		/*playerRect[1].x += vel[2];
		playerRect[2].y += vel[3];*/

		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()){
		case KeyEvent.VK_UP:
			vel[1] = -2;
			break;
		case KeyEvent.VK_DOWN:
			vel[1] = 2;
			break;
		case KeyEvent.VK_LEFT:
			vel[0] = -2;
			break;
		case KeyEvent.VK_RIGHT:
			vel[0] = 2;
			break;
		
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
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

}
