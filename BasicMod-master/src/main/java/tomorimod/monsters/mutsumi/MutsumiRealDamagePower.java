package tomorimod.monsters.mutsumi;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class MutsumiRealDamagePower extends BasePower {
    public static final String POWER_ID = makeID(MutsumiRealDamagePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;



    public MutsumiRealDamagePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner,0);
    }

}
