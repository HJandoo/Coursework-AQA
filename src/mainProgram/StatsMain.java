package mainProgram;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import engine.SQLFunctions;

public class StatsMain extends JFrame {

	private static final long serialVersionUID = 1L;

	Object[][] data = new Object[SQLFunctions.getNumberOfPlayers()][5];
	String[] columns = { "Username", "Kills", "Deaths", "K/D", "Win rate/%" };

	JTable t = new JTable(data, columns);

	public StatsMain() {
		
		t.setAutoCreateRowSorter(true);
		t.setForeground(Color.BLACK);
		t.setBackground(Color.WHITE);
		
		SQLFunctions.getStats(data);
		
		add(new JScrollPane(t));
		
		setTitle("Statistics");
		setResizable(false);
		setSize(400, 400);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
}
