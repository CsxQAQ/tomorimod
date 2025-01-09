package tomorimod.monsters.sakishadow;

import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import tomorimod.cards.notshow.WelcomeToAveMujica;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static tomorimod.TomoriMod.makeID;

public class WelcomeToAveMujicaPatch {

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="preBattlePrep"
    )
    public static class AbstractPlayerPreBattlePrepPatch{
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void insert(AbstractPlayer __instance){
            for(AbstractMonster monster:AbstractDungeon.getCurrRoom().monsters.monsters){
                if(monster instanceof SakiShadowMonster){
                    __instance.drawPile.clear();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new WelcomeToAveMujica()));
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "drawPile");

                int[] lines = LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
                return new int[]{lines[0]+1};
            }
        }
    }



}


