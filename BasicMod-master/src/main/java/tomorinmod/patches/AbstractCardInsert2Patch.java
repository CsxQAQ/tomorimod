//package tomorinmod.patches;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import tomorinmod.savedata.customdata.CraftingRecipes;
//
//import static tomorinmod.BasicMod.imagePath;
//
//@SpirePatch(
//        clz= AbstractCard.class,
//        method = "<ctor>",
//        paramtypez={
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
//public class AbstractCardInsert2Patch {
//    @SpireInsertPatch(
//            rloc=1
//
//    )
//    public static void Insert(AbstractCard __instance){
//        AbstractCardInsertPatch.initializeMaterialIcon(__instance);
//    }
//
//
//}
