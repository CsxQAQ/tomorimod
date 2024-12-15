package tomorinmod.powers.forms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.actions.ApplyGravityAction;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class SingerTomorinPower extends BasePower implements FormEffect{
    public static final String POWER_ID = makeID(SingerTomorinPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public SingerTomorinPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0); // 不使用 amount 作为层数
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void applyFormPower() {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SingerTomorinPower(AbstractDungeon.player), 1)
        );
    }

    @Override
    public void applyEffectPower() {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));

        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
    }
}
