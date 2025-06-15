import java.util.LinkedList;
import java.util.List;

public class KeySequenceDetector {
    private final List<String> targetSequence;
    private final LinkedList<String> buffer;
    private boolean isDetected = false;

    public KeySequenceDetector(List<String> targetSequence) {
        this.targetSequence = targetSequence;
        this.buffer = new LinkedList<>();
    }

    // キー入力を追加し、順番が一致したらtrueを返す
    public boolean input(String key) {
        if(isDetected){
            return true;
        }

        buffer.add(key);
        System.out.println("Key added to buffer: " + key);
        // バッファサイズを制限
        if (buffer.size() > targetSequence.size()) {
            buffer.removeFirst();
        }
        isDetected = buffer.equals(targetSequence);
        return isDetected;
    }

    public boolean isDetected() {
        return isDetected;
    }

    // バッファをクリア
    public void reset() {
        buffer.clear();
        isDetected = false;
    }
}