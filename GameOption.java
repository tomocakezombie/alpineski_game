public class GameOption {

    public enum GameOptionType{
        NORMAL, HARD, ENDLESS
    }

    private final int NORMALOBSTACLE = 2;
    private final int NORMALFLAG = 35;

    private final int HARDOBSTACLE = 4;
    private final int HARDFLAG = 30;

    private final int EXTREMEOBSTACLE = 2;
    private final int EXTREMEFLAG = 35;

    private final int intervalFlag;
    private final int obstacleFrequency;

    public GameOption(GameOptionType type) {
        switch (type) {
            case NORMAL:
                this.intervalFlag = NORMALFLAG;
                this.obstacleFrequency = NORMALOBSTACLE;
                break;
            case HARD:
                this.intervalFlag = HARDFLAG;
                this.obstacleFrequency = HARDOBSTACLE;
                break;
            case ENDLESS:
                this.intervalFlag = EXTREMEFLAG;
                this.obstacleFrequency = EXTREMEOBSTACLE;
                break;
            default:
                throw new IllegalArgumentException("Unknown GameOptionType: " + type);
        }
    }

    public int getIntervalFlag() {
        return intervalFlag;
    }

    public int getObstacleFrequency() {
        return obstacleFrequency;
    }
}
