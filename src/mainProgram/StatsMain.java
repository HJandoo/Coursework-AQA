package mainProgram;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import engine.SQLFunctions;

public class StatsMain extends JFrame {

	private static final long serialVersionUID = 1L;

	Object[][] data = new Object[SQLFunctions.getNumberOfPlayers()][6];
	String[] columns = { "Username", "Kills", "Deaths", "K/D", "Win rate/%", "WMD" };

	JTable table = new JTable(data, columns);

	public StatsMain() {
		// Give properties to this frame and table
		
		table.setAutoCreateRowSorter(true);
		table.setForeground(Color.BLACK);
		table.setBackground(Color.WHITE);
		table.getTableHeader().setReorderingAllowed(false);
		table.setCellSelectionEnabled(false);		
		
		// Retrieve statistics of all players from the database
		// and add them to table
		SQLFunctions.getStats(data);
		
		add(new JScrollPane(table));
		setTitle("Statistics");
		setResizable(false);
		setSize(500, 400);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
}
