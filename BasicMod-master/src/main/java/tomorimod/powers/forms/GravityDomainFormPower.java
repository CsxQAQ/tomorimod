package tomorimod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import tomorimod.actions.ApplyGravityAction;
import tomorimod.powers.GravityPower;

import static tomorimod.TomoriMod.makeID;

public class GravityDomainFormPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(GravityDomainFormPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public GravityDomainFormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        //this.amount=amount;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer){
            applyEffectPower();
        }else{
            addToBot(new ApplyPowerAction(this.owner,this.owner,
                    new GravityPower(this.owner,this.amount),this.amount));
        }
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+ " 层 #y重力 。";
        super.updateDescription();
    }

    @Override
    public void applyEffectPower() {
        flash();
        addToBot(new ApplyGravityAction(amount));
    }
}
