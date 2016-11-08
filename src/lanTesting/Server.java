package lanTesting;

import java.awt.Font;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Server {
	
	JFrame f = new JFrame();
	JPanel p = new JPanel();
	JLabel l = new JLabel();
	
	
	ServerSocket s;
	Socket soc;
	ObjectOutputStream o;
	ObjectInputStream i;
	
	public Server() {
		f.setSize(300, 300);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.add(p);
		
		p.setLayout(null);
		
		l.setBounds(0, 0, 300, 300);
		l.setFont(new Font("Arial", Font.PLAIN, 20));
		p.add(l);
		
		try {
			s = new ServerSocket(6789, 100);
						
			while (true) {
				try {
					soc = s.accept();
					System.out.println("Connected to " + soc.getInetAddress());
					o = new ObjectOutputStream(soc.getOutputStream());
					o.flush();
					i = new ObjectInputStream(soc.getInputStream());
					
					l.setText("<html><li>" + soc.getInetAddress());
					
				}catch (Exception e) {
					
				}
			}
			
			
			
		} catch (Exception e) {
			
		}
		
				
	}
	
	
	
	public static void main (String[] args) {
		Server s = new Server();
	}

}
