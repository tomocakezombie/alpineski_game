public class Flame {
    private int flameCount;
    private int flameCountMax;
    private boolean isFlameCountMaxReached;
    public Flame(int flameCountMax) {
        this.flameCount = 0;
        this.flameCountMax = flameCountMax;
        this.isFlameCountMaxReached = false;
    }

    public void incrementFlameCount() {
        flameCount++;
        if (flameCount >= flameCountMax) {
            flameCount = 0; // Reset to 0 when max is reached
            isFlameCountMaxReached = true; // Set flag when max is reached
        }
    }

    public boolean isFlameCountMaxReached() {
        return isFlameCountMaxReached;
    }

    public int getFlameCount() {
        return flameCount;
    }

    public int getFlameCountMax() {
        return flameCountMax;
    }
}
