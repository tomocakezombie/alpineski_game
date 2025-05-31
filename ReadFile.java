import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.List; // 追加
import java.util.ArrayList; // 追加
import java.util.HashMap;
import java.util.Map;

public class ReadFile {
    private final String fileNameMap;
    private final MapData mapData;
    private ConsoleColor consoleColor;
    private int basicColor = 0;

    private static final Map<String, String> charMap = new HashMap<>();
    private static final Map<String, Integer> charColorMap = new HashMap<>();
    private static final Map<String, Integer> backGroundColorMap = new HashMap<>();

    public ReadFile(String fileNameMap) {
       
        this.fileNameMap = fileNameMap;
        this.mapData = new MapData();
        this.consoleColor = new ConsoleColor();
    }

    public void setBasicColor(int basicColor){
        if (0 <= basicColor && basicColor < ConsoleColor.COLORMAXINDEX) {
            this.basicColor = basicColor;
        } else {
            throw new IllegalArgumentException("Invalid color index: " + basicColor);
        }
    }

    public MapData getMapData() {
        readFileMap();
        return mapData;
    }

    private void readFileMap() {
       try (BufferedReader br = new BufferedReader(
               new InputStreamReader(new FileInputStream(fileNameMap), "UTF-8"))) {
           String line;
           int row = 0;
   
           // ファイル全体を読み込んで最大列数を計算
           int maxCols = 0;
           List<String> lines = new ArrayList<>();
           while ((line = br.readLine()) != null) {
               lines.add(line);
               maxCols = Math.max(maxCols, line.length());
           }
   
           // マップサイズを設定
           mapData.resize(lines.size(), maxCols);
   
           // マップデータを設定
           for (String currentLine : lines) {
               for (int col = 0; col < currentLine.length(); col++) {
                    mapData.setChar(col, row, currentLine.charAt(col));
                    int charColorValue = mapData.getCharColor(col, row);
                    int backgroundColorValue = basicColor; // デフォルトの背景色を設定
                    if(charColorMap.containsKey(String.valueOf(currentLine.charAt(col)))) {
                        charColorValue = charColorMap.get(String.valueOf(currentLine.charAt(col)));
                    } 
                    if(backGroundColorMap.containsKey(String.valueOf(currentLine.charAt(col)))) {
                        backgroundColorValue = backGroundColorMap.get(String.valueOf(currentLine.charAt(col)));
                    }
                    mapData.setColor(col, row, charColorValue, backgroundColorValue); 
                }
                row++;
           }
        } catch (IOException e) {
           System.err.println("ファイルの読み込み中にエラーが発生しました: " + e.getMessage());
           e.printStackTrace();
        }
    }


    public void setChar(char charName, String charValue) {
        charMap.put(String.valueOf(charName).toUpperCase(), charValue);
    }

    // char 型を受け取るオーバーロードを追加
    public void setChar(char charName, char charValue) {
        setChar(charName, String.valueOf(charValue));
    }

    public void setColor(char colorName, int charColorValue, int backgroundColorValue) {
        if (0 <= charColorValue && charColorValue < ConsoleColor.COLORMAXINDEX) {
            charColorMap.put(String.valueOf(colorName).toUpperCase(), charColorValue);
            // System.out.println("charColorValue: " + charColorValue);
        }

        if (0 <= backgroundColorValue && backgroundColorValue < ConsoleColor.COLORMAXINDEX) {
            backGroundColorMap.put(String.valueOf(colorName).toUpperCase(), backgroundColorValue);
            // System.out.println("backgroundValue: " + backgroundColorValue);
        }

    }


}
