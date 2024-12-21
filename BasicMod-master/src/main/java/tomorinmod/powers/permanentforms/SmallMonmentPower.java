package tomorinmod.powers.permanentforms;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorinmod.actions.BigGirlsBandEraAction;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class SmallMonmentPower extends BasePower {
    public static final String POWER_ID = makeID(SmallMonmentPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public SmallMonmentPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }


}
