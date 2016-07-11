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


public class MapPanel extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;   
	
	Random r = new Random();
	Timer t = new Timer(5, this);
	
	Rectangle[] rects = new Rectangle[20];
	
	public MapPanel() {

		setFocusable(true);
		t.start();
		
		for (int i = 0; i < 20; i++) {

			rects[i] = new Rectangle(r.nextInt(1610) - 50, r.nextInt(970) - 50, r.nextInt(320) + 50, r.nextInt(320) + 50);
			
			while (rects[i].x < 40 || rects[i].x > 1860 || rects[i].y < 60 || rects[i].y > 1020) {
				rects[i] = new Rectangle(r.nextInt(1810) - 50, r.nextInt(970) - 50, r.nextInt(580) + 50, r.nextInt(230) + 30);
				
			}
		}
		
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		RenderingHints rh2 = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setRenderingHints(rh);
		g2d.setRenderingHints(rh2);
		
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, 1920, 1080);
		
		for (int i = 0; i < 20; i++) {
			rects[i] = new Rectangle(rects[i].x, rects[i].y, rects[i].width, rects[i].height);
		}
		
		for (Rectangle r : rects) {
			g.setColor(new Color(0, 129, 222));				
			g.fillRect(r.x, r.y, r.width, r.height);
		}
					
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		repaint();
	}



}
