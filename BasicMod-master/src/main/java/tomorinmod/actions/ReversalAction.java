package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.actions.CheckShineGravityAction;
import tomorinmod.cards.MygoTogether;
import tomorinmod.powers.Gravity;
import tomorinmod.powers.Shine;

public class ReversalAction extends AbstractGameAction {
    private final AbstractPlayer p;

    public ReversalAction(AbstractPlayer p) {
        this.p = p;
    }

    @Override
    public void update() {
        AbstractPower gravity = AbstractDungeon.player.getPower(Gravity.POWER_ID);
        AbstractPower shine = AbstractDungeon.player.getPower(Shine.POWER_ID);

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
                addToTop(new RemoveSpecificPowerAction(p, p, Shine.POWER_ID));
                addToTop(new ApplyPowerAction(p, p, new Gravity(p, shineAmount), shineAmount));
                addToTop(new CheckShineGravityAction(p));
            }
            if (gravityAmount > 0) {
                addToTop(new RemoveSpecificPowerAction(p, p, Gravity.POWER_ID));
                addToTop(new ApplyPowerAction(p, p, new Shine(p, gravityAmount), gravityAmount));
                addToTop(new CheckShineGravityAction(p));
            }
        }

        isDone = true;
    }
}
