package tomorimod.monsters.mutumi.friendly;

public class FriendlyMonsterMoveInfo {
    public byte nextMove;
    public AbstractFriendlyMonster.Intent intent;
    public int baseDamage;
    public int multiplier;
    public boolean isMultiDamage;

    public FriendlyMonsterMoveInfo(byte nextMove, AbstractFriendlyMonster.Intent intent, int intentBaseDmg, int multiplier, boolean isMultiDamage) {
        this.nextMove = nextMove;
        this.intent = intent;
        this.baseDamage = intentBaseDmg;
        this.multiplier = multiplier;
        this.isMultiDamage = isMultiDamage;
    }

}
