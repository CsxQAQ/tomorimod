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

    public DomainExpansionPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        applyEffectPower();
    }

    @Override
    public void applyFormPower() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,
                AbstractDungeon.player, new DomainExpansionPower(AbstractDungeon.player), 1));
    }

    @Override
    public void applyEffectPower() {
        addToBot(new ApplyGravityAction(3));
    }
}
