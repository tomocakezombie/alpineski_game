
public class GameDifficulty extends Select {
    public static final String NORMAL = "NORMAL";
    public static final String HARD = "HARD";
    public static final String ENDLRESS = "ENDLESS";
    public int positionX;
    public int positionY;
    public int charColor;
    public int backGroundColor;

    public GameDifficulty() {
        super(new String[]{NORMAL, HARD, ENDLRESS});
        this.positionX = 0;
        this.positionY = 0;
        this.charColor = 0; // Default color (white)
        this.backGroundColor = 255; // Default background color (black)
    }

    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public void setColor(int charColor, int backGroundColor) {
        this.charColor = charColor;
        this.backGroundColor = backGroundColor;
    }

    public void put(GameMapView view) throws InterruptedException {
        view.putString(ChangeChar.toZenkaku(getCurrentSelection()), positionX, positionY, charColor);
    }

    public void put(ConsoleView view) throws InterruptedException {
        view.putString(ChangeChar.toZenkaku(getCurrentSelection()), positionX, positionY, charColor, backGroundColor);
    }
}