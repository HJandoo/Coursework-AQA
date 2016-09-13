package mainProgram;

import javax.swing.JFrame;

public class MapMain extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MapPanel p = new MapPanel();
	
	public MapMain() {
		setVisible(true);
		setSize(1920, 1080);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		add(p);
	}

}
