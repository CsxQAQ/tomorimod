package tomorimod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorimod.powers.GravityPower;
import tomorimod.powers.ShinePower;

import static tomorimod.TomoriMod.makeID;

public class CheckShineGravityAction extends AbstractGameAction {
    private final AbstractCreature target;

    public CheckShineGravityAction(AbstractCreature target) {
        this.target = target;
    }

    @Override
    public void update() {

        if(AbstractDungeon.player.hasPower(makeID("MygoTogetherPower"))){
            this.isDone=true;
            return;
        }

        AbstractPower shine = target.getPower(ShinePower.POWER_ID);
        AbstractPower gravity = target.getPower(GravityPower.POWER_ID);

        if (shine != null && gravity != null) {
            int reduceAmount = Math.min(shine.amount, gravity.amount);

            // 更新层数
            shine.amount -= reduceAmount;
            gravity.amount -= reduceAmount;

            // 移除层数为 0 的 Power
            if (shine.amount <= 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(target, target, ShinePower.POWER_ID));
            }
            if (gravity.amount <= 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(target, target, GravityPower.POWER_ID));
            }
        }

        this.isDone = true;
    }
}
