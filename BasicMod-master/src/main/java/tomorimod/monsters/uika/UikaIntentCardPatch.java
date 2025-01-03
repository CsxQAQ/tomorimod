package tomorimod.monsters.uika;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UikaIntentCardPatch {
    @SpirePatch(
            clz= AbstractMonster.class,
            method=SpirePatch.CLASS
    )
    public static class AbstractMonsterFieldPatch{
        public static SpireField<AbstractCard> intentCard = new SpireField<>(() -> null);
    }

    @SpirePatch(
            clz=AbstractMonster.class,
            method = "renderIntent"
    )
    public static class RenderCardPatch{
        @SpirePostfixPatch
        public static void postfix(AbstractMonster __instance, SpriteBatch sb){
            if(AbstractMonsterFieldPatch.intentCard.get(__instance)!=null){
                AbstractMonsterFieldPatch.intentCard.get(__instance).render(sb);
            }
        }
    }


    public static void setPosition(AbstractCard card,float x,float y){
        card.current_x = x;
        card.target_x = x;
        card.current_y = y;
        card.target_y = y;
        card.drawScale=0.5f;
        card.targetDrawScale = 0.5f;
    }
}
