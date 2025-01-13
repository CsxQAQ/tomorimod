package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class SmallMonmentPower extends BasePower {
    public static final String POWER_ID = makeID(SmallMonmentPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public boolean isEffected=false;

    public SmallMonmentPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        updateDescription();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0];
        if(isEffected){
            description+=DESCRIPTIONS[1];
        }
    }

    public void applyEffect(){
        isEffected=true;
        flash();
        updateDescription();
    }


}
