package tomorinmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.powers.BasePower;
import tomorinmod.powers.Gravity;

import static tomorinmod.BasicMod.makeID;

public class Shine extends BasePower {
    public static final String POWER_ID = makeID("Shine");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public Shine(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount = amount;
        updateGravityInteraction(); // 初始化时检查并处理 Gravity
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        updateGravityInteraction(); // 每次叠加时检查 Gravity
    }

    private void updateGravityInteraction() {
        AbstractPower gravity = this.owner.getPower(Gravity.POWER_ID);
        if (gravity != null) {
            int gravityAmount = gravity.amount;
            if (gravityAmount > 0) {
                // 计算相互抵消的层数
                int reduceAmount = Math.min(this.amount, gravityAmount);

                // 更新层数
                this.amount -= reduceAmount;
                gravity.amount -= reduceAmount;

                // 移除层数为 0 的 Power
                if (this.amount <= 0) AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
                if (gravity.amount <= 0) AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Gravity.POWER_ID));
            }
        }
    }
}
