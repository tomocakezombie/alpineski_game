public class Hitpoint {
    int hitpoint;

    public Hitpoint(int hitpoint) {
        if (hitpoint < 0) {
            throw new IllegalArgumentException("Hitpoint cannot be negative: " + hitpoint);
        }
        this.hitpoint = hitpoint;
        // this.maxHitpoint = hitpoint; // 初期値は最大ヒットポイントと同じ
    }

    public Hitpoint addHitpoint(int hitpoint) {
        if (hitpoint < 0) {
            throw new IllegalArgumentException("Hitpoint cannot be negative: " + hitpoint);
        }
        return new Hitpoint(this.hitpoint + hitpoint);
    }

    public int getHitpointIntValue() {
        return this.hitpoint;
    }

}
