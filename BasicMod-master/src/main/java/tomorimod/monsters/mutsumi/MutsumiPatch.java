package tomorimod.monsters.mutsumi;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.notshow.Cucumber;

import static tomorimod.TomoriMod.makeID;

public class MutsumiPatch {
    @SpirePatch(
            clz = EmptyDeckShuffleAction.class,
            method = "update"
    )
    public static class MutsumiGiveCucumberPatch {
        @SpireInsertPatch(
                rloc=20
        )
        public static void insert(){
            for(AbstractMonster monster: AbstractDungeon.getCurrRoom().monsters.monsters){
                if(!monster.isDeadOrEscaped()&&monster.hasPower(makeID("MutsumiGiveCucumberPower"))){
                    int num=AbstractDungeon.player.drawPile.group.size();
                    if(num<monster.getPower(makeID("MutsumiGiveCucumberPower")).amount){
                        for(int i=0;i<monster.getPower(makeID("MutsumiGiveCucumberPower")).amount-num+getDrawCardActionCount();i++){
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction
                                    (new Cucumber(), 1, true, true));
                        }
                    }
                }
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
