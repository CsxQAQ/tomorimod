package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.actions.ApplyGravityAction;

import static tomorinmod.BasicMod.makeID;

public class DomainExpansionPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(DomainExpansionPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public DomainExpansionPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount=amount;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        applyEffectPower();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+amount+ "层 #y重力 。";
        super.updateDescription();
    }

    @Override
    public void applyEffectPower() {
        flash();
        addToBot(new ApplyGravityAction(amount));
    }
}
