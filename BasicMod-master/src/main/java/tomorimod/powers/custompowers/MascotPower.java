package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.powers.BasePower;
import tomorimod.powers.forms.BaseFormPower;
import tomorimod.powers.forms.FormEffect;
import tomorimod.util.PlayerUtils;

import static tomorimod.TomoriMod.makeID;

public class MascotPower extends BasePower {
    public static final String POWER_ID = makeID(MascotPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public MascotPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if(PlayerUtils.getPowerNum(makeID("ShinePower"))>0){
            flash();
            addToBot(new GainEnergyAction(amount));
        }
        if(PlayerUtils.getPowerNum(makeID("GravityPower"))>0){
            flash();
            addToBot(new DrawCardAction(amount));
        }
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+ " 点 [E] 。"+DESCRIPTIONS[1]+ amount+" 张牌。";
    }
}
