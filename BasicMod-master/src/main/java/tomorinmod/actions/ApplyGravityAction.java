package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.powers.GravityPower;

public class ApplyGravityAction extends AbstractGameAction {

    private int amount;
    public ApplyGravityAction(int amount) {
        this.amount=amount;
    }

    public void update() {
        addToTop(new CheckShineGravityAction(AbstractDungeon.player));
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GravityPower(AbstractDungeon.player, amount), amount));
        isDone=true;
    }

}
