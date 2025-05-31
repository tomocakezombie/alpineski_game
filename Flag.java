public class Flag {
    private int positionX;
    private int positionY;
    private int color;
    private char icon;
    private char line;
    private int lineLength = 10;
    private int backGroundColor;
    private boolean isFinish = false;

    public Flag(char c, char line, int positionX, int positionY, int lineLength, int color, int backGroundColor) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.icon = c;
        this.line = line;
        this.color = color;
        this.lineLength = lineLength;
        this.backGroundColor = backGroundColor;
    }

    public void changeIcon(char icon) {
        this.icon = icon;
    }

    public void put(ConsoleView view) throws InterruptedException {
        String toPutString ="";
        toPutString += icon;
        for(int i = 0;i < lineLength;i++){
            toPutString += line;
        }
        toPutString += icon;
        view.putString(toPutString, positionX, positionY, this.color, this.backGroundColor);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void update(){
        positionY -= 1;
    }

    public int getLineLength() {
        return lineLength;
    }

    public boolean isActive() {
        if(positionX < 0 || positionX >= ConsoleView.WIDTH) {
            return false;
        }
        if(positionY < 0 || positionY >= ConsoleView.HEIGHT) {
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
