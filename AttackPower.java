public class AttackPower {
    int attackPower;
    public AttackPower(int attackPower) {
        if(attackPower < 0) {
            throw new IllegalArgumentException("Attack power cannot be negative");
        }
        this.attackPower = attackPower;
    }

    public AttackPower addAttackPower(int attackPower){
        if(attackPower < 0) {
            throw new IllegalArgumentException("Attack power cannot be negative");
        }

        return new AttackPower(this.attackPower + attackPower);
    }

    public int getAttackPowerIntValue() {
        return this.attackPower;
    }
}
