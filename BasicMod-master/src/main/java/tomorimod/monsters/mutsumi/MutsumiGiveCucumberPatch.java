package tomorimod.monsters.mutsumi;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import tomorimod.cards.notshow.mutsumi.Cucumber;
import tomorimod.util.MonsterUtils;

import java.util.ArrayList;

import static tomorimod.TomoriMod.makeID;

public class MutsumiGiveCucumberPatch {
    @SpirePatch(
            clz = EmptyDeckShuffleAction.class,
            method = "update"
    )
    public static class GiveCucumberPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void insert(){
            for(AbstractMonster monster: AbstractDungeon.getCurrRoom().monsters.monsters){
                if(!monster.isDeadOrEscaped()&&monster.hasPower(makeID("MutsumiGiveCucumberPower"))){
                    int canDraw = Settings.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
                    if (canDraw < 0) {
                        canDraw = 0;
                    }
                    int actualDraw = Math.min(getDrawCardActionCount(), canDraw);

                    int num = AbstractDungeon.player.drawPile.size();
                    int need = monster.getPower(makeID("MutsumiGiveCucumberPower")).amount + actualDraw - num;

                    if (need > 0) {
                        for (int i = 0; i < need; i++) {
                            AbstractDungeon.actionManager.addToTop(new QuickMakeTempCardInDrawPileAction(
                                    new Cucumber(), 1, true, true
                            ));
                        }
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(EmptyDeckShuffleAction.class, "isDone");

                int[] lines = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
                return new int[]{lines[lines.length-1]};
            }
        }

        public static int getDrawCardActionCount() {
            int count = 0;
            for (AbstractGameAction action : AbstractDungeon.actionManager.actions) {
                if (action instanceof DrawCardAction) {
                    count=count+action.amount;
                }
            }
            return count;
        }
    }
}
