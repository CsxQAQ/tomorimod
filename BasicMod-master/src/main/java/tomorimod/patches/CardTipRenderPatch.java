package tomorimod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import tomorimod.monsters.taki.TakiPressurePatch;
import tomorimod.tags.CustomTags;

import java.util.ArrayList;

import static tomorimod.TomoriMod.makeID;

public class CardTipRenderPatch {

    @SpirePatch(
            clz = TipHelper.class,
            method = "renderKeywords",
            paramtypez = { float.class, float.class, com.badlogic.gdx.graphics.g2d.SpriteBatch.class, ArrayList.class }
    )
    public static class RenderKeywordsPatch {
        @SpirePrefixPatch
        public static void Prefix(float x, float y, SpriteBatch sb, ArrayList<String> keywords,AbstractCard ___card) {
            if (___card.exhaust==true&&!keywords.contains("消耗")) {
                keywords.add("消耗");
            }
            if(___card.selfRetain==true&&!keywords.contains("保留")){
                keywords.add("保留");
            }
            if(TakiPressurePatch.AbstractPressureFieidPatch.isTakiLocked.get(___card)&&!keywords.contains(makeID("压力"))){
                keywords.add(makeID("压力"));
            }

            if(___card.tags.contains(CustomTags.SHORTTERMGOAL)&&!keywords.contains(makeID("短期目标"))){
                keywords.add(makeID("短期目标"));
            }
            if(AbstractDungeon.player!=null&&!checkInMasterDeck(___card)&&!keywords.contains(makeID("衍生"))){
                keywords.add(makeID("衍生"));
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
