public class ModelPreparePlaying{
    private GameState gameState;
    private ModelPlaying modelPlaying;
    private ConsoleView view;

    ModelPreparePlaying(GameState gameState, ModelPlaying modelPlaying, ConsoleView view) {
        // コンストラクタの実装
        // ゲームの初期化や状態設定を行う
        this.gameState = gameState;
        this.modelPlaying = modelPlaying;
        this.view = view;
    }

    public void process(String event) {
        // イベント処理の実装
        // 例えば、ゲーム開始の準備や初期設定を行う
        modelPlaying.resetState();
        modelPlaying.setGameDifficulty();
        gameState.setNextState();
        view.setResetBackGroundColor(modelPlaying.getBaseBackGroundColor());
    }
}