public class Arrow {

    private int x;
    private int y;
    private Direction direction;
    private int color;
    private int backGroundColor;
    private int width;
    private Flame flameCount;
    private Direction upArrow;
    private Direction downArrow;
    private Direction leftArrow;
    private Direction rightArrow;

    public Arrow(int x, int y, Direction direction, int color, int backGroundColor, int width) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.color = color;
        this.backGroundColor = backGroundColor;
        this.width = width;

        this.upArrow = new Direction(Direction.UP);
        this.downArrow = new Direction(Direction.DOWN);
        this.leftArrow = new Direction(Direction.LEFT);
        this.rightArrow = new Direction(Direction.RIGHT);

        this.flameCount = new Flame(width);
    }

    public void move(){
        // フレームの高進
        flameCount.incrementFlameCount();
    }

    public void put(ConsoleView view) {
        int arrowX = x;
        int arrowY = y;

        int flameValue = flameCount.getFlameCount();
        if(upArrow.isEqual(direction)) {
            view.putString("＾", arrowX, arrowY - flameValue, color, backGroundColor);
        } else if(downArrow.isEqual(direction)) {
            view.putString("Ｖ", arrowX, arrowY + flameValue, color, backGroundColor);
        } else if(leftArrow.isEqual(direction)) {
            view.putString("＜", arrowX - flameValue, arrowY, color, backGroundColor);
        } else if(rightArrow.isEqual(direction)) {
            view.putString("＞", arrowX + flameValue, arrowY, color, backGroundColor);
        } else {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }
    
}
