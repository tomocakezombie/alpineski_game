public class GameComment {
    private String comment;
    private int charColor = 255; // 緑色
    private int backGroundColor = 0; // 黒色
    private int commentX;
    private int commentY;
    private int viewFlame;
    private int viewFlameMax;

    public GameComment(String comment, int viewFlameMax) {
        this.comment = comment;
        this.viewFlameMax = viewFlameMax;
        this.viewFlame = viewFlameMax; // 初期値は最大フレーム数
    }

    public void setColor(int charColor, int backGroundColor) {
        this.charColor = charColor;
        this.backGroundColor = backGroundColor;
    }

    public void resetGameComment(){
        this.viewFlame = viewFlameMax; // 初期値は最大フレーム数
    }

    public void setPosition(int x, int y) {
        this.commentX = x;
        this.commentY = y;
    }

    public void countViewFlame(){
        if (viewFlame < viewFlameMax) {
            viewFlame++;
        }
    }

    public void resetViewFlame(){
        this.viewFlame = 0;
    }

    public boolean putComment(ConsoleView view) throws InterruptedException {
        if(viewFlame >= viewFlameMax){
            return false;
        }

        view.putString(comment, commentX, commentY, charColor, backGroundColor);
        // view.putString(comment, commentX, commentY, charColor);
        return true;
    }

    public boolean putComment(GameMapView view) throws InterruptedException {
        if(viewFlame >= viewFlameMax){
            return false;
        }

        // view.putString(comment, commentX, commentY, charColor, backGroundColor);
        view.putString(comment, commentX, commentY, charColor);
        return true;
    }
}