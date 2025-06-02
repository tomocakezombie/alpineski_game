public class Flag extends Drawable{
    
    private char line;
    private int lineLength = 10;
    private boolean isFinish = false;

    public Flag(char c, char line, int positionX, int positionY, int lineLength, int color, int backGroundColor) {
        super(positionX, positionY, c, color, backGroundColor);
        this.line = line;
    }

    @Override
    public void put(ConsoleView view) throws InterruptedException {
        String toPutString = "";
        toPutString += icon;
        for (int i = 0; i < lineLength; i++) {
            toPutString += line;
        }
        toPutString += icon;
        view.putString(toPutString, x, y, charColor, backGroundColor);
    }

    public int getPositionX() {
        return x;
    }

    public int getPositionY() {
        return y;
    }

    public void update(){
        y -= 1;
    }

    public int getLineLength() {
        return lineLength;
    }

    public boolean isActive() {
        if (x < 0 || x >= GameMapView.WIDTH) {
            return false;
        }
        if (y < 0 || y >= GameMapView.HEIGHT) {
            return false;
        }
        return true;
    }

    public boolean getIsFinish() {
        return isFinish;
    }

    public void changeIsFinish() {
        this.isFinish = true;
    }


}
