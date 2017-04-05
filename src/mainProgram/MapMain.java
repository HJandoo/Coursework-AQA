package mainProgram;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import engine.Player;
import engine.SQLFunctions;
import engine.Weapon;

public class MapMain extends JFrame{

	private static final long serialVersionUID = 1L;
	
	ImageIcon img = new ImageIcon("Gun Mania Logo_RESIZED.png");
	
	static int widthOfFrame, heightOfFrame;
	
	public MapMain(Player[] players, Weapon[] weapons) {
		setUndecorated(true);
		// This increments the matches played by the players by 1
		// and updates the database to match this change
		SQLFunctions.updateMatches(players, 0);
		// Initialise the panel for the main gamed
		MapPanel p = new MapPanel(players, weapons);
		// Assign properties to this frame
		setResizable(false);
		setVisible(true);
		setSize(getResolutionWidth(), getResolutionHeight());
		widthOfFrame = getWidth();
		heightOfFrame = getHeight();
		setLocationRelativeTo(null);
		setTitle("Gun Mania");
		setIconImage(img.getImage());
		add(p);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
	}
	
	public static int getResolutionWidth() {
		int[] widths = { 1920, 1600, 1366, 1280 };
		int width = widths[0];
		
		for (int i = 0; i < widths.length; i++) {
			if (OptionsPanel.res == i) {
				width = widths[i];
				return width;
			}
		}
		return width;
	}
	
	public static int getResolutionHeight() {
		int[] heights = { 1080, 900, 768, 720 };
		int height = heights[0];
		
		for (int i= 0; i < heights.length; i++) {
			if (OptionsPanel.res == i) {
				height = heights[i];
				return height;
			}
		}
		return height;
	}
}
