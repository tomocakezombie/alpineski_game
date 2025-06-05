import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Model {

	private ConsoleView view;
	private ConsoleController controller;
	private LinkedList<Bullet> bullets;
	private GameState gameState;
	private GameDifficulty gameDifficulty; // ゲームの難易度（1: Easy, 2: Normal, 3: Hard）
	private Score score;
	
	// 新しいクラス
	private ModelPrepareStart modelPrepareStart;
	private ModelStart modelStart;
	private ModelPreparePlaying modelPreparePlaying;
	private ModelPlaying modelPlaying;
	private ModelWrite modelWrite;
	private ModelGameEnd modelGameEnd;

	public Model() {
		view = new ConsoleView();
		// System.out.println("view finished");
		controller = new ConsoleController(this);
		// System.out.println("controller finished");
		this.bullets = new LinkedList<Bullet>();
		// this.gameState = new GameState(GameState.START);
		this.gameState = new GameState(); // 初期状態をPREPAREPLAYINGに設定
		this.score = new Score();

		// String[] gameDifficultyStrings = {"NORMAL", "HARD", "ENDLESS"};
		// this.gameDifficulty = new GameDifficulty(gameDifficultyStrings);
		this.gameDifficulty = new GameDifficulty();

		this.modelPrepareStart = new ModelPrepareStart(gameState, view);
		this.modelStart = new ModelStart(view, gameState, gameDifficulty);

		this.modelPlaying = new ModelPlaying(view, gameState, gameDifficulty, score, controller);
		this.modelPreparePlaying = new ModelPreparePlaying(gameState, this.modelPlaying, view);

		this.modelWrite = new ModelWrite(score, gameState, gameDifficulty);
		this.modelGameEnd = new ModelGameEnd(view, gameState, score, gameDifficulty);
		// this.modelGameEnd = new ModelGameEnd(ConsoleView view, GameState gameState, Score score)
		
	}
	
	public synchronized void process(String event) throws IOException, InterruptedException {

		// ここでは実際には描画しないでステータスを更新する。
		switch( gameState.getState() ) {
			case GameState.PREPARESTART:
				modelPrepareStart.process(event);
				break;
			case GameState.START:
				modelStart.process(event);
				break;
			case GameState.PREPAREPLAYING:
				modelPreparePlaying.process(event);
				break;
			case GameState.PLAYING:
				modelPlaying.process(event);
				break;
			case GameState.WRITEFILE:
				modelWrite.process(event);
			case GameState.GAMEEND:
				modelGameEnd.process(event);
				break;
			default:
				break;
		}

		// 実際に描画
		view.update();	
		// System.out.println(gameState.getState());
	}
	
	public void run() throws IOException, InterruptedException{
		controller.run();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Model model = new Model();
		model.run();
	}
	


}
