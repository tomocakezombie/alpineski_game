public class Arrow {

    private int x;
    private int y;
    private Direction direction;
    private int color;
    private int backGroundColor;
    private int width;
    private int flameCount = 0;
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
    }

    public void put(ConsoleView view) {
        int arrowX = x;
        int arrowY = y;

        if(upArrow.isEqual(direction)) {
            view.putString("＾", arrowX, arrowY - flameCount, color, backGroundColor);
        } else if(downArrow.isEqual(direction)) {
            view.putString("Ｖ", arrowX, arrowY + flameCount, color, backGroundColor);
        } else if(leftArrow.isEqual(direction)) {
            view.putString("＜", arrowX - flameCount, arrowY, color, backGroundColor);
        } else if(rightArrow.isEqual(direction)) {
            view.putString("＞", arrowX + flameCount, arrowY, color, backGroundColor);
        } else {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        // フレームの高進
        flameCount++;
        flameCount %= width; 
    }
    
}
