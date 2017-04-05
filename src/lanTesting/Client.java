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

	ObjectInputStream inputStream;
	ObjectOutputStream outputStream;
	Socket socket;
	Rectangle rectangle = new Rectangle();
	int x, y, width, height;

	public Client() {	
		Panel panel = new Panel();

		setTitle("Client");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		add(panel);

	}

	class Panel extends JPanel implements ActionListener{

		private static final long serialVersionUID = 1L;
		
		Timer timer = new Timer(10, this);

		Panel() {
			setLayout(null);
			try {
				socket = new Socket(InetAddress.getByName("192.168.0.18"), 12345);
				outputStream = new ObjectOutputStream(socket.getOutputStream());
				outputStream.flush();
				inputStream = new ObjectInputStream(socket.getInputStream());

				x = inputStream.readInt();
				y = inputStream.readInt();
				width = inputStream.readInt();
				height = inputStream.readInt();
				rectangle.setBounds(x, y, width, height);		
				
			} catch (Exception e) {
				e.printStackTrace();

			}
			timer.start();
			
		}

		public void paintComponent(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1920, 1080);			
			g.setColor(Color.BLACK);
			g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				
				socket = new Socket(InetAddress.getByName("192.168.0.18"), 12345);
				outputStream = new ObjectOutputStream(socket.getOutputStream());
				outputStream.flush();
				inputStream = new ObjectInputStream(socket.getInputStream()); 
				x = inputStream.readInt();
				y = inputStream.readInt();
				width = inputStream.readInt();
				height = inputStream.readInt();
				rectangle.setBounds(x, y, width, height);
				repaint();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			repaint();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		Client c = new Client();
	}
}
