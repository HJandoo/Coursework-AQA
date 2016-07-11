package ai;

import javax.swing.JFrame;

public class Frame {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		
		Main m = new Main();
		
		f.setTitle("AI Testing");
		f.setSize(1920, 1080);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(m);
		//f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
	}
	
}
