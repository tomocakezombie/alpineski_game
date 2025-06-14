import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Model {

	// MVCの内Viewの変数
	private ConsoleView view;
	// MVCの内Controllerの変数
	private ConsoleController controller;
	// ゲームの状態を管理する変数
	private GameState gameState;
	// ゲームの難易度（1: Easy, 2: Normal, 3: Hard）
	private GameDifficulty gameDifficulty;
	// ゲームのスコアを保持する
	private Score score;
	
	// Modelの派生クラス
	private ModelPrepareStart modelPrepareStart;
	private ModelStart modelStart;
	private ModelPreparePlaying modelPreparePlaying;
	private ModelPlaying modelPlaying;
	private ModelWrite modelWrite;
	private ModelGameEnd modelGameEnd;

	public Model() {
		view = new ConsoleView();
		controller = new ConsoleController(this);
		// 初期状態をPREPAREPLAYINGに設定
		this.gameState = new GameState(); // コンストラクタで呼び出したときのみ、PRESTARTに遷移
		this.score = new Score();
		this.gameDifficulty = new GameDifficulty();
		this.modelPrepareStart = new ModelPrepareStart(gameState, view);
		this.modelStart = new ModelStart(view, gameState, gameDifficulty);
		this.modelPlaying = new ModelPlaying(view, gameState, gameDifficulty, score, controller);
		this.modelPreparePlaying = new ModelPreparePlaying(gameState, this.modelPlaying, view);
		this.modelWrite = new ModelWrite(score, gameState, gameDifficulty);
		this.modelGameEnd = new ModelGameEnd(view, gameState, score, gameDifficulty);
	}
	
	public synchronized void process(String event) throws IOException, InterruptedException {

		// ステータスを更新する。
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
	}
	
	public void run() throws IOException, InterruptedException{
		controller.run();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Model model = new Model();
		model.run();
	}
}
