package tomorimod.patches;

import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.core.Settings;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import tomorimod.cards.customcards.Recycle;
import tomorimod.monsters.sakishadow.ShuffleFromMasterDeckPatch;

import java.util.ArrayList;
import java.util.Set;

public class RecyclePatch {
    @SpirePatch(
            clz= Soul.class,
            method=SpirePatch.CLASS
    )
    public static class SoulFieldPatch {
        public static SpireField<Boolean> isFromExhaustPile = new SpireField<>(() -> false);
    }

    @SpirePatch(
            clz=Soul.class,
            method = "shuffle",
            paramtypez = {
                    AbstractCard.class, boolean.class
            }
    )
    public static class ShuffleInsertPatch{
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void insert(Soul __instance, AbstractCard card, boolean isInvisible, @ByRef Vector2[] ___pos,
                                  float ___DISCARD_X,float ___DISCARD_Y){
            if(SoulFieldPatch.isFromExhaustPile.get(__instance)){
                ___pos[0]=new Vector2(___DISCARD_X,___DISCARD_Y+100.0f* Settings.scale);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(Soul.class, "pos");

                int[] lines = LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
                return new int[]{lines[0]+1};
            }
        }
    }
}
