package tomorinmod.powers.custompowers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class ConveyFeelingPower extends BasePower {
    public static final String POWER_ID = makeID(ConveyFeelingPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ConveyFeelingPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

}
