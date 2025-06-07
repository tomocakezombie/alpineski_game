public class GameState {
    public static final int PREPARESTART = -1;
    public static final int START = 0;
    public static final int PREPAREPLAYING = 1;
    public static final int PLAYING = 2;
    public static final int WRITEFILE = 3;
    public static final int GAMEEND = 4;

    private int state;
    private final int MAXSTATE = 5;

    public GameState() {
        this.state = PREPARESTART;
    }

    public GameState(int initialState) {
        if (initialState < PREPARESTART || initialState >= MAXSTATE) {
            throw new IllegalArgumentException("Invalid initial state: " + initialState);
        }
        this.state = initialState;
    }

    // リセットしてもPREPARESTARTの状態に戻さない
    public void resetState() {
        this.state = START;
    }

    // 次のゲーム状態に進める
    public void setNextState(){
        this.state = (this.state + 1) % MAXSTATE;
    }

    // ゲームの状態を取得する
    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        if (state < PREPARESTART || state >= MAXSTATE) {
            throw new IllegalArgumentException("Invalid state: " + state);
        }
        this.state = state;
    }
}