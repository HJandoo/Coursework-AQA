package mainProgram;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
	
	ImageIcon img = new ImageIcon("Gun Mania Logo_RESIZED.png");

	JPanel p = new JPanel();
	
	JLabel l1 = new JLabel("GUN MANIA");
	JLabel l2 = new JLabel(img);
	
	JButton playGame = new JButton("Play Game");
	JButton stats = new JButton("Stats");
	JButton options = new JButton("Options");
	
	Font f1 = new Font("Arial", Font.BOLD, 30);
	Font f2 = new Font("Arial", Font.BOLD, 14);
	
	Color bl = Color.BLACK;
	Color wh = Color.WHITE;
	
	public static Player p1, p2;
	static String player1Name;
	
	static boolean multiP;
	
	public MainMenu() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Gun Mania");
		setIconImage(img.getImage());
		
		p.setLayout(null);
		add(p);
		p.setBackground(wh);
		
		l1.setBounds(10, 10, 280, 40);
		l1.setHorizontalAlignment(JLabel.CENTER);
		l1.setFont(f1);
		l1.setForeground(bl);
		p.add(l1);
		
		l2.setBounds(10, 60, 100, 100);
		p.add(l2);
		

		playGame.setBounds(10, 200, 275, 40);
		playGame.setFont(f2);
		playGame.setForeground(bl);
		p.add(playGame);
		
		stats.setBounds(10, 250, 275, 40);
		stats.setFont(f2);
		stats.setForeground(bl);
		p.add(stats);
		
		options.setBounds(10, 300, 275, 40);
		options.setFont(f2);
		options.setForeground(bl);
		p.add(options);
		
		playGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Weapon.createWeapons();
				
				String player1Name = JOptionPane.showInputDialog("Player 1 pls");				
				p1 = new Player(player1Name, 1000, Weapon.weapons[0]);
				
				String player2Name = JOptionPane.showInputDialog("Player 2 pls");				
				p2 = new Player(player2Name, 1000, Weapon.weapons[0]);
				
				multiP = true;
				
				@SuppressWarnings("unused")
				MapMain m = new MapMain();
			} 
			
		});

		stats.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			} 
			
		});
		
		options.addActionListener(new ActionListener() {
			
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
