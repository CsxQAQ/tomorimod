package tomorimod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static tomorimod.TomoriMod.makeID;

public class MusicCompositionPower extends BasePower {
    public static final String POWER_ID = makeID(MusicCompositionPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public MusicCompositionPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }


}
