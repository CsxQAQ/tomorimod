package tomorimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class GameActionManagerPatch {
    @SpireInsertPatch(
        locator = Locator.class
    )
    public static SpireReturn insert(GameActionManager __instance){
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                if (!AbstractDungeon.player.hasPower("Barricade") && !AbstractDungeon.player.hasPower("Blur")) {
                    if (!AbstractDungeon.player.hasRelic("Calipers")) {
                        AbstractDungeon.player.loseBlock();
                    } else {
                        AbstractDungeon.player.loseBlock(15);
                    }
                }
                isDone=true;
            }
        });

        if (!(AbstractDungeon.getCurrRoom()).isBattleOver) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DrawCardAction(null, AbstractDungeon.player.gameHandSize, true));
            AbstractDungeon.player.applyStartOfTurnPostDrawRelics();
            AbstractDungeon.player.applyStartOfTurnPostDrawPowers();
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new EnableEndTurnButtonAction());
        }
        return SpireReturn.Return();
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(GameActionManager.class, "damageReceivedThisTurn");

            int[] lines = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            return new int[]{lines[lines.length-1] + 1};
        }
    }

}