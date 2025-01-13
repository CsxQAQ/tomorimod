package tomorimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.monsters.taki.TakiPressurePatch;
import tomorimod.tags.CustomTags;

public class MakeStatEquivalentCopyPatch {

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class StateEquelPatch{
        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> prefix(AbstractCard __instance){
            AbstractCard card = __instance.makeCopy();
            if(!(card instanceof BaseMusicCard)){
                for(int i = 0; i < __instance.timesUpgraded; ++i) {
                    card.upgrade();
                }
                card.name = __instance.name;
                card.upgraded = __instance.upgraded;
            }

            card.target = __instance.target;
            card.timesUpgraded = __instance.timesUpgraded;
            card.baseDamage = __instance.baseDamage;
            card.baseBlock = __instance.baseBlock;
            card.baseMagicNumber = __instance.baseMagicNumber;
            card.cost = __instance.cost;
            card.costForTurn = __instance.costForTurn;
            card.isCostModified = __instance.isCostModified;
            card.isCostModifiedForTurn = __instance.isCostModifiedForTurn;
            card.inBottleLightning = __instance.inBottleLightning;
            card.inBottleFlame = __instance.inBottleFlame;
            card.inBottleTornado = __instance.inBottleTornado;
            card.isSeen = __instance.isSeen;
            card.isLocked = __instance.isLocked;
            card.misc = __instance.misc;
            card.freeToPlayOnce = __instance.freeToPlayOnce;


            card.exhaust=__instance.exhaust;
            card.isInnate=__instance.isInnate;
            TakiPressurePatch.AbstractPressureFieidPatch.isTakiLocked.set(card,
                    TakiPressurePatch.AbstractPressureFieidPatch.isTakiLocked.get(__instance));

            if(__instance.tags.contains(CustomTags.SHORTTERMGOAL)){
                card.tags.add(CustomTags.SHORTTERMGOAL);
            }

            return SpireReturn.Return(card);
        }
    }
}
