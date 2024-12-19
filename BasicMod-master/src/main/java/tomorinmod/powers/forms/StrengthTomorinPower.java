package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.actions.ApplyGravityAction;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class StrengthTomorinPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(StrengthTomorinPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public StrengthTomorinPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        applyEffectPower();
    }

    @Override
    public void applyFormPower() {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthTomorinPower(AbstractDungeon.player,1), 1)
        );
    }

    @Override
    public void applyEffectPower() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
    }
}
