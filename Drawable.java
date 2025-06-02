public abstract class Drawable {
    protected int x;
    protected int y;
    protected char icon;
    protected int charColor;
    protected int backGroundColor;

    public Drawable(int x, int y, int charColor, int backGroundColor) {
        this.x = x;
        this.y = y;
        this.charColor = charColor;
        this.backGroundColor = backGroundColor;
    }

    public Drawable(int x, int y, char icon, int charColor, int backGroundColor) {
        this.x = x;
        this.y = y;
        this.icon = icon;
        this.charColor = charColor;
        this.backGroundColor = backGroundColor;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setColor(int charColor, int backGroundColor) {
        this.charColor = charColor;
        this.backGroundColor = backGroundColor;
    }

    public void setIcon(char icon){
        this.icon = icon;
    }

    public abstract void put(ConsoleView view) throws InterruptedException;
}