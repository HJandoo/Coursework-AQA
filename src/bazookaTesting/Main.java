package bazookaTesting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JFrame{
	
	Main() {
		setSize(400, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Bazooka Testing program");
		
		Panel p = new Panel();
		
		add(p);
		
	}

	public static void main(String[] args) {
		new Main();
		
	}

}

class Panel extends JPanel implements ActionListener, KeyListener {
	
	
	Rectangle player = new Rectangle(190, 140, 20, 20);
	Rectangle bazookaWeapon = new Rectangle(player.x - 3, player.y + 14, 28, 7);
	
	int[] xpoints = { bazookaWeapon.x + bazookaWeapon.width, bazookaWeapon.x + bazookaWeapon.width + 10, bazookaWeapon.x + bazookaWeapon.width };
	int[] yPoints = { 2, 5, 8 };
	
	int vel = 2;
	
	Timer t = new Timer(5, this);
	Polygon missile = new Polygon(xpoints, yPoints, 3);
	
	public Panel() {
		setLayout(null);
		setFocusable(true);
		addKeyListener(this);
		setFocusTraversalKeysEnabled(false);
		t.start();
	}
	
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setRenderingHints(rh);
		g2d.setRenderingHints(rh2);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 400, 300);
		
		g.setColor(Color.RED);
		g.fillRect(player.x, player.y, player.width, player.height);
		
		g.setColor(new Color(0, 130, 0));
		g.fillRect(bazookaWeapon.x, bazookaWeapon.y, bazookaWeapon.width, bazookaWeapon.height);
		
		g.setColor(new Color(50, 50, 50));
		g.fillPolygon(missile);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		xpoints[0] += vel;
		xpoints[1] += vel;
		xpoints[2] += vel;
		
		missile = new Polygon(xpoints, yPoints, 3);
		
		repaint();
	}
	
}
