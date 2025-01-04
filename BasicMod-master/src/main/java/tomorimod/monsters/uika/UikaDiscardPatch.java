package tomorimod.monsters.uika;

import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.core.Settings;
import tomorimod.monsters.uika.uikacard.UikaCard;

public class UikaDiscardPatch {

    @SpirePatch(
            clz= Soul.class,
            method = "discard",
            paramtypez = {
                    AbstractCard.class, boolean.class
            }
    )
    public static class DiscardInsertPatch{
        @SpireInsertPatch(
                rloc=8
        )
        public static void insert(Soul __instance, AbstractCard card, boolean isInvisible, CardGroup ___group, @ByRef Vector2[] ___pos, @ByRef Vector2[] ___target){
            if(card instanceof UikaCard){
                ___pos[0]=new Vector2(card.current_x,card.current_y);
                ___target[0]=new Vector2(Settings.WIDTH+200.0f*Settings.scale,Settings.HEIGHT-200.0f*Settings.scale);
                ___group.removeTopCard();
            }
        }
    }

    @SpirePatch(
            clz= Soul.class,
            method = "isCarryingCard"
    )
    public static class isCarryingCardPatch{
        @SpirePrefixPatch
        public static SpireReturn<Boolean> prefix(Soul __instance){
            if(__instance.card instanceof UikaCard){
                return SpireReturn.Return(true);
            }else{
                return SpireReturn.Continue();
            }
        }
    }
}

