public class Score {
    private int score;
    private int charColor = 255; // 緑色
    private int backGroundColor = 0; // 黒色
    private int scoreX;
    private int scoreY;

    public Score() {
        this.score = 0;
    }

    public void setColor(int charColor, int backGroundColor) {
        this.charColor = charColor;
        this.backGroundColor = backGroundColor;
    }

    public void setPosition(int x, int y) {
        this.scoreX = x;
        this.scoreY = y;
    }

    public void addScore(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        this.score = 0;
    }

    public void put(GameMapView view) throws InterruptedException {
        String scoreString = "";
        int temp = score;

        // 数字を全角文字に変換
        while (temp > 0) {
            char fullWidthDigit = (char) ('０' + (temp % 10)); // 全角数字に変換
            scoreString = fullWidthDigit + scoreString;
            temp /= 10;
        }

        // スコアが0の場合は全角の'０'を表示
        if (scoreString.isEmpty()) {
            scoreString = "０";
        }

        scoreString += "ｍ";

        // view.putString(scoreString, ConsoleView.WIDTH - 10, 3, 1, 0);
        view.putString(scoreString, scoreX, scoreY, charColor, backGroundColor);
    }

    public void put(ConsoleView view) throws InterruptedException {
        String scoreString = "";
        int temp = score;

        // 数字を全角文字に変換
        while (temp > 0) {
            char fullWidthDigit = (char) ('０' + (temp % 10)); // 全角数字に変換
            scoreString = fullWidthDigit + scoreString;
            temp /= 10;
        }

        // スコアが0の場合は全角の'０'を表示
        if (scoreString.isEmpty()) {
            scoreString = "０";
        }

        scoreString += "ｍ";

        // view.putString(scoreString, ConsoleView.WIDTH - 10, 3, 1, 0);
        view.putString(scoreString, scoreX, scoreY, charColor, backGroundColor);
    }
}
