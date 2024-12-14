package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorinmod.powers.Gravity;
import tomorinmod.powers.Shine;
import tomorinmod.savedata.SaveForm;

public class ApplyShineAction extends AbstractGameAction {

    private int amount;
    public ApplyShineAction(int amount) {
        this.amount=amount;
    }

    public void update() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Shine(AbstractDungeon.player, amount), amount));
        addToBot(new CheckShineGravityAction(AbstractDungeon.player));
        isDone=true;
    }

}
