package tomorimod.monsters.mutsumi;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;
import tomorimod.cards.special.Cucumber;

import java.util.ArrayList;
import java.util.Iterator;

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
                    if(num<MutsumiMonster.CUCUMBER){
                        for(int i=0;i<MutsumiMonster.CUCUMBER-num+getDrawCardActionCount();i++){
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
