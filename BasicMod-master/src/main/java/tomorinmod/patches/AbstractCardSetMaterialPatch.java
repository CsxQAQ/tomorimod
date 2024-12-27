package tomorinmod.patches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;
import tomorinmod.savedata.customdata.CraftingRecipes;
import tomorinmod.util.RenderUtils;

import static tomorinmod.BasicMod.imagePath;
import static tomorinmod.BasicMod.makeID;


public class AbstractCardSetMaterialPatch {
    @SpirePatch(
            clz= AbstractCard.class,
            method=SpirePatch.CLASS
    )
    public static class AbstractCardFieldPatch {
        public static SpireField<String> material = new SpireField<>(() -> "");
        public static SpireField<Integer> level = new SpireField<>(() -> -1);
        public static SpireField<Texture> ICON = new SpireField<>(() -> null);
    }

    @SpirePatch(
            clz= AbstractCard.class,
            method = "<ctor>",
            paramtypez={
                    String.class,
                    String.class,
                    String.class,
                    int.class,
                    String.class,
                    AbstractCard.CardType.class,
                    AbstractCard.CardColor.class,
                    AbstractCard.CardRarity.class,
                    AbstractCard.CardTarget.class,
                    DamageInfo.DamageType.class
            }
    )
    public static class AbstractCardInsertPatch {
        @SpirePostfixPatch
        public static void postFix(AbstractCard __instance) {
            if (CardCrawlGame.mode!= CardCrawlGame.GameMode.CHAR_SELECT) {
                initializeMaterialIcon(__instance);
            }
        }
    }

    @SpirePatch(
            clz= AbstractCard.class,
            method = "render",
            paramtypez={
                    SpriteBatch.class
            }
    )
    public static class AbstractCardRenderPatch {

        @SpirePostfixPatch
        public static void postFix(AbstractCard __instance, SpriteBatch sb) {
            draw(__instance, sb);
        }
    }


    public static void initializeMaterialIcon(AbstractCard card){
        setMaterialAndLevel(card);
        try {
            if(AbstractCardFieldPatch.material.get(card)!=""){
                switch(card.rarity){
                    case COMMON:
                    case BASIC:
                    case SPECIAL:
                        AbstractCardFieldPatch.ICON.set(card, new Texture(imagePath("materials/card/" +
                                AbstractCardFieldPatch.material.get(card) + "_common.png")));
                        break;
                    case UNCOMMON:
                        AbstractCardFieldPatch.ICON.set(card, new Texture(imagePath("materials/card/" +
                                AbstractCardFieldPatch.material.get(card) + "_uncommon.png")));
                        break;
                    case RARE:
                        AbstractCardFieldPatch.ICON.set(card, new Texture(imagePath("materials/card/" +
                                AbstractCardFieldPatch.material.get(card) + "_rare.png")));
                        break;
                }
                if(card.cardID.equals(makeID("Yellow"))||card.cardID.equals(makeID("Green"))||card.cardID.equals(makeID("Red"))){
                    AbstractCardFieldPatch.ICON.set(card, new Texture(imagePath("materials/card/" +
                            AbstractCardFieldPatch.material.get(card) + "_rare.png")));
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load icon texture: " + e.getMessage());
            AbstractCardFieldPatch.ICON.set(card, null);
        }
    }

    public static void setMaterialAndLevel(AbstractCard card){
        if(CraftingRecipes.getInstance().cardMaterialHashMap==null){
            return;
        }
        if(CraftingRecipes.getInstance().cardMaterialHashMap.get(card.cardID)!=null){
            AbstractCardFieldPatch.material.set(card,CraftingRecipes.getInstance().cardMaterialHashMap.get(card.cardID));
        }
        if(card.rarity!=null){
            switch (card.rarity) {
                case COMMON:
                case BASIC:
                case SPECIAL:
                    AbstractCardFieldPatch.level.set(card,1);
                    break;
                case UNCOMMON:
                    AbstractCardFieldPatch.level.set(card,2);
                    break;
                case RARE:
                    AbstractCardFieldPatch.level.set(card,3);
                    break;
                default:
                    break;
            }
        }
        if(card.cardID.equals(makeID("Yellow"))||card.cardID.equals(makeID("Green"))||card.cardID.equals(makeID("Red"))){
            AbstractCardFieldPatch.level.set(card,3);
        }
    }

    public static void draw(AbstractCard card,SpriteBatch sb){
        if(AbstractCardFieldPatch.ICON.get(card)!=null) {
            //RenderUtils.RenderBadge(sb,this,this.ICON,0,this.transparency);
            RenderUtils.RenderBadge(sb, card, AbstractCardFieldPatch.ICON.get(card), 0, card.transparency);

        }
    }

}
