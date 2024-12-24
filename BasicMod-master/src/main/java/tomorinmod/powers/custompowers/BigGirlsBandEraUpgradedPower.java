package tomorinmod.powers.custompowers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorinmod.actions.cardactions.BigGirlsBandEraAction;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class BigGirlsBandEraUpgradedPower extends BasePower {
    public static final String POWER_ID = makeID(BigGirlsBandEraUpgradedPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public BigGirlsBandEraUpgradedPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }


    @Override
    public void atStartOfTurn() {
        addToBot(new BigGirlsBandEraAction(true));
    }
}
