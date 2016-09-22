package mainProgram;

import javax.swing.JFrame;

public class OptionsMain extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	OptionsPanel p = new OptionsPanel();
	
	public OptionsMain() {
		setTitle("Options");
		setLocationRelativeTo(null);
		setSize(400, 400);
		setVisible(true);
		add(p);
		
	}

}
