package tomorinmod.patches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import tomorinmod.savedata.customdata.CraftingRecipes;
import tomorinmod.util.RenderUtils;

import static tomorinmod.BasicMod.imagePath;


@SpirePatch(
        clz= AbstractCard.class,
        method = "render",
        paramtypez={
                SpriteBatch.class
        }
)
public class AbstractCardRenderPatch {

    @SpirePostfixPatch
    public static void postFix(AbstractCard __instance,SpriteBatch sb){
        draw(__instance,sb);
    }

    public static void draw(AbstractCard card,SpriteBatch sb){
        if(AbstractCardFieldPatch.ICON.get(card)!=null) {
            //RenderUtils.RenderBadge(sb,this,this.ICON,0,this.transparency);
            RenderUtils.RenderBadge(sb, card, AbstractCardFieldPatch.ICON.get(card), 0, card.transparency);

        }
    }



}
