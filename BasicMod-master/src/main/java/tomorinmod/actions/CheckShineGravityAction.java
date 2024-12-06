package tomorinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.powers.Gravity;
import tomorinmod.powers.Shine;

public class CheckShineGravityAction extends AbstractGameAction {
    private final AbstractCreature target;

    public CheckShineGravityAction(AbstractCreature target) {
        this.target = target;
    }

    @Override
    public void update() {
        AbstractPower shine = target.getPower(Shine.POWER_ID);
        AbstractPower gravity = target.getPower(Gravity.POWER_ID);

        if (shine != null && gravity != null) {
            int reduceAmount = Math.min(shine.amount, gravity.amount);

            // 更新层数
            shine.amount -= reduceAmount;
            gravity.amount -= reduceAmount;

            // 移除层数为 0 的 Power
            if (shine.amount <= 0) {
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(target, target, Shine.POWER_ID));
            }
            if (gravity.amount <= 0) {
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(target, target, Gravity.POWER_ID));
            }
        }

        this.isDone = true;
    }
}
