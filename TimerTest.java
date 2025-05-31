import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class TimerTest implements ActionListener{
	
	private final static int DELAY = 2000;
	private Timer timer;
	
	public TimerTest() {
		timer = new Timer(DELAY, this);
	}
	
	public void start() {timer.start();}
	public void stop() {timer.stop();}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Hello, World!");
	}
	
	public static void main(String[] args) throws InterruptedException {
		TimerTest t = new TimerTest();
		t.start();
		Thread.sleep(10000);
		t.stop();
		System.out.println("exit main func");
	}
	
}
