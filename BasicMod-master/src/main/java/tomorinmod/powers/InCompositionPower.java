package tomorinmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static tomorinmod.BasicMod.makeID;

public class InCompositionPower extends BasePower {
    public static final String POWER_ID = makeID("InCompositionPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public InCompositionPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

}
