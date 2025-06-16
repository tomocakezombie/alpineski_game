import java.io.Console;
import java.io.IOException;

public class ModelPrepareStart {
    private GameState gameState;
    private ConsoleView view;
    private MapData mapData;
    private int flameCount = 0;
    private int flameCountMax = 42; // 最大フレーム数
    
    public ModelPrepareStart(GameState gameState, ConsoleView view) {
        this.gameState = gameState;
        this.view = view;
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
        // ここに時間経過に伴う処理を追加

        if(flameCount >= flameCountMax) {
            // フレーム数が最大に達した場合、ゲーム状態を更新
            gameState.setNextState();
        }


        flameCount++;
    }

    private void processKeyInput(String event) throws IOException, InterruptedException {
        // ここにキー入力に伴う処理を追加
        if(event.equals("ENTER")){
            gameState.setNextState();
            return ;
        }
    }

    private void processAlways() throws IOException, InterruptedException {
        view.setResetBackGroundColor(0);

        if(flameCount < flameCountMax/2){
            view.putString("企画者：　ｔ２３ｃｓ００７　榎尾　圭登", ConsoleView.WIDTH / 2 - 10, ConsoleView.HEIGHT / 2 - 1, 234+flameCount, 0);
            view.putString("開発者：　ｔ２３ｃｓ０１４　神田　智輝", ConsoleView.WIDTH / 2 - 10, ConsoleView.HEIGHT / 2 + 1, 234+flameCount, 0);    
        } else {
            view.putString("企画者：　ｔ２３ｃｓ００７　榎尾　圭登", ConsoleView.WIDTH / 2 - 10, ConsoleView.HEIGHT / 2 - 1, 255 - (flameCount - flameCountMax/2), 0);
            view.putString("開発者：　ｔ２３ｃｓ０１４　神田　智輝", ConsoleView.WIDTH / 2 - 10, ConsoleView.HEIGHT / 2 + 1, 255 - (flameCount - flameCountMax/2), 0);    
        } 
    }
}
