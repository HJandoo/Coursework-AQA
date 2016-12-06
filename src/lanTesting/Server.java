package lanTesting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Server implements Runnable{

	static JFrame f = new JFrame();
	
	JLabel l = new JLabel();
	
	ServerSocket s;
	static Socket soc;
	static ObjectOutputStream o;
	static ObjectInputStream i;

	static Rectangle r = new Rectangle();
	
	public Server(Socket sok){
		
		this.soc = sok;
	
		try {

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public static void sendX(int x) throws Exception {
		o.writeInt(x);
		o.flush();
		
	}

	static class pan extends JPanel implements ActionListener{

		private static final long serialVersionUID = 1L;
		
		Timer t = new Timer(10, this);
		int vel = 1;
		
		public pan() {
			setLayout(null);
			t.start();
		}
				
		public void paintComponent(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 500, 500);
			
			g.setColor(Color.BLACK);
			g.fillRect(r.x, r.y, r.width, r.height);
		}
		
		

		@Override
		public void actionPerformed(ActionEvent e) {
			
			r.x += vel;
			
			if (r.x >= 450) {
				vel *= -1;
			} if (r.x <= 0) {
				vel *= -1;
			}
			
			try {
				sendX(vel);
			} catch (Exception e1) {
				//e1.printStackTrace();
			}
			
			repaint();
			
		}	
	}
	

	public static void main (String[] args) {
		
		Server ser = new Server(soc);
		
		pan p = new pan();
		f.setSize(500, 500);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setTitle("Server");
		f.add(p);
		
		r.setBounds(125, 125, 50, 50);
		
		try {
			ServerSocket s = new ServerSocket(12345, 100);
				
			while (true) {
				try {
					soc = s.accept();
					System.out.println("Connected to " + soc.getInetAddress());
					new Thread(new Server(soc)).start();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			o = new ObjectOutputStream(soc.getOutputStream());
			o.flush();
			i = new ObjectInputStream(soc.getInputStream());
			
			o.writeInt(r.x);
			o.writeInt(r.y);
			o.writeInt(r.width);
			o.writeInt(r.height);
			o.writeObject(r);
			o.flush();
						
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
