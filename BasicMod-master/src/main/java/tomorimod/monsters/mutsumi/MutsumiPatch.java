package tomorimod.monsters.mutsumi;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CNCardTextColors;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import tomorimod.cards.notshow.Cucumber;

import java.util.ArrayList;

import static tomorimod.TomoriMod.makeID;

public class MutsumiPatch {
    @SpirePatch(
            clz = EmptyDeckShuffleAction.class,
            method = "update"
    )
    public static class MutsumiGiveCucumberPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void insert(){
            for(AbstractMonster monster: AbstractDungeon.getCurrRoom().monsters.monsters){
                if(!monster.isDeadOrEscaped()&&monster.hasPower(makeID("MutsumiGiveCucumberPower"))){
                    int num=AbstractDungeon.player.drawPile.group.size();
                    if(num<monster.getPower(makeID("MutsumiGiveCucumberPower")).amount){
                        for(int i=0;i<monster.getPower(makeID("MutsumiGiveCucumberPower")).amount-num+getDrawCardActionCount();i++){
                            AbstractDungeon.actionManager.addToBottom(new QuickMakeTempCardInDrawPileAction
                                    (new Cucumber(), 1, true, true));
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
