package tomorimod.powers.forms;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static tomorimod.TomoriMod.makeID;

public class AstronomyMinisterPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(AstronomyMinisterPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public AstronomyMinisterPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        //this.amount=amount;
        this.updateDescription();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+" 。";
        super.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        applyEffectPower();
    }

    @Override
    public void applyEffectPower() {
        flash();
        addToBot(new ScryAction(amount));
    }
}
