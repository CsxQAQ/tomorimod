package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.powers.Gravity;
import tomorinmod.powers.Shine;

public class ApplyGravityAction extends AbstractGameAction {

    private int amount;
    public ApplyGravityAction(int amount) {
        this.amount=amount;
    }

    public void update() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Gravity(AbstractDungeon.player, amount), amount));
        addToBot(new CheckShineGravityAction(AbstractDungeon.player));
        isDone=true;
    }

}
