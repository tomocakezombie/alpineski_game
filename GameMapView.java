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
			System.out.println("setColor: x is out of bounds: " + x);
			return false;
		}
		if(y < 0 || y >= HEIGHT) {
			System.out.println("setColor: y is out of bounds: " + y);
			return false;
		}
	    if (charColor < 0 || charColor > ConsoleColor.COLORMAXINDEX) {
	        return false;
	    }
	    if(backGroundColor < 0 || backGroundColor > ConsoleColor.COLORMAXINDEX) {
	        return false;
	    }
	    
	    screen.setColor(x, y, charColor, backGroundColor);
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
	
	public void putChar(char c, int x, int y) {
		if(x >= WIDTH || x < 0) {
			System.out.println("putChar: x is out of bounds: " + x);
			return ;
		}
		if(y >= HEIGHT || y < 0) {
			System.out.println("putChar: y is out of bounds: " + y);
			return ;
		}
		
		screen.setChar(x, y, c);
	}

    public void putString(String str, int x, int y, int charColor, int backGroundColor) {
		if(x >= WIDTH || x < 0) {
			System.out.println("putString: x is out of bounds: " + x);
			return ;
		}
		if(y >= HEIGHT || y < 0) {
			System.out.println("putString: y is out of bounds: " + y);
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




}