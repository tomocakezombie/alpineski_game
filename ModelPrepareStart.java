import java.io.Console;
import java.io.IOException;

public class ModelPrepareStart {
    private GameState gameState;
    private ConsoleView view;
    private MapData mapData;
    private int flameCount = 0;
    private int flameCountMax = 40; // 最大フレーム数
    
    public ModelPrepareStart(GameState gameState, ConsoleView view) {
        this.gameState = gameState;
        this.view = view;

        // ReadFile readFile = new ReadFile("./ReadFiles/")
    }

    public void process(String event) throws IOException, InterruptedException {

        if(event.equals("TIME_ELAPSED")) { // 時間経過イベント
            processTimeElapsed();
        }
        else { // キー入力イベント
            processKeyInput(event);
        }

        // 常に実行する処理
        processAlways();
    }

    private void processTimeElapsed() throws IOException, InterruptedException {
        // 時間経過に応じた処理を実行
        // 例えば、ゲームの状態を更新するなど
        // System.out.println("Time elapsed, updating game state...");
        // ここに時間経過に伴う処理を追加

        if(flameCount >= flameCountMax) {
            // フレーム数が最大に達した場合、ゲーム状態を更新
            gameState.setNextState();
            // System.out.println("Game state changed to PLAYING");
            // ここでゲームの状態をPLAYINGに変更する処理を追加
        }


        flameCount++;
    }

    private void processKeyInput(String event) throws IOException, InterruptedException {
        // キー入力に応じた処理を実行
        // 例えば、ゲームの状態を変更するなど
        // System.out.println("Key input received: " + event);
        // ここにキー入力に伴う処理を追加
        if(event.equals("ENTER")){
            gameState.setNextState();
            // System.out.println("Game state changed to START");
            return ;
        }
    }

    private void processAlways() throws IOException, InterruptedException {
        // 常に実行する処理をここに追加
        // 例えば、ゲームの状態を更新するなど
        // ここに常に実行する処理を追加
        view.setResetBackGroundColor(0);

        if(10 < flameCount & flameCount < 30){
            view.putString("企画者：　ｔ２３ｃｓ００７　榎尾　圭登",ConsoleView.WIDTH / 2 - 5, ConsoleView.HEIGHT / 2, 255, 0);
            view.putString("開発者：　ｔ２３ｃｓ０１４　神田　智輝", ConsoleView.WIDTH / 2 - 5, ConsoleView.HEIGHT / 2 + 3, 255, 0);
        }
     }
}
