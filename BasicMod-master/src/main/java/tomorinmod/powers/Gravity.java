package tomorinmod.powers;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class Gravity extends BasePower {
    public static final String POWER_ID = makeID("Gravity");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public Gravity(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount = amount;
        updateShineInteraction(); // 初始化时检查并处理 Shine
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        updateShineInteraction(); // 每次叠加时检查 Shine
    }

    private void updateShineInteraction() {
        AbstractPower shine = this.owner.getPower(Shine.POWER_ID);
        if (shine != null) {
            int shineAmount = shine.amount;
            if (shineAmount > 0) {
                // 计算相互抵消的层数
                int reduceAmount = Math.min(this.amount, shineAmount);

                // 更新层数
                this.amount -= reduceAmount;
                shine.amount -= reduceAmount;

                // 移除层数为 0 的 Power
                if (this.amount <= 0) AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
                if (shine.amount <= 0) AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, Shine.POWER_ID));
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) return;

        int[] damageArray = new int[AbstractDungeon.getMonsters().monsters.size()];
        for (int i = 0; i < damageArray.length; i++) {
            damageArray[i] = this.amount;
        }
        addToBot(new DamageAllEnemiesAction(this.owner, damageArray, DamageInfo.DamageType.THORNS, DamageAllEnemiesAction.AttackEffect.SLASH_HORIZONTAL));
    }
}
