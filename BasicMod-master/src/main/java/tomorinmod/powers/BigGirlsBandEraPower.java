package tomorinmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorinmod.actions.BigGirlsBandEraAction;

import static tomorinmod.BasicMod.makeID;

public class BigGirlsBandEraPower extends BasePower{
    public static final String POWER_ID = makeID("BigGirlsBandEraPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private boolean isUpgraded;

    public BigGirlsBandEraPower(AbstractCreature owner,boolean isUpgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        this.isUpgraded= isUpgraded;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new BigGirlsBandEraAction(isUpgraded));
    }
}
