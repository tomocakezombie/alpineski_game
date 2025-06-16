public class Obstacle  {
    private int positionX;
	private int positionY;
	private int movementX;
	private int movementY;
	private int color;
	private char icon;
	private int backGroundColor;
	private AttackPower attackPower;
	
	public Obstacle(char c, int positionX, int positionY, int movementX, int movementY, int color, int backGroundColor) {
		super();
		this.positionX = positionX;
		this.positionY = positionY;
		this.movementX = movementX;
		this.movementY = movementY;
		this.icon = c;
		this.color = color;
		this.backGroundColor = backGroundColor;
		attackPower = new AttackPower(1); // デフォルトの攻撃力
	}

	public void changeIcon(char icon){
		this.icon = icon;
	}

	public void setAttackPower(AttackPower attackPower) {
		this.attackPower = attackPower;
	}

	public AttackPower getAttackPower() {
		return attackPower;
	}
	
	// 弾丸の状態を更新する
	public void update() {
		positionX += movementX;
		positionY += movementY;
		// 画面外に出たら非アクティブにする
	}
	
	public boolean isActive() {
		if(positionX < 0|| positionX >= GameMapView.WIDTH) {
			return false;
		}
		if(positionY < 0 || positionY >= GameMapView.HEIGHT) {
			return false;
		}
		
		return true;
	}
	
	public void put(GameMapView view) throws InterruptedException {
		// 画面外に出たら非アクティブにする
		view.putChar(icon, positionX, positionY);
		view.setColor(this.positionX, this.positionY, this.color, this.backGroundColor);
	}

    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }
}
