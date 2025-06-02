public class GameMapView extends ConsoleView {
    
    private Player player;

    public static int HEIGHT = 60;
	public static int WIDTH = 150;
    
    public GameMapView(Player player){
        this.screen = new MapData(HEIGHT, WIDTH);
        this.player = player;
    }

    public void putConsoleView(ConsoleView view) throws InterruptedException {
        int playerX = player.getPositionX();
        int playerY = player.getPositionY();

        // 画面の中心座標
        int centerX = ConsoleView.WIDTH / 2;
        int centerY = ConsoleView.HEIGHT / 4;
        // System.out.println("Center position: (" + centerX + ", " + centerY + ")");

        // マップの表示開始位置
        int startX = playerX - centerX;
        int startY = playerY - centerY;
        // System.out.println("Player position: (" + playerX + ", " + playerY + ")");

        // マップを描画
        view.putGameMap(startX, startY, this.screen);
        // System.out.println("Map drawn from (" + startX + ", " + startY + ") to (" + (startX + ConsoleView.WIDTH) + ", " + (startY + ConsoleView.HEIGHT) + ")");

        // 画面クリア
        clear();
    }

    //  下の処理は、ゴミみたいな処理である。  絶対直すべき処理 継承すべきじゃなかった。
    public boolean setColor(int x, int y, int charColor, int backGroundColor) {
		if(x < 0 || x >= WIDTH) {
			// System.out.println("setColor: x is out of bounds: " + x);
			return false;
		}
		if(y < 0 || y >= HEIGHT) {
			// System.out.println("setColor: y is out of bounds: " + y);
			return false;
		}
	    if (charColor < 0 || charColor > ConsoleColor.COLORMAXINDEX) {
	        return false;
	    }
	    if(backGroundColor < 0 || backGroundColor > ConsoleColor.COLORMAXINDEX) {
	        return false;
	    }
	    
	    // screen.setColor(x, y, charColor, backGroundColor);
		this.screen.setColor(x, y, charColor);
		this.screen.setBackGroundColor(x, y, backGroundColor);
	    return true;
	}
	
	public boolean setColor(int x, int y, int charColor) {
		if(x < 0 || x >= WIDTH) {
			System.out.println("setColor: x is out of bounds: " + x);
			return false;
		}
		if(y < 0 || y >= HEIGHT) {
			System.out.println("setColor: y is out of bounds: " + y);
			return false;
		}
	    if (charColor < 0 || charColor >= ConsoleColor.COLORMAXINDEX) {
	        return false;
	    }
	    
	    screen.setColor(x, y, charColor);
	    return true;
	} 

	public boolean setBackGroundColor(int x, int y, int backGroundColor) {
		if(x < 0 || x >= WIDTH) {
			System.out.println("setBackGroundColor: x is out of bounds: " + x);
			return false;
		}
		if(y < 0 || y >= HEIGHT) {
			System.out.println("setBackGroundColor: y is out of bounds: " + y);
			return false;
		}
	    if (backGroundColor < 0 || backGroundColor >= ConsoleColor.COLORMAXINDEX) {
	        return false;
	    }
	    
	    screen.setBackGroundColor(x, y, backGroundColor);
	    return true;
	}
	
	public void putChar(char c, int x, int y) {
		if(x >= WIDTH || x < 0) {
			// System.out.println("GameMapView putChar: x is out of bounds: " + x);
			return ;
		}
		if(y >= HEIGHT || y < 0) {
			// System.out.println("GameMapView putChar: y is out of bounds: " + y);
			return ;
		}
		
		screen.setChar(x, y, c);
	}

    public void putString(String str, int x, int y, int charColor, int backGroundColor) {
		if(x >= WIDTH || x < 0) {
			// System.out.println("putString: x is out of bounds: " + x);
			return ;
		}
		if(y >= HEIGHT || y < 0) {
			// System.out.println("putString: y is out of bounds: " + y);
			return ;
		}

		int startXIndex = 0;
		if(x < 0){
			startXIndex = x*-1;
		}

		for(int i = 0;i < str.length();i++) {
			if(x + i >= WIDTH) {
				break;
			}
			screen.setChar(x + i, y, str.charAt(i));
			screen.setColor(x + i, y, charColor, backGroundColor);
		}
	}

	public void putString(String str, int x, int y, int charColor) {
		if(x >= WIDTH || x < 0) {
			// System.out.println("putString: x is out of bounds: " + x);
			return ;
		}
		if(y >= HEIGHT || y < 0) {
			// System.out.println("putString: y is out of bounds: " + y);
			return ;
		}

		int startXIndex = 0;
		if(x < 0){
			startXIndex = x*-1;
		}

		for(int i = 0;i < str.length();i++) {
			if(x + i >= WIDTH) {
				break;
			}
			screen.setChar(x + i, y, str.charAt(i));
			screen.setColor(x + i, y, charColor);
		}
	}

	public void putMap(int x, int y, MapData map) {
		if(x >= WIDTH) {
			System.out.println("putMap: x is out of bounds: " + x);
			return ;
		}
		if(y >= HEIGHT) {
			System.out.println("putMap: y is out of bounds: " + y);
			return ;
		}

		int startMapXIndex = 0;
		int startMapYIndex = 0;

		if(x < 0){
			startMapXIndex = x*-1;
		}
		if(y < 0){
			startMapYIndex = y*-1;
		}

		if(map == null) {
			return ;
		}

		int mapWidth = Math.min(map.getWidth(), WIDTH);
		int mapHeight = Math.min(map.getHeight(), HEIGHT);

		for(int i = startMapYIndex;i < mapHeight;i++) {
			for(int j = startMapXIndex;j < mapWidth;j++) {
				screen.setChar(x + j, y + i, map.getChar(j, i));
				screen.setColor(x + j, y + i, map.getCharColor(j, i), map.getBackGroundColor(j, i));
				// System.out.println("Putting map char at (" + (x + j) + ", " + (y + i) + ") with char: " + map.getChar(j, i) + ", charColor: " + map.getCharColor(j, i) + ", backGroundColor: " + map.getBackGroundColor(j, i));
			}
		}
		
	}




}