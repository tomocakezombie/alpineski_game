import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.io.*;

public class ConsoleController implements ActionListener{
	
	private final static int DELAY = 100;
	private final static int MIN_DELAY = 40; // 最小遅延時間
	private Model model;
	private Timer timer;
	private int nowDelay = DELAY; // 現在の遅延時間
	
	public ConsoleController(Model m) {
		model = m;
		timer = new Timer(DELAY, this);
	}
	
	public void run() throws IOException, InterruptedException {
		timer.start();
		BufferedReader reader
			= new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		while ( (line = reader.readLine() ) != null) {
			if (line.isEmpty()) {
				line = "ENTER";
			}
			if (line.trim().isEmpty()) {
				line = "SPACE";
			}
			model.process(line);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			model.process("TIME_ELAPSED");
		} catch (InterruptedException e1) {
			System.err.println("InterruptedExceptionが発生しました: " + e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e2) {
			System.err.println("InterruptedExceptionが発生しました: " + e2.getMessage());
			e2.printStackTrace();
		}
	}
	
	public boolean setSubDelay() {
		if(nowDelay <= MIN_DELAY) {
			return false; // 遅延時間が最小値以下の場合は変更しない
		}
		nowDelay = nowDelay - 1;
        timer.setDelay(nowDelay);
		return true;
    }

	public void resetDelay() {
		nowDelay = DELAY; // 遅延時間を初期値にリセット
		timer.setDelay(nowDelay);
	}
}
