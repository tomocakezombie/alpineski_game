import java.util.Iterator;

public class ConsoleView{
	protected MapData screen;
	
	// private Model model;
	private ConsoleCursor consoleCursor;
	protected int resetCharColor;
	protected int resetBackGroundColor;
	
	public static int HEIGHT = 40;
	public static int WIDTH = 80;
	// public static int WIDTH = 100;

	public ConsoleView() {
		super();
		this.screen = new MapData(HEIGHT, WIDTH);
		this.consoleCursor = new ConsoleCursor(0, 0);
		this.resetCharColor = ConsoleColor.COLORMAXINDEX - 1;
		this.resetBackGroundColor = 0;
		
		clear();
	}
	
	// すべて'_'で初期化する
	public void clear() {
		this.screen.clear(resetCharColor, resetBackGroundColor);
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
	    
	    screen.setColor(x, y, charColor, backGroundColor);
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


	public void paint() throws InterruptedException {

		consoleCursor.moveFirstCursor();

		StringBuffer s = new StringBuffer();
		for(int i = 0;i < HEIGHT;i++) {
			for(int j = 0;j < WIDTH;j++) {
				s.append("\u001B[38;5;" + screen.getCharColor(j, i) + "m" + 
						 "\u001B[48;5;" + screen.getBackGroundColor(j, i) + "m" + 
						  String.valueOf(screen.getChar(j, i)) + "\u001B[0m");
			}
			s.append("\n");
		}

		System.out.println(s);
	
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

	public void putGameMap(int startX, int startY, MapData map) {


		MapData targetMap = new MapData(HEIGHT, WIDTH);

		for(int i = 0;i < HEIGHT;i++){
			for(int j = 0;j < WIDTH;j++){
				targetMap.setChar(j, i, map.getChar(startX + j, startY + i));
				targetMap.setColor(j, i, map.getCharColor(startX + j, startY + i), map.getBackGroundColor(startX + j, startY + i));
			}
		}

		for(int i = 0;i < HEIGHT;i++) {
			for(int j = 0;j < WIDTH;j++) {
				this.screen.setChar(j, i, targetMap.getChar(j, i));
				this.screen.setColor(j, i, targetMap.getCharColor(j, i), targetMap.getBackGroundColor(j, i));
			}
		}
		
	}

	public void update() throws InterruptedException {
		paint();
		clear();
	}

	public void setResetCharColor(int resetCharColor) {
		this.resetCharColor = resetCharColor;
	}
	
	public void setResetBackGroundColor(int resetBackGroundColor) {
		this.resetBackGroundColor = resetBackGroundColor;
	}

	public int getResetCharColor() {
		return resetCharColor;
	}
	
}
