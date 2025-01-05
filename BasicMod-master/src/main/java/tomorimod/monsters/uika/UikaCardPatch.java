package tomorimod.monsters.uika;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import tomorimod.cards.uikacard.UikaCard;

public class UikaCardPatch {

    @SpirePatch(
            clz= AbstractCard.class,
            method="hover"
    )
    public static class HoverPatch{
        @SpirePrefixPatch
        public static SpireReturn<?> prefix(AbstractCard __instance,@ByRef boolean[] ___hovered){
            if(__instance instanceof UikaCard){
                if(!___hovered[0]){
                    ___hovered[0]=true;
                    __instance.drawScale = 0.6F;
                    __instance.targetDrawScale = 0.6F;
                }
                return SpireReturn.Return();
            }else{
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(
            clz= AbstractCard.class,
            method="unhover"
    )
    public static class UnHoverPatch{
        @SpirePrefixPatch
        public static SpireReturn<?> prefix(AbstractCard __instance,@ByRef boolean[] ___hovered,@ByRef float[] ___hoverDuration,@ByRef boolean[] ___renderTip){
            if(__instance instanceof UikaCard){
                if(___hovered[0]){
                    ___hovered[0]=false;
                    ___hoverDuration[0]=0.0f;
                    ___renderTip[0]=false;
                    __instance.targetDrawScale = 0.5F;
                }
                return SpireReturn.Return();
            }else{
                return SpireReturn.Continue();
            }
        }
    }
}
