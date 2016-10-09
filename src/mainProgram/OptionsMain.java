package mainProgram;

import javax.swing.JFrame;

public class OptionsMain extends JFrame{

	private static final long serialVersionUID = 1L;
	
	OptionsPanel p = new OptionsPanel();
	
	public OptionsMain() {
		setTitle("Options");
		setBounds(MainMenu.x + 40, MainMenu.y + 163, 250, 210);
		setResizable(false);
		setVisible(true);
		add(p);
		
	}

}
