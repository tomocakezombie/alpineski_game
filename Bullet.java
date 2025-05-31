import java.util.Random;

public class Bullet {
	private int positionX;
	private int positionY;
	private int movementX;
	private int movementY;
	private int color;
	private char icon;
	private int backGroundColor;
	
	public Bullet(char c, int positionX, int positionY, int movementX, int movementY, int color, int backGroundColor) {
		super();
		this.positionX = positionX;
		this.positionY = positionY;
		this.movementX = movementX;
		this.movementY = movementY;
		this.icon = c;
		this.color = color;
		this.backGroundColor = backGroundColor;
	}

	public void changeIcon(char icon){
		this.icon = icon;
	}
	
	// 弾丸の状態を更新する
	public void update() {
		positionX += movementX;
		positionY += movementY;
		// 画面外に出たら非アクティブにする
	}
	
	public boolean isActive() {
		if(positionX < 0|| positionX >= ConsoleView.WIDTH) {
			return false;
		}
		if(positionY < 0 || positionY >= ConsoleView.HEIGHT) {
			return false;
		}
		
		return true;
	}
	
	public void put(ConsoleView view) throws InterruptedException {
		// 画面外に出たら非アクティブにする
		view.putChar(icon, positionX, positionY);
		view.setColor(this.positionX, this.positionY, this.color);
	}

	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
		return positionY;
	}

}
