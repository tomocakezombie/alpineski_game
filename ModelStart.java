import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

// スタート画面を管理する
public class ModelStart {
    private ConsoleView view;
    private MapData Logo1;
    private MapData Logo2;
    private MapData Mountain;
    private boolean isView = false;
    private GameDifficulty gameDifficulty;
    
    private int baseBackGroundColor;
    private GameState gameState;

    // ロゴの右上の座標を保存
    private int Logo1X;
    private int Logo1Y;

    private int Logo2X;
    private int Logo2Y;

    // 山の位置情報
    private int MountainX;
    private int MountainY;

    // 弾を保存するリスト
    private LinkedList<Bullet> bullets;

    // ユーザの入力を待つための変数
    private Select userSelect;

    // // 出力する場面の状態を持つための変数
    private boolean isUserEnterDescriptin = false;
    private boolean isUserEnterRanking = false;
    private boolean isUserEnterDifficulty = false;


    ModelStart(ConsoleView view, GameState gameState, GameDifficulty gameDifficulty) {
        this.view = view;
        Logo1X = 10;
        Logo1Y = 3;
        Logo2X = ConsoleView.WIDTH;
        Logo2Y = Logo1Y + 10;
        this.bullets = new LinkedList<Bullet>();
        this.gameState = gameState;

        baseBackGroundColor = 111;    

        ReadFile readfile1 = new ReadFile("./ReadFiles/LOGO1.txt");
                                
        readfile1.setColor('＃', 15, 15);
        readfile1.setColor('　', baseBackGroundColor, baseBackGroundColor);
        readfile1.setBasicColor(baseBackGroundColor);
        Logo1 = readfile1.getMapData();

        ReadFile readfile2 = new ReadFile("./ReadFiles/LOGO2.txt");
        readfile2.setColor('＃', 15, 15);
        readfile2.setColor('　', baseBackGroundColor, baseBackGroundColor);
        readfile2.setBasicColor(baseBackGroundColor);
        Logo2 = readfile2.getMapData();

        ReadFile readfileMountain = new ReadFile("./ReadFiles/MOUNTAIN.txt");
        readfileMountain.setColor('＼', 15, baseBackGroundColor);
        readfileMountain.setColor('／', 15, baseBackGroundColor);
        readfileMountain.setColor('￣', 15, baseBackGroundColor);
        readfileMountain.setColor('　', baseBackGroundColor, baseBackGroundColor);
        readfileMountain.setBasicColor(baseBackGroundColor);
        Mountain = readfileMountain.getMapData();
        MountainX = 52;
        MountainY = 20;

        String[] userSelectString = new String[]{
            "ゲーム開始",
            "ルール確認",
            "ランキング",
            "ゲーム終了"
        };
        this.userSelect = new Select(userSelectString);

        this.gameDifficulty = gameDifficulty;
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
        Logo2X = Math.max(Logo2X - 5, Logo1X);
        
        Random random = new Random();
        // 例: 1からobstacleFrequency未満の乱数
        int randomValue = random.nextInt(ConsoleView.WIDTH - 1) + 1;

        Bullet bulletTest = new Bullet('＊', randomValue, 0, 1, 1, 254, baseBackGroundColor);
        // Bullet bulletTest = new Bullet('＊', randomValue, 0, 1, 1, 254, 1);
        bullets.add(bulletTest);
    }

    private void processKeyInput(String event) {

        // System.out.println("event: " + event);
        // キー入力処理
        if(event.equals("ENTER")){
            // ENTERキーが押されたときの処理

            if(isUserEnterDescriptin){
                // ユーザがルール確認やランキングを選択している場合
                isUserEnterDescriptin = false;
                return;
            }
            if(isUserEnterRanking){
                // ユーザがランキングを選択している場合
                isUserEnterRanking = false;
                return;
            }
            if(isUserEnterDifficulty){
                // ユーザが難易度を選択している場合
                isUserEnterDifficulty = false;
                gameState.setNextState();
                return;
            }

            switch(userSelect.getCurrentIndex()) {
                case 0: // モード選択 (今は無視してゲーム開始)
                    isUserEnterDifficulty = true;
                    break;
                case 1: // ルール確認
                    isUserEnterDescriptin = true;
                    break;
                case 2: // ランキング
                    isUserEnterRanking = true;
                    break;
                case 3: // ゲーム終了
                    System.exit(0);
                    break;
                default:
                    break;
            }
            return;
        }
        if(event.equals("SPACE")){
            // スペースキーが押されたときの処理
            // gameState.setNextState();
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
        if(event.equals("LEFT") || event.equals("a")){
            // 左矢印キーが押されたときの処理
            if(isUserEnterDifficulty){
                // 難易度選択中の場合
                gameDifficulty.previous();
                return;
            }
            return;
        }
        if(event.equals("RIGHT") || event.equals("d")){
            // 右矢印キーが押されたときの処理
            if(isUserEnterDifficulty){
                // 難易度選択中の場合
                gameDifficulty.next();
                return;
            }
            return;
        }
        return;
    }

    // 常に実行する処理
    private void processAlways() throws InterruptedException,IOException{
        
        // System.out.println(MountainX + ", " + MountainY);

        if(isUserEnterDifficulty){
            // 難易度選択中の場合
            view.putString("難易度選択画面", ConsoleView.WIDTH / 2 - 3, ConsoleView.HEIGHT / 4-5, 15, baseBackGroundColor);
            view.putString("難易度を変更するには左右キーを押してください", ConsoleView.WIDTH / 4 +10, ConsoleView.HEIGHT / 4 + 1, 15, baseBackGroundColor);
            view.putString("現在の難易度: " + gameDifficulty.getCurrentSelection(), ConsoleView.WIDTH / 2-5, ConsoleView.HEIGHT / 4 + 2, 15, baseBackGroundColor);

            for(int i = 0; i < gameDifficulty.getOptions().length; i++) {
                if(i == gameDifficulty.getCurrentIndex()) {
                    // 選択中の項目は色を変える
                    view.putString(gameDifficulty.getOptions()[i], ConsoleView.WIDTH / 2 - 10 + i * 10, ConsoleView.HEIGHT / 4 + 4 , 1, baseBackGroundColor);
                } else {
                    view.putString(gameDifficulty.getOptions()[i], ConsoleView.WIDTH / 2 - 10 + i * 10, ConsoleView.HEIGHT / 4 + 4 , 15, baseBackGroundColor);
                }
            }

            isUserEnterDescriptin = false;
            isUserEnterRanking = false;
            return;
        }

        if(isUserEnterDescriptin){
            view.putString("ルール確認画面", ConsoleView.WIDTH / 2 - 3, ConsoleView.HEIGHT / 4-5, 15, baseBackGroundColor);
            view.putString("このゲームは矢印キーまたはＷＡＳＤキーで操作します", ConsoleView.WIDTH / 2 - 20, ConsoleView.HEIGHT / 4 + 2, 15, baseBackGroundColor);
            view.putString("矢印キーでプレイヤーを移動させ、", ConsoleView.WIDTH / 2 - 20, ConsoleView.HEIGHT / 4 + 3, 15, baseBackGroundColor);
            view.putString("旗の間を通っていきます。", ConsoleView.WIDTH / 2 - 20, ConsoleView.HEIGHT / 4 + 4, 15, baseBackGroundColor);
            view.putString("旗の間を通らないとダメージを受けます。", ConsoleView.WIDTH / 2 - 20, ConsoleView.HEIGHT / 4 + 5, 15, baseBackGroundColor);
            view.putString("障害物にぶつかるとダメージを受けます。", ConsoleView.WIDTH / 2 - 20, ConsoleView.HEIGHT / 4 + 6, 15, baseBackGroundColor);
            view.putString("３回ミスするとゲームオーバーです。", ConsoleView.WIDTH / 2 - 20, ConsoleView.HEIGHT / 4 + 7, 15, baseBackGroundColor);
            view.putString("ルールを確認したらＥＮＴＥＲキーを押してください", ConsoleView.WIDTH / 2 - 20, ConsoleView.HEIGHT / 4 + 1, 15, baseBackGroundColor);
            return;
        }

        if(isUserEnterRanking){
            // ランキング確認中の場合
            view.putString("ランキング画面", ConsoleView.WIDTH / 2 - 3, ConsoleView.HEIGHT / 4-5, 15, baseBackGroundColor);
            showRanking();
            return;
        }

        view.putMap(MountainX, MountainY, Mountain);
        view.putMap(Logo1X, Logo1Y, Logo1);
        view.putMap(Logo2X, Logo2Y, Logo2);
        putBullets();
        view.setResetBackGroundColor(baseBackGroundColor);
        putUserSelect();
        return ;
        
    }

    private void putUserSelect() throws InterruptedException {
        int count = 0;
        for(String str : userSelect.getOptions()) {
            if(count == userSelect.getCurrentIndex()) {
                // 選択中の項目は色を変える
                view.putString(str, ConsoleView.WIDTH / 2 - 5, 25 + count*2, 1, baseBackGroundColor);
            } else {
                view.putString(str, ConsoleView.WIDTH / 2 - 5, 25 + count*2, 15, baseBackGroundColor);
            }
            // view.putString(str, ConsoleView.WIDTH / 2 - 5, 25 + count*2, 15, baseBackGroundColor);
            count++;
        }
    }

    private void showRanking() throws IOException, InterruptedException {
        // ファイルからスコアを読み取る
        List<String> lines = Files.readAllLines(Path.of("./ReadFiles/RANKING.txt"));
        List<Integer> scores = new ArrayList<>();

        // スコアを整数に変換
        for (String line : lines) {
            try {
                scores.add(Integer.parseInt(line.trim()));
            } catch (NumberFormatException e) {
                System.err.println("Invalid score format: " + line);
            }
        }

        // スコアを降順にソート
        Collections.sort(scores, Collections.reverseOrder());

        // ランキングを表示
        view.putString("ランキング", ConsoleView.WIDTH / 2 - 3, ConsoleView.HEIGHT / 4 - 5, 15, baseBackGroundColor);
        int rank = 1;
        for (int score : scores) {
            view.putString(rank + "位: " + score, ConsoleView.WIDTH / 2 - 10, ConsoleView.HEIGHT / 4 - 5 + rank, 15, baseBackGroundColor);
            rank++;
            if (rank > 10) break; // 上位10位まで表示
        }

        view.putString("ランキングを確認したらＥＮＴＥＲキーを押してください", ConsoleView.WIDTH / 2 - 20, ConsoleView.HEIGHT / 4 + 12, 15, baseBackGroundColor);
    }
    
}
