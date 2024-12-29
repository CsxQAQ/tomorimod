package tomorimod.patches;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.TipHelper;

import java.util.ArrayList;

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
        }
    }
}
