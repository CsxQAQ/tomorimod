package tomorimod.monsters.sakishadow;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.sun.crypto.provider.HmacMD5KeyGenerator;
import tomorimod.cards.notshow.sakishadow.WelcomeToAveMujica;
import tomorimod.util.MonsterUtils;

import static tomorimod.TomoriMod.makeID;

public class WelcomeToAveMujicaPatch {

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="preBattlePrep"
    )
    public static class AbstractPlayerPreBattlePrepPatch{
        @SpirePostfixPatch
        public static void postfix(AbstractPlayer __instance){
            if(MonsterUtils.getMonster(makeID("SakiShadowMonster"))!=null){
                __instance.drawPile.clear();
                AbstractDungeon.actionManager.addToBottom(
                        new MakeTempCardInHandAction(new WelcomeToAveMujica()));
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
            if(MonsterUtils.getMonster(makeID("SakiShadowMonster"))!=null){
                return SpireReturn.Return();
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
            if(MonsterUtils.getMonster(makeID("SakiShadowMonster"))!=null){
                return SpireReturn.Return();
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
            if(MonsterUtils.getMonster(makeID("SakiShadowMonster"))!=null){
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }



}


