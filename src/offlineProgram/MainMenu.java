package offlineProgram;

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
import javax.swing.JOptionPane;
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
	Weapon[][] weapons = new Weapon[2][7];
	
	static 	int[] choice = new int[3];
	int time;
	
	@SuppressWarnings("unused")
	public MainMenu() {
		
		choice[0]  = 1;
		choice[1] = 2;
		choice[2] = 0;
		
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
		
		setTitle("Gun Mania OFFLINE");
		setIconImage(img.getImage());
		add(p);
		p.setLayout(null);
		
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
				
				SQLFunctions.getOfflineWeapons(weapons);
				
				getTime(time);
				
				MapMain m = new MapMain(players, weapons);		
			}
		});

		stats.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getParent(), "Sorry, this can't be used in offline mode", "This can't be used", JOptionPane.ERROR_MESSAGE);
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
	
	@SuppressWarnings("unused")
	public void getTime(int time) {
		boolean timeFound = false;
		
		if (OptionsPanel.timeLim == 0) {
			OptionsPanel.timeLim = 180;
		} else {
			
			int[] diff = new int[OptionsPanel.timelims.length];
			
			for (int i = 0; i < OptionsPanel.timelims.length; i++) {
				
				if (OptionsPanel.timeLim != OptionsPanel.timelims[i]) {
					diff[i] = OptionsPanel.timelims[i] - OptionsPanel.timeLim;
					timeFound = false;
				} else {
					timeFound = true;
				}
			}
			
			if (!timeFound) {
				int small = diff[0];
				int index = 0;
				
				for (int i = 0; i < OptionsPanel.timelims.length; i++) {
					if (diff[i] < small) {
						small = diff[i];
						index = i;
					}
				}
				time = OptionsPanel.timeLim + diff[0];
			}
	
		}
		
		
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainMenu m = new MainMenu();
	}

}
