import java.io.Console;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class ModelGameEnd {
    // MVCの内Viewの変数
    private ConsoleView view;
    private Score score;
    private GameDifficulty gameDifficulty;
    private int baseBackGroundColor;
    private GameState gameState;
    private Select userSelect;
    // 弾を保存するリスト 雪を表現
    private LinkedList<Bullet> bullets;

    private MapData scoreLogo;

    // 矢印を表示する
    private LinkedList<Arrow> userArrows;

    ModelGameEnd(ConsoleView view, GameState gameState, Score score, GameDifficulty gameDifficulty) {
        this.view = view;
        
        this.bullets = new LinkedList<Bullet>();
        this.gameState = gameState;

        this.score = score;
        
        this.gameDifficulty = gameDifficulty;

        baseBackGroundColor = 111;    

        String[] userSelectString = new String[]{
            "リトライ",
            "タイトルへ戻る",
        };
        userSelect = new Select(userSelectString);
        userArrows = new LinkedList<Arrow>();

        for(int i = 0;i <= userSelect.getMaxSelection();i++){
            userArrows.add(new Arrow(ConsoleView.WIDTH / 2 - 7, ConsoleView.HEIGHT / 2 + 4 + 2 * i, new Direction(Direction.RIGHT), 1, baseBackGroundColor, 3));
        }

        // スコアロゴの読み込み
        ReadFile readFile = new ReadFile("ReadFiles/SCORELOGO.txt");
        readFile.setBasicColor(baseBackGroundColor);
        readFile.setColor('　',baseBackGroundColor,baseBackGroundColor);
        readFile.setColor('＃',255,255);
        scoreLogo = readFile.getMapData();
        
    }

    public void updateBullets() {
        for(Iterator<Bullet> i = bullets.iterator();i.hasNext();) {
            Bullet bullet = i.next();
            if(!bullet.isActive()) {
                i.remove();
            } else {
                bullet.update();
            }
        }
    }
    
    public void putBullets() throws InterruptedException {
        for(Iterator<Bullet> i = bullets.iterator();i.hasNext();) {
            Bullet bullet = i.next();
            bullet.put(view);
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
    
    private void processTimeElapsed(){
        updateBullets();
        
        Random random = new Random();
        int randomValue = random.nextInt(1, ConsoleView.WIDTH);

        Bullet bulletTest = new Bullet('＊', randomValue, 0, 1, 1, 255, baseBackGroundColor);
        bullets.add(bulletTest);

        moveArrows();
    }

    private void processKeyInput(String event) {
        // キー入力処理
        if(event.equals("ENTER") || event.equals("SPACE")){
            // ENTERキーが押されたときの処理
            if(userSelect.getCurrentIndex() == 0){
                // リトライを選択した場合
                gameState.setState(GameState.PREPAREPLAYING);
            } else if(userSelect.getCurrentIndex() == 1){
                // タイトルへ戻るを選択した場合
                gameState.setNextState();
            }

            userSelect.reset();

            return;
        }


        if(event.equals("UP") || event.equals("w")){
            // 上矢印キーが押されたときの処理
            userSelect.previous();
            return;
        }
        if(event.equals("DOWN") || event.equals("s")){
            // 下矢印キーが押されたときの処理
            userSelect.next();
            return;
        }
        if(event.equals("LEFT")){
            // 左矢印キーが押されたときの処理
            return;
        }
        if(event.equals("RIGHT")){
            // 右矢印キーが押されたときの処理
            return;
        }
        return;
    }

    // 常に実行する処理
    private void processAlways() throws InterruptedException {
        
      
        // 画面の更新
        view.setResetBackGroundColor(baseBackGroundColor);

        putBullets();

        // view.putString("スコア", ConsoleView.WIDTH / 2 - 3, ConsoleView.HEIGHT / 2 - 5, 255, baseBackGroundColor);
        view.putString("ゲーム難易度：", ConsoleView.WIDTH / 2 - 8, ConsoleView.HEIGHT / 2 - 2, 255, baseBackGroundColor);

        gameDifficulty.setPosition(ConsoleView.WIDTH / 2 - 1, ConsoleView.HEIGHT / 2 - 2);
        gameDifficulty.setColor(255, baseBackGroundColor);
        gameDifficulty.put(view);

        score.setPosition(ConsoleView.WIDTH / 2 - 3, ConsoleView.HEIGHT / 2);
        score.setColor(255, baseBackGroundColor);
        score.put(view);

        // スコアロゴの表示
        // scoreLogo.put(view, ConsoleView.WIDTH / 2 - scoreLogo.getWidth() / 2, ConsoleView.HEIGHT / 2 - 10);
        view.putMap(ConsoleView.WIDTH / 2 - scoreLogo.getWidth() / 2, ConsoleView.HEIGHT / 2 - 10, scoreLogo);

        putUserSelect();
        
    }

    private void putUserSelect() throws InterruptedException {
        int count = 0;
        for(String str : userSelect.getOptions()) {
            if(count == userSelect.getCurrentIndex()) {
                // 選択中の項目は色を変える
                view.putString(str, ConsoleView.WIDTH / 2 - str.length() / 2 - 1, 24 + count*2, 1, baseBackGroundColor);
                userArrows.get(count).put(view);
            } else {
                view.putString(str, ConsoleView.WIDTH / 2 - str.length() / 2 - 1, 24 + count*2, 15, baseBackGroundColor);
            }
            count++;
        }
    }

    private void moveArrows() {
        // 矢印を動かす
        for(Arrow arrow : userArrows) {
            arrow.move();
        }
    }
}
