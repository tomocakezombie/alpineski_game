public class KeyCount {
    boolean isFilled;
    int count;
    int threshold;

    public KeyCount(int threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold cannot be negative: " + threshold);
        }
        this.threshold = threshold;
        this.count = 0;
        // 0なら最初から満たされているとする
        this.isFilled = (threshold == 0);
    }

    public void increment() {
        if(isFilled){
            return ;
        }

        count++;
        if (count >= threshold) {
            isFilled = true;
        }
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void reset(){
        count = 0;
        isFilled = false;
    }
}
