package tomorinmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.actions.CheckShineGravityAction;
import tomorinmod.cards.rare.Revolution;

import static tomorinmod.BasicMod.makeID;

public class GravityTomorinPower extends BasePower {
    public static final String POWER_ID = makeID("GravityTomorinPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public GravityTomorinPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0); // 不使用 amount 作为层数
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Gravity(AbstractDungeon.player,4), 4));
        AbstractDungeon.actionManager.addToBottom(new CheckShineGravityAction(AbstractDungeon.player));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
