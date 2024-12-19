package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.cards.customcards.MygoTogether;
import tomorinmod.powers.GravityPower;
import tomorinmod.powers.ShinePower;

public class ReversalAction extends AbstractGameAction {
    private final AbstractPlayer p;

    public ReversalAction(AbstractPlayer p) {
        this.p = p;
    }

    @Override
    public void update() {
        AbstractPower gravity = AbstractDungeon.player.getPower(GravityPower.POWER_ID);
        AbstractPower shine = AbstractDungeon.player.getPower(ShinePower.POWER_ID);

        int gravityAmount = gravity != null ? gravity.amount : 0;
        int shineAmount = shine != null ? shine.amount : 0;

        if (MygoTogether.isMygoTogetherUsed) {
            if (shine != null) {
                shine.amount = gravityAmount;
                shine.updateDescription();
            }
            if (gravity != null) {
                gravity.amount = shineAmount;
                gravity.updateDescription();
            }
        } else {
            if (shineAmount > 0) {
                addToBot(new RemoveSpecificPowerAction(p, p, ShinePower.POWER_ID));
                addToBot(new ApplyGravityAction(shineAmount));
            }
            if (gravityAmount > 0) {
                addToBot(new RemoveSpecificPowerAction(p, p, GravityPower.POWER_ID));
                addToBot(new ApplyShineAction(gravityAmount));
            }
        }

        isDone = true;
    }
}
