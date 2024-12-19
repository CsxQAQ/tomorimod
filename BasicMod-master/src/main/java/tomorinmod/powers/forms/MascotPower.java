package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class MascotPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(MascotPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private boolean isEffected=false;

    public MascotPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount=amount;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if(!isEffected){
            applyEffectPower();
            isEffected=true;
            description = description+"（ #y已生效 ）";
        }
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+ "点 [E] 。";
        super.updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(power.name.equals(this.name)){
            isEffected=false;
        }
    }

    @Override
    public void applyEffectPower() {
        addToBot(new GainEnergyAction(amount));
    }
}
