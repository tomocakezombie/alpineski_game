import java.io.Console;
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
    // MVCのViewを保持
    private ConsoleView view;
    
    // スタート画面の上のロゴ
    private MapData Logo1;
    
    // スタート画面の下のロゴ
    private MapData Logo2;
    
    // スタート画面の右にある山
    private MapData Mountain;
    
    // ゲーム難易度設定画面の山
    private MapData MountainDifficulty;
    
    // ゲーム説明画面で表示する説明
    private MapData Description;
    
    // 隠し画像を保存
    private MapData HiddenImage;
    private KeySequenceDetector keySequenceDetector;
    private int HiddenImageX = 55;
    private int HiddenImageY = ConsoleView.HEIGHT;
    private int HiddenImageYMin = 10;

    // 隠し画像を保存
    private MapData HiddenImage2;
    // private KeySequenceDetector keySequenceDetector2;
    private KeyCount keycount;
    private int HiddenImage2X = 0;
    private int HiddenImage2Y = ConsoleView.HEIGHT;
    private int HiddenImage2YMin = 20;


    
    // ランキングのロゴで表示する
    private MapData RankingLogo;
    private int RankingLogoX;
    private int RankingLogoY;

    private boolean isView = false;
    private GameDifficulty gameDifficulty;
    
    private int baseBackGroundColor;
    private GameState gameState;

    // ロゴの右上の座標を保存
    private int Logo1X;
    private int Logo1Y;

    private int Logo2X;
    private int Logo2Y;

    private int DescriptionX;
    private int DescriptionY;

    // 山の位置情報
    private int MountainX;
    private int MountainY;

    // ゲーム難易度設定画面の山情報
    private int[] MountainDifficultyXs;
    private int[] MountainDifficultyYs;

    // 弾を保存するリスト
    private LinkedList<Bullet> bullets;

    // 矢印の文字を描画する
    private Arrow backArrow;
    private List<Arrow> frontArrows;
    private List<Arrow> difficultArrows;

    // ユーザの入力を待つための変数
    private Select userSelect;
    private int userSelectX;

    // // 出力する場面の状態を持つための変数
    private boolean isUserEnterDescriptin = false;
    private boolean isUserEnterRanking = false;
    private boolean isUserEnterDifficulty = false;
    private int flame = 0; // 時間経過を管理するための変数

    ModelStart(ConsoleView view, GameState gameState, GameDifficulty gameDifficulty) {
        this.view = view;
        Logo1X = 10;
        Logo1Y = 3;
        Logo2X = ConsoleView.WIDTH;
        Logo2Y = Logo1Y + 10;
        this.bullets = new LinkedList<Bullet>();
        this.gameState = gameState;

        baseBackGroundColor = 111;    

        // ファイル読み込み処理
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

        ReadFile readfileDescription = new ReadFile("./ReadFiles/DESCRIPTION.txt");
        readfileDescription.setBasicColor(baseBackGroundColor);
        Description = readfileDescription.getMapData();
        DescriptionX = ConsoleView.WIDTH / 2 - Description.getWidth() / 2;
        DescriptionY = 4;

        ReadFile readfileMountainDifficulty = new ReadFile("./ReadFiles/MOUNTAIN_DIFFICULTY.txt");
        readfileMountainDifficulty.setColor('　', 15, baseBackGroundColor);
        readfileMountainDifficulty.setBasicColor(baseBackGroundColor);
        MountainDifficulty = readfileMountainDifficulty.getMapData();

        MountainDifficultyXs = new int[1];
        MountainDifficultyYs = new int[1];
        
        MountainDifficultyXs[0]=-15;
        MountainDifficultyYs[0]=14;


        ReadFile readfileHiddenImage = new ReadFile("./ReadFiles/SECRET.txt");
        readfileHiddenImage.setColor('Ａ', 255, 255);
        readfileHiddenImage.setColor('Ｂ', 0, 0);
        readfileHiddenImage.setColor('Ｃ', 172, 172);
        readfileHiddenImage.setColor('Ｄ', 178, 178);
        readfileHiddenImage.setColor('Ｅ', baseBackGroundColor, baseBackGroundColor);
        HiddenImage = readfileHiddenImage.getMapData();

        ReadFile readfileHiddenImage2 = new ReadFile("./ReadFiles/SECRET2.txt");
        readfileHiddenImage2.setColor('Ａ', 255, 255);
        readfileHiddenImage2.setColor('Ｂ', 0, 0);
        readfileHiddenImage2.setColor('Ｃ', 172, 172);
        readfileHiddenImage2.setColor('Ｄ', 178, 178);
        readfileHiddenImage2.setColor('Ｅ', baseBackGroundColor, baseBackGroundColor);
        readfileHiddenImage2.setColor('Ｆ', 255, 255);
        HiddenImage2 = readfileHiddenImage2.getMapData();

        List<String> sequence = List.of("UP", "UP", "DOWN", "DOWN", "LEFT", "RIGHT", "LEFT", "RIGHT", "b", "a");
        keySequenceDetector = new KeySequenceDetector(sequence);

        // keySequenceDetector2 = new KeySequenceDetector(sequence);
        keycount = new KeyCount(120);

        String[] userSelectString = new String[]{
            "モード選択",
            "ルール確認",
            "ランキング",
            "ゲーム終了"
        };
        this.userSelect = new Select(userSelectString);

        this.gameDifficulty = gameDifficulty;
        userSelectX = ConsoleView.WIDTH / 2 - 15;

        backArrow = new Arrow(ConsoleView.WIDTH / 2 - 9, ConsoleView.HEIGHT - 3, new Direction(Direction.RIGHT), 1, baseBackGroundColor, 4);
        frontArrows = new ArrayList<Arrow>();
        difficultArrows = new ArrayList<Arrow>();

        for(int i = 0;i < 4;i++) {
            Arrow frontArrow = new Arrow(ConsoleView.WIDTH / 2 - 9, 25 + i*2, new Direction(Direction.RIGHT), 1, baseBackGroundColor, 4);
            frontArrows.add(frontArrow);
        }

        for(int i = 0;i < gameDifficulty.getOptions().length;i++) {
            Arrow difficultArrow = new Arrow(userSelectX + i * 10 - 3, ConsoleView.HEIGHT / 4 + 4, new Direction(Direction.RIGHT), 1, baseBackGroundColor, 3);
            difficultArrows.add(difficultArrow);
        }

        ReadFile readfileRankingLogo = new ReadFile("./ReadFiles/RANKINGLOGO.txt");
        readfileRankingLogo.setColor('＃', 255, 255);
        readfileRankingLogo.setColor('　', baseBackGroundColor, baseBackGroundColor);
        RankingLogo = readfileRankingLogo.getMapData();
        RankingLogoX = ConsoleView.WIDTH / 2 - RankingLogo.getWidth() / 2;
        RankingLogoY = ConsoleView.HEIGHT / 4 - 5;


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
    
    // 時間経過処理
    private void processTimeElapsed(){
        updateBullets();
        Logo2X = Math.max(Logo2X - 10, Logo1X);
        
        Random random = new Random();
        // 1からobstacleFrequency未満の乱数
        int randomValue = random.nextInt(ConsoleView.WIDTH - 1) + 1;

        Bullet bulletTest = new Bullet('＊', randomValue, 0, 1, 1, 254, baseBackGroundColor);
        bullets.add(bulletTest);

        moveArrows();


        // 秘密画像の動作
        if(keySequenceDetector.isDetected()) {
            if(HiddenImageY > HiddenImageYMin) {
                // 秘密画像が表示されている場合、Y座標を下げる
                HiddenImageY -= 1;
            }
        }

        // 隠し画像2の動作
        if(keycount.isFilled()) {
            if(HiddenImage2Y > HiddenImage2YMin) {
                // 秘密画像が表示されている場合、Y座標を下げる
                HiddenImage2Y -= 1;
            }
        }

        flame++;
    }

    // キー入力処理
    private void processKeyInput(String event) {
        // 隠しコマンド入力受付
        if(isUserEnterDifficulty){
            keySequenceDetector.input(event);
        }

        if(!isUserEnterDescriptin && !isUserEnterRanking && !isUserEnterDifficulty) {
            // ユーザがルール確認やランキングを選択していない場合
            // keySequenceDetector2.input(event);
            keycount.increment();
        }

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
                // 初期化
                HiddenImageY = ConsoleView.HEIGHT;
                keySequenceDetector.reset();
                HiddenImage2Y = ConsoleView.HEIGHT;
                // keySequenceDetector2.reset();
                keycount.reset();
                return;
            }

            switch(userSelect.getCurrentIndex()) {
                case 0: 
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
        // 難易度選択中の場合
        if(isUserEnterDifficulty){
           
            // 山の描画
            for(int i = 0; i < MountainDifficultyXs.length; i++) {
                view.putMap(MountainDifficultyXs[i], MountainDifficultyYs[i], MountainDifficulty);
            }

             // 秘密画像の描画
            if(keySequenceDetector.isDetected()) {
                // シークエンスが検出された場合
                view.putMap(HiddenImageX, HiddenImageY, HiddenImage);
            }

            view.putString("モード選択画面", ConsoleView.WIDTH / 2 - 5, ConsoleView.HEIGHT / 4-5, 15, baseBackGroundColor);
            view.putString("左右キーで難易度変更、ＥＮＴＥＲキーでゲームを開始できます", ConsoleView.WIDTH / 4 +5, ConsoleView.HEIGHT / 4 + 1, 15, baseBackGroundColor);
            view.putString("現在のモード：" + ChangeChar.toZenkaku(gameDifficulty.getCurrentSelection()), ConsoleView.WIDTH / 2-7, ConsoleView.HEIGHT / 4 + 2, 15, baseBackGroundColor);

            for(int i = 0; i < gameDifficulty.getOptions().length; i++) {
                if(i == gameDifficulty.getCurrentIndex()) {
                    // 選択中の項目は色を変える
                    view.putString(ChangeChar.toZenkaku(gameDifficulty.getOptions()[i]), userSelectX + i * 10, ConsoleView.HEIGHT / 4 + 4 , 1, baseBackGroundColor);
                    difficultArrows.get(i).put(view);
                } else {
                    view.putString(ChangeChar.toZenkaku(gameDifficulty.getOptions()[i]), userSelectX + i * 10, ConsoleView.HEIGHT / 4 + 4 , 15, baseBackGroundColor);
                }
            }

            isUserEnterDescriptin = false;
            isUserEnterRanking = false;
            return;
        }

        if(isUserEnterDescriptin){
            // putYajirusi();
            backArrow.put(view);
            view.putString("ＥＮＴＥＲキーで戻る", ConsoleView.WIDTH / 2 - 5, ConsoleView.HEIGHT - 3, 1, baseBackGroundColor);

            // 説明の描画
            view.putMap(DescriptionX, DescriptionY, Description);
            return;
        }

        if(isUserEnterRanking){
            // ランキング確認中の場合
            view.putString("ランキング画面", ConsoleView.WIDTH / 2 - 3, ConsoleView.HEIGHT / 4-5, 15, baseBackGroundColor);
            showRanking();
            // putYajirusi();
            backArrow.put(view);
            view.putString("ＥＮＴＥＲキーで戻る", ConsoleView.WIDTH / 2 - 5, ConsoleView.HEIGHT - 3, 1, baseBackGroundColor);
            return;
        }

        if(keycount.isFilled()) {
            // シークエンスが検出された場合
            view.putMap(HiddenImage2X, HiddenImage2Y, HiddenImage2);
        }

        view.putMap(MountainX, MountainY, Mountain);
        view.putMap(Logo1X, Logo1Y, Logo1);
        view.putMap(Logo2X, Logo2Y, Logo2);
        putBullets();
        view.setResetBackGroundColor(baseBackGroundColor);
        putUserSelect();

        return;
        
    }

    private void putUserSelect() throws InterruptedException {
        int count = 0;
        for(String str : userSelect.getOptions()) {
            if(count == userSelect.getCurrentIndex()) {
                // 選択中の項目は色を変える
                view.putString(str, ConsoleView.WIDTH / 2 - 5, 25 + count*2, 1, baseBackGroundColor);
                frontArrows.get(count).put(view);
            } else {
                view.putString(str, ConsoleView.WIDTH / 2 - 5, 25 + count*2, 15, baseBackGroundColor);
            }
            count++;
        }
    }

    private void showRanking() throws IOException, InterruptedException {
        String[] files = {
            "./ReadFiles/Ranking/RANKINGNORMAL.txt",
            "./ReadFiles/Ranking/RANKINGHARD.txt",
            "./ReadFiles/Ranking/RANKINGENDLESS.txt"
        };
        String[] titles = { "NORMAL", "HARD", "ENDLESS" };

        int baseX = ConsoleView.WIDTH / 8; // 横並びの基準位置
        int sectionY = ConsoleView.HEIGHT / 2;

        view.putMap(RankingLogoX, RankingLogoY, RankingLogo);

        for (int i = 0; i < files.length; i++) {
            List<String> lines = Files.readAllLines(Path.of(files[i]));
            List<Integer> scores = new ArrayList<>();

            for (String line : lines) {
                try {
                    scores.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    // 無効な行は無視
                }
            }
            Collections.sort(scores, Collections.reverseOrder());

            // タイトル表示（横にずらす）
            int x = baseX + i * (ConsoleView.WIDTH / 3);
            view.putString("〇" + ChangeChar.toZenkaku(titles[i]) , x, sectionY, 15, baseBackGroundColor);

            // スコア表示（縦に並べる）
            int rank = 1;
            for (int score : scores) {
                view.putString(ChangeChar.toZenkaku(String.valueOf(rank)) + "位：" + ChangeChar.toZenkaku(String.valueOf(score)), x, sectionY + rank, 15, baseBackGroundColor);
                rank++;
                if (rank > 10) break;
            }
        }

    }

    private void moveArrows() {
        // 矢印の動作
        backArrow.move();
        for(Arrow arrow : frontArrows) {
            arrow.move();
        }
        for(Arrow arrow : difficultArrows) {
            arrow.move();
        }
    }
    
}
