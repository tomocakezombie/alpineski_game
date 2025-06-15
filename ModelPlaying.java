import java.io.Console;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

// 画面を管理する
public class ModelPlaying {
    // MVCにおけるViewを保持
    private ConsoleView view;
    // MVCのViewとModelの中間を担うView
    private GameMapView gameMapView;
    // private MapData PlayingMap;
    private boolean isView = false;
    private ReadFile readfile;
    private int baseBackGroundColor;
    private GameState gameState;
    private ConsoleController controller;
    // ゲームの端っこを表現
    private MapData skyMap;

    private GameDifficulty gameDifficulty; // ゲームの難易度（1: Easy, 2: Normal, 3: Hard）

    private int invincibleTime = -1;
    private static final int INVINCIBLE_TIME_MAX = 5; 
    private int flame = 0;

    // ロゴの右上の座標を保存
    // private int PlayingMapX;
    // private int PlayingMapY;

    // プレイヤーの基本情報
    private Player player;

    // 障害物を保存するリスト
    private LinkedList<Obstacle> obstacles;
    private LinkedList<Obstacle> avalancheObstacles;

    // 旗を保存するリスト
    private LinkedList<Flag> flags;
    private int intervalFlag;

    // スコアの保存
    private Score score;

    // 表示コメントを管理
    private GameComment missObjComment;
    private GameComment missFlagComment;
    private GameComment successComment;

    // 障害物を置く頻度の設定
    private int obstacleFrequency = 5; 

    // ゲームマップの内、プレイヤーのいけない所を設定
    private int grancePeriod;

    private MapData dangerMap;

    // private int flameCount = 0;

    // 雪崩を管理するクラス
    private Avalanche avalanche;

    public void resetState(){
        // ゲームの状態をリセットする
        this.obstacles.clear();
        this.flags.clear();
        this.player.resetHitpoint();
        this.player.resetPosition();
        this.invincibleTime = -1;
        this.flame = 0;
        missObjComment.resetGameComment();
        missFlagComment.resetGameComment();
        successComment.resetGameComment();
        this.gameMapView.setResetBackGroundColor(baseBackGroundColor);
        this.score.resetScore();
        this.controller.resetDelay();
        this.avalancheObstacles.clear();
        this.avalanche.reset();
    }

    ModelPlaying(ConsoleView view, GameState gameState, GameDifficulty gameDifficulty, Score score, ConsoleController controller) {
        // コンストラクタ
        this.view = view;
        // PlayingMapX = 10;
        // PlayingMapY = GameMapView.HEIGHT / 2;
        this.obstacles = new LinkedList<Obstacle>();
        this.flags = new LinkedList<Flag>();
        this.gameState = gameState;
        this.grancePeriod = 40;
        this.gameDifficulty = gameDifficulty;
        baseBackGroundColor = 15;                                   
        this.player = new Player(3, GameMapView.WIDTH / 2, grancePeriod/4+1, '＠', grancePeriod, grancePeriod/4+1, GameMapView.WIDTH-grancePeriod, 31);
        this.player.setplayerCharColor(1);
        this.player.setPlayerBackGroundColor(baseBackGroundColor);
        this.player.setHitpointBackGroundColor(baseBackGroundColor);
        this.player.setPlayerDamageColor(3);

        this.intervalFlag = 30;

        int playerX = player.getPositionX();
        int playerY = player.getPositionY();

        this.score = score;
        this.score.setColor(0, baseBackGroundColor);

        // 画面の中心座標
        int centerX = ConsoleView.WIDTH / 2;
        int centerY = ConsoleView.HEIGHT / 2;
        // System.out.println("Center position: (" + centerX + ", " + centerY + ")");

        // マップの表示開始位置
        int startX = playerX - centerX;
        int startY = playerY - centerY;

        this.score = score;
        this.score.setColor(0, baseBackGroundColor);
        this.score.setPosition(startX, startY+1);
        
        this.missObjComment = new GameComment("ＭＩＳＳ！", INVINCIBLE_TIME_MAX);
        this.missObjComment.setColor(1, baseBackGroundColor);
        this.missFlagComment = new GameComment("旗を取り逃がした！", INVINCIBLE_TIME_MAX);
        this.missFlagComment.setColor(1, baseBackGroundColor);
        this.successComment = new GameComment("ＮＩＣＥ！", INVINCIBLE_TIME_MAX);
        this.successComment.setColor(4, baseBackGroundColor);

        this.gameMapView = new GameMapView(player);
        this.gameMapView.setResetBackGroundColor(baseBackGroundColor);

        this.skyMap = new MapData(GameMapView.HEIGHT, grancePeriod);

        this.skyMap.clearCharColor(111);
        this.skyMap.clearBackground(111);

        this.controller = controller;

        this.avalancheObstacles = new LinkedList<Obstacle>();
        this.avalanche = new Avalanche(0.005, avalancheObstacles);
        this.avalanche.setMinMax(grancePeriod, GameMapView.WIDTH - grancePeriod);
        this.avalanche.setWidth(30);
        this.avalanche.setBackgroundColor(baseBackGroundColor);

        ReadFile readFileDanger = new ReadFile("./ReadFiles/DANGER.txt");
        readFileDanger.setBasicColor(baseBackGroundColor);
        readFileDanger.setColor('＝', 0, baseBackGroundColor);
        this.dangerMap = readFileDanger.getMapData();
        
    }

    public void updateObstacles() {
        for(Iterator<Obstacle> i = obstacles.iterator();i.hasNext();) {
            Obstacle obstacle = i.next();
            if(!obstacle.isActive()) {
                i.remove();
            } else {
                obstacle.update();
            }
        }
    }

    public void updateAvalancheObstacles() {
        for(Iterator<Obstacle> i = avalancheObstacles.iterator();i.hasNext();) {
            Obstacle obstacle = i.next();
            if(!obstacle.isActive()) {
                i.remove();
            } else {
                obstacle.update();
                
                // 旗が雪崩に巻き込まれた場合、旗を消す
                for(Iterator<Flag> j = flags.iterator(); j.hasNext();) {
                    Flag flag = j.next();
                    if(
                        flag.getPositionX() <= obstacle.getPositionX() &&
                        obstacle.getPositionX() <= flag.getPositionX() + flag.getLineLength() + 1 &&
                        obstacle.getPositionY() == flag.getPositionY()
                        )
                        {
                        j.remove();
                        }

                }
            }
        }
    }

    public void updateFlags() {
        for(Iterator<Flag> i = flags.iterator();i.hasNext();) {
            Flag flag = i.next();
            if(!flag.isActive()) {
                i.remove();
            } else {
                flag.update();
            }
        }
    }
    
    public void putObstacles() throws InterruptedException {
        for(Iterator<Obstacle> i = obstacles.iterator();i.hasNext();) {
            Obstacle obstacle = i.next();
            obstacle.put(gameMapView);
        }
    }

    public void putAvalancheObstacles() throws InterruptedException {
        for(Iterator<Obstacle> i = avalancheObstacles.iterator();i.hasNext();) {
            Obstacle obstacle = i.next();
            obstacle.put(gameMapView);
        }
    }

    public void putFlags() throws InterruptedException {
        for(Iterator<Flag> i = flags.iterator();i.hasNext();) {
            Flag flag = i.next();
            flag.put(gameMapView);
        }
    }

    // 基本的にこのメソッドはいじらずにprivateメソッドをいじる
    public void process(String event) throws IOException, InterruptedException {

        if(event.equals("TIME_ELAPSED")) { // 時間経過イベント
            processTimeElapsed();
        }
        else { // キー入力イベント
            processKeyInput(event);
        }

        // 常に実行する処理
        processAlways();
    }
    
    // 時間経過イベントの処理
    private void processTimeElapsed(){

        Random random = new Random();
        int randomValue = random.nextInt(obstacleFrequency - 1) + 1;
        for (int i = 0; i < randomValue; i++) {
            int randomX = random.nextInt(GameMapView.WIDTH - grancePeriod) + grancePeriod;
            int randomY = GameMapView.HEIGHT - 2;

            // 旗の範囲にかぶっていないかチェック
            boolean isOnFlag = false;
            for (Flag flag : flags) {
                if (randomY == flag.getPositionY() &&
                    randomX >= flag.getPositionX() &&
                    randomX <= flag.getPositionX() + flag.getLineLength() + 1) {
                    isOnFlag = true;
                    break;
                }
            }
            if (isOnFlag) {
                // 旗の範囲なら障害物を生成しない
                continue;
            }

            Obstacle obstacle = new Obstacle('＊', randomX, randomY, 0, -1, 0, baseBackGroundColor);
            obstacles.add(obstacle);
        }

        // 旗の生成
        if(flame % intervalFlag == 0){
            generateFlag();
        }

        // エンドレス用の処理
        if(gameDifficulty.getCurrentSelection() == GameDifficulty.ENDLRESS) {
            // 15 フレーム事に画面更新速度を増加
            if(flame != 0 && flame % 30 == 0) {
                controller.setSubDelay();
            }

            if(flame == 500){
                intervalFlag = 30;
                obstacleFrequency = 5;
                
            }

            // 雪崩の発生をさせるかも
            avalanche.runAvalanche();
        }

        updateObstacles();
        updateAvalancheObstacles();
        updateFlags();
        flame++;
        score.addScore(1);
        missObjComment.countViewFlame();
        missFlagComment.countViewFlame();
        successComment.countViewFlame();
    }

    private void generateFlag() {

        if(avalanche.isHappenAvalanche()){
            if(avalanche.isPlaceLeft()) {
                // 雪崩が左側に発生する場合
                Random random = new Random();
                random.nextInt();
                Flag flag = new Flag('Ｆ', 'ー', avalanche.getMinX() + random.nextInt(40 - 20) + 20 , GameMapView.HEIGHT-1 , 10, 1, baseBackGroundColor);
                flags.add(flag);
            } else {
                // 雪崩が右側に発生する場合
                Random random = new Random();
                random.nextInt();
                Flag flag = new Flag('Ｆ', 'ー', avalanche.getMaxX() - random.nextInt(40 - 20) + 20 , GameMapView.HEIGHT-1 , 10, 1, baseBackGroundColor);
                flags.add(flag);
            }
        } else {
            Random random = new Random();
            int randomX = grancePeriod + random.nextInt(GameMapView.WIDTH/4);
            Flag flag = new Flag('Ｆ', 'ー', randomX, GameMapView.HEIGHT-1 , 10, 1, baseBackGroundColor);
            flags.add(flag);
        }

        // 旗の位置を障害物の位置と重ならないように調整
        for(Iterator <Obstacle> i = obstacles.iterator(); i.hasNext();) {
            Obstacle obstacle = i.next();
            for(Flag flag: flags){
                if(flag.getPositionX() <= obstacle.getPositionX() &&
                 obstacle.getPositionX() <= flag.getPositionX() + flag.getLineLength() + 1 &&
                 obstacle.getPositionY() == flag.getPositionY()){
                    i.remove();
                 }
            }
        }


    }

    private void processKeyInput(String event) throws InterruptedException {

        // キー入力処理
        if(event.equals("ENTER")){
            // ENTERキーが押されたときの処理
            
            // gameMapView.showDebugInfo();
            // view.showDebugInfo();
            // Thread.sleep(100000); // 1秒待機
            return;
        }
        if(event.equals("SPACE")){
            // スペースキーが押されたときの処理
            // gameState.setNextState();
            return;
        }

        if(event.equals("UP") || event.equals("w")){
            // 上矢印キーが押されたときの処理
            player.subY();
            return;
        }
        if(event.equals("DOWN") || event.equals("s")){
            // 下矢印キーが押されたときの処理
            player.addY();
            return;
        }
        if(event.equals("LEFT") || event.equals("a")){
            // 左矢印キーが押されたときの処理
            player.subX();
            return;
        }
        if(event.equals("RIGHT") || event.equals("d")){
            // 右矢印キーが押されたときの処理
            player.addX();
            return;
        }

        return;
    }

    public void setGameDifficulty(){
        switch(gameDifficulty.getCurrentSelection()) {
            case GameDifficulty.NORMAL: // Normal
                intervalFlag = 30;
                obstacleFrequency = 4;
                break;
            case GameDifficulty.HARD: // Hard
                intervalFlag = 25;
                obstacleFrequency = 8;
                break;
            case GameDifficulty.ENDLRESS: // Endless
                intervalFlag = 30;
                obstacleFrequency = 5;
                break;
            default:
                intervalFlag = 30;
        }
    }

    // 常に実行する処理
    private void processAlways() throws InterruptedException {
        
        // パラメータ処理
        PlayerHitObstacle();
        PlayerMissFlag();

        // 処理の位置おかしくね?
        if(player.getHitpoint() <= 0) {
            gameState.setNextState();
            player.resetHitpoint();
            player.resetPosition();
            missObjComment.resetViewFlame();
            missFlagComment.resetViewFlame();
            successComment.resetViewFlame();
            obstacles.clear();
            flags.clear();
            controller.resetDelay();
            return;
        }

        // 画面描画処理

        int playerX = player.getPositionX();
        int playerY = player.getPositionY();

        // 画面の中心座標
        int centerX = ConsoleView.WIDTH / 2;
        int centerY = ConsoleView.HEIGHT / 4;
        // System.out.println("Center position: (" + centerX + ", " + centerY + ")");

        // マップの表示開始位置
        int startX = playerX - centerX;
        int startY = playerY - centerY;


        this.score.setPosition(startX+ConsoleView.WIDTH-5, startY);

        gameMapView.setResetBackGroundColor(baseBackGroundColor);
        
        putFlags();
        putObstacles();
        putAvalancheObstacles();
        
        gameMapView.putMap(0, 0, skyMap); // 空のマップを描画 
        gameMapView.putMap(gameMapView.WIDTH - grancePeriod, 0, skyMap); // プレイ中のマップを描画

        gameMapView.putString("ＨＰ：", startX, startY, 0);
        player.putPlayerHitpoint(gameMapView, startX+3, startY);
        score.setColor(0, baseBackGroundColor);
        score.put(gameMapView);

        gameDifficulty.setPosition(startX, startY + 1);
        gameDifficulty.setColor(0, baseBackGroundColor);
        gameDifficulty.put(gameMapView);

        if(invincibleTime > 0){
            player.putPlayerDamage(gameMapView); // 無敵時間中は色を変えて表示
        } else {
            player.putPlayer(gameMapView);
        }

        missObjComment.setPosition(player.getPositionX() + 1, player.getPositionY() - 1);
        missFlagComment.setPosition(player.getPositionX() + 1, player.getPositionY() - 1);
        successComment.setPosition(player.getPositionX() + 1, player.getPositionY() - 1);

        // エンドレス限定処理
        if(gameDifficulty.getCurrentSelection() == GameDifficulty.ENDLRESS){
            // スピードアップ処理
            // if(flame > 5 && 0 <= flame % 30 && flame % 30 < 5){
            //     gameMapView.putString("．．．！", player.getPositionX() + 1, player.getPositionY() - 1, 4);
            // }

            if(avalanche.isDangerous()){
                gameMapView.putString("気を付けろ！", player.getPositionX() + 1, player.getPositionY() - 2, 4);
                if(avalanche.isPlaceLeft()){
                    gameMapView.putString("左", player.getPositionX()+1, player.getPositionY() - 3, 1);
                } else {
                    gameMapView.putString("右", player.getPositionX()+1, player.getPositionY() - 3, 1);
                }
                gameMapView.putString("側に雪崩が発生！", player.getPositionX() + 2, player.getPositionY() - 3, 4);
            }
     
        } 

        

        missObjComment.putComment(gameMapView);
        missFlagComment.putComment(gameMapView);
        successComment.putComment(gameMapView);



        // ConsoleViewに描画
        gameMapView.putConsoleView(view);

        System.out.println("time:"+avalanche.getTime());

        
    }

    private void PlayerHitObstacle() {
        if(invincibleTime > 0){
            invincibleTime--;
            return ;
        }

        for(Obstacle obstacle: obstacles){
            int obstaclePositionX = obstacle.getPositionX();
            int obstaclePositionY = obstacle.getPositionY();
            if(obstaclePositionX == player.getPositionX() && obstaclePositionY == player.getPositionY()) {
                // プレイヤーに弾が当たった場合の処理
                player.damage(obstacle.getAttackPower());
                invincibleTime = INVINCIBLE_TIME_MAX; // 無敵時間を設定
                missObjComment.resetViewFlame();
            }
        }

        for(Obstacle obstacle: avalancheObstacles){
            int obstaclePositionX = obstacle.getPositionX();
            int obstaclePositionY = obstacle.getPositionY();
            if(obstaclePositionX == player.getPositionX() && obstaclePositionY == player.getPositionY()) {
                // プレイヤーに雪崩の障害物が当たった場合の処理
                player.damage(obstacle.getAttackPower());
                invincibleTime = INVINCIBLE_TIME_MAX; // 無敵時間を設定
                missObjComment.resetViewFlame();
            }
        }
    }

    private void PlayerMissFlag() {
        for(Iterator<Flag> i = flags.iterator();i.hasNext();) {
            Flag flag = i.next();
            int flagPositionX = flag.getPositionX();
            int flagPositionY = flag.getPositionY();

            if(flagPositionY != player.getPositionY()) {
                continue;
            }
            if(flag.getIsFinish()){
                continue;
            }

            if(flagPositionX <= player.getPositionX() && 
                player.getPositionX() <= flagPositionX + flag.getLineLength() + 1) {
                successComment.resetViewFlame();
            } else {
                missFlagComment.resetViewFlame();
                // System.out.println("missFlagComment resetViewFlame");
                player.damage();
            }

            flag.changeIsFinish();
            return; // 1つの旗に対してのみ処理を行う

        }
    }

    public int getBaseBackGroundColor() {
        return baseBackGroundColor;
    }

    
}
