import java.util.Random;

// 雪崩を管理するクラス
public class Avalanche {
    private Random random;
    private double probability; // 雪崩が起きる確率（0.0～1.0）
    private boolean isAvalanching = false; // 雪崩が起きているかどうか

    public Avalanche(double probability) {
        this.random = new Random();
        this.probability = probability;
    }

    /**
     * 雪崩が起きるかどうかを判定する
     * @return 雪崩が起きればtrue、起きなければfalse
     */
    public boolean isHappenAvalanche() {
        // 既に雪崩が起きている場合、もう一度雪崩は発生しない。
        if(isAvalanching){
            return false;
        }

        // 確率に基づいて雪崩が起きるかどうかを判定
        if(random.nextDouble() < probability){
            return true;
        }

        return false;
    }

    public void 
}
