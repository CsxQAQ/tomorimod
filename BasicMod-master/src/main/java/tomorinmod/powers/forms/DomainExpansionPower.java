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

    private int magicNumber;

    public DomainExpansionPower(AbstractCreature owner,int amount,int magicNumber) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.magicNumber=magicNumber;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        applyEffectPower();
    }

    @Override
    public void updateDescription(){
        description=DESCRIPTIONS[0]+magicNumber+ "层 #y重力 。";
    }
//    @Override
//    public void applyFormPower() {
//        addToBot(new ApplyPowerAction(AbstractDungeon.player,
//                AbstractDungeon.player, new DomainExpansionPower(AbstractDungeon.player,1), 1));
//    }

    @Override
    public void applyEffectPower() {
        addToBot(new ApplyGravityAction(3));
    }
}
