package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static tomorinmod.BasicMod.makeID;

public class PantPowerUpgraded extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(PantPowerUpgraded.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public PantPowerUpgraded(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        applyEffectPower();
    }

    @Override
    public void applyFormPower() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,
                AbstractDungeon.player, new PantPowerUpgraded(AbstractDungeon.player), 1));
    }

    @Override
    public void applyEffectPower() {
        addToTop(new GainBlockAction(AbstractDungeon.player,4));
    }


}
