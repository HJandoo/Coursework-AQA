package ai;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Timer t = new Timer(5, this);

	int vx = 0, vy = 1;

	Rectangle aiRect = new Rectangle(50, 100, 30, 30);

	Rectangle lineHor = new Rectangle(aiRect.x + 15 - 2000, aiRect.y + 15, 4000, 1);
	Rectangle lineVer = new Rectangle(aiRect.x + 15, aiRect.y + 15 - 1000, 1, 2000);

	Rectangle[] hitRect = new Rectangle[5];

	Rectangle[] bullet = new Rectangle[2];

	public Main() {
		setFocusable(true);

		t.start();

		hitRect[0] = new Rectangle(160, 530, 40, 30);
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1920, 1080);

		g.setColor(Color.BLUE);
		g.fillRect(aiRect.x, aiRect.y, aiRect.width, aiRect.height);

		g.setColor(Color.RED);
		g.fillRect(hitRect[0].x, hitRect[0].y, hitRect[0].width, hitRect[0].height);

		g.fillRect(lineHor.x, lineHor.y, lineHor.width, lineHor.height);
		g.fillRect(lineVer.x, lineVer.y, lineVer.width, lineVer.height);

		if (lineHor.intersects(hitRect[0]) || lineVer.intersects(hitRect[0])) {
			System.out.println("shoot!");
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		aiRect.x += vx;
		aiRect.y += vy;
		lineHor.x += vx;
		lineHor.y += vy;
		lineVer.x += vx;
		lineVer.y += vy;

		repaint();

	}

}
