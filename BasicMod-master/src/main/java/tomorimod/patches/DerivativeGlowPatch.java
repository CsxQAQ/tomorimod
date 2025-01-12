package tomorimod.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.configs.TomoriConfig;


public class DerivativeGlowPatch {
    @SpirePatch(
            clz= AbstractCard.class,
            method="triggerOnGlowCheck"
    )
    public static class AbstractCardInsertPatch {
        @SpirePrefixPatch
        public static void preFix(AbstractCard __instance) {
            if(TomoriConfig.config.getBool("derivativeGlow-enabled")){
                if(checkInMasterDeck(__instance)){
                    __instance.glowColor = new Color(0.2F, 0.9F, 1.0F, 0.25F); //蓝
                }else{
                    __instance.glowColor = new Color(0.0F, 1.0F, 0.0F, 0.25F); //绿
                }
            }

        }
    }

    public static boolean checkInMasterDeck(AbstractCard card){
        if(AbstractDungeon.player!=null){
            for(AbstractCard c:AbstractDungeon.player.masterDeck.group){
                if(c.uuid.equals(card.uuid)){
                    return true;
                }
            }
        }
        return false;
    }

}
