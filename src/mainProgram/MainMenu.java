package mainProgram;

/** 
 * Computer Science AQA Coursework 2016/17 - GUN MANIA
 * Made by Harnaam Jandoo
 * Candidate number: 5039
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Player;
import engine.SQLFunctions;
import engine.Weapon;

public class MainMenu extends JFrame {

	private static final long serialVersionUID = 1L;

	ImageIcon img = new ImageIcon("Gun Mania Logo_RESIZED.png");

	Font f1 = new Font("Arial", Font.BOLD, 30);
	Font f2 = new Font("Arial", Font.BOLD, 14);

	Color bl = Color.BLACK;
	Color wh = Color.WHITE;
	
	Player[] players = new Player[2];
	Weapon[][] weapons = new Weapon[2][5];
	
	static 	int[] choice = new int[3];
	static int timeLim;

	@SuppressWarnings("unused")
	public MainMenu() {
		
		choice[0]  = 1;
		choice[1] = 2;
		choice[2] = 0;
		
		timeLim = 180;
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 505);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel();

		JLabel l1 = new JLabel("GUN MANIA");
		JLabel l2 = new JLabel(img);

		JButton playGame = new JButton("Play Game");
		JButton stats = new JButton("Stats");
		JButton options = new JButton("Options");
		JButton quit = new JButton("Quit");
		
		final int x;
		final int y;
		final int height;
		
		final boolean multiP = true;
		
		x = getX();
		y = getY();
		height = getHeight();
		
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

		l2.setBounds(10, 60, 280, 201);
		p.add(l2);

		playGame.setBounds(10, 275, 275, 40);
		playGame.setFont(f2);
		playGame.setForeground(bl);
		playGame.setBackground(new Color(225, 225, 225));
		p.add(playGame);

		stats.setBounds(10, 325, 275, 40);
		stats.setFont(f2);
		stats.setForeground(bl);
		stats.setBackground(new Color(225, 225, 225));
		p.add(stats);

		options.setBounds(10, 375, 275, 40);
		options.setFont(f2);
		options.setForeground(bl);
		options.setBackground(new Color(225, 225, 225));
		p.add(options);

		quit.setBounds(10, 425, 275, 40);
		quit.setFont(f2);
		quit.setForeground(bl);
		quit.setBackground(new Color(225, 225, 225));
		p.add(quit);

		playGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				SQLFunctions.getWeapons(weapons);
				
				LoginMain m = new LoginMain(0, players, weapons, x, y, height, multiP);		
			}
		});

		stats.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StatsMain m = new StatsMain();
			}
		});

		options.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OptionsMain m = new OptionsMain(x, y, choice);
			}
		});

		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainMenu m = new MainMenu();
	}

}
