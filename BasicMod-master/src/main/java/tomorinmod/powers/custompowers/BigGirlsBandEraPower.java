package tomorinmod.powers.custompowers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorinmod.actions.BigGirlsBandEraAction;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class BigGirlsBandEraPower extends BasePower {
    public static final String POWER_ID = makeID(BigGirlsBandEraPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public BigGirlsBandEraPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }


    @Override
    public void atStartOfTurn() {
        addToBot(new BigGirlsBandEraAction(false));
    }
}
