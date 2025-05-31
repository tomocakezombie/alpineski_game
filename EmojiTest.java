public class EmojiTest {
    public static void main(String[] args) {
        System.out.println("=== 🐎 絵文字表示テスト ===");

        // 絵文字を含む文字列を表示
        String horseLine = "馬が走る: 🐎🐎🐎🐎🦓🏇🏼🎠";
        System.out.println(horseLine);

        // 他の絵文字も含めて確認
        System.out.println("スタート！🚦 ゴール！🏁");

        // 注意喚起
        System.out.println("\n※ 絵文字が正しく表示されない場合は、");
        System.out.println("  - ターミナルがUTF-8に対応しているか");
        System.out.println("  - フォントが絵文字に対応しているか");
        System.out.println("  を確認してください。");
    }
}
