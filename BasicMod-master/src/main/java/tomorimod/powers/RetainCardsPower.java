package tomorimod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static tomorimod.TomoriMod.makeID;

public class RetainCardsPower extends BasePower {
    public static final String POWER_ID = makeID(RetainCardsPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public RetainCardsPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner,0);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (!c.isEthereal) {
                    c.retain = true;
                }
            }
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, makeID("RetainCardsPower")));
    }
}
