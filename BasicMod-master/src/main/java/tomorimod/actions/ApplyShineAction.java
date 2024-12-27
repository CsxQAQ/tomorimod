package tomorimod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.powers.ShinePower;

public class ApplyShineAction extends AbstractGameAction {

    private int amount;
    public ApplyShineAction(int amount) {
        this.amount=amount;
    }

    public void update() {
        addToTop(new CheckShineGravityAction(AbstractDungeon.player));
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ShinePower(AbstractDungeon.player, amount), amount));
        isDone=true;
    }

}
