package tomorimod.monsters.sakishadow;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class SakiRightPower extends BasePower {
    public static final String POWER_ID = makeID(SakiRightPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public SakiRightPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

}
