package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static tomorinmod.BasicMod.makeID;

public class AstronomyMinisterPowerUpgraded extends BaseFormPower implements FormEffect{
    public static final String POWER_ID = makeID(AstronomyMinisterPowerUpgraded.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public AstronomyMinisterPowerUpgraded(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public void atStartOfTurn() {
        applyEffectPower();
    }

    @Override
    public void applyFormPower() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,
                AbstractDungeon.player, new AstronomyMinisterPowerUpgraded(AbstractDungeon.player), 1));
    }

    @Override
    public void applyEffectPower() {
        addToBot(new ScryAction(3));
    }
}
