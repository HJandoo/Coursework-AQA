package strobeTesting;

import javax.swing.JFrame;

public class Frame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			JFrame f = new JFrame();
			
			f.setSize(500, 500);
			f.setResizable(false);
			f.setLocationRelativeTo(null);
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			Main p = new Main();
			
			f.add(p);
			

	}

}
