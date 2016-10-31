package offlineProgram;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import engine.Player;
import engine.SQLFunctions;
import engine.Weapon;

public class MapMain extends JFrame{

	private static final long serialVersionUID = 1L;
	
	ImageIcon img = new ImageIcon("Gun Mania Logo_RESIZED.png");
	
	public MapMain(Player[] players, Weapon[][] weapons) {
		//SQLFunctions.updateMatches(players, 0);
		MapPanel p = new MapPanel(players, weapons);
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
