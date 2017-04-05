package lanTesting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Client extends JFrame implements Serializable {

	private static final long serialVersionUID = 1L;

	ObjectInputStream i;
	ObjectOutputStream o;
	Socket s;
	
	Rectangle r = new Rectangle();
	
	int x, y, width, height;

	public Client() {
		
		pan p = new pan();

		setTitle("Client");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		add(p);

	}

	class pan extends JPanel implements ActionListener{

		private static final long serialVersionUID = 1L;
		
		Timer t = new Timer(10, this);

		pan() {
			setLayout(null);
			try {

				s = new Socket(InetAddress.getByName("10.1.129.57"), 55000);
				o = new ObjectOutputStream(s.getOutputStream());
				o.flush();
				i = new ObjectInputStream(s.getInputStream());

				x = i.readInt();
				y = i.readInt();
				width = i.readInt();
				height = i.readInt();
				r.setBounds(x, y, width, height);
				
				
			} catch (Exception e) {
				e.printStackTrace();

			}
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
			try {
				
				s = new Socket(InetAddress.getByName("82.20.202.89"), 12345);
				o = new ObjectOutputStream(s.getOutputStream());
				o.flush();
				i = new ObjectInputStream(s.getInputStream()); 
				x = i.readInt();
				y = i.readInt();
				width = i.readInt();
				height = i.readInt();
				//r.setBounds((Rectangle) i.readObject());
				r.setBounds(x, y, width, height);
				repaint();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	
			repaint();
			
		}

	}

	public static void main(String[] args) throws Exception {
		new Client();
	}

}
