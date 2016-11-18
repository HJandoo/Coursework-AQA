package offlineProgram;

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
	static Integer[] timelims = { 3, 4, 5, 6, 7, 8, 9, 10 };
	String[] colours = { "White", "Grey", "Black",  "Green",  "Yellow", "Orange" };
	String[] reso = { "1920x1080", "1600x900", "1366x768", "1280x720" };
	
	JComboBox<Integer> scoreComboBox = new JComboBox<Integer>(scorelims);
	JComboBox<Integer> timeComboBox = new JComboBox<Integer>(timelims);
	JComboBox<String> backgroundComboBox = new JComboBox<String>(colours);
	JComboBox<String> wallComboBox = new JComboBox<String>(colours);
	JComboBox<String> resolutionComboBox = new JComboBox<String>(reso);
	
	static int scoreLim, timeLim = 180;
	
	static Color[] colourChoices = { Color.WHITE, new Color(225, 225, 225), Color.BLACK, Color.GREEN, Color.YELLOW, Color.ORANGE };
	
	static Color backgroundColour = colourChoices[1], wallColour = colourChoices[2];

	public OptionsPanel(final int[] choice) {
		// Initialising properties of the panel as well as the components
		// to give the desired layout for the options menu
		
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
		
		scoreComboBox.setBounds(130, 10, 100, 20);
		scoreComboBox.setSelectedItem(20);
		scoreComboBox.setForeground(Color.BLACK);
		add(scoreComboBox);
		
		timeComboBox.setBounds(130, 40, 100, 20);
		timeComboBox.setForeground(Color.BLACK);
		timeComboBox.setSelectedIndex(choice[2]);
		add(timeComboBox);
		
		backgroundComboBox.setBounds(130, 70, 100, 20);
		backgroundComboBox.setForeground(Color.BLACK);
		backgroundComboBox.setSelectedIndex(choice[0]);
		add(backgroundComboBox);
		
		wallComboBox.setBounds(130, 100, 100, 20);
		wallComboBox.setForeground(Color.BLACK);
		wallComboBox.setSelectedIndex(choice[1]);
		add(wallComboBox);
		
		resolutionComboBox.setBounds(130, 130, 100, 20);
		resolutionComboBox.setForeground(Color.BLACK);
		resolutionComboBox.setSelectedItem("1920x1080");
		add(resolutionComboBox);
		
		apply.setBounds(10, 170, 220, 30);
		apply.setForeground(Color.BLACK);
		apply.setBackground(new Color(225, 225, 225));
		add(apply);
		
		// When the button 'apply' is pressed, retrieve information in 
		// the comboBoxes and alter the game depending on this info
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scoreLim = (int) scoreComboBox.getSelectedItem();
				timeLim = (int) timeComboBox.getSelectedItem() * 60;
								
				choice[0] = backgroundComboBox.getSelectedIndex();
				choice[1] = wallComboBox.getSelectedIndex();
				
				backgroundComboBox.setSelectedIndex(choice[0]);
				wallComboBox.setSelectedIndex(choice[1]);

				for (int i = 0; i < 6; i++) {
					if (choice[0] == i) {
						backgroundColour = colourChoices[i];						
					}	
					if (choice[1] == i) {
						wallColour = colourChoices[i];
					}
				}
				
				// Close the options window once complete
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(getParent());
				topFrame.dispose();	
		
			}
			
		});
	}
	
	
}