package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.actions.cardactions.BigGirlsBandEraAction;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class YeyingranPower extends BasePower {
    public static final String POWER_ID = makeID(YeyingranPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public YeyingranPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

}
