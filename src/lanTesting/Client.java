package lanTesting;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Client extends JFrame{
	
	JPanel p = new JPanel();

	ObjectInputStream i;
	ObjectOutputStream o;
	Socket s;
	
	public Client() throws Exception {		
		
		s = new Socket(InetAddress.getByName("192.168.0.18"), 6789);
		
		o = new ObjectOutputStream(s.getOutputStream());
		o.flush();
		i = new ObjectInputStream(s.getInputStream());
		
		setSize(300, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	@SuppressWarnings("unused")
	public static void main (String[] args) throws Exception {
		Client c = new Client();
	}
	
}
