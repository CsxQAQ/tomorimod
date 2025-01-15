package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.actions.ApplyGravityAction;
import tomorimod.powers.BasePower;
import tomorimod.powers.GravityPower;
import tomorimod.powers.forms.BaseFormPower;
import tomorimod.powers.forms.FormEffect;

import static tomorimod.TomoriMod.makeID;

public class GravityDomainPower extends BasePower {
    public static final String POWER_ID = makeID(GravityDomainPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public GravityDomainPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        if(isPlayer){
            addToBot(new ApplyGravityAction(amount));
        }else{
            addToBot(new ApplyPowerAction(this.owner,this.owner,
                    new GravityPower(this.owner,this.amount),this.amount));
        }
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+ " 层 #y重力 。";
    }
}
