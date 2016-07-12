package testingThings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MapPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Random r = new Random();
	Timer t = new Timer(5, this);

	Rectangle[] rects = new Rectangle[250];

	Rectangle[] playerRect = new Rectangle[2];

	Player[] players = new Player[2];

	public MapPanel() {

		players[0] = MainMenu.p1;
		players[1] = MainMenu.p2;

		setFocusable(true);
		t.start();

		playerRect[0] = new Rectangle(20, 20, 20, 20);

		if (MainMenu.multiP) {
			playerRect[1] = new Rectangle(1900, 1060, 20, 20);
		}

		for (int i = 0; i < 80; i += 4) {

			int x1 = 20 * r.nextInt(96), y1 = 20 * r.nextInt(54);
			int x2 = x1, y2 = y1 + 20;
			int x3 = x1, y3 = y2 + 20;
			int x4 = x1 + 20, y4 = y3;

			rects[i] = new Rectangle(x1, y1, 20, 20);
			rects[i + 1] = new Rectangle(x2, y2, 20, 20);
			rects[i + 2] = new Rectangle(x3, y3, 20, 20);
			rects[i + 3] = new Rectangle(x4, y4, 20, 20);

			while (rects[i].x < 40 || rects[i].x > 1860 || rects[i].y < 60
					|| rects[i].y > 1020) {
				x1 = 20 * r.nextInt(96);
				y1 = 20 * r.nextInt(54);
				x2 = x1;
				y2 = y1 + 20;
				x3 = x1;
				y3 = y2 + 20;
				x4 = x1 + 20;
				y4 = y3;

				rects[i] = new Rectangle(x1, y1, 20, 20);
				rects[i + 1] = new Rectangle(x2, y2, 20, 20);
				rects[i + 2] = new Rectangle(x3, y3, 20, 20);
				rects[i + 3] = new Rectangle(x4, y4, 20, 20);

			}
		}

		for (int i = 80; i < 250; i++) {

			int width = 20 * r.nextInt(2) + 20;
			int height = 20 * r.nextInt(2) + 20;

			rects[i] = new Rectangle(20 * r.nextInt(192), 20 * r.nextInt(108),
					width, height);

			while (rects[i].x < 40 || rects[i].x > 1860 || rects[i].y < 60
					|| rects[i].y > 1020) {
				rects[i] = new Rectangle(20 * r.nextInt(192),
						20 * r.nextInt(108), width, height);
			}
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

		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, 1920, 1080);

		for (int i = 0; i < 20; i++) {
			rects[i] = new Rectangle(rects[i].x, rects[i].y, rects[i].width,
					rects[i].height);
		}

		for (Rectangle r : rects) {
			g.setColor(new Color(0, 129, 222));
			g.fillRect(r.x, r.y, r.width, r.height);
		}

		g.setColor(Color.RED);
		g.fillRect(playerRect[0].x, playerRect[0].y, playerRect[0].width,playerRect[0].height);

		if (MainMenu.multiP) {

			g.setColor(Color.GREEN);
			g.fillRect(playerRect[1].x, playerRect[1].y, playerRect[1].width,playerRect[1].height);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		repaint();
	}

}
