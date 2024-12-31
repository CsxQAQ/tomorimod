package tomorimod.monsters.mutsumi;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class FriendlyMonsterPower extends BasePower {
    public static final String POWER_ID = makeID(FriendlyMonsterPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public FriendlyMonsterPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }


}