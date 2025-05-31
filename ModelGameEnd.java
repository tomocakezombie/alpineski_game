import java.io.Console;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class ModelGameEnd {
    
    private ConsoleView view;
    private MapData Logo1;
    private MapData Logo2;
    private boolean isView = false;
    private Score score;
    
    private int baseBackGroundColor;
    private GameState gameState;


    // 弾を保存するリスト
    private LinkedList<Bullet> bullets;

    ModelGameEnd(ConsoleView view, GameState gameState, Score score) {
        this.view = view;
        
        this.bullets = new LinkedList<Bullet>();
        this.gameState = gameState;

        this.score = score;
        

        baseBackGroundColor = 111;    
        
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
    }

    private void processKeyInput(String event) {

        // System.out.println("event: " + event);
        // キー入力処理
        if(event.equals("ENTER")){
            // ENTERキーが押されたときの処理
            isView = true;
            return;
        }
        if(event.equals("SPACE")){
            // スペースキーが押されたときの処理
            gameState.setNextState();
            // System.out.println("ゲームを元に戻します");
            return;
        }

        if(event.equals("UP")){
            // 上矢印キーが押されたときの処理
            return;
        }
        if(event.equals("DOWN")){
            // 下矢印キーが押されたときの処理
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
        score.setPosition(ConsoleView.WIDTH / 2, ConsoleView.HEIGHT / 2);
        score.setColor(0, baseBackGroundColor);
        score.put(view);
        
    }

    

}
