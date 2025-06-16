// 最小値から最大値までの乱数を生成するクラス
public class MyRandom {

    int min;
    int max;

    public MyRandom(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public MyRandom(){
        this.min = 0;
        this.max = 100; // Default range
    }

    public int getRandomValue() {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
