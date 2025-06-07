public class MapData {
    private char[][] map;
	private int[][] mapCharColor;
	private int[][] mapBackGroundColor;

    private int height;
    private int width;

    MapData() {
        this.height = 0;
        this.width = 0;
        this.map = new char[0][0];
        this.mapCharColor = new int[0][0];
        this.mapBackGroundColor = new int[0][0];
    }

    MapData(int height, int width) {
        this.height = height;
        this.width = width;
        this.map = new char[height][width];
        this.mapCharColor = new int[height][width];
        this.mapBackGroundColor = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.map[i][j] = '　';
                this.mapCharColor[i][j] = 255;
                this.mapBackGroundColor[i][j] = 0;
            }
        }
    }

    MapData(char[][] map) {
        this.height = map.length;
        this.width = map[0].length;
        this.map = map;
        this.mapCharColor = new int[height][width];
        this.mapBackGroundColor = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.mapCharColor[i][j] = 255;
                this.mapBackGroundColor[i][j] = 0;
            }
        }
    }

    public boolean clear(int charColor, int backGroundColor) {
        if(charColor < 0 || charColor > ConsoleColor.COLORMAXINDEX) {
            return false;
        }
        if(backGroundColor < 0 || backGroundColor > ConsoleColor.COLORMAXINDEX) {
            return false;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.map[i][j] = '　';
                this.mapCharColor[i][j] = charColor;
                this.mapBackGroundColor[i][j] = backGroundColor;
            }
        }

        return true;
    }

    public boolean clearBackground(int backGroundColor) {
        if(backGroundColor < 0 || backGroundColor > ConsoleColor.COLORMAXINDEX) {
            return false;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.mapBackGroundColor[i][j] = backGroundColor;
            }
        }

        return true;
    }

    public boolean clearCharColor(int charColor) {
        if(charColor < 0 || charColor > ConsoleColor.COLORMAXINDEX) {
            return false;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.mapCharColor[i][j] = charColor;
            }
        }

        return true;
    }

    public boolean clear(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.map[i][j] = '　';
                this.mapCharColor[i][j] = ConsoleColor.COLORMAXINDEX;
                this.mapBackGroundColor[i][j] = 0;
            }
        }
        return true;
    }

    public boolean setColor(int x, int y, int charColor, int backGroundColor) {
        if (x < 0 || x >= width) {
            return false;
        }
        if (y < 0 || y >= height) {
            return false;
        }
        if (charColor < 0 || charColor >= ConsoleColor.COLORMAXINDEX) {
            return false;
        }
        if (backGroundColor < 0 || backGroundColor >= ConsoleColor.COLORMAXINDEX) {
            return false;
        }

        this.mapCharColor[y][x] = charColor;
        this.mapBackGroundColor[y][x] = backGroundColor;

        // System.out.println("setColor: (" + x + ", " + y + ") = " + charColor + ", " + backGroundColor);

        return true;
    }

    public boolean setColor(int x, int y, int charColor) {
        if (x < 0 || x >= width) {
            return false;
        }
        if (y < 0 || y >= height) {
            return false;
        }
        if (charColor < 0 || charColor >= ConsoleColor.COLORMAXINDEX) {
            return false;
        }

        mapCharColor[y][x] = charColor;

        return true;
    }

    public boolean setBackGroundColor(int x, int y, int backGroundColor) {
        if (x < 0 || x >= width) {
            return false;
        }
        if (y < 0 || y >= height) {
            return false;
        }
        if (backGroundColor < 0 || backGroundColor >= ConsoleColor.COLORMAXINDEX) {
            return false;
        }

        mapBackGroundColor[y][x] = backGroundColor;

        return true;
    }

    public char getChar(int x, int y) {
        if (x < 0 || x >= width) {
            return ' ';
        }
        if (y < 0 || y >= height) {
            return ' ';
        }
        return map[y][x];
    }

    public int getCharColor(int x, int y) {
        if (x < 0 || x >= width) {
            return 255;
        }
        if (y < 0 || y >= height) {
            return 255;
        }
        return mapCharColor[y][x];
    }

    public int getBackGroundColor(int x, int y) {
        if (x < 0 || x >= width) {
            return 0;
        }
        if (y < 0 || y >= height) {
            return 0;
        }
        return mapBackGroundColor[y][x];
    }

    public void setChar(int x, int y, char c) {
        if (x < 0 || x >= width) {
            return;
        }
        if (y < 0 || y >= height) {
            return;
        }
        map[y][x] = c;
    }

    public void setChar(int x, int y, char c, int charColor, int backGroundColor) {
        if (x < 0 || x >= width) {
            return;
        }
        if (y < 0 || y >= height) {
            return;
        }
        map[y][x] = c;
        mapCharColor[y][x] = charColor;
        mapBackGroundColor[y][x] = backGroundColor;
    }

    public void resize(int newHeight, int newWidth) {
        char[][] newMap = new char[newHeight][newWidth];
        int[][] newMapCharColor = new int[newHeight][newWidth];
        int[][] newMapBackGroundColor = new int[newHeight][newWidth];

        // 既存のデータをコピー
        for (int i = 0; i < Math.min(height, newHeight); i++) {
            for (int j = 0; j < Math.min(width, newWidth); j++) {
                newMap[i][j] = map[i][j];
                newMapCharColor[i][j] = mapCharColor[i][j];
                newMapBackGroundColor[i][j] = mapBackGroundColor[i][j];
            }
        }

        // 新しい領域を初期化
        for (int i = height; i < newHeight; i++) {
            for (int j = width; j < newWidth; j++) {
                newMap[i][j] = ' ';
                newMapCharColor[i][j] = ConsoleColor.COLORMAXINDEX-1;
                newMapBackGroundColor[i][j] = 0;
            }
        }

        this.map = newMap;
        this.mapCharColor = newMapCharColor;
        this.mapBackGroundColor = newMapBackGroundColor;
        this.height = newHeight;
        this.width = newWidth;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void showDebugInfo(){
        System.out.println("mapData Char:");
        for(int i = 0;i < height;i++){
            for(int j = 0;j < width;j++){
                System.out.print(map[i][j]+ ",");
            }
            System.out.println();
        }
        System.out.println("mapCharColor:");
        for(int i = 0;i < height;i++){
            for(int j = 0;j < width;j++){
                System.out.print(mapCharColor[i][j]+",");
            }
            System.out.println();
        }
        System.out.println("mapBackGroundColor:");
        for(int i = 0;i < height;i++){
            for(int j = 0;j < width;j++){
                System.out.print(mapBackGroundColor[i][j]+",");
            }
            System.out.println();
        }
    }

}
