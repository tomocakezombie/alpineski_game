import java.util.HashMap;
import java.util.Map;

public class ConsoleColor {
    public static final int COLORMAXINDEX = 255; // 最大値
    public static final int COLORBLACK = 0;     // 黒

    private static final Map<String, Integer> colorMap = new HashMap<>();

    // コンストラクタで色を初期化
    public ConsoleColor() {
        initializeColors();
    }

    // 色を初期化するメソッド
    private void initializeColors() {
        colorMap.put("BLACK", 0);
        colorMap.put("RED", 85);       // 赤
        colorMap.put("GREEN", 170);    // 緑
        colorMap.put("BLUE", 255);     // 青
        colorMap.put("YELLOW", 127);   // 黄
        colorMap.put("CYAN", 212);     // シアン
        colorMap.put("MAGENTA", 42);   // マゼンタ
        colorMap.put("WHITE", 255);    // 白
    }

    public void setColor(String colorName, int colorValue) {
        if (colorValue < 0 || colorValue > COLORMAXINDEX) {
            return;
        }
        colorMap.put(colorName.toUpperCase(), colorValue);
    }
    
    public void setColor(char colorName, int colorValue) {
        if (colorValue < 0 || colorValue > COLORMAXINDEX) {
            return;
        }
        colorMap.put(String.valueOf(colorName).toUpperCase(), colorValue);
    }

    // 色名を 0～255 の範囲に変換するメソッド
    public int getColor(String colorName) {
        return colorMap.getOrDefault(colorName.toUpperCase(), COLORBLACK);
    }

    public int getColor(char colorName) {
        return colorMap.getOrDefault(String.valueOf(colorName).toUpperCase(), COLORBLACK);
    }

    // 色名を取得する（デバッグ用）
    public void printAvailableColors() {
        System.out.println("Available Colors:");
        for (String color : colorMap.keySet()) {
            System.out.println(color + " -> " + colorMap.get(color));
        }
    }
}
