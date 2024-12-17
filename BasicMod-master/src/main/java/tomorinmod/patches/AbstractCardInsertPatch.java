package tomorinmod.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import tomorinmod.savedata.customdata.CraftingRecipes;

import static tomorinmod.BasicMod.imagePath;


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
public class AbstractCardInsertPatch {
//    @SpireInsertPatch(
//            rloc=1
//
//    )
//    public static void insert(AbstractCard __instance){
//        AbstractCardInsertPatch.initializeMaterialIcon(__instance);
//    }
    @SpirePostfixPatch
    public static void postFix(AbstractCard __instance){
        initializeMaterialIcon(__instance);
    }

    public static void initializeMaterialIcon(AbstractCard card){
        setMaterialAndLevel(card);
        try {
            if(AbstractCardFieldPatch.material.get(card)!=""){
                AbstractCardFieldPatch.ICON.set(card, new Texture(imagePath("materials/" + AbstractCardFieldPatch.material.get(card) + ".png")));
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
                case COMMON://
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
    }

}
