package timerTesting;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String[] args) {
		
		final Timer t = new Timer();
		
		System.out.println("Working...");
		
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("All done");
				t.cancel();
			}
			
		}, 3000);
		
		
	}

}
