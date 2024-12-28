package tomorimod.monsters.anon;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class AnonCallPower extends BasePower {
    public static final String POWER_ID = makeID(AnonCallPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public AnonCallPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public void atEndOfRound(){
        addToBot(new CallChordAction());
    }
}
