package mainProgram;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	JLabel scoreLimit = new JLabel("Score Limit");
	JLabel bg = new JLabel("Background Colour");
	JLabel wa = new JLabel("Wall Colour");
	JLabel rs = new JLabel("Resolution");
	
	JButton save = new JButton("Save settings");
	
	Integer[] scorelims = { 10, 15, 20, 25, 30, 35, 40, 45, 50 };
	String[] colours = { "White", "Grey", "Black",  "Green",  "Yellow", "Orange" };
	String[] reso = { "1920x1080", "1600x900", "1366x768", "1280x720" };
	
	JComboBox<Integer> cb1 = new JComboBox<Integer>(scorelims);
	JComboBox<String> cb2 = new JComboBox<String>(colours);
	JComboBox<String> cb3 = new JComboBox<String>(colours);
	JComboBox<String> cb4 = new JComboBox<String>(reso);
	
	

	public OptionsPanel() {
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		scoreLimit.setBounds(10, 10, 100, 20);
		scoreLimit.setForeground(Color.BLACK);
		add(scoreLimit);
		
		bg.setBounds(10, 40, 120, 20);
		bg.setForeground(Color.BLACK);
		add(bg);
		
		wa.setBounds(10, 70, 100, 20);
		wa.setForeground(Color.BLACK);
		add(wa);
		
		rs.setBounds(10, 100, 100, 20);
		rs.setForeground(Color.BLACK);
		add(rs);
		
		cb1.setBounds(130, 10, 100, 20);
		cb1.setSelectedItem(20);
		cb1.setForeground(Color.BLACK);
		add(cb1);
		
		cb2.setBounds(130, 40, 100, 20);
		cb2.setForeground(Color.BLACK);
		cb2.setSelectedItem("Grey");
		add(cb2);
		
		cb3.setBounds(130, 70, 100, 20);
		cb3.setForeground(Color.BLACK);
		cb3.setSelectedItem("Black");
		add(cb3);
		
		cb4.setBounds(130, 100, 100, 20);
		cb4.setForeground(Color.BLACK);
		cb4.setSelectedItem("1920x1080");
		add(cb4);
		
		save.setBounds(10, 140, 220, 30);
		save.setForeground(Color.BLACK);
		save.setBackground(new Color(225, 225, 225));
		add(save);
	}
	
}
