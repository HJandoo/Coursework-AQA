package mainProgram;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MapMain extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ImageIcon img = new ImageIcon("Gun Mania Logo_RESIZED.png");
	
	public MapMain(Player[] players, Weapon[] weapons) {
		MapPanel p = new MapPanel(players, weapons);
		setVisible(true);
		setSize(1920, 1080);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("Gun Mania");
		setIconImage(img.getImage());
		add(p);
	}

}
