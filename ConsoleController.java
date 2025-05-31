import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.io.*;

public class ConsoleController implements ActionListener{
	
	private final static int DELAY = 100;
	private Model model;
	private Timer timer;
	
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
				System.out.println("エンターキーが押されました");
				line = "ENTER";
			}
			if (line.trim().isEmpty()) {
				System.out.println("スペースのみが入力されました");
				line = "SPACE";
			}
			model.process(line);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			model.process("TIME_ELAPSED");
		} catch (InterruptedException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (IOException e2) {
			System.err.println("InterruptedExceptionが発生しました: " + e2.getMessage());
			e2.printStackTrace();
		}
	}
}
