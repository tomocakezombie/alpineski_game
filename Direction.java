public class Direction {
    public static final int UP = 0;    // 上
    public static final int DOWN = 1;  // 下
    public static final int LEFT = 2;  // 左
    public static final int RIGHT = 3; // 右
    private int direction;

    public Direction(int direction) {
        if (direction < 0 || direction > 3) {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }
        this.direction = direction;
    }

    public boolean isEqual(Direction other) {
        if (other == null) {
            return false;
        }
        return this.direction == other.direction;
    }
}