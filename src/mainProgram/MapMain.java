package mainProgram;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import engine.Player;
import engine.SQLFunctions;
import engine.Weapon;

public class MapMain extends JFrame{

	private static final long serialVersionUID = 1L;
	
	ImageIcon img = new ImageIcon("Gun Mania Logo_RESIZED.png");
	
	public MapMain(Player[] players, Weapon[][] weapons) {
		// This increments the matches played by the players by 1
		// and updates the database to match this change
		SQLFunctions.updateMatches(players, 0);
		// Initialise the panel for the main gamed
		MapPanel p = new MapPanel(players, weapons);
		// Assign properties to this frame
		setVisible(true);
		setSize(1920, 1080);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("Gun Mania");
		setIconImage(img.getImage());
		add(p);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
	}

}
