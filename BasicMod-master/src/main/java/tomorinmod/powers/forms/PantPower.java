package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;

import static tomorinmod.BasicMod.makeID;

public class PantPower extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(PantPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public PantPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        applyEffectPower();
    }

    @Override
    public void applyFormPower() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,
                AbstractDungeon.player, new PantPower(AbstractDungeon.player,1), 1));
    }

    @Override
    public void applyEffectPower() {
        addToTop(new GainBlockAction(AbstractDungeon.player,3));
    }


}
