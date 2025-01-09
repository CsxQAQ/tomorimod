package tomorimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import tomorimod.cards.music.Yeyingran;

import static tomorimod.TomoriMod.makeID;

public class YeyingranPatch{
    @SpirePatch(
            clz = VigorPower.class,
            method = "onUseCard"
    )
    public static class VigorPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> prefix(VigorPower __instance, AbstractCard card, UseCardAction action) {
            if (card.type != AbstractCard.CardType.ATTACK) {
                return SpireReturn.Return();
            }

            __instance.flash();

            if (__instance.owner.hasPower(makeID("YeyingranPower"))) {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(__instance.owner, __instance.owner, "Vigor", 1));
                if (__instance.amount <= 0) {
                    AbstractDungeon.actionManager.addToBottom(
                            new RemoveSpecificPowerAction(__instance.owner, __instance.owner, "Vigor"));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(
                        new RemoveSpecificPowerAction(__instance.owner, __instance.owner, "Vigor"));
            }
            return SpireReturn.Return();

        }
    }

    @SpirePatch(
            clz = UseCardAction.class,
            method = "<ctor>",
            paramtypez = {
                    AbstractCard.class,
                    AbstractCreature.class
            }
    )
    public static class UseCardActionPatch{
        @SpirePostfixPatch
        public static void postfix(){
            if(Yeyingran.vigorNum>0){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,
                        AbstractDungeon.player,new VigorPower(AbstractDungeon.player,Yeyingran.vigorNum),Yeyingran.vigorNum));
                Yeyingran.vigorNum=0;
            }
        }
    }
}

