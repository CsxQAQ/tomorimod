package tomorimod.monsters.sakishadow;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.notshow.sakishadow.WelcomeToAveMujica;

public class WelcomeToAveMujicaPatch {

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="preBattlePrep"
    )
    public static class AbstractPlayerPreBattlePrepPatch{
        @SpirePostfixPatch
        public static void postfix(AbstractPlayer __instance){
            for(AbstractMonster monster:AbstractDungeon.getCurrRoom().monsters.monsters){
                if(monster instanceof SakiShadowMonster){
                    __instance.drawPile.clear();
                }
            }
        }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="applyPreCombatLogic"
    )
    public static class ApplyPreCombatLogicPatch{
        @SpirePrefixPatch
        public static SpireReturn prefix(AbstractPlayer __instance){
            for(AbstractMonster monster:AbstractDungeon.getCurrRoom().monsters.monsters){
                if(monster instanceof SakiShadowMonster){
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="applyStartOfCombatLogic"
    )
    public static class ApplyStartOfCombatLogic{
        @SpirePrefixPatch
        public static SpireReturn prefix(AbstractPlayer __instance){
            for(AbstractMonster monster:AbstractDungeon.getCurrRoom().monsters.monsters){
                if(monster instanceof SakiShadowMonster){
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="applyStartOfCombatPreDrawLogic"
    )
    public static class ApplyStartOfCombatPreDrawLogic{
        @SpirePrefixPatch
        public static SpireReturn prefix(AbstractPlayer __instance){
            for(AbstractMonster monster:AbstractDungeon.getCurrRoom().monsters.monsters){
                if(monster instanceof SakiShadowMonster){
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }



}


