public class ConsoleCursor {
    private final int firstX;
    private final int firstY;
    
    public ConsoleCursor(int x, int y) {
        this.firstX = x;
        this.firstY = y;

        System.out.print("\u001B[2J"); // 画面全体をクリア
    } 
    
    public void moveCursor(int x, int y){
        if(x < 0 || y < 0) {
            return ;
        }
        System.out.print(String.format("\u001B[%d;%dH", y, x));
    }

    public void moveFirstCursor() {
        moveCursor(firstX, firstY);
    }
}
