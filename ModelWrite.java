import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption; // 追加

public class ModelWrite {

    private Score score;
    private GameState gameState;
    private GameDifficulty gameDifficulty;
    private String filePathNormal = "./ReadFiles/Ranking/RANKINGNORMAL.txt";
    private String filePathHard = "./ReadFiles/Ranking/RANKINGHARD.txt";
    private String filePathENDLESS = "./ReadFiles/Ranking/RANKINGENDLESS.txt";

    ModelWrite(Score score, GameState gameState, GameDifficulty gameDifficulty) {
        this.score = score;
        this.gameState = gameState;
        this.gameDifficulty = gameDifficulty;
    }

    public Score getScore() {
        return score;
    }
    public GameState getGameState() {
        return gameState;
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

    private void processTimeElapsed() {
        // 時間経過に伴う処理をここに実装
        ;
    }
    private void processKeyInput(String event) {
        // キー入力に伴う処理をここに実装
        ;
    }
    private void processAlways() {
        // 常に実行する処理をここに実装
        writeToFile();
        gameState.setNextState();
    }

    private void writeToFile() {
        // スコアをファイルに追記保存
        try {
            String scoreLine = String.valueOf(score.getScore()) + System.lineSeparator();
            Files.write(
                switch (gameDifficulty.getCurrentSelection()) {
                    case GameDifficulty.NORMAL -> Path.of(filePathNormal);
                    case GameDifficulty.HARD -> Path.of(filePathHard);
                    case GameDifficulty.ENDLRESS -> Path.of(filePathENDLESS);
                    default -> throw new IllegalStateException("Unexpected value: " + gameDifficulty.getCurrentSelection());
                },
                scoreLine.getBytes(),
                StandardOpenOption.CREATE, // ファイルがなければ作成
                StandardOpenOption.APPEND  // 追記モード
            );
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
