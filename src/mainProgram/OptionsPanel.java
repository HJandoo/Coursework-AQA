package mainProgram;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class OptionsPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	JLabel scoreLimit = new JLabel("Score Limit");
	JLabel timeLimit = new JLabel("Time Limit");
	JLabel bg = new JLabel("Background Colour");
	JLabel wa = new JLabel("Wall Colour");
	JLabel rs = new JLabel("Resolution");
	
	JButton apply = new JButton("Apply settings");
	
	Integer[] scorelims = { 10, 15, 20, 25, 30, 35, 40, 45, 50 };
	Integer[] timelims = { 3, 4, 5, 6, 7, 8, 9, 10 };
	String[] colours = { "White", "Grey", "Black",  "Green",  "Yellow", "Orange" };
	String[] reso = { "1920x1080", "1600x900", "1366x768", "1280x720" };
	
	JComboBox<Integer> scoreCB = new JComboBox<Integer>(scorelims);
	JComboBox<Integer> timeCB = new JComboBox<Integer>(timelims);
	JComboBox<String> backCB = new JComboBox<String>(colours);
	JComboBox<String> foreCB = new JComboBox<String>(colours);
	JComboBox<String> resCB = new JComboBox<String>(reso);
	
	static int scoreLim, timeLim;
	
	static Color[] c = { Color.WHITE, new Color(225, 225, 225), Color.BLACK, Color.GREEN, Color.YELLOW, Color.ORANGE };
	
	static Color back = c[1], wall = c[2];

	public OptionsPanel(final int[] choice) {

		setLayout(null);
		setBackground(Color.WHITE);
		
		scoreLimit.setBounds(10, 10, 100, 20);
		scoreLimit.setForeground(Color.BLACK);
		add(scoreLimit);
		
		timeLimit.setBounds(10, 40, 100, 20);
		timeLimit.setForeground(Color.BLACK);
		add(timeLimit);
		
		bg.setBounds(10, 70, 120, 20);
		bg.setForeground(Color.BLACK);
		add(bg);
		
		wa.setBounds(10, 100, 100, 20);
		wa.setForeground(Color.BLACK);
		add(wa);
		
		rs.setBounds(10, 130, 100, 20);
		rs.setForeground(Color.BLACK);
		add(rs);
		
		scoreCB.setBounds(130, 10, 100, 20);
		scoreCB.setSelectedItem(20);
		scoreCB.setForeground(Color.BLACK);
		add(scoreCB);
		
		timeCB.setBounds(130, 40, 100, 20);
		timeCB.setForeground(Color.BLACK);
		timeCB.setSelectedIndex(choice[2]);
		add(timeCB);
		
		backCB.setBounds(130, 70, 100, 20);
		backCB.setForeground(Color.BLACK);
		backCB.setSelectedIndex(choice[0]);
		add(backCB);
		
		foreCB.setBounds(130, 100, 100, 20);
		foreCB.setForeground(Color.BLACK);
		foreCB.setSelectedItem(choice[1]);
		add(foreCB);
		
		resCB.setBounds(130, 130, 100, 20);
		resCB.setForeground(Color.BLACK);
		resCB.setSelectedItem("1920x1080");
		add(resCB);
		
		apply.setBounds(10, 170, 220, 30);
		apply.setForeground(Color.BLACK);
		apply.setBackground(new Color(225, 225, 225));
		add(apply);
		
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scoreLim = (int) scoreCB.getSelectedItem();
				MainMenu.timeLim = (int) timeCB.getSelectedItem() * 60;
				
				System.out.println(MainMenu.timeLim);
				
				choice[0] = backCB.getSelectedIndex();
				choice[1] = foreCB.getSelectedIndex();
				
				backCB.setSelectedIndex(choice[0]);
				foreCB.setSelectedIndex(choice[1]);

				for (int i = 0; i < 6; i++) {
					if (choice[0] == i) {
						back = c[i];						
					}	
					if (choice[1] == i) {
						wall = c[i];
					}
				}
				
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(getParent());
				topFrame.dispose();	
				
				
				
			}
			
		});
	}
	
}
