import java.util.Random;
import java.util.LinkedList;

// 雪崩を管理するクラス
public class Avalanche {
    private Random random;
    private double probability; // 雪崩が起きる確率（0.0～1.0）
    private boolean isAvalanching = false; // 雪崩が起きているかどうか
    private boolean isDangerous = false; // 雪崩の前兆フラグ
    private int dangerTime;
    private int dangerMaxTime = 60; // 雪崩の前兆が続く最大フレーム数
    private LinkedList<Obstacle> obstacles;
    private int minX;
    private int maxX;
    private int width = 30;
    private int backgroundColor; // 雪崩の背景色
    private boolean placeLeft;

    // 雪崩が発生している最大フレーム数
    private int maxTime = 90;
    // 雪崩中のフレーム
    private int time;

    public Avalanche(double probability, LinkedList<Obstacle> obstacles) {
        this.random = new Random();
        this.probability = probability;
        this.obstacles = obstacles;
        time = 0;
    }

    public void setMinMax(int min, int max){
        this.minX = min;
        this.maxX = max;
    }

    public int setWidth(int width) {
        this.width = width;
        return this.width;
    }

    public int getTime() {
        return time;
    }

    public boolean isPlaceLeft() {
        return placeLeft;
    }

    public int getMaxX() {
        return maxX;
    }
    public int getMinX() {
        return minX;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
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
            isAvalanching = true;
            return true;
        }

        return false;
    }

    public void putAvalanche() {
        int startX = placeLeft ? minX : maxX - width;
        for(int i = startX; i < width + startX; i++) {
            if(i > maxX) break;
            Obstacle obstacle = new Obstacle('＊', i, 0, 0, 1, 0, backgroundColor);
            obstacle.setAttackPower(999);
            obstacles.add(obstacle);
        }
    }

    public void endAvalanche() {
        // 雪崩を終了する
        isAvalanching = false;
    }

    // 雪崩が起きるかどうかを判定し、発生している場合は障害物を配置する関数
    public boolean runAvalanche() {
        
        // すでに雪崩中なら障害物を配置
        if (isAvalanching) {
            // 雪崩の時間が終了したら終了
            if (time > maxTime) {
                endAvalanche();
                time = 0;
                return false;
            }
            putAvalanche();
            time++;
            return true;
        }

        // 危険予告中
        if (isDangerous) {
            dangerTime++;
            if (dangerTime >= dangerMaxTime) {
                // 前兆が終わったら雪崩開始
                isAvalanching = true;
                isDangerous = false;
                dangerTime = 0;
                putAvalanche();
                time = 1;
                return true;
            }
            // まだ前兆中
            return false;
        }

        // 雪崩が起きるか判定（前兆開始）
        if (random.nextDouble() < probability) {
            isDangerous = true;
            dangerTime = 0;
            // ここで警告表示などを行う
            placeLeft = random.nextDouble() <= 0.5; // 左側に雪崩が起きるかどうか
            return false;
        }

        return false;
    }

    public boolean isDangerous() {
        return isDangerous;
    }

    public boolean isAvalanching() {
        return isAvalanching;
    }

    public void reset(){
        isAvalanching = false;
        isDangerous = false;
        dangerTime = 0;
        time = 0;
        obstacles.clear();
        placeLeft = random.nextDouble() <= 0.5; // 初期状態で左側に雪崩が起きるかどうか
    }
}
