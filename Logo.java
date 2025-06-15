// view mapをするクラス
public class Logo {
    Mapdata mapdata;
    int x;
    int y;

    public Logo(Mapdata mapdata, int x, int y) {
        this.mapdata = mapdata;
        this.x = x;
        this.y = y;
    }

    public void putMap(ConsoleView view){
        view.putMap(x, y, mapdata);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}
