package tomorimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static tomorimod.TomoriMod.makeID;

@SpirePatch(
        clz = VigorPower.class,
        method = "onUseCard"
)
public class VigorPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> prefix(VigorPower __instance, AbstractCard card, UseCardAction action) {
        // 如果不是攻击卡，则继续使用原本逻辑即可
        if (card.type != AbstractCard.CardType.ATTACK) {
            return null;
        }

        // 原本的闪光特效
        __instance.flash();

        // 判断玩家是否具有某个 Power
        if (__instance.owner.hasPower(makeID("YeyingranPower"))) {
            // 如果有，则只减少 amount
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(__instance.owner, __instance.owner, "Vigor", 1));
            // 如果减少完后小于等于 0，就移除
            if (__instance.amount <= 0) {
                AbstractDungeon.actionManager.addToBottom(
                        new RemoveSpecificPowerAction(__instance.owner, __instance.owner, "Vigor"));
            }
        } else {
            // 如果没有，则执行和原版一样的移除操作
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(__instance.owner, __instance.owner, "Vigor"));
        }
        return SpireReturn.Return();

        // patch 前缀中直接处理了逻辑，就不需要再让原方法继续执行了
        // 因此可以 return，从而阻断原方法后续逻辑
        // 如果你想让后续逻辑继续执行，可以不 return 或者用其他写法
    }
}