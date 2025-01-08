package tomorimod.monsters.uika;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.FloatCounter;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.CardTrailEffect;
import com.megacrit.cardcrawl.vfx.combat.EmpowerEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import tomorimod.cards.uikacard.UikaCard;
import tomorimod.savedata.customdata.CraftingRecipes;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GoldTrailEffectPatch {

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

        @SpireInsertPatch(
                locator=Locator.class
        )
        public static SpireReturn Insert(Soul __instance, float ___vfxTimer, ArrayList<Vector2> ___controlPoints,
                                  Vector2 ___pos, CatmullRomSpline<Vector2> ___crs,Vector2[] ___points) {
            if(SoulFieldPatch.isUika.get(__instance)){
                if (!__instance.isDone && ___vfxTimer < 0.0F) {
                    ___vfxTimer = 0.015F;
                    if (!___controlPoints.isEmpty()) {
                        if (!((Vector2)___controlPoints.get(0)).equals(___pos)) {
                            ___controlPoints.add(___pos.cpy());
                        }
                    } else {
                        ___controlPoints.add(___pos.cpy());
                    }

                    if (___controlPoints.size() > 10) {
                        ___controlPoints.remove(0);
                    }

                    if (___controlPoints.size() > 3) {
                        Vector2[] vec2Array = new Vector2[0];
                        ___crs.set(___controlPoints.toArray(vec2Array), false);

                        for(int i = 0; i < 20; ++i) {
                            if (___points[i] == null) {
                                ___points[i] = new Vector2();
                            }

                            Vector2 derp = (Vector2)___crs.valueAt(___points[i], (float)i / 19.0F);

                            //CardTrailEffect effect = (CardTrailEffect)trailEffectPool.obtain();
                            GoldenCardTrailEffect effect=new GoldenCardTrailEffect();
                            effect.init(derp.x, derp.y);
                            //CardTrailEffectFieldPatch.isUika.set(effect,true);
                            AbstractDungeon.topLevelEffects.add(effect);
                        }
                    }
                }
                return SpireReturn.Return();
            }else{
                return SpireReturn.Continue();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(Soul.class, "vfxTimer");

                int[] lines=LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
                return new int[]{lines[0]+1};
            }
        }
    }


}

