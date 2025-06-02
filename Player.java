public class Player {
    private int hitpoint;
    private int maxHitpoint;
    private int positionX;
    private int positionY;
    private int startPositionX;
    private int startPositionY;
    private char playerChar;
    private int playerCharColor;
    private int playerBackGroundColor;
    private int playerDamageColor = 1;
    private int maxPositionX;
    private int maxPositionY;
    private int minPositionX;
    private int minPositionY;


    private char hitpointChar = '＄';
    private int hitpointCharColor = 1;
    private int hitpointBackGroundColor = 0;

    Player(int hitpoint, int positionX, int positionY, char playerChar, int minPositionX, int minPositionY, int maxPositionX, int maxPositionY) {
        if (hitpoint < 0) {
            throw new IllegalArgumentException("Hitpoint cannot be negative: " + hitpoint);
        }
        if( positionX < minPositionX || positionX >= maxPositionX) {
            throw new IllegalArgumentException("Invalid positionX: " + positionX + ", must be between " + minPositionX + " and " + (maxPositionX - 1));
        }
        if( positionY < minPositionY || positionY >= maxPositionY) {
            throw new IllegalArgumentException("Invalid positionY: " + positionY + ", must be between " + minPositionY + " and " + (maxPositionY - 1));
        }
        this.hitpoint = hitpoint;
        this.maxHitpoint = hitpoint; // 初期値は最大ヒットポイントと同じ
        this.positionX = positionX;
        this.positionY = positionY;
        this.startPositionX = positionX;
        this.startPositionY = positionY;
        this.playerChar = playerChar;
        this.minPositionX = minPositionX;
        this.minPositionY = minPositionY;
        this.maxPositionX = maxPositionX;
        this.maxPositionY = maxPositionY;
        this.playerDamageColor = playerDamageColor;
    }

    public void resetPosition() {
        this.positionX = startPositionX;
        this.positionY = startPositionY;
    }

    public void setPlayerDamageColor(int playerDamageColor) {
        if (playerDamageColor < 0 || playerDamageColor > ConsoleColor.COLORMAXINDEX) {
            throw new IllegalArgumentException("Invalid damage color index: " + playerDamageColor);
        }
        this.playerDamageColor = playerDamageColor;
    }

    public void setplayerCharColor(int playerCharColor) {
        if (playerCharColor < 0 || playerCharColor > ConsoleColor.COLORMAXINDEX) {
            throw new IllegalArgumentException("Invalid character color index: " + playerCharColor);
        }
        this.playerCharColor = playerCharColor;
    }

    public void setPlayerBackGroundColor(int playerBackGroundColor) {
        if (playerBackGroundColor < 0 || playerBackGroundColor > ConsoleColor.COLORMAXINDEX) {
            throw new IllegalArgumentException("Invalid background color index: " + playerBackGroundColor);
        }
        this.playerBackGroundColor = playerBackGroundColor;
    }

    public int getHitpoint() {
        return hitpoint;
    }

    public void resetHitpoint() {
        this.hitpoint = maxHitpoint; // ヒットポイントを最大値にリセット
    }

    public void addX() {
        // 右端より左なら右へ
        if (positionX < maxPositionX - 1) {
            positionX++;
        }
    }
    public void addY() {
        if (positionY < maxPositionY - 1) {
            positionY++;
        }
    }
    public void subX() {
        System.out.println("positionX: " + positionX + ", minPositionX: " + minPositionX);
        if (positionX > minPositionX) {
            positionX--;
        }
    }
    public void subY() {
        if (positionY > minPositionY) {
            positionY--;
        }
    }

    public void damage() {
        hitpoint = Math.max(hitpoint - 1, 0);
    }

    public void heal() {
        hitpoint = Math.min(hitpoint + 1, maxHitpoint);
        hitpoint++;
    }

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }

    public void putPlayer(GameMapView view) throws InterruptedException {
        // System.out.println("Player");
        view.putChar(playerChar, positionX, positionY);
        view.setColor(positionX, positionY, playerCharColor, playerBackGroundColor);
        // view.setColor(positionX, positionY, playerCharColor);
        // view.setBackGroundColor(positionX, positionY, playerBackGroundColor);
    }

    public void putPlayerDamage(GameMapView view) throws InterruptedException {
        // プレイヤーがダメージを受けたときの表示
        // System.out.println("Putting player at (" + positionX + ", " + positionY + ") with damage color: " + playerDamageColor + " and background color: " + playerBackGroundColor);
        // System.out.println("Player");
        view.putChar(playerChar, positionX, positionY);
        view.setColor(positionX, positionY, playerDamageColor, playerBackGroundColor);
    }

    // 画面に色を一時的に変更して表示
    public void putPlayer(GameMapView view, int charColor, int backGroundColor) throws InterruptedException {
        // System.out.println("Putting player at (" + positionX + ", " + positionY + ") with charColor: " + charColor + " and backGroundColor: " + backGroundColor);
        // System.out.println("Player");
        view.putChar(playerChar, positionX, positionY);
        view.setColor(positionX, positionY, charColor, backGroundColor);
    }

    public void putPlayerHitpoint(GameMapView view, int x, int y) throws InterruptedException {
        String hitpointString = "";
        for (int i = 0; i < hitpoint; i++) {
            hitpointString += hitpointChar;
        }
        // view.putString(hitpointString, x, y, hitpointCharColor, hitpointBackGroundColor);
        view.putString(hitpointString, x, y, hitpointCharColor);

        // System.out.println("Hitpoint: " + hitpoint + ", Max Hitpoint: " + maxHitpoint);
    }

    public void setHitpointCharColor(int hitpointCharColor) {
        if (hitpointCharColor < 0 || hitpointCharColor > ConsoleColor.COLORMAXINDEX) {
            throw new IllegalArgumentException("Invalid hitpoint character color index: " + hitpointCharColor);
        }
        this.hitpointCharColor = hitpointCharColor;
    }

    public void setHitpointBackGroundColor(int hitpointBackGroundColor) {
        if (hitpointBackGroundColor < 0 || hitpointBackGroundColor > ConsoleColor.COLORMAXINDEX) {
            throw new IllegalArgumentException("Invalid hitpoint background color index: " + hitpointBackGroundColor);
        }
        this.hitpointBackGroundColor = hitpointBackGroundColor;
    }

}

