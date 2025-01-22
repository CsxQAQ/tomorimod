package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.powers.BasePower;
import tomorimod.powers.forms.BaseFormPower;
import tomorimod.powers.forms.FormEffect;

import static tomorimod.TomoriMod.makeID;

public class AstronomyMinisterPower extends BasePower {
    public static final String POWER_ID = makeID(AstronomyMinisterPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public AstronomyMinisterPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.updateDescription();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+" 。";
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ScryAction(amount));
    }

}
