package tomorimod.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import tomorimod.cards.notshow.Cucumber;
import tomorimod.cards.notshow.SakiShadow;
import tomorimod.monsters.uika.CustomCardTrailEffect;

import java.util.ArrayList;

public class CustomColorTrailEffectPatch {

    @SpirePatch(
            clz= Soul.class,
            method=SpirePatch.CLASS
    )
    public static class SoulFieldPatch {
        public static SpireField<Boolean> isUika = new SpireField<>(() -> false);
    }

    @SpirePatch(
            clz=Soul.class,
            method="updateMovement"
    )
    public static class setUikaEffect {

        @SpirePatch(
                clz = Soul.class,
                method = "updateMovement"
        )
        public static class SetUikaEffect {

            @SpireInsertPatch(
                    locator = Locator.class
            )
            public static SpireReturn Insert(Soul __instance, float ___vfxTimer, ArrayList<Vector2> ___controlPoints,
                                             Vector2 ___pos, CatmullRomSpline<Vector2> ___crs, Vector2[] ___points) {
                Color effectColor = null;

                if (SoulFieldPatch.isUika.get(__instance)) {
                    effectColor = Color.GOLD.cpy();
                } else if (__instance.card instanceof SakiShadow) {
                    effectColor = Color.RED.cpy();
                } else if (__instance.card instanceof Cucumber) {
                    effectColor = Color.GREEN.cpy();
                }

                if (effectColor != null) {
                    applyTrailEffect(__instance, ___vfxTimer, ___controlPoints, ___pos, ___crs, ___points, effectColor);
                    return SpireReturn.Return();
                } else {
                    return SpireReturn.Continue();
                }
            }

            private static void applyTrailEffect(Soul __instance, float vfxTimer, ArrayList<Vector2> controlPoints,
                                                 Vector2 pos, CatmullRomSpline<Vector2> crs, Vector2[] points, Color color) {
                if (!__instance.isDone && vfxTimer < 0.0F) {
                    vfxTimer = 0.015F;
                    if (!controlPoints.isEmpty()) {
                        if (!controlPoints.get(0).equals(pos)) {
                            controlPoints.add(pos.cpy());
                        }
                    } else {
                        controlPoints.add(pos.cpy());
                    }

                    if (controlPoints.size() > 10) {
                        controlPoints.remove(0);
                    }

                    if (controlPoints.size() > 3) {
                        Vector2[] vec2Array = new Vector2[0];
                        crs.set(controlPoints.toArray(vec2Array), false);

                        for (int i = 0; i < 20; ++i) {
                            if (points[i] == null) {
                                points[i] = new Vector2();
                            }

                            Vector2 derp = crs.valueAt(points[i], (float) i / 19.0F);

                            CustomCardTrailEffect effect = new CustomCardTrailEffect(color);
                            effect.init(derp.x, derp.y);
                            AbstractDungeon.topLevelEffects.add(effect);
                        }
                    }
                }
            }


            private static class Locator extends SpireInsertLocator {
                public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                    Matcher finalMatcher = new Matcher.FieldAccessMatcher(Soul.class, "vfxTimer");

                    int[] lines = LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
                    return new int[]{lines[0] + 1};
                }
            }
        }

    }
}

