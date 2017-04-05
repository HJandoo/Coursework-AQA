package mainProgram;

import javax.swing.JFrame;

public class OptionsMain extends JFrame{

	private static final long serialVersionUID = 1L;

	public OptionsMain(int x, int y, int[] choice) {
		// Give properties to this frame, where x and y represent the x and y coordinates of the main menu window.
		// This means that the options window is centered
		setTitle("Options");
		setBounds(x + 40, y + 163, 250, 240);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// Initialise and add a new panel to this frame
		OptionsPanel p = new OptionsPanel(choice);
		add(p);
		
	}
}
