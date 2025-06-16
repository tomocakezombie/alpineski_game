public class GameMapView extends ConsoleView {
    
    private Player player;

    public static int HEIGHT = 60;
	public static int WIDTH = 150;
    
    public GameMapView(Player player){
        this.screen = new MapData(HEIGHT, WIDTH);
        this.player = player;
    }

	public GameMapView(){
		this.screen = new MapData(HEIGHT, WIDTH);
		this.player = null; // プレイヤーが設定されていない場合
	}

    public void putConsoleView(ConsoleView view) throws InterruptedException {
        int playerX = player.getPositionX();
        int playerY = player.getPositionY();

        // 画面の中心座標
        int centerX = ConsoleView.WIDTH / 2;
        int centerY = ConsoleView.HEIGHT / 4;

        // マップの表示開始位置
        int startX = playerX - centerX;
        int startY = playerY - centerY;

        // マップを描画
        view.putGameMap(startX, startY, this.screen);
       
        // 画面クリア
        clear();
    }

    public boolean setColor(int x, int y, int charColor, int backGroundColor) {
		if(x < 0 || x >= WIDTH) {
			return false;
		}
		if(y < 0 || y >= HEIGHT) {
			return false;
		}
	    if (charColor < 0 || charColor > ConsoleColor.COLORMAXINDEX) {
	        return false;
	    }
	    if(backGroundColor < 0 || backGroundColor > ConsoleColor.COLORMAXINDEX) {
	        return false;
	    }
	    
		this.screen.setColor(x, y, charColor);
		this.screen.setBackGroundColor(x, y, backGroundColor);
	    return true;
	}
	
	public boolean setColor(int x, int y, int charColor) {
		if(x < 0 || x >= WIDTH) {
			return false;
		}
		if(y < 0 || y >= HEIGHT) {
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
			return false;
		}
		if(y < 0 || y >= HEIGHT) {
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
			return ;
		}
		if(y >= HEIGHT || y < 0) {
			return ;
		}
		
		screen.setChar(x, y, c);
	}

    public void putString(String str, int x, int y, int charColor, int backGroundColor) {
		if(x >= WIDTH || x < 0) {
			return ;
		}
		if(y >= HEIGHT || y < 0) {
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
			return ;
		}
		if(y >= HEIGHT || y < 0) {
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
			return ;
		}
		if(y >= HEIGHT) {
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
			}
		}
		
	}




}