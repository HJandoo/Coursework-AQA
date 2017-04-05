package lanTesting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Server implements Runnable {

	static JFrame frame = new JFrame();

	static Socket socket;
	static ObjectOutputStream outputStream;
	static ObjectInputStream inputStream;
	static Rectangle rectangle = new Rectangle();

	@SuppressWarnings("static-access")
	public Server(Socket socket) {

		this.socket = socket;
	}

	public static void sendX(int x) throws Exception {
		outputStream.writeInt(x);
		outputStream.flush();

	}

	static class pan extends JPanel implements ActionListener {

		private static final long serialVersionUID = 1L;

		Timer timer = new Timer(5, this);
		int velocity = 1;

		public pan() {
			setLayout(null);
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

			rectangle.x += velocity;

			if (rectangle.x <= 0) {
				velocity = 2;
				rectangle.x += velocity;
			}		
			if (rectangle.x >= 500 - rectangle.width) {
				velocity = -2;
				rectangle.x += velocity;
			}
			
			repaint();

		}
	}

	@SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) {

		Server ser = new Server(socket);

		pan p = new pan();
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTitle("Server");
		frame.add(p);

		rectangle.setBounds(-70, 125, 50, 50);

		try {
			ServerSocket s = new ServerSocket(12345, 100);

			while (true) {
				try {
					socket = s.accept();
					System.out.println("Connected to " + socket.getInetAddress());
					new Thread(new Server(socket)).start();
				} catch (Exception e) {
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
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(socket.getInputStream());

			outputStream.writeInt(rectangle.x);
			outputStream.writeInt(rectangle.y);
			outputStream.writeInt(rectangle.width);
			outputStream.writeInt(rectangle.height);
			outputStream.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
