package tomorimod.monsters.sakishadow;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CNCardTextColors;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import tomorimod.util.MonsterUtils;

import java.util.ArrayList;

import static tomorimod.TomoriMod.makeID;

public class SakiShadowRightPatch {
    @SpirePatch(
            clz= AbstractPlayer.class,
            method="damage",
            paramtypez = {
                    DamageInfo.class
            }
    )
    public static class DamagePatch{
        @SpirePostfixPatch
        public static void postfix(AbstractPlayer __instance,DamageInfo damageInfo){
            applyAfterDamage();
        }

    }

    public static void applyAfterDamage(){
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            AbstractPower power= MonsterUtils.getPower(makeID("SakiShadowMonster"),makeID("SakiShadowRightPower"));
            if(power instanceof SakiShadowRightPower){
                ((SakiShadowRightPower) power).afterDamage();
            }
        }
    }


//    @SpirePatch(
//            clz= AbstractPlayer.class,
//            method="damage",
//            paramtypez = {
//                    DamageInfo.class
//            }
//    )
//    public static class DamagePatch{
//        @SpireInsertPatch(
//                locator = Locator.class
//        )
//        public static void insert(AbstractPlayer __instance,DamageInfo damageInfo){
//            if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
//                AbstractPower power= MonsterUtils.getPower(makeID("SakiShadowMonster"),makeID("SakiShadowRightPower"));
//                if(power instanceof SakiShadowRightPower){
//                    ((SakiShadowRightPower) power).afterDamage();
//                }
//            }
//        }
//
//
//        private static class Locator extends SpireInsertLocator {
//            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
//                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "currentHealth");
//                int[] lines = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
//                return new int[]{lines[1]+1};
//            }
//        }
//    }
//
//    @SpirePatch(
//            clz= AbstractPlayer.class,
//            method="damage",
//            paramtypez = {
//                    DamageInfo.class
//            }
//    )
//    public static class DamagePatch2{
//        @SpireInsertPatch(
//                locator = Locator.class,
//                localvars={"damageAmount"}
//
//        )
//        public static void insert(AbstractPlayer __instance,DamageInfo damageInfo, @ByRef int damageAmount[]){
//            if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
//                AbstractPower power= MonsterUtils.getPower(makeID("SakiShadowMonster"),makeID("SakiShadowRightPower"));
//                if(power!=null&&power.amount>0){
//                    damageAmount[0]=Math.max(power.amount,damageAmount[0]);
//                }
//            }
//        }
//
//
//        private static class Locator extends SpireInsertLocator {
//            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
//                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "lastDamageTaken");
//                int[] lines = LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
//                return new int[]{lines[0]};
//            }
//        }
//    }



}


