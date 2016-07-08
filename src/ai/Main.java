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
	
	int vx = 0, vy = 2;
	
	Rectangle r = new Rectangle(50, 100, 30, 30);
	
	Rectangle l1 = new Rectangle(r.x + 15 - 2000, r.y + 15, 4000, 1);
	Rectangle l2 = new Rectangle(r.x + 15, r.y + 15 - 1000, 1, 2000);
	
	Rectangle[] hit = new Rectangle[5];
	
	
	
	//INSTALL JAVA FX!!!
	

	public Main() {
		setFocusable(true);

		t.start();
		
		hit[0] = new Rectangle(160, 530, 40, 30);
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1920, 1080);

		g.setColor(Color.BLUE);
		g.fillRect(r.x, r.y, r.width, r.height);

		g.setColor(Color.RED);
		g.fillRect(hit[0].x, hit[0].y, hit[0].width, hit[0].height);
		
		g.fillRect(l1.x, l1.y, l1.width, l1.height);		
		g.fillRect(l2.x, l2.y, l2.width, l2.height);
		
		if (l1.intersects(hit[0]) || l2.intersects(hit[0])) {
			System.out.println("shoot!");
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		r.x += vx;
		r.y += vy;
		l1.x += vx;
		l1.y += vy;
		l2.x += vx;
		l2.y += vy;
		
		repaint();
		
	}

}
