package testingThings;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel p = new JPanel();
	
	JLabel l1 = new JLabel("SHOOTING GAME");
	
	JButton b1 = new JButton("Single Player");
	JButton b2 = new JButton("Multiplayer");
	JButton b3 = new JButton("Player Stats");
	
	Font f1 = new Font("Arial", Font.BOLD, 20);
	Font f2 = new Font("Arial", Font.BOLD, 14);
	
	Color bl = Color.BLACK;
	Color wh = Color.WHITE;
	
	public MainMenu() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Birds-eye-view-shooting-game");
		
		p.setLayout(null);
		
		add(p);
		
		l1.setBounds(10, 10, 280, 40);
		l1.setHorizontalAlignment(JLabel.CENTER);
		l1.setFont(f1);
		l1.setForeground(bl);
		p.add(l1);
		
		b1.setBounds(10, 200, 280, 40);
		b1.setFont(f2);
		b1.setForeground(bl);
		b1.setBackground(wh);
		p.add(b1);
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainMenu m = new MainMenu();
	}
	
}
