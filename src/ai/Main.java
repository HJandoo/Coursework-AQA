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

	Timer t = new Timer(6, this);
	
	int vx = 1, vy = 0;
	
	Rectangle r = new Rectangle(50, 100, 30, 30);
	
	
	//INSTALL JAVA FX!!!
	

	public Main() {
		setFocusable(true);

		t.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1920, 1080);

		g.setColor(Color.BLUE);
		g.fillRect(r.x, r.y, r.width, r.height);

		g.setColor(Color.RED);
		g.fillRect(200, 0, 40, 900);
		g.fillRect(160, 530, 400, 30);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		r.x += vx;
		r.y += vy;
		
		repaint();
		
	}

}
