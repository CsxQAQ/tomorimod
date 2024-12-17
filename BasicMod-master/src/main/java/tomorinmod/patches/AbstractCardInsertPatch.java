//package tomorinmod.patches;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.evacipated.cardcrawl.modthespire.lib.SpireField;
//import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.DamageInfo;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import tomorinmod.savedata.customdata.CraftingRecipes;
//
//import static tomorinmod.BasicMod.characterPath;
//import static tomorinmod.BasicMod.imagePath;
//
//@SpirePatch(
//        clz= AbstractCard.class,
//        method = "<ctor>",
//        paramtypez={
//            String.class,
//            String.class,
//            String.class,
//            String.class,
//                int.class,
//                String.class,
//                AbstractCard.CardType.class,
//                AbstractCard.CardColor.class,
//                AbstractCard.CardRarity.class,
//                AbstractCard.CardTarget.class
//        }
//)
//public class AbstractCardInsertPatch {
//    @SpireInsertPatch(
//            rloc=1
//
//    )
//    public static void Insert(AbstractCard __instance){
//        initializeMaterialIcon(__instance);
//    }
//
//    public static void initializeMaterialIcon(AbstractCard card){
//        setMaterialAndLevel(card);
//        try {
//            if(AbstractCardFieldPatch.material.get(card)!=""){
//                AbstractCardFieldPatch.ICON.set(card, new Texture(imagePath("materials/" + AbstractCardFieldPatch.material.get(card) + ".png")));
//            }
//        } catch (Exception e) {
//            System.err.println("Failed to load icon texture: " + e.getMessage());
//            AbstractCardFieldPatch.ICON.set(card, null);
//        }
//    }
//
//    public static void setMaterialAndLevel(AbstractCard card){
//        if(CraftingRecipes.getInstance().cardMaterialHashMap==null){
//            return;
//        }
//        if(CraftingRecipes.getInstance().cardMaterialHashMap.get(card.cardID)!=null){
//            AbstractCardFieldPatch.material.set(card,CraftingRecipes.getInstance().cardMaterialHashMap.get(card.cardID));
//        }
//        if(card.rarity!=null){
//            switch (card.rarity) {
//                case COMMON:
//                    AbstractCardFieldPatch.level.set(card,1);
//                    break;
//                case UNCOMMON:
//                    AbstractCardFieldPatch.level.set(card,2);
//                    break;
//                case RARE:
//                    AbstractCardFieldPatch.level.set(card,3);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//}
