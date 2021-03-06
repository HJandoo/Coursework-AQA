package testingOtherThings;

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

	int vx = 0, vy = 1, count = 0;

	Rectangle aiRect = new Rectangle(50, 400, 30, 30);

	Rectangle lineHor = new Rectangle(aiRect.x + 15 - 2000, aiRect.y + 15, 4000, 1);
	Rectangle lineVer = new Rectangle(aiRect.x + 15, aiRect.y + 15 - 1000, 1, 2000);

	Rectangle[] hitRect = new Rectangle[5];

	Rectangle[] bullet = new Rectangle[4];

	public Main() {
		setFocusable(true);

		t.start();

		hitRect[0] = new Rectangle(160, 530, 40, 30);
		
		bullet[0] = new Rectangle(2000, 2000, 1920, 1);
		bullet[1] = new Rectangle(aiRect.x - 1950, aiRect.y, 1920, 1);
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1920, 1080);

		g.setColor(Color.BLUE);
		g.fillRect(aiRect.x, aiRect.y, aiRect.width, aiRect.height);

		g.setColor(Color.RED);
		g.fillRect(hitRect[0].x, hitRect[0].y, hitRect[0].width,
				hitRect[0].height);

		g.fillRect(lineHor.x, lineHor.y, lineHor.width, lineHor.height);
		g.fillRect(lineVer.x, lineVer.y, lineVer.width, lineVer.height);
		
		g.setColor(Color.YELLOW);
		g.fillRect(bullet[0].x, bullet[0].y, bullet[0].width, bullet[0].height);

		if (lineHor.intersects(hitRect[0]) || lineVer.intersects(hitRect[0])) {
			
			count++;
			
			bullet[0].x = aiRect.x + 30;
			bullet[0].y = aiRect.y + 30;
			bullet[0].width = hitRect[0].x - bullet[0].x;
			
			if (count % 4 == 0) {				
				bullet[0].x = aiRect.x + 30;
				bullet[0].y = aiRect.y + 30;
			} else {
				bullet[0].x = 2000;
				bullet[0].y = 2000;
			}
			

		} else{
			bullet[0].x = 2000;
			bullet[0].y = 2000;
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
		
		if (aiRect.y >= 600) {
			vy = -1;
		}
		
		if (aiRect.y <= 470) {
			vy = 1;
		}

		repaint();

	}

}