public class ShowColorTable {
    public static void main(String[] args) {
        for (int i = 0; i < 256; i++) {
            // 文字色（前景色）で出力
            System.out.printf("\u001B[38;5;%dm%3d\u001B[0m ", i, i);

            // 1行に6個ずつ表示
            if ((i + 1) % 6 == 0) {
                System.out.println();
            }
        }
    }
}
