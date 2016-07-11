package testingThings;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainMenu extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel p = new JPanel();
	
	JLabel l1 = new JLabel("SHOOTING GAME");
	
	JButton sp = new JButton("Single Player");
	JButton mp = new JButton("Multiplayer");
	JButton stat = new JButton("Player Stats");
	JButton opt = new JButton("Options");
	
	Font f1 = new Font("Arial", Font.BOLD, 30);
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
		
		sp.setBounds(10, 200, 275, 40);
		sp.setFont(f2);
		sp.setForeground(bl);
		p.add(sp);
		
		mp.setBounds(10, 250, 275, 40);
		mp.setFont(f2);
		mp.setForeground(bl);
		p.add(mp);
		
		stat.setBounds(10, 300, 275, 40);
		stat.setFont(f2);
		stat.setForeground(bl);
		p.add(stat);
		
		opt.setBounds(10, 350, 275, 40);
		opt.setFont(f2);
		opt.setForeground(bl);
		p.add(opt);
		
		sp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String player1Name = JOptionPane.showInputDialog("Username pls");
				
				Player p1 = new Player(player1Name, 1000, Weapon.weapons[0]);
				
				Weapon.createWeapons();
				
				@SuppressWarnings("unused")
				MapMain m = new MapMain();
			} 
			
		});

		mp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Weapon.createWeapons();
				
				String player1Name = JOptionPane.showInputDialog("Player 1 pls");
				
				Player p1 = new Player(player1Name, 1000, Weapon.weapons[0]);
				
				String player2Name = JOptionPane.showInputDialog("Player 2 pls");
				
				Player p2 = new Player(player2Name, 1000, Weapon.weapons[0]);				
				
				@SuppressWarnings("unused")
				MapMain m = new MapMain();
			} 
			
		});
		
		stat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			} 
			
		});
		
		opt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OptionsMain m = new OptionsMain();
			} 
			
		});
		
		
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainMenu m = new MainMenu();
	}
	
}
