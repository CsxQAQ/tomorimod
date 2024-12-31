package tomorimod.monsters.taki;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TakiPressurePatch {
    @SpirePatch(
            clz= AbstractCard.class,
            method=SpirePatch.CLASS
    )
    public static class AbstractPressureFieidPatch {
        public static SpireField<Boolean> isTakiLocked = new SpireField<>(() -> false);
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "canUse"
    )
    public static class AbstractCardCanUsePatch{
        @SpirePrefixPatch
        public static SpireReturn<Boolean> prefix(AbstractCard __instance,AbstractPlayer p, AbstractMonster m){
            if(AbstractPressureFieidPatch.isTakiLocked.get(__instance)){
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
        }
    }

}
