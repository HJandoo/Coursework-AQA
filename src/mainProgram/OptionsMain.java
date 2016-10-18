package mainProgram;

import javax.swing.JFrame;

public class OptionsMain extends JFrame{

	private static final long serialVersionUID = 1L;
	
	
	
	public OptionsMain(int x, int y, int[] choice) {
		setTitle("Options");
		setBounds(x + 40, y + 163, 250, 240);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		OptionsPanel p = new OptionsPanel(choice);
		add(p);
		
	}

}
