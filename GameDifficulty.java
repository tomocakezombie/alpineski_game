
public class GameDifficulty extends Select {
    public static final String NORMAL = "NORMAL";
    public static final String HARD = "HARD";
    public static final String ENDLRESS = "ENDLESS";

    public GameDifficulty() {
        super(new String[]{NORMAL, HARD, ENDLRESS});
    }
}