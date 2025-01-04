package tomorimod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class GameActionManagerPatch {
    @SpireInsertPatch(
        rloc = 247
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

}