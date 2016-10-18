package strobeTesting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JPanel implements ActionListener, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Timer t = new Timer(10, this);
	
	Rectangle l = new Rectangle(500, 500, 500, 2);
	
	int count = 39;
	boolean pressed = false;
	
	public Main() {
		setFocusable(true);
		addKeyListener(this);
		setFocusTraversalKeysEnabled(false);
		
		t.start();
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 500);
		
		g.setColor(Color.YELLOW);
		g.fillRect(l.x, l.y, l.width, l.height);
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (pressed) {
			count++;
			System.out.println(count);

			if (count == 40) {
				l.x = 0;
				l.y = 249;
			} else {
				l.x = 500;
				l.y = 500;
			}
			
			if (count >= 80) {
				count = 39;
			}
		}
		
		
		
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			pressed = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		count = 39;
		pressed = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
