public class ChangeChar {
    /**
     * 半角英数字を全角に変換する
     */
    public static String toZenkaku(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            // 半角英数字
            if (c >= 0x21 && c <= 0x7E) {
                sb.append((char)(c + 0xFEE0));
            } else if (c == ' ') {
                sb.append('　'); // 半角スペース→全角スペース
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    public static String toZenkaku(int num) {
        StringBuilder sb = new StringBuilder();
        String str = Integer.toString(num);
        for (char c : str.toCharArray()) {
            // 半角英数字
            if (c >= 0x21 && c <= 0x7E) {
                sb.append((char)(c + 0xFEE0));
            } else if (c == ' ') {
                sb.append('　'); // 半角スペース→全角スペース
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 半角数字1文字を全角数字1文字に変換
     */
    public static char toZenkakuDigit(char c) {
        if (c >= '0' && c <= '9') {
            return (char)(c - '0' + '０');
        }
        return c;
    }

    public static char toZenkakuDigit(int digit) {
        if (digit >= 0 && digit <= 9) {
            return (char)(digit + '０');
        }
        throw new IllegalArgumentException("Digit must be between 0 and 9: " + digit);
    }
}
