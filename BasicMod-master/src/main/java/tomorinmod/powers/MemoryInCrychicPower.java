package tomorinmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static tomorinmod.BasicMod.makeID;

public class MemoryInCrychicPower extends BasePower {
    public static final String POWER_ID = makeID("MemoryInCrychicPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public MemoryInCrychicPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0); // 不使用 amount 作为层数
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
